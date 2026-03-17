package com.homestyle.demo.service;

import com.homestyle.demo.entity.Carrello;
import com.homestyle.demo.entity.CarrelloProdotto;
import com.homestyle.demo.entity.Prodotto;
import com.homestyle.demo.entity.Utente;
import com.homestyle.demo.repository.CarrelloProdottoRepository;
import com.homestyle.demo.repository.CarrelloRepository;
import com.homestyle.demo.repository.ProdottoRepository;
import com.homestyle.demo.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.ControlliUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarrelloService {

    private final CarrelloRepository carrelloRepo;
    private final UtenteRepository utenteRepo;
    private final ProdottoRepository prodottoRepo;

    @Transactional(readOnly = true)
    public Carrello trovaCarrelloPerId(UUID idCarello){
        log.info("Inizio a cercare il carello con id: {}", idCarello);

        ControlliUtils.controlloIdValido(idCarello, "carello");

        Carrello carrelloTrovato = carrelloRepo.findById(idCarello).orElseThrow(
                () -> {
                    log.error("Carello con id: {}, non trovato", idCarello);
                    return new IllegalArgumentException("Carello non trovato");
                }
        );

        log.info("Trovato carello con id: {}", carrelloTrovato.getId());
        return carrelloTrovato;
    }

    public Carrello trovaCarelloPerUtente(UUID idUtente){
        log.info("Inizio a cercare il carello dell'utente con id: {}", idUtente);

        ControlliUtils.controlloIdValido(idUtente, "utente");

        Carrello carrelloTrovato = carrelloRepo.findByUtente_IdAndStato(idUtente, Carrello.Stato.ATTIVO).orElseThrow(
                () -> {
                    log.error("Carello non trovato, per utente con id: {}", idUtente);
                    return new IllegalArgumentException("Carello dell'utente non trovato");
                }
        );

        log.info("Carello trovato con id: {}, dell'utente con id: {}", carrelloTrovato.getId(), idUtente);
        return carrelloTrovato;
    }


    @Transactional
    public Carrello aggiuntaProdottiCarrello(UUID idUtente, List<CarrelloProdotto> nuoviProdotti) {
        log.info("Aggiunta prodotti al carrello per utente id: {}", idUtente);

        ControlliUtils.controlloIdValido(idUtente, "utente");

        // Validazione lista
        if (nuoviProdotti == null || nuoviProdotti.isEmpty()) {
            log.error("Lista prodotti vuota per utente id: {}", idUtente);
            throw new IllegalArgumentException("La lista dei prodotti non può essere vuota");
        }

        for (CarrelloProdotto carrelloProdotto : nuoviProdotti) {
            ControlliUtils.controlloEsistenzaCampo(carrelloProdotto, "carrello prodotto");

            Prodotto prodotto = prodottoRepo.findById(carrelloProdotto.getProdotto().getId())
                    .orElseThrow(() -> {
                        log.error("Prodotto con id: {} non trovato", carrelloProdotto.getProdotto().getId());
                        return new IllegalArgumentException("Prodotto non trovato");
                    });

            if (carrelloProdotto.getQuantita() <= 0) {
                log.error("Quantità non valida: {}", carrelloProdotto.getQuantita());
                throw new IllegalArgumentException("La quantità deve essere maggiore di zero");
            }

            if (carrelloProdotto.getQuantita() > prodotto.getQuantitaDisponibile()) {
                log.error("Quantità richiesta {} > disponibile {}", carrelloProdotto.getQuantita(), prodotto.getQuantitaDisponibile());
                throw new IllegalArgumentException("Quantità richiesta superiore alla disponibilità");
            }
        }

        // Cerca carrello attivo, se non esiste lo crea implicitamente
        Carrello carrello = carrelloRepo
                .findByUtente_IdAndStato(idUtente, Carrello.Stato.ATTIVO)
                .orElseGet(() -> {
                    log.info("Nessun carrello attivo, ne creo uno per utente id: {}", idUtente);
                    return creaCarrello(idUtente); // metodo privato
                });

        aggiungiProdottiAlCarrello(carrello, nuoviProdotti);
        log.info("Prodotti aggiornati nel carrello id: {}", carrello.getId());

        Carrello carrelloSalvato = carrelloRepo.save(carrello);
        log.info("Carrello id: {} salvato con {} prodotti", carrelloSalvato.getId(), carrelloSalvato.getProdotti().size());

        return carrelloSalvato;
    }

     Carrello creaCarrello(UUID idUtente) {
        log.info("Creazione carrello per utente id: {}", idUtente);

        // 1. Utente esiste?
        Utente utente = utenteRepo.findById(idUtente)
                .orElseThrow(() -> {
                    log.error("Utente id: {} non trovato", idUtente);
                    return new IllegalArgumentException("Utente non trovato");
                });

        // 2. Ha già un carrello attivo?
        if (carrelloRepo.existsByUtente_IdAndStato(idUtente, Carrello.Stato.ATTIVO)) {
            log.error("Utente id: {} ha già un carrello attivo", idUtente);
            throw new IllegalStateException("Esiste già un carrello attivo per questo utente");
        }

        // 3. Crea e salva
        Carrello nuovoCarrello = new Carrello();
        nuovoCarrello.setUtente(utente);
        nuovoCarrello.setStato(Carrello.Stato.ATTIVO);

        Carrello salvato = carrelloRepo.save(nuovoCarrello);
        log.info("Carrello id: {} creato per utente id: {}", salvato.getId(), idUtente);

        return salvato;
    }



    @Transactional
    public void svuotaCarrello(UUID idCarrello) {
        log.info("Inizio a svuotare il carrello con id: {}", idCarrello);
        // carrello.getProdotti().clear()
        ControlliUtils.controlloIdValido(idCarrello, "carrello");

        Carrello carrelloTrovato = carrelloRepo.findById(idCarrello).orElseThrow(
                () -> {
                    log.error("Carello non trovato con id: {}", idCarrello);
                    return new IllegalArgumentException("Carello non trovato");
                }
        );
        // orphanRemoval fa il resto, nessuna query manuale necessaria
        carrelloTrovato.getProdotti().clear();
    }

    @Transactional
    public void eliminaCarello(UUID idCarello){
        log.info("Inizio a eliminare il carello con id: {}", idCarello);

        ControlliUtils.controlloIdValido(idCarello, "carello");
        if (!carrelloRepo.existsById(idCarello)) {
            log.error("Carello con id: {}, non trovato", idCarello);
            throw new IllegalArgumentException("Carello non trovato");
        }

        log.info("Carello con id: {}, trovato", idCarello);
        carrelloRepo.deleteById(idCarello);
        log.info("Carello con id: {}, eliminato con successo", idCarello);
    }

    private void aggiungiProdottiAlCarrello(Carrello carrello, List<CarrelloProdotto> nuoviOrdini) {
        // Costruiamo un Set degli id già presenti → lookup O(1) invece di O(n)
        Set<UUID> prodottiEsistenti = carrello.getProdotti()
                .stream()
                .map(cp -> cp.getProdotto().getId())
                .collect(Collectors.toSet());

        for (CarrelloProdotto nuovoOrdine : nuoviOrdini) {
            UUID idNuovoProdotto = nuovoOrdine.getProdotto().getId();

            if (prodottiEsistenti.contains(idNuovoProdotto)) {
                // Prodotto già presente → aggiorna solo la quantità
                carrello.getProdotti().stream()
                        .filter(cp -> cp.getProdotto().getId().equals(idNuovoProdotto))
                        .findFirst()
                        .ifPresent(cp -> cp.setQuantita(cp.getQuantita() + nuovoOrdine.getQuantita()));
            } else {
                // Prodotto nuovo → collegalo al carrello e aggiungilo
                nuovoOrdine.setCarrello(carrello);
                carrello.getProdotti().add(nuovoOrdine);
            }
        }
    }

}
