package com.homestyle.demo.controller;

import com.homestyle.demo.dto.request.ProdottoRequestDTO;
import com.homestyle.demo.dto.response.ProdottoResponseDTO;
import com.homestyle.demo.mapper.ProdottoMapper;
import com.homestyle.demo.service.ProdottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/prodotto")
@RequiredArgsConstructor
public class ProdottoController {

    private final ProdottoMapper prodottoMapper;
    private final ProdottoService prodottoService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdottoResponseDTO> creaProdotto(
            @RequestBody ProdottoRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                prodottoMapper.toDTO(
                        prodottoService.creaProdotto(
                                prodottoMapper.toEntity(requestDTO)
                        )
                )
        );
    }//creaProdotto


    @GetMapping("/{prodottoId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProdottoResponseDTO> getProdottoById(
            @PathVariable UUID prodottoId
    ) {
        return ResponseEntity.ok(
                prodottoMapper.toDTO(
                        prodottoService.getProdottoById(prodottoId)
                )
        );
    }//getProdottoById


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ProdottoResponseDTO>> getAllProdotti() {
        return ResponseEntity.ok(
                prodottoMapper.toDTOs(
                        prodottoService.getAllProdotti()
                )
        );
    }//getAllProdotti


    @PutMapping("/{prodottoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdottoResponseDTO> modificaProdotto(
            @PathVariable UUID prodottoId,
            @RequestBody ProdottoRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                prodottoMapper.toDTO(
                        prodottoService.modificaProdotto(
                                prodottoId,
                                prodottoMapper.toEntity(requestDTO)
                        )
                )
        );
    }//modificaProdotto


    @DeleteMapping("/{prodottoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProdotto(
            @PathVariable UUID prodottoId
    ) {
        prodottoService.deleteProdotto(prodottoId);
        return ResponseEntity.noContent().build();
    }//deleteProdotto

}//ProdottoController

