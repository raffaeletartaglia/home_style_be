package com.homestyle.demo.service;

import com.homestyle.demo.ErroreCodice;
import com.homestyle.demo.entity.Carrello;
import com.homestyle.demo.entity.CarrelloProdotto;
import com.homestyle.demo.repository.CarrelloProdottoRepository;
import com.homestyle.demo.repository.CarrelloRepository;
import exception.EntitaNonTrovataException;
import exception.StockInsufficienteException;
import exception.ValoreNonValidoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.ControlliUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarrelloProdottoService {

    private final CarrelloProdottoRepository carrelloProdottoRepo;
    private final CarrelloRepository carrelloRepo;

    @Transactional(readOnly = true)
    public CarrelloProdotto trovaCarrelloProdottoPerId(UUID idCarrelloProdotto) {
        log.info("Inizio a cercare il carrello prodotto con id: {}", idCarrelloProdotto);

        ControlliUtils.controlloIdValido(idCarrelloProdotto, "carrello prodotto");

        CarrelloProdotto carrelloProdottoTrovato = carrelloProdottoRepo.findById(idCarrelloProdotto).orElseThrow(
                () -> {
                    log.error("Carrello prodotto con id: {} non trovato", idCarrelloProdotto);
                    return new EntitaNonTrovataException(ErroreCodice.CARRELLO_PRODOTTO_NON_TROVATO);
                }
        );
        log.info("Carrello prodotto con id: {} trovato", idCarrelloProdotto);

        return carrelloProdottoTrovato;
    }//trovaCarrelloProdottoPerId

    @Transactional(readOnly = true)
    public List<CarrelloProdotto> trovaProdottiDelCarrello(UUID idCarrello) {
        log.info("Inizio a cercare i prodotti del carrello con id: {}", idCarrello);

        ControlliUtils.controlloIdValido(idCarrello, "carrello");

        if (!carrelloRepo.existsById(idCarrello)) {
            log.error("Carrello con id: {} non trovato", idCarrello);
            throw new EntitaNonTrovataException(ErroreCodice.CARRELLO_NON_TROVATO);
        }

        List<CarrelloProdotto> carrelloProdottoTrovato = carrelloProdottoRepo.findByCarrello_Id(idCarrello);
        if (carrelloProdottoTrovato.isEmpty()) {
            log.warn("Carrello prodotto di carrello con id: {} vuoto", idCarrello);
            // se preferisci, puoi NON lanciare eccezione e restituire lista vuota
            throw new ValoreNonValidoException(
                    ErroreCodice.CARRELLO_VUOTO,
                    "Carrello prodotto vuoto"
            );
        }
        log.info("Carrello prodotto di carrello con id: {} trovato", idCarrello);

        return carrelloProdottoTrovato;
    }//trovaProdottiDelCarrello

    @Transactional
    public CarrelloProdotto aggiornaQuantita(UUID idCarrelloProdotto, Integer nuovaQuantita) {
        log.info("Inizio a modificare la quantità del carrello prodotto con id: {}", idCarrelloProdotto);
        ControlliUtils.controlloIdValido(idCarrelloProdotto, "carrello prodotto");

        CarrelloProdotto carrelloProdotto = carrelloProdottoRepo.findById(idCarrelloProdotto).orElseThrow(
                () -> {
                    log.error("Carrello prodotto con id: {} non trovato", idCarrelloProdotto);
                    return new EntitaNonTrovataException(ErroreCodice.CARRELLO_PRODOTTO_NON_TROVATO);
                }
        );
        log.info("Carrello prodotto con id: {} trovato", idCarrelloProdotto);

        if (nuovaQuantita == null || nuovaQuantita < 0) {
            log.error("Quantità inserita non valida: {}", nuovaQuantita);
            throw new ValoreNonValidoException(
                    ErroreCodice.CARRELLO_PRODOTTO_QUANTITA_NON_VALIDA,
                    "Quantità inserita non valida"
            );
        }

        if (nuovaQuantita == 0) {
            log.info("Quantità = 0, rimuovo prodotto dal carrello");
            carrelloProdotto.getCarrello().getProdotti().remove(carrelloProdotto);
            carrelloProdottoRepo.delete(carrelloProdotto);
            return carrelloProdotto; // o null, in base a come vuoi gestire la risposta
        }

        if (nuovaQuantita > carrelloProdotto.getProdotto().getQuantitaDisponibile()) {
            log.error("Quantità richiesta: {}, superiore alla quantità disponibile: {}",
                    nuovaQuantita, carrelloProdotto.getProdotto().getQuantitaDisponibile());
            throw new StockInsufficienteException();
        }

        carrelloProdotto.setQuantita(nuovaQuantita);
        CarrelloProdotto salvato = carrelloProdottoRepo.save(carrelloProdotto);
        log.info("Quantità aggiornata per carrello prodotto id: {}", salvato.getId());
        return salvato;
    }//aggiornaQuantita

    @Transactional
    public void rimuoviProdottoDalCarrello(UUID idCarrelloProdotto) {
        log.info("Rimozione prodotto dal carrello, CarrelloProdotto id: {}", idCarrelloProdotto);

        ControlliUtils.controlloIdValido(idCarrelloProdotto, "carrello prodotto");

        CarrelloProdotto carrelloProdotto = carrelloProdottoRepo.findById(idCarrelloProdotto)
                .orElseThrow(() -> {
                    log.error("CarrelloProdotto id: {} non trovato", idCarrelloProdotto);
                    return new EntitaNonTrovataException(ErroreCodice.CARRELLO_PRODOTTO_NON_TROVATO);
                });

        log.info("CarrelloProdotto trovato, lo rimuovo dalla lista del carrello");
        carrelloProdotto.getCarrello().getProdotti().remove(carrelloProdotto);
        carrelloProdottoRepo.delete(carrelloProdotto);
        log.info("Prodotto rimosso con successo dal carrello id: {}", carrelloProdotto.getCarrello().getId());
    }//rimuoviProdottoDalCarrello

    @Transactional(readOnly = true)
    public BigDecimal calcolaTotaleCarrello(UUID idCarrello) {
        log.info("Calcolo totale carrello id: {}", idCarrello);

        ControlliUtils.controlloIdValido(idCarrello, "carrello");

        Carrello carrello = carrelloRepo.findById(idCarrello)
                .orElseThrow(() -> {
                    log.error("Carrello id: {} non trovato", idCarrello);
                    return new EntitaNonTrovataException(ErroreCodice.CARRELLO_NON_TROVATO);
                });

        BigDecimal totale = carrello.getProdotti()
                .stream()
                .map(cp -> cp.getProdotto().getPrezzo()
                        .multiply(BigDecimal.valueOf(cp.getQuantita())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        log.info("Totale carrello id: {} = {}", idCarrello, totale);
        return totale;
    }//calcolaTotaleCarrello
}//CarrelloProdottoService
