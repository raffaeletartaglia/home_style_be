package com.homestyle.demo.service;

import com.homestyle.demo.ErroreCodice;
import com.homestyle.demo.entity.Categoria;
import com.homestyle.demo.repository.CategoriaRepository;
import exception.EntitaNonTrovataException;
import exception.ValoreNonValidoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import utils.ControlliUtils;



import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaService {

	private final CategoriaRepository categoriaRepo;

	public Categoria getCategoriaById(UUID idCategoria) {
		log.info("Ricerca categoria con id: {}", idCategoria);
		ControlliUtils.controlloIdValido(idCategoria, "Categoria");

		Categoria categoria = categoriaRepo.findById(idCategoria).orElseThrow(
				() -> {
					log.error("Categoria con id: {} non trovata", idCategoria);
					return new EntitaNonTrovataException(ErroreCodice.CATEGORIA_NON_TROVATA);

				}
		);

		log.info("Categoria con id: {} trovata", idCategoria);
		return categoria;
	}

	/**
	 * Ritorna la lista di tutte le categorie
	 */
	public List<Categoria> getAllCategorie() {
		log.info("Recupero di tutte le categorie");
		List<Categoria> categorie = categoriaRepo.findAll();

		if (categorie.isEmpty()) {
			log.warn("Nessuna categoria trovata");
		} else {
			log.info("Trovate {} categorie", categorie.size());
		}

		return categorie;
	}

	public Categoria addCategoria(Categoria categoria) {

		if (controllaLunghezzaNomeCategoria(categoria.getNomeCategoria())) {
			log.error("Nome categoria non valido: {}", categoria.getNomeCategoria());
			throw new ValoreNonValidoException(
					ErroreCodice.CATEGORIA_NOME_NON_VALIDO,
					"Nome invalido, caratteri inferiori a 0 o superiori a 100 caratteri"
			);
		}

		if (controlloLunghezzaDescrizione(categoria.getDescrizione())) {
			log.error("Descrizione categoria non valida");
			throw new ValoreNonValidoException(
					ErroreCodice.CATEGORIA_DESCRIZIONE_NON_VALIDA,
					"Descrizione invalida, descrizione vuota o superiore a 255 caratteri"
			);
		}

		Categoria salvata = categoriaRepo.save(categoria);
		log.info("Categoria creata con id: {}", salvata.getId());
		return salvata;
	}

	public Categoria modifyCategoria(UUID idCategoria, Categoria categoria) {

		log.info("Modifica categoria id: {}", idCategoria);
		ControlliUtils.controlloIdValido(idCategoria, "Categoria");

		Categoria vecchiaCategoria = categoriaRepo.findById(idCategoria).orElseThrow(
				() -> {
					log.error("Categoria con id: {} non trovata", idCategoria);
					return new EntitaNonTrovataException(ErroreCodice.CATEGORIA_NON_TROVATA);
				}
		);

		vecchiaCategoria.setNomeCategoria(categoria.getNomeCategoria());
		Categoria salvata = categoriaRepo.save(vecchiaCategoria);
		log.info("Categoria id: {} aggiornata", salvata.getId());
		return salvata;
	}

	public void deleteCategoria(UUID idCategoria) {

		log.info("Eliminazione categoria id: {}", idCategoria);
		ControlliUtils.controlloIdValido(idCategoria, "Categoria");

		Categoria vecchiaCategoria = categoriaRepo.findById(idCategoria).orElseThrow(
				() -> {
					log.error("Categoria con id: {} non trovata", idCategoria);
					return new exception.EntitaNonTrovataException(ErroreCodice.CATEGORIA_NON_TROVATA);
				}
		);

		categoriaRepo.deleteById(idCategoria);
		log.info("Categoria id: {} eliminata", vecchiaCategoria.getId());
	}

	private boolean controllaLunghezzaNomeCategoria(String nomeCategoria) {
		return !nomeCategoria.isEmpty() && nomeCategoria.length() < 100;
	}

	private boolean controlloLunghezzaDescrizione(String descrizione) {
		return !descrizione.isEmpty() && descrizione.length() < 255;
	}
}
