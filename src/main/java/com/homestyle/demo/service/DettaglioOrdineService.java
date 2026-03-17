package com.homestyle.demo.service;

import com.homestyle.demo.ErroreCodice;
import com.homestyle.demo.entity.DettaglioOrdine;
import com.homestyle.demo.repository.DettaglioOrdineRepository;
import exception.EntitaNonTrovataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.ControlliUtils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DettaglioOrdineService {

    private final DettaglioOrdineRepository dettaglioOrdineRepository;

    @Transactional(readOnly = true)
    public DettaglioOrdine trovaDettaglioPerId(UUID idDettaglio) {
        log.info("Cerco dettaglio ordine con id: {}", idDettaglio);
        ControlliUtils.controlloIdValido(idDettaglio, "dettaglio ordine");

        return dettaglioOrdineRepository.findById(idDettaglio).orElseThrow(
                () -> {
                    log.error("Dettaglio ordine con id: {} non trovato", idDettaglio);
                    return new EntitaNonTrovataException(ErroreCodice.DETTAGLIO_ORDINE_NON_TROVATO);
                }
        );
    }

    @Transactional(readOnly = true)
    public List<DettaglioOrdine> trovaDettagliPerOrdine(UUID idOrdine) {
        log.info("Cerco dettagli ordine per ordine id: {}", idOrdine);
        ControlliUtils.controlloIdValido(idOrdine, "ordine");

        List<DettaglioOrdine> dettagli =
                dettaglioOrdineRepository.findByOrdine_Id(idOrdine);

        log.info("Trovati {} dettagli per ordine id: {}", dettagli.size(), idOrdine);
        return dettagli;
    }

    @Transactional(readOnly = true)
    public List<DettaglioOrdine> trovaDettagliPerProdotto(UUID idProdotto) {
        log.info("Cerco dettagli ordine per prodotto id: {}", idProdotto);
        ControlliUtils.controlloIdValido(idProdotto, "prodotto");

        List<DettaglioOrdine> dettagli =
                dettaglioOrdineRepository.findByProdotto_Id(idProdotto);

        log.info("Trovati {} dettagli per prodotto id: {}", dettagli.size(), idProdotto);
        return dettagli;
    }
}
