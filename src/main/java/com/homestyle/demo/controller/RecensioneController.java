package com.homestyle.demo.controller;

import com.homestyle.demo.dto.request.RecensioneRequestDTO;
import com.homestyle.demo.dto.response.RecensioneResponseDTO;
import com.homestyle.demo.mapper.RecensioneMapper;
import com.homestyle.demo.service.RecensioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recensione")
@RequiredArgsConstructor
public class RecensioneController {

    private final RecensioneMapper recensioneMapper;
    private final RecensioneService recensioneService;


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RecensioneResponseDTO> creaRecensione(
            @RequestBody RecensioneRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                recensioneMapper.toDTO(
                        recensioneService.creaRecensione(
                                recensioneMapper.toEntity(requestDTO)
                        )
                )
        );
    }//creaRecensione


    @GetMapping("/prodotto/{prodottoId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<RecensioneResponseDTO>> getRecensioniByProdotto(
            @PathVariable UUID prodottoId
    ) {
        return ResponseEntity.ok(
                recensioneMapper.toDTOs(
                        recensioneService.getRecensioniByProdotto(prodottoId)
                )
        );
    }//getRecensioniByProdotto


    @GetMapping("/utente/{utenteId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<RecensioneResponseDTO>> getRecensioniByUtente(
            @PathVariable UUID utenteId
    ) {
        return ResponseEntity.ok(
                recensioneMapper.toDTOs(
                        recensioneService.getRecensioniByUtente(utenteId)
                )
        );
    }//getRecensioniByUtente


    @GetMapping("/dettaglio-ordine/{dettaglioOrdineId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<RecensioneResponseDTO>> getRecensioneByDettaglioOrdine(
            @PathVariable UUID dettaglioOrdineId
    ) {
        return ResponseEntity.ok(
                recensioneMapper.toDTOs(
                        recensioneService.getRecensioneByDettaglioOrdine(dettaglioOrdineId)
                )
        );
    }//getRecensioneByDettaglioOrdine


    @PutMapping("/{recensioneId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<RecensioneResponseDTO> modificaRecensione(
            @PathVariable UUID recensioneId,
            @RequestBody RecensioneRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                recensioneMapper.toDTO(
                        recensioneService.modificaRecensione(
                                recensioneId,
                                recensioneMapper.toEntity(requestDTO)
                        )
                )
        );
    }//modificaRecensione


    @DeleteMapping("/{recensioneId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> eliminaRecensione(
            @PathVariable UUID recensioneId
    ) {
        recensioneService.eliminaRecensione(recensioneId);
        return ResponseEntity.noContent().build();
    }//eliminaRecensione

}//RecensioneController

