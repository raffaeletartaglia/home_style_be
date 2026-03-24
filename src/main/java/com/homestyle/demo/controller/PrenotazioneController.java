package com.homestyle.demo.controller;

import com.homestyle.demo.dto.request.PrenotazioneRequestDTO;
import com.homestyle.demo.dto.response.PrenotazioneResponseDTO;
import com.homestyle.demo.entity.Prenotazione;
import com.homestyle.demo.mapper.PrenotazioneMapper;
import com.homestyle.demo.service.PrenotazioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/prenotazione")
@RequiredArgsConstructor
public class PrenotazioneController {

    private final PrenotazioneMapper prenotazioneMapper;
    private final PrenotazioneService prenotazioneService;

    // === CREA PRENOTAZIONE ===
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PrenotazioneResponseDTO> creaPrenotazione(
            @RequestBody PrenotazioneRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                prenotazioneMapper.toDTO(
                        prenotazioneService.creaPrenotazione(
                                prenotazioneMapper.toEntity(requestDTO)
                        )
                )
        );
    }//creaPrenotazione


    @GetMapping("/utente/{utenteId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<PrenotazioneResponseDTO>> getPrenotazioniByUtente(
            @PathVariable UUID utenteId
    ) {
        return ResponseEntity.ok(
                prenotazioneMapper.toDTOs(
                        prenotazioneService.getPrenotazioniByUtente(utenteId)
                )
        );
    }//getPrenotazioniByUtente


    @GetMapping("/prodotto/{prodottoId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<PrenotazioneResponseDTO>> getPrenotazioniByProdotto(
            @PathVariable UUID prodottoId
    ) {
        return ResponseEntity.ok(
                prenotazioneMapper.toDTOs(
                        prenotazioneService.getPrenotazioniByProdotto(prodottoId)
                )
        );
    }//getPrenotazioniByProdotto


    @GetMapping("/prodotto/{prodottoId}/attive")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<PrenotazioneResponseDTO>> getPrenotazioniAttiveByProdotto(
            @PathVariable UUID prodottoId
    ) {
        return ResponseEntity.ok(
                prenotazioneMapper.toDTOs(
                        prenotazioneService.getPrenotazioniAttiveByProdotto(prodottoId)
                )
        );
    }//getPrenotazioniAttiveByProdotto


    @GetMapping("/stato/{stato}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PrenotazioneResponseDTO>> getPrenotazioniByStato(
            @PathVariable Prenotazione.Stato stato
    ) {
        return ResponseEntity.ok(
                prenotazioneMapper.toDTOs(
                        prenotazioneService.getPrenotazioniByStato(stato)
                )
        );
    }//getPrenotazioniByStato


    @GetMapping("/in-arrivo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PrenotazioneResponseDTO>> getPrenotazioniInArrivo(
            @RequestParam LocalDate data
    ) {
        return ResponseEntity.ok(
                prenotazioneMapper.toDTOs(
                        prenotazioneService.getPrenotazioniInArrivo(data)
                )
        );
    }//getPrenotazioniInArrivo


    @PutMapping("/annulla/{prenotazioneId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PrenotazioneResponseDTO> annullaPrenotazione(
            @PathVariable UUID prenotazioneId
    ) {
        return ResponseEntity.ok(
                prenotazioneMapper.toDTO(
                        prenotazioneService.annullaPrenotazione(prenotazioneId)
                )
        );
    }//annullaPrenotazione


    @PutMapping("/esegui/{prenotazioneId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PrenotazioneResponseDTO> eseguiPrenotazione(
            @PathVariable UUID prenotazioneId
    ) {
        return ResponseEntity.ok(
                prenotazioneMapper.toDTO(
                        prenotazioneService.eseguiPrenotazione(prenotazioneId)
                )
        );
    }//eseguiPrenotazione

}//PrenotazioneController
