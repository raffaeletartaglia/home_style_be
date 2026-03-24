package com.homestyle.demo.controller;

import com.homestyle.demo.dto.response.MovimentoMagazzinoResponseDTO;
import com.homestyle.demo.mapper.MovimentoMagazzinoMapper;
import com.homestyle.demo.service.MovimentoMagazzinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/movimento-magazzino")
@RequiredArgsConstructor
public class MovimentoMagazzinoController {

    private final MovimentoMagazzinoMapper movimentoMagazzinoMapper;
    private final MovimentoMagazzinoService movimentoMagazzinoService;


    @GetMapping("/prodotto/{prodottoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MovimentoMagazzinoResponseDTO>> getMovimentiByProdotto(
            @PathVariable UUID prodottoId
    ) {
        return ResponseEntity.ok(
                movimentoMagazzinoMapper.toDTOs(
                        movimentoMagazzinoService.getMovimentiByProdotto(prodottoId)
                )
        );
    }//getMovimentiByProdotto


    @GetMapping("/ordine/{ordineId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MovimentoMagazzinoResponseDTO>> getMovimentiByOrdine(
            @PathVariable UUID ordineId
    ) {
        return ResponseEntity.ok(
                movimentoMagazzinoMapper.toDTOs(
                        movimentoMagazzinoService.getMovimentiByOrdine(ordineId)
                )
        );
    }//getMovimentiByOrdine


    @GetMapping("/reso/{resoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MovimentoMagazzinoResponseDTO>> getMovimentiByReso(
            @PathVariable UUID resoId
    ) {
        return ResponseEntity.ok(
                movimentoMagazzinoMapper.toDTOs(
                        movimentoMagazzinoService.getMovimentiByReso(resoId)
                )
        );
    }//getMovimentiByReso

}//MovimentoMagazzinoController
