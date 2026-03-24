package com.homestyle.demo.service;

import java.util.UUID;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.homestyle.demo.ErroreCodice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import utils.ControlliUtils;
import exception.*;

import com.homestyle.demo.repository.CategoriaRepository;
import com.homestyle.demo.repository.DettaglioOrdineRepository;
import com.homestyle.demo.repository.ProdottoRepository;

import com.homestyle.demo.entity.Prodotto;
import com.homestyle.demo.entity.Categoria;
import com.homestyle.demo.entity.Ordine.StatoOrdine;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdottoService {

	private final ProdottoRepository prodottoRepo;
	private final CategoriaRepository categoriaRepo;
	private final DettaglioOrdineRepository dettaglioOrdineRepo;

	public Prodotto creaProdotto(Prodotto prodotto) {
		log.info("Creazione nuovo prodotto");

		if (!controlloMarca(prodotto.getMarca())) {
			log.error("Marca non valida: {}", prodotto.getMarca());
			throw new ValoreNonValidoException("Marca non valida", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		if (!controlloNomeProdotto(prodotto.getNomeProdotto())) {
			log.error("Nome prodotto non valido: {}", prodotto.getNomeProdotto());
			throw new ValoreNonValidoException("Nome prodotto non valido", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		ControlliUtils.controlloIdValido(prodotto.getCategoria().getId(), "Categoria");
		log.info("Id categoria valido: {}", prodotto.getCategoria().getId());

		Categoria categoria = categoriaRepo.findById(prodotto.getCategoria().getId())
				.orElseThrow(() -> {
					log.error("Categoria non trovata id={}", prodotto.getCategoria().getId());
					return new EntitaNonTrovataException(ErroreCodice.CATEGORIA_NON_TROVATA);
				});

		prodotto.setCategoria(categoria);
		log.info("Categoria associata al prodotto id={}", categoria.getId());

		if (!controlloColore(prodotto.getColore())) {
			log.error("Colore prodotto non valido: {}", prodotto.getColore());
			throw new ValoreNonValidoException("Colore prodotto non valido", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		if (!controlloModello(prodotto.getModello())) {
			log.error("Modello prodotto non valido: {}", prodotto.getModello());
			throw new ValoreNonValidoException("Modello prodotto non valido", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		if (!controlloPrezzo(prodotto.getPrezzo())) {
			log.error("Prezzo prodotto non valido: {}", prodotto.getPrezzo());
			throw new ValoreNonValidoException("Prezzo prodotto non valido", ErroreCodice.PRODOTTO_NON_DISPONIBILE);
		}

		if (!controlloDescrizione(prodotto.getDescrizione())) {
			log.error("Descrizione prodotto non valida: {}", prodotto.getDescrizione());
			throw new ValoreNonValidoException("Descrizione prodotto non valida", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		if (!controlloIncludeMontaggio(prodotto.getIncludeMontaggio())) {
			log.error("Include montaggio non valido");
			throw new ValoreNonValidoException("Include montaggio non valido", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		if (!controlloSogliaRiordino(prodotto.getSogliaRiordino())) {
			log.error("Soglia riordino non valida: {}", prodotto.getSogliaRiordino());
			throw new ValoreNonValidoException("Soglia riordino non valida", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		if (!controlloQuantitaRiordino(prodotto.getQuantitaRiordinoStandard())) {
			log.error("Quantità riordino standard non valida: {}", prodotto.getQuantitaRiordinoStandard());
			throw new ValoreNonValidoException("Quantità riordino standard non valida", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		if (!controlloDataDisponibilita(prodotto.getDataProssimaDisponibilita())) {
			log.error("Data prossima disponibilità non valida: {}", prodotto.getDataProssimaDisponibilita());
			throw new ValoreNonValidoException("Data prossima disponibilità non valida", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		Prodotto prodottoSalvato = prodottoRepo.save(prodotto);
		log.info("Prodotto creato con id={}", prodottoSalvato.getId());

		return prodottoSalvato;
	}//creaProdotto

	public void deleteProdotto(UUID prodottoId) {

		log.info("Eliminazione prodotto id={}", prodottoId);

		ControlliUtils.controlloIdValido(prodottoId, "Prodotto");

		Prodotto prodotto = prodottoRepo.findById(prodottoId)
				.orElseThrow(() -> {
					log.error("Prodotto non trovato per id={}", prodottoId);
					return new EntitaNonTrovataException(ErroreCodice.PRODOTTO_NON_TROVATO);
				});

		if (dettaglioOrdineRepo.existsByProdottoIdAndOrdineStatoOrdineIn(
				prodottoId,
				List.of(StatoOrdine.IN_ELABORAZIONE, StatoOrdine.SPEDITO))) {

			log.error("Impossibile eliminare prodotto id={} - presente in ordini attivi", prodottoId);

			throw new ValoreNonValidoException(
					"Impossibile eliminare il prodotto: presente in ordini attivi",
					ErroreCodice.PRODOTTO_NON_DISPONIBILE
			);
		}

		prodottoRepo.delete(prodotto);

		log.info("Prodotto eliminato con successo id={}", prodottoId);
	}//deleteProdotto

	public Prodotto getProdottoById(UUID prodottoId) {
		log.info("Recupero prodotto id={}", prodottoId);

		ControlliUtils.controlloIdValido(prodottoId, "Prodotto");

		Prodotto prodotto = prodottoRepo.findById(prodottoId)
				.orElseThrow(() -> {
					log.error("Prodotto non trovato per id={}", prodottoId);
					return new EntitaNonTrovataException(ErroreCodice.PRODOTTO_NON_TROVATO);
				});

		return prodotto;
	}//getProdottoById

	public List<Prodotto> getAllProdotti() {
		log.info("Recupero lista completa prodotti");
		return prodottoRepo.findAll();
	}//getAllProdotti

	public Prodotto modificaProdotto(UUID prodottoId, Prodotto prodotto) {
		log.info("Modifica prodotto id={}", prodottoId);

		ControlliUtils.controlloIdValido(prodottoId, "Prodotto");

		Prodotto prodottoEsistente = prodottoRepo.findById(prodottoId)
				.orElseThrow(() -> {
					log.error("Prodotto non trovato id={}", prodottoId);
					return new EntitaNonTrovataException(ErroreCodice.PRODOTTO_NON_TROVATO);
				});

		if (!controlloMarca(prodotto.getMarca())) {
			log.error("Marca non valida: {}", prodotto.getMarca());
			throw new ValoreNonValidoException("Marca non valida", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		if (!controlloNomeProdotto(prodotto.getNomeProdotto())) {
			log.error("Nome prodotto non valido: {}", prodotto.getNomeProdotto());
			throw new ValoreNonValidoException("Nome prodotto non valido", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		ControlliUtils.controlloIdValido(prodotto.getCategoria().getId(), "Categoria");
		log.info("Id categoria valido: {}", prodotto.getCategoria().getId());

		Categoria categoria = categoriaRepo.findById(prodotto.getCategoria().getId())
				.orElseThrow(() -> {
					log.error("Categoria non trovata id={}", prodotto.getCategoria().getId());
					return new EntitaNonTrovataException(ErroreCodice.CATEGORIA_NON_TROVATA);
				});

		if (!controlloColore(prodotto.getColore())) {
			log.error("Colore prodotto non valido: {}", prodotto.getColore());
			throw new ValoreNonValidoException("Colore prodotto non valido", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		if (!controlloModello(prodotto.getModello())) {
			log.error("Modello prodotto non valido: {}", prodotto.getModello());
			throw new ValoreNonValidoException("Modello prodotto non valido", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		if (!controlloPrezzo(prodotto.getPrezzo())) {
			log.error("Prezzo prodotto non valido: {}", prodotto.getPrezzo());
			throw new ValoreNonValidoException("Prezzo prodotto non valido", ErroreCodice.PRODOTTO_NON_DISPONIBILE);
		}

		if (!controlloDescrizione(prodotto.getDescrizione())) {
			log.error("Descrizione prodotto non valida: {}", prodotto.getDescrizione());
			throw new ValoreNonValidoException("Descrizione prodotto non valida", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		if (!controlloIncludeMontaggio(prodotto.getIncludeMontaggio())) {
			log.error("Include montaggio non valido");
			throw new ValoreNonValidoException("Include montaggio non valido", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		if (!controlloSogliaRiordino(prodotto.getSogliaRiordino())) {
			log.error("Soglia riordino non valida: {}", prodotto.getSogliaRiordino());
			throw new ValoreNonValidoException("Soglia riordino non valida", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		if (!controlloQuantitaRiordino(prodotto.getQuantitaRiordinoStandard())) {
			log.error("Quantità riordino standard non valida: {}", prodotto.getQuantitaRiordinoStandard());
			throw new ValoreNonValidoException("Quantità riordino standard non valida", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		if (!controlloDataDisponibilita(prodotto.getDataProssimaDisponibilita())) {
			log.error("Data prossima disponibilità non valida: {}", prodotto.getDataProssimaDisponibilita());
			throw new ValoreNonValidoException("Data prossima disponibilità non valida", ErroreCodice.ERRORE_VALIDAZIONE);
		}

		prodottoEsistente.setMarca(prodotto.getMarca());
		prodottoEsistente.setNomeProdotto(prodotto.getNomeProdotto());
		prodottoEsistente.setCategoria(categoria);
		prodottoEsistente.setColore(prodotto.getColore());
		prodottoEsistente.setModello(prodotto.getModello());
		prodottoEsistente.setPrezzo(prodotto.getPrezzo());
		prodottoEsistente.setDescrizione(prodotto.getDescrizione());
		prodottoEsistente.setIncludeMontaggio(prodotto.getIncludeMontaggio());
		prodottoEsistente.setSogliaRiordino(prodotto.getSogliaRiordino());
		prodottoEsistente.setQuantitaRiordinoStandard(prodotto.getQuantitaRiordinoStandard());
		prodottoEsistente.setDataProssimaDisponibilita(prodotto.getDataProssimaDisponibilita());

		Prodotto aggiornato = prodottoRepo.save(prodottoEsistente);

		log.info("Prodotto aggiornato con successo id={}", aggiornato.getId());

		return aggiornato;
	}//modificaProdotto



	private boolean controlloMarca(String marca) {
		log.debug("Controllo marca: {}", marca);
		return marca != null && !marca.isEmpty() && marca.length() < 100;
	}

	private boolean controlloNomeProdotto(String nomeProdotto) {
		log.debug("Controllo nome prodotto: {}", nomeProdotto);
		return nomeProdotto != null && !nomeProdotto.isEmpty() && nomeProdotto.length() < 150;
	}

	private boolean controlloColore(String colore) {
		log.debug("Controllo colore: {}", colore);
		return colore != null && !colore.isEmpty() && colore.length() < 50;
	}

	private boolean controlloModello(String modello) {
		log.debug("Controllo modello: {}", modello);
		return modello != null && !modello.isEmpty() && modello.length() < 100;
	}

	private boolean controlloPrezzo(BigDecimal prezzo) {
		log.debug("Controllo prezzo: {}", prezzo);
		return prezzo != null && prezzo.compareTo(BigDecimal.ZERO) > 0;
	}

	private boolean controlloDescrizione(String descrizione) {
		log.debug("Controllo descrizione: {}", descrizione);
		return descrizione != null && !descrizione.isBlank();
	}

	private boolean controlloIncludeMontaggio(Boolean includeMontaggio) {
		log.debug("Controllo include montaggio: {}", includeMontaggio);
		return includeMontaggio != null;
	}

	private boolean controlloSogliaRiordino(Integer soglia) {
		log.debug("Controllo soglia riordino: {}", soglia);
		return soglia != null && soglia >= 0;
	}

	private boolean controlloQuantitaRiordino(Integer quantita) {
		log.debug("Controllo quantità riordino: {}", quantita);
		return quantita != null && quantita > 0;
	}

	private boolean controlloDataDisponibilita(LocalDate data) {
		log.debug("Controllo data disponibilità: {}", data);
		return data == null || !data.isBefore(LocalDate.now());
	}

}//ProdottoService
