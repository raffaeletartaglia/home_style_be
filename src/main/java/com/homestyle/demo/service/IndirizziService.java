package com.homestyle.demo.service;

import com.homestyle.demo.entity.Indirizzo;
import com.homestyle.demo.entity.Utente;
import com.homestyle.demo.repository.IndirizzoRepository;
import com.homestyle.demo.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import utils.ControlliUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndirizziService {

    private final IndirizzoRepository inidirizziRepo;
    private final UtenteRepository utenteRepository;

    /**
     * Trova un singolo indirizzo in base al suo id
     * @param idIndirizzo
     * @return
     */
    Indirizzo trovareIndirizzoDallId(UUID idIndirizzo) {
        log.info("Cerco l'indirizzo con id: {}", idIndirizzo);
        ControlliUtils.controlloIdValido(idIndirizzo, "indirizzo");

        Indirizzo indirizzo = inidirizziRepo.findById(idIndirizzo).orElseThrow(
                () -> {
                    log.error("Indirizzo non trovato con id: {}", idIndirizzo);
                    return new IllegalArgumentException("Indirizzo non trovato con id: " + idIndirizzo);
                }
        );

        log.info("Indirizzo trovato");
        return indirizzo;
    }

    /**
     * Trova tutti gli indirizzi associati a un utente in base all'id dell'utente
     * @param idUtente
     * @return
     */
    List<Indirizzo> trovaTuttiGliIndirizziDallIdUtente(UUID idUtente) {
        log.info("Cerco gli indirizzi in base all'id utente: {}", idUtente);

        ControlliUtils.controlloIdValido(idUtente, "utente");
        Utente utente = utenteRepository.findById(idUtente).orElseThrow(
                () -> {
                    log.error("Utente con id: {}, non trovato", idUtente);
                    return new IllegalArgumentException("Utente non trovato con id: " + idUtente);
                }
        );

        List<Indirizzo> indirizziUtente = inidirizziRepo.findByUtente(utente);

        log.info("Indirizzi dell'utente con id: {}, trovati", idUtente);
        return indirizziUtente;
    }

    /**
     * Trova tutti gli indirizzi di un utente filtrati per tipo
     * @param idUtente
     * @param tipo
     * @return
     */
    List<Indirizzo> trovareIndirizziPerTipo(UUID idUtente, Indirizzo.Tipo tipo) {
        log.info("Cerco gli indirizzi di tipo: {} per l'utente con id: {}", tipo, idUtente);

        ControlliUtils.controlloIdValido(idUtente, "utente");

        ControlliUtils.controlloTipoIndirizzo(tipo != null ? tipo.toString() : null);

        Utente utenteTrovato = utenteRepository.findById(idUtente).orElseThrow(
                () -> {
                    log.error("Utente con id: {}, non trovato", idUtente);
                    return new IllegalArgumentException("Utente non trovato con id: " + idUtente);
                }
        );

        List<Indirizzo> indirizzi = inidirizziRepo.findByUtenteAndTipo(utenteTrovato, tipo);

        log.info("Trovati {} indirizzi di tipo: {} per l'utente con id: {}", indirizzi.size(), tipo, idUtente);
        return indirizzi;
    }

    /**
     * Trova tutti gli indirizzi di tutti gli utenti
     */
    List<Indirizzo> prendiTuttiGliIndirizzi() {
        log.info("Cerco tutti gli indirizzi");

        List<Indirizzo> indirizzi = inidirizziRepo.findAll();

        log.info("Tutti gli indirizzi sono stati trovati");
        return indirizzi;
    }

    /**
     * Aggiunge un singolo indirizzo a un utente
     */
    @Transactional
    Indirizzo aggiungiUnIndirizzo(UUID idUtente, Indirizzo indirizzo) {
        log.info("Inizio a creare il nuovo indirizzo dell'utente con id: {}", idUtente);

        ControlliUtils.controlloIdValido(idUtente, "utente");

        Utente utenteTrovato = utenteRepository.findById(idUtente).orElseThrow(
                () -> {
                    log.error("Utente con id: {}, non trovato", idUtente);
                    return new IllegalArgumentException("Utente non trovato con id: " + idUtente);
                }
        );

        log.info("Utente con id: {}, trovato", idUtente);
        ControlliUtils.controlloEsistenzaCampo(indirizzo, "Il nuovo indirizzo che vuoi aggiungere è null");
        ControlliUtils.controlloTipoIndirizzo(indirizzo.getTipo().toString());

        Indirizzo nuovoIndirizzo = generaIndirizzo(indirizzo, utenteTrovato);

        log.info("Nuovo indirizzo creato");
        Indirizzo indirizzoSalvato = inidirizziRepo.save(nuovoIndirizzo);
        log.info("Nuovo indirizzo salvato con id: {}", indirizzoSalvato.getId());
        return indirizzoSalvato;
    }

    /**
     * Aggiunge una lista di indirizzi a un utente
     */
    @Transactional
    List<Indirizzo> aggiungiIndirizzi(UUID idUtente, List<Indirizzo> indirizzi) {
        log.info("Inizio a creare i nuovi indirizzi dell'utente con id: {}", idUtente);

        ControlliUtils.controlloIdValido(idUtente, "utente");

        Utente utenteTrovato = utenteRepository.findById(idUtente).orElseThrow(
                () -> {
                    log.error("Utente con id: {}, non trovato", idUtente);
                    return new IllegalArgumentException("Utente non trovato con id: " + idUtente);
                }
        );

        log.info("Utente con id: {}, trovato", idUtente);
        List<Indirizzo> nuoviIndirizzi = new ArrayList<>();
        for (Indirizzo indirizzo : indirizzi) {
            ControlliUtils.controlloEsistenzaCampo(indirizzo, "Il nuovo indirizzo che vuoi aggiungere è null");
            ControlliUtils.controlloTipoIndirizzo(indirizzo.getTipo().toString());
            nuoviIndirizzi.add(generaIndirizzo(indirizzo, utenteTrovato));
        }

        log.info("Lista di nuovi indirizzi creata");
        List<Indirizzo> indirizziSalvati = inidirizziRepo.saveAll(nuoviIndirizzi);
        log.info("Nuovi indirizzi salvati");
        return indirizziSalvati;
    }

    /**
     * Modifica un indirizzo esistente
     */
    @Transactional
    Indirizzo modificaIndirizzo(UUID idIndirizzo, UUID idUtente, Indirizzo indirizzoModificato) {
        log.info("Inizio a modificare l'indirizzo con id: {}, dell'utente con id: {}", idIndirizzo, idUtente);

        ControlliUtils.controlloIdValido(idUtente, "utente");
        ControlliUtils.controlloIdValido(idIndirizzo, "indirizzo");
        ControlliUtils.controlloEsistenzaCampo(indirizzoModificato, "L'indirizzo modificato è null");

        log.info("Cerco l'utente con id: {}", idUtente);
        Utente utenteTrovato = utenteRepository.findById(idUtente).orElseThrow(
                () -> {
                    log.error("Utente con id: {}, non trovato", idUtente);
                    return new IllegalArgumentException("Utente non trovato con id: " + idUtente);
                }
        );

        log.info("Cerco l'indirizzo con id: {}", idIndirizzo);
        Indirizzo indirizzoTrovato = inidirizziRepo.findById(idIndirizzo).orElseThrow(
                () -> {
                    log.error("Indirizzo con id: {}, non trovato", idIndirizzo);
                    return new IllegalArgumentException("Indirizzo non trovato con id: " + idIndirizzo);
                }
        );

        if (!indirizzoTrovato.getUtente().getId().equals(utenteTrovato.getId())) {
            log.error("L'indirizzo con id: {} non appartiene all'utente con id: {}", idIndirizzo, idUtente);
            throw new IllegalArgumentException("L'indirizzo non appartiene all'utente specificato");
        }

        Indirizzo nuovoIndirizzo = generaIndirizzo(indirizzoModificato, utenteTrovato);
        nuovoIndirizzo.setId(indirizzoTrovato.getId());
        log.info("Nuovo indirizzo generato");

        Indirizzo indirizzoSalvato = inidirizziRepo.save(nuovoIndirizzo);
        log.info("Indirizzo con id: {}, modificato correttamente", indirizzoSalvato.getId());
        return indirizzoSalvato;
    }

    /**
     * Elimina un singolo indirizzo in base all'id
     */
    @Transactional
    void eliminaIndirizzo(UUID idIndirizzo) {
        log.info("Inizio a eliminare l'indirizzo con id: {}", idIndirizzo);

        ControlliUtils.controlloIdValido(idIndirizzo, "indirizzo");

        if (!inidirizziRepo.existsById(idIndirizzo)) {
            log.error("Indirizzo con id: {}, non trovato", idIndirizzo);
            throw new IllegalArgumentException("Indirizzo non trovato con id: " + idIndirizzo);
        }

        inidirizziRepo.deleteById(idIndirizzo);
        log.info("Indirizzo con id: {}, eliminato con successo", idIndirizzo);
    }

    /**
     * Elimina uno specifico indirizzo di un utente verificando l'ownership
     */
    @Transactional
    public void eliminaGliIndirizziDiUnUtente(UUID idUtente, UUID idIndirizzo) {
        log.info("Inizio a eliminare l'indirizzo con id: {}, dell'utente con id: {}", idIndirizzo, idUtente);

        ControlliUtils.controlloIdValido(idUtente, "utente");
        ControlliUtils.controlloIdValido(idIndirizzo, "indirizzo");

        Utente utenteTrovato = utenteRepository.findById(idUtente).orElseThrow(
                () -> {
                    log.error("Utente con id: {}, non trovato", idUtente);
                    return new IllegalArgumentException("Utente non trovato con id: " + idUtente);
                }
        );

        Indirizzo indirizzoTrovato = inidirizziRepo.findById(idIndirizzo).orElseThrow(
                () -> {
                    log.error("Indirizzo con id: {}, non trovato", idIndirizzo);
                    return new IllegalArgumentException("Indirizzo non trovato con id: " + idIndirizzo);
                }
        );

        if (!indirizzoTrovato.getUtente().getId().equals(utenteTrovato.getId())) {
            log.error("L'indirizzo con id: {} non appartiene all'utente con id: {}", idIndirizzo, idUtente);
            throw new IllegalArgumentException("L'indirizzo non appartiene all'utente specificato");
        }

        inidirizziRepo.deleteById(idIndirizzo);
        log.info("Indirizzo con id: {} dell'utente: {}, eliminato con successo", idIndirizzo, idUtente);
    }

    /**
     * Elimina tutti gli indirizzi di un utente
     */
    @Transactional
    public void eliminaTuttiGliIndirizziDiUnUtente(UUID idUtente) {
        log.info("Inizio a eliminare tutti gli indirizzi dell'utente con id: {}", idUtente);

        ControlliUtils.controlloIdValido(idUtente, "utente");

        if (!utenteRepository.existsById(idUtente)) {
            log.error("Utente con id: {}, non trovato", idUtente);
            throw new IllegalArgumentException("Utente non trovato con id: " + idUtente);
        }

        Utente utenteTrovato = utenteRepository.findById(idUtente)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato con id: " + idUtente));

        inidirizziRepo.deleteByUtente(utenteTrovato);
        log.info("Indirizzi dell'utente: {}, eliminati con successo", idUtente);
    }

    /**
     * Elimina tutti gli indirizzi presenti nel DB
     */
    @Transactional
    void eliminaTuttiGliIndirizzi() {
        log.info("Inizio a eliminare tutti gli indirizzi");
        inidirizziRepo.deleteAll();
        log.info("Tutti gli indirizzi sono stati eliminati");
    }

    /**
     * Metodo privato di utilità per generare un nuovo indirizzo
     * @param indirizzo
     * @param utente
     * @return
     */
    private Indirizzo generaIndirizzo(Indirizzo indirizzo, Utente utente) {
        log.info("Genero il nuovo indirizzo");
        Indirizzo nuovoIndirizzo = new Indirizzo();

        nuovoIndirizzo.setUtente(utente);
        nuovoIndirizzo.setNazione(indirizzo.getNazione());
        nuovoIndirizzo.setVia(indirizzo.getVia());
        nuovoIndirizzo.setNumeroCivico(indirizzo.getNumeroCivico());
        nuovoIndirizzo.setCitta(indirizzo.getCitta());
        nuovoIndirizzo.setProvincia(indirizzo.getProvincia());
        nuovoIndirizzo.setCap(indirizzo.getCap());
        nuovoIndirizzo.setTipo(indirizzo.getTipo());

        log.info("Nuovo indirizzo generato");
        return nuovoIndirizzo;
    }
}
