package com.homestyle.demo.controller;

import com.homestyle.demo.dto.response.ResoResponseDTO;
import com.homestyle.demo.entity.Reso;
import com.homestyle.demo.mapper.ResoMapper;
import com.homestyle.demo.service.ResoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/resi")
@RequiredArgsConstructor
public class ResoController {

    private final ResoService resoService;
    private final ResoMapper resoMapper;



    /**
     * USER/ADMIN: recupera un reso per id
     */
    @GetMapping("/{idReso}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResoResponseDTO> getResoPerId(@PathVariable UUID idReso) {
        return ResponseEntity.ok(
                resoMapper.toDTO(resoService.trovaResoPerId(idReso))
        );
    }

    /**
     * USER/ADMIN: recupera reso per dettaglio ordine (1:1)
     */
    @GetMapping("/dettaglio-ordine/{idDettaglioOrdine}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResoResponseDTO> getResoPerDettaglioOrdine(@PathVariable UUID idDettaglioOrdine) {
        return ResponseEntity.ok(
                resoMapper.toDTO(resoService.trovaResoPerDettaglioOrdine(idDettaglioOrdine))
        );
    }

    // ============ CREAZIONE ============

    /**
     * USER/ADMIN: crea un reso per una riga ordine
     */
    @PostMapping("/dettaglio-ordine/{idDettaglioOrdine}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResoResponseDTO> creaReso(
            @PathVariable UUID idDettaglioOrdine,
            @RequestParam UUID idIndirizzoReso,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataResoPrevista,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime oraRitiroReso,
            @RequestParam String motivo) {

        Reso reso = resoService.creaReso(
                idDettaglioOrdine,
                idIndirizzoReso,
                dataResoPrevista,
                oraRitiroReso,
                motivo
        );

        return ResponseEntity.ok(resoMapper.toDTO(reso));
    }

    // ============ MODIFICHE ============

    /**
     * USER/ADMIN: aggiorna data/ora di ritiro (se il reso lo prevede)
     */
    @PutMapping("/{idReso}/ritiro")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResoResponseDTO> aggiornaDataOraRitiro(
            @PathVariable UUID idReso,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataRitiro,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime oraRitiro) {

        Reso reso = resoService.aggiornaDataOraRitiro(idReso, dataRitiro, oraRitiro);
        return ResponseEntity.ok(resoMapper.toDTO(reso));
    }

    /**
     * USER/ADMIN: annulla una richiesta di reso (se ancora possibile)
     */
    @PostMapping("/{idReso}/annulla")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResoResponseDTO> annullaReso(@PathVariable UUID idReso) {
        Reso reso = resoService.annullaReso(idReso);
        return ResponseEntity.ok(resoMapper.toDTO(reso));
    }
}//ResoController

