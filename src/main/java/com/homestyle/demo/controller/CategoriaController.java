package com.homestyle.demo.controller;

import com.homestyle.demo.dto.request.CategoriaRequestDTO;
import com.homestyle.demo.dto.response.CategoriaResponseDTO;
import com.homestyle.demo.entity.Categoria;
import com.homestyle.demo.mapper.CategoriaMapper;
import com.homestyle.demo.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categorie")
@RequiredArgsConstructor
public class CategoriaController {

	private final CategoriaMapper categoriaMapper;
	private final CategoriaService categoriaService;

	/**
	 * USER/ADMIN: trova categoria per id
	 */
	@GetMapping("/{idCategoria}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<CategoriaResponseDTO> trovaCategoriaPerId(@PathVariable UUID idCategoria) {
		return ResponseEntity.ok(
				categoriaMapper.toDTO(
						categoriaService.getCategoriaById(idCategoria)
				)
		);
	}

	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<CategoriaResponseDTO>> getAllCategorie() {
		return ResponseEntity.ok(
				categoriaMapper.toDTOs(categoriaService.getAllCategorie())
		);
	}


	/**
	 * ADMIN: crea una nuova categoria
	 */
	@PostMapping
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoriaResponseDTO> aggiungiCategoria(
			@RequestBody CategoriaRequestDTO requestDTO) {
		return ResponseEntity.ok(
				categoriaMapper.toDTO(categoriaService.addCategoria(categoriaMapper.toEntity(requestDTO))));
	}//aggiungiCategoria

	/**
	 * ADMIN: modifica una categoria esistente
	 */
	@PutMapping("/{idCategoria}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoriaResponseDTO> modificaCategoria(
			@PathVariable UUID idCategoria,
			@RequestBody CategoriaRequestDTO requestDTO) {

		Categoria daModificare = categoriaMapper.toEntity(requestDTO);
		Categoria aggiornata = categoriaService.modifyCategoria(idCategoria, daModificare);
		return ResponseEntity.ok(categoriaMapper.toDTO(aggiornata));
	}

	/**
	 * ADMIN: elimina una categoria
	 */
	@DeleteMapping("/{idCategoria}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> eliminaCategoria(@PathVariable UUID idCategoria) {
		categoriaService.deleteCategoria(idCategoria);
		return ResponseEntity.noContent().build();
	}
}
