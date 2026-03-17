package com.homestyle.demo.service;

import com.homestyle.demo.entity.Ordine;
import com.homestyle.demo.entity.Spedizione;
import com.homestyle.demo.repository.OrdineRepository;
import com.homestyle.demo.repository.SpedizioneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.ControlliUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpedizioneService {

    private final SpedizioneRepository spedizioneRepository;
    private final OrdineRepository ordineRepository;

    @Transactional(readOnly = true)
    public Spedizione trovaSpedizionePerId(UUID idSpedizione) {
        log.info("Cerco spedizione con id: {}", idSpedizione);
        ControlliUtils.controlloIdValido(idSpedizione, "spedizione");

        return spedizioneRepository.findById(idSpedizione).orElseThrow(
                () -> {
                    log.error("Spedizione con id: {} non trovata", idSpedizione);
                    return new IllegalArgumentException("Spedizione non trovata");
                }
        );
    }

    @Transactional(readOnly = true)
    public List<Spedizione> trovaSpedizioniPerOrdine(UUID idOrdine) {
        log.info("Cerco spedizioni per ordine id: {}", idOrdine);
        ControlliUtils.controlloIdValido(idOrdine, "ordine");

        List<Spedizione> spedizioni =
                spedizioneRepository.findByOrdine_Id(idOrdine);
        log.info("Trovate {} spedizioni per ordine id: {}", spedizioni.size(), idOrdine);
        return spedizioni;
    }


    @Transactional
    public Spedizione creaSpedizione(UUID idOrdine, String corriere, String codiceTracking) {
        log.info("Creazione spedizione per ordine id: {}", idOrdine);
        ControlliUtils.controlloIdValido(idOrdine, "ordine");

        Ordine ordine = ordineRepository.findById(idOrdine).orElseThrow(
                () -> {
                    log.error("Ordine con id: {} non trovato", idOrdine);
                    return new IllegalArgumentException("Ordine non trovato");
                }
        );

        if (ordine.getStatoOrdine() != Ordine.StatoOrdine.IN_ELABORAZIONE) {
            log.error("Impossibile creare spedizione per ordine id: {} con stato: {}",
                    idOrdine, ordine.getStatoOrdine());
            throw new IllegalStateException("La spedizione può essere creata solo per ordini IN_ELABORAZIONE");
        }

        Spedizione spedizione = new Spedizione();
        spedizione.setOrdine(ordine);
        spedizione.setCorriere(corriere);
        spedizione.setCodiceTracking(codiceTracking);
        spedizione.setStatoSpedizione(Spedizione.StatoSpedizione.PREPARAZIONE);

        Spedizione salvata = spedizioneRepository.save(spedizione);
        log.info("Spedizione creata con id: {} per ordine id: {}", salvata.getId(), idOrdine);

        log.info("Modifica stato dell'ordine da: {} a nuovo stato {}", ordine.getStatoOrdine(), Ordine.StatoOrdine.SPEDITO);
        ordine.setStatoOrdine(Ordine.StatoOrdine.SPEDITO);
        ordineRepository.save(ordine);
        log.info("Spedizione salvata con successo e stato ordine modificato");
        return salvata;
    }

    @Transactional
    public Spedizione aggiornaStatoSpedizione(UUID idSpedizione,
                                              Spedizione.StatoSpedizione nuovoStato) {
        log.info("Aggiornamento stato spedizione id: {} -> {}", idSpedizione, nuovoStato);
        ControlliUtils.controlloIdValido(idSpedizione, "spedizione");

        Spedizione spedizione = spedizioneRepository.findById(idSpedizione).orElseThrow(
                () -> {
                    log.error("Spedizione con id: {} non trovata", idSpedizione);
                    return new IllegalArgumentException("Spedizione non trovata");
                }
        );

        Spedizione.StatoSpedizione statoAttuale = spedizione.getStatoSpedizione();
        log.info("Stato attuale spedizione id {}: {}", idSpedizione, statoAttuale);

        // Gestione annullamento: consentito SOLO se si è in PREPARAZIONE
        if (nuovoStato == Spedizione.StatoSpedizione.ANNULLATO) {
            if (statoAttuale != Spedizione.StatoSpedizione.PREPARAZIONE) {
                log.error("Impossibile annullare spedizione id: {} con stato attuale: {}",
                        idSpedizione, statoAttuale);
                throw new IllegalStateException(
                        "La spedizione può essere annullata solo se è in stato PREPARAZIONE"
                );
            }
            spedizione.setStatoSpedizione(Spedizione.StatoSpedizione.ANNULLATO);
            Spedizione annullata = spedizioneRepository.save(spedizione);
            log.info("Spedizione id: {} annullata con successo", annullata.getId());
            return annullata;
        }

        // Transizioni semplici consentite (puoi raffinarle con uno switch se vuoi):
        // PREPARAZIONE -> SPEDITO -> IN_TRANSITO -> CONSEGNATO
        // (e nessun cambio possibile da ANNULLATO o CONSEGNATO)
        if (statoAttuale == Spedizione.StatoSpedizione.ANNULLATO
                || statoAttuale == Spedizione.StatoSpedizione.CONSEGNATO) {
            log.error("Impossibile cambiare lo stato di una spedizione in stato: {}", statoAttuale);
            throw new IllegalStateException(
                    "Impossibile modificare una spedizione già " + statoAttuale
            );
        }

        spedizione.setStatoSpedizione(nuovoStato);

        if (nuovoStato == Spedizione.StatoSpedizione.SPEDITO) {
            spedizione.setDataSpedizione(LocalDateTime.now());
        }
        if (nuovoStato == Spedizione.StatoSpedizione.CONSEGNATO) {
            spedizione.setDataConsegnaEffettiva(LocalDateTime.now());
        }

        Spedizione salvata = spedizioneRepository.save(spedizione);
        log.info("Spedizione id: {} aggiornata a stato: {}", salvata.getId(), salvata.getStatoSpedizione());
        return salvata;
    }

}

