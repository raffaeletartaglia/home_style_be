package com.homestyle.demo.service;

import java.util.UUID;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import utils.ControlliUtils;
import exception.*;

import com.homestyle.demo.repository.CartaPagamentoRepository;
import com.homestyle.demo.repository.ModalitaPagamentoRepository;
import com.homestyle.demo.repository.OrdineRepository;
import com.homestyle.demo.repository.PagamentoRepository;

import com.homestyle.demo.entity.CartaPagamento;
import com.homestyle.demo.entity.Ordine;
import com.homestyle.demo.entity.Pagamento;
import com.homestyle.demo.entity.Prodotto;
import com.homestyle.demo.entity.ModalitaPagamento;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagamentoService {

    private final PagamentoRepository pagamentoRepo;
    private final OrdineRepository ordineRepo;
    private final ModalitaPagamentoRepository modalitaPagamentoRepo;
    private final CartaPagamentoRepository cartaPagamentoRepo;

    public Pagamento creaPagamento(Pagamento pagamento) {

        log.info("Creazione nuovo pagamento");

        
        ControlliUtils.controlloIdValido(pagamento.getOrdine().getId(), "Ordine");
        log.info("Id ordine valido: {}", pagamento.getOrdine().getId());

        Ordine ordine = ordineRepo.findById(pagamento.getOrdine().getId())
                .orElseThrow(() -> new EntitaNonTrovataException("Ordine non trovato"));

        pagamento.setOrdine(ordine);
        log.info("Ordine trovato e associato al pagamento: {}", ordine.getId());

        
        ControlliUtils.controlloIdValido(pagamento.getModalitaPagamento().getId(), "Modalita Pagamento");
        log.info("Id modalita pagamento valido: {}", pagamento.getModalitaPagamento().getId());

        ModalitaPagamento modalitaPagamento = modalitaPagamentoRepo
                .findById(pagamento.getModalitaPagamento().getId())
                .orElseThrow(() -> new EntitaNonTrovataException("Modalita Pagamento non trovata"));

        pagamento.setModalitaPagamento(modalitaPagamento);
        log.info("Modalita pagamento trovata e associata: {}", modalitaPagamento.getId());

        
        if (pagamento.getCartaPagamento() != null) {

            ControlliUtils.controlloIdValido(pagamento.getCartaPagamento().getId(), "Carta Pagamento");
            log.info("Id carta pagamento valido: {}", pagamento.getCartaPagamento().getId());

            CartaPagamento cartaPagamento = cartaPagamentoRepo
                    .findById(pagamento.getCartaPagamento().getId())
                    .orElseThrow(() -> new EntitaNonTrovataException("Carta Pagamento non trovata"));

            pagamento.setCartaPagamento(cartaPagamento);
            log.info("Carta pagamento trovata e associata: {}", cartaPagamento.getId());
        }

        if (!controlloNumeroRate(pagamento.getNumeroRate())) {
            log.error("Numero rate non valido: {}", pagamento.getNumeroRate());
            throw new ValoreNonValidoException("Numero rate non valido");
        }

        if (!controlloRataCorrente(pagamento.getRataCorrente())) {
            log.error("Rata corrente non valida: {}", pagamento.getRataCorrente());
            throw new ValoreNonValidoException("Rata corrente non valida");
        }

        if (!controlloRataCorrenteVsNumeroRate(pagamento.getRataCorrente(), pagamento.getNumeroRate())) {
            log.error("Rata corrente maggiore del numero di rate");
            throw new ValoreNonValidoException("Rata corrente non può essere maggiore del numero di rate");
        }

        if (!controlloImporto(pagamento.getImporto())) {
            log.error("Importo pagamento non valido: {}", pagamento.getImporto());
            throw new ValoreNonValidoException("Importo pagamento non valido");
        }

        if (!controlloImportoRata(pagamento.getImportoRata())) {
            log.error("Importo rata non valido: {}", pagamento.getImportoRata());
            throw new ValoreNonValidoException("Importo rata non valido");
        }

        if (!controlloDataPagamento(pagamento.getDataPagamento())) {
            log.error("Data pagamento non valida: {}", pagamento.getDataPagamento());
            throw new ValoreNonValidoException("Data pagamento non valida");
        }

        if (!controlloFattura(pagamento.getFattura())) {
            log.error("Fattura non valida");
            throw new ValoreNonValidoException("Fattura non valida");
        }

        Pagamento pagamentoSalvato = pagamentoRepo.save(pagamento);

        log.info("Pagamento creato con id: {}", pagamentoSalvato.getId());

        return pagamentoSalvato;

    }//creaPagamento
    
    public Pagamento getPagamentoById(UUID pagamentoId) {
    	log.info("Trovo un pagamento specifico per mezzo l'uso di pagametoId={}", pagamentoId);
	   	
	   	   ControlliUtils.controlloIdValido(pagamentoId, "Pagamento");
	   	   log.info("Id pagamentovalido: {}", pagamentoId);
	   	   
	   	   Pagamento pagamento = pagamentoRepo.findById(pagamentoId).orElseThrow(
	   			   () -> {
	   				   log.error("Pagamneto non trovato per l'id: {}", pagamentoId);
	   				   throw new ValoreNonValidoException("id pagamento non trovato");
	   			   }
	   			   );
	   	log.info("Pagamento trovato: {}", pagamento.getId());
	   	   return pagamento;
    }//getPagamentoById
    
    public Pagamento getPagamentoByOrdine(UUID ordineId) {
    	log.info("Ricerca pagamento per ordine: {}", ordineId);

        ControlliUtils.controlloIdValido(ordineId, "Ordine");

        Ordine ordine = ordineRepo.findById(ordineId)
                .orElseThrow(() -> new EntitaNonTrovataException("Ordine non trovato"));

        Pagamento pagamento = pagamentoRepo.findByOrdine(ordine)
                .orElseThrow(() -> new EntitaNonTrovataException("Pagamento non trovato per l'ordine"));

        log.info("Pagamento trovato: {}", pagamento.getId());

        return pagamento;
    }//getPagamentoByOrdine
    
    public Pagamento annullaPagamento(UUID id) {
    	log.info("Annullamento pagamento con id: {}", id);

        ControlliUtils.controlloIdValido(id, "Pagamento");

        Pagamento pagamento = pagamentoRepo.findById(id)
                .orElseThrow(() -> new EntitaNonTrovataException("Pagamento non trovato"));

        if (Boolean.FALSE.equals(pagamento.getPagamentoEffettuato())) {
            log.error("Il pagamento non è stato effettuato quindi non può essere annullato");
            throw new ValoreNonValidoException("Il pagamento non risulta effettuato");
        }

        pagamento.setPagamentoEffettuato(false);
        pagamento.setDataPagamento(null);
        pagamento.setRataCorrente(1);
        pagamento.setFattura(null);

        Pagamento pagamentoAggiornato = pagamentoRepo.save(pagamento);

        log.info("Pagamento annullato con successo: {}", pagamentoAggiornato.getId());

        return pagamentoAggiornato;
    }//annullaPagamento
    
    public Pagamento effettuaPagamento(UUID pagamentoId) {
    	log.info("Esecuzione pagamento con id: {}", pagamentoId);

        ControlliUtils.controlloIdValido(pagamentoId, "Pagamento");

        Pagamento pagamento = pagamentoRepo.findById(pagamentoId)
                .orElseThrow(() -> new EntitaNonTrovataException("Pagamento non trovato"));

        if (Boolean.TRUE.equals(pagamento.getPagamentoEffettuato())) {
            log.error("Pagamento già effettuato: {}", pagamentoId);
            throw new ValoreNonValidoException("Pagamento già effettuato");
        }

        pagamento.setPagamentoEffettuato(true);
        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setRataCorrente(pagamento.getNumeroRate());

        if (pagamento.getFattura() == null || pagamento.getFattura().isEmpty()) {
            pagamento.setFattura("FATTURA-" + pagamentoId.toString());
        }

        Pagamento pagamentoAggiornato = pagamentoRepo.save(pagamento);

        log.info("Pagamento effettuato con successo: {}", pagamentoAggiornato.getId());

        return pagamentoAggiornato;
    }//effettuaPagamento
    
    public Pagamento pagaRata(UUID pagamentoId) {
    	log.info("Pagamento di una rata per pagamento id: {}", pagamentoId);

        ControlliUtils.controlloIdValido(pagamentoId, "Pagamento");

        Pagamento pagamento = pagamentoRepo.findById(pagamentoId)
                .orElseThrow(() -> new EntitaNonTrovataException("Pagamento non trovato"));

        
        if (Boolean.TRUE.equals(pagamento.getPagamentoEffettuato())) {
            log.error("Pagamento già completato: {}", pagamentoId);
            throw new ValoreNonValidoException("Pagamento già completato");
        }

        
        Integer rataCorrente = pagamento.getRataCorrente();
        Integer numeroRate = pagamento.getNumeroRate();

        if (rataCorrente >= numeroRate) {
            log.error("Non ci sono più rate da pagare per il pagamento: {}", pagamentoId);
            throw new ValoreNonValidoException("Tutte le rate sono già state pagate");
        }

        rataCorrente += 1;
        pagamento.setRataCorrente(rataCorrente);

        if (rataCorrente.equals(numeroRate)) {
            pagamento.setPagamentoEffettuato(true);
            log.info("Pagamento completato per pagamento id: {}", pagamentoId);
        }
        pagamento.setDataPagamento(LocalDateTime.now());

        if (pagamento.getFattura() == null || pagamento.getFattura().isEmpty()) {
            pagamento.setFattura("FATTURA-" + pagamentoId.toString());
        }
        Pagamento pagamentoAggiornato = pagamentoRepo.save(pagamento);

        log.info("Rata pagata con successo. Rata corrente: {}/{}", rataCorrente, numeroRate);

        return pagamentoAggiornato;
    }// pagaRata
    
    private boolean controlloNumeroRate(Integer numeroRate) {
    	if (numeroRate == null)
            return false;
        return numeroRate > 0;
    }//controlloNumeroRate

    private boolean controlloRataCorrente(Integer rataCorrente) {
    	if (rataCorrente == null)
            return false;
    	return rataCorrente >= 1;
    }//controlloRataCorrente

    private boolean controlloRataCorrenteVsNumeroRate(Integer rataCorrente, Integer numeroRate) {
    	if (rataCorrente == null || numeroRate == null)
            return false;
    	return rataCorrente <= numeroRate;
    }//controlloRataCorrenteVsNumeroRate

    private boolean controlloImporto(BigDecimal importo) {
    	if (importo == null)
            return false;
    	return importo.compareTo(BigDecimal.ZERO) >= 0;
    }//controlloImporto

    private boolean controlloImportoRata(BigDecimal importoRata) {
    	if (importoRata == null)
            return false;

        return importoRata.compareTo(BigDecimal.ZERO) >= 0;
    }//controlloImportoRata

    private boolean controlloDataPagamento(LocalDateTime dataPagamento) {
    	if (dataPagamento == null)
            return true;
        return !dataPagamento.isAfter(LocalDateTime.now());
        }//controlloDataPagamento

    private boolean controlloFattura(String fattura) {
    	if (fattura == null)
            return true;
    	return !fattura.trim().isEmpty();
    }//controlloFattura

}//PagamentoService