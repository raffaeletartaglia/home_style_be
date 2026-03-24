package com.homestyle.demo.controller;

import com.homestyle.demo.dto.request.DettaglioOrdineRequestDTO;
import com.homestyle.demo.dto.response.DettaglioOrdineResponseDTO;
import com.homestyle.demo.mapper.DettaglioOrdineMapper;
import com.homestyle.demo.service.DettaglioOrdineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dettaglio-ordine")
@RequiredArgsConstructor
public class DettaglioOrdineController {

    private final DettaglioOrdineMapper dettaglioOrdineMapper;
    private final DettaglioOrdineService dettaglioOrdineService;

    // === TROVA DETTAGLIO PER ID ===
    @GetMapping("/{idDettaglio}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<DettaglioOrdineResponseDTO> trovaDettaglioPerId(@PathVariable UUID idDettaglioOrdine) {
        return ResponseEntity.ok(
                dettaglioOrdineMapper.toDTO(
                        dettaglioOrdineService.trovaDettaglioPerId(idDettaglioOrdine)
                )
        );
    }//trovaDettaglioPerId


    @GetMapping("/ordine")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<DettaglioOrdineResponseDTO>> trovaDettagliPerOrdine(@RequestBody UUID idDettaglioOrdine) {
        return ResponseEntity.ok(
                dettaglioOrdineMapper.toDTOs(
                        dettaglioOrdineService.trovaDettagliPerOrdine(idDettaglioOrdine)
                )
        );
    }//trovaDettagliPerOrdine


    @GetMapping("/prodotto")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<DettaglioOrdineResponseDTO>> trovaDettagliPerProdotto(@RequestBody UUID idDettaglioOrdine) {
        return ResponseEntity.ok(
                dettaglioOrdineMapper.toDTOs(
                        dettaglioOrdineService.trovaDettagliPerProdotto(idDettaglioOrdine)
                )
        );
    }//trovaDettagliPerProdotto

}//DettaglioOrdineController

