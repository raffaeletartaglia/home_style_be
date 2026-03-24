package com.homestyle.demo.controller;

import com.homestyle.demo.dto.response.SpedizioneResponseDTO;
import com.homestyle.demo.entity.Spedizione;
import com.homestyle.demo.mapper.SpedizioneMapper;
import com.homestyle.demo.service.SpedizioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/spedizioni")
@RequiredArgsConstructor
public class SpedizioneController {

    private final SpedizioneService spedizioneService;
    private final SpedizioneMapper spedizioneMapper;

    /**
     * USER/ADMIN: recupera una spedizione per id
     */
    @GetMapping("/{idSpedizione}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<SpedizioneResponseDTO> getSpedizionePerId(@PathVariable UUID idSpedizione) {
        return ResponseEntity.ok(
                spedizioneMapper.toDTO(spedizioneService.trovaSpedizionePerId(idSpedizione))
        );
    }

    /**
     * USER/ADMIN: recupera tutte le spedizioni di un ordine
     */
    @GetMapping("/ordine/{idOrdine}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<SpedizioneResponseDTO>> getSpedizioniPerOrdine(@PathVariable UUID idOrdine) {
        return ResponseEntity.ok(
                spedizioneMapper.toDTOs(spedizioneService.trovaSpedizioniPerOrdine(idOrdine))
        );
    }

    // ================== CREAZIONE ==================

    /**
     * ADMIN: crea una spedizione per un ordine (in preparazione)
     */
    @PostMapping("/ordine/{idOrdine}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpedizioneResponseDTO> creaSpedizione(
            @PathVariable UUID idOrdine,
            @RequestParam String corriere,
            @RequestParam(required = false) String codiceTracking) {

        Spedizione spedizione = spedizioneService.creaSpedizione(idOrdine, corriere, codiceTracking);
        return ResponseEntity.ok(spedizioneMapper.toDTO(spedizione));
    }

    // ================== MODIFICA STATO ==================

    /**
     * ADMIN: aggiorna lo stato di una spedizione.
     * - Può annullare solo se la spedizione è in PREPARAZIONE
     * - Non può modificare spedizioni ANNULLATE o CONSEGNATE
     */
    @PutMapping("/{idSpedizione}/stato")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpedizioneResponseDTO> aggiornaStatoSpedizione(
            @PathVariable UUID idSpedizione,
            @RequestParam Spedizione.StatoSpedizione nuovoStato) {

        Spedizione spedizione = spedizioneService.aggiornaStatoSpedizione(idSpedizione, nuovoStato);
        return ResponseEntity.ok(spedizioneMapper.toDTO(spedizione));
    }
}//SpedizioneController

