package com.homestyle.demo.service;

import com.homestyle.demo.ErroreCodice;
import com.homestyle.demo.entity.DettaglioOrdine;
import com.homestyle.demo.entity.Indirizzo;
import com.homestyle.demo.entity.Reso;
import com.homestyle.demo.repository.DettaglioOrdineRepository;
import com.homestyle.demo.repository.IndirizzoRepository;
import com.homestyle.demo.repository.ResoRepository;
import exception.EntitaNonTrovataException;
import exception.OperazioneNonConsentitaException;
import exception.ResoGiaEsistenteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.ControlliUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResoService {

    private final ResoRepository resoRepository;
    private final DettaglioOrdineRepository dettaglioOrdineRepository;
    private final IndirizzoRepository indirizzoRepository;

    @Transactional(readOnly = true)
    public Reso trovaResoPerId(UUID idReso) {
        log.info("Cerco reso con id: {}", idReso);
        ControlliUtils.controlloIdValido(idReso, "reso");

        return resoRepository.findById(idReso).orElseThrow(
                () -> {
                    log.error("Reso con id: {} non trovato", idReso);
                    return new EntitaNonTrovataException(ErroreCodice.RESO_NON_TROVATO);
                }
        );
    }

    @Transactional(readOnly = true)
    public Reso trovaResoPerDettaglioOrdine(UUID idDettaglioOrdine) {
        log.info("Cerco reso per dettaglio ordine id: {}", idDettaglioOrdine);
        ControlliUtils.controlloIdValido(idDettaglioOrdine, "dettaglio ordine");

        return resoRepository.findByDettaglioOrdine_Id(idDettaglioOrdine).orElseThrow(
                () -> {
                    log.error("Nessun reso trovato per dettaglio ordine id: {}", idDettaglioOrdine);
                    return new EntitaNonTrovataException(ErroreCodice.RESO_NON_TROVATO);
                }
        );
    }

    @Transactional
    public Reso creaReso(UUID idDettaglioOrdine,
                         UUID idIndirizzoReso,
                         LocalDate dataResoPrevista,
                         LocalTime oraRitiroReso,
                         String motivo) {

        log.info("Creazione reso per dettaglio ordine id: {}", idDettaglioOrdine);

        ControlliUtils.controlloIdValido(idDettaglioOrdine, "dettaglio ordine");
        ControlliUtils.controlloIdValido(idIndirizzoReso, "indirizzo");

        // 1. DettaglioOrdine esiste?
        DettaglioOrdine dettaglio = dettaglioOrdineRepository.findById(idDettaglioOrdine)
                .orElseThrow(() -> {
                    log.error("Dettaglio ordine id: {} non trovato", idDettaglioOrdine);
                    return new EntitaNonTrovataException(ErroreCodice.DETTAGLIO_ORDINE_NON_TROVATO);
                });

        // 2. Esiste già un reso per questo dettaglio? (1:1)
        if (dettaglio.getReso() != null) {
            log.error("Esiste già un reso per dettaglio ordine id: {}", idDettaglioOrdine);
            throw new ResoGiaEsistenteException();
        }

        // 3. Indirizzo reso
        Indirizzo indirizzo = indirizzoRepository.findById(idIndirizzoReso)
                .orElseThrow(() -> {
                    log.error("Indirizzo reso id: {} non trovato", idIndirizzoReso);
                    return new EntitaNonTrovataException(ErroreCodice.INDIRIZZO_NON_TROVATO);
                });

        // 4. Creazione reso
        Reso reso = new Reso();
        reso.setDettaglioOrdine(dettaglio);
        reso.setIndirizzoReso(indirizzo);
        reso.setDataResoPrevista(dataResoPrevista);
        reso.setOraRitiroReso(oraRitiroReso);
        reso.setMotivo(motivo);
        reso.setStatoReso(Reso.StatoReso.IN_PREPARAZIONE); // se vuoi uno stato iniziale

        Reso salvato = resoRepository.save(reso);
        log.info("Reso creato con successo, id: {}", salvato.getId());
        return salvato;
    }

    @Transactional
    public Reso aggiornaDataOraRitiro(UUID idReso,
                                      LocalDate nuovaData,
                                      LocalTime nuovaOra) {
        log.info("Aggiornamento data/ora ritiro per reso id: {}", idReso);
        ControlliUtils.controlloIdValido(idReso, "reso");

        Reso reso = resoRepository.findById(idReso).orElseThrow(
                () -> {
                    log.error("Reso con id: {} non trovato", idReso);
                    return new EntitaNonTrovataException(ErroreCodice.RESO_NON_TROVATO);
                }
        );

        reso.setDataResoPrevista(nuovaData);
        reso.setOraRitiroReso(nuovaOra);

        Reso salvato = resoRepository.save(reso);
        log.info("Reso id: {} aggiornato con nuova data/ora ritiro", salvato.getId());
        return salvato;
    }

    @Transactional
    public Reso annullaReso(UUID idReso) {
        log.info("Inizio annullamento reso id: {}", idReso);
        ControlliUtils.controlloIdValido(idReso, "reso");

        Reso reso = resoRepository.findById(idReso).orElseThrow(
                () -> {
                    log.error("Reso con id: {} non trovato", idReso);
                    return new EntitaNonTrovataException(ErroreCodice.RESO_NON_TROVATO);
                }
        );

        // Non puoi annullare un reso già annullato o già ritirato
        if (reso.getStatoReso() == Reso.StatoReso.ANNULLATO || reso.getStatoReso() == Reso.StatoReso.RITIRATO) {
            log.error("Impossibile annullare reso id: {} con stato: {}", idReso, reso.getStatoReso());
            throw new OperazioneNonConsentitaException(
                    "Impossibile annullare un reso già " + reso.getStatoReso(),
                    ErroreCodice.PRENOTAZIONE_STATO_NON_VALIDO);
        }

        reso.setStatoReso(Reso.StatoReso.ANNULLATO);
        Reso salvato = resoRepository.save(reso);
        log.info("Reso id: {} annullato con successo", salvato.getId());
        return salvato;
    }

}
