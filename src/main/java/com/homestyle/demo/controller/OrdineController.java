package com.homestyle.demo.controller;

import com.homestyle.demo.dto.response.OrdineResponseDTO;
import com.homestyle.demo.entity.Ordine;
import com.homestyle.demo.mapper.OrdineMapper;
import com.homestyle.demo.service.OrdineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/ordini")
@RequiredArgsConstructor
public class OrdineController {

    private final OrdineService ordineService;
    private final OrdineMapper ordineMapper;


    /**
     * USER/ADMIN: recupera un ordine per id
     */
    @GetMapping("/{idOrdine}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<OrdineResponseDTO> getOrdinePerId(@PathVariable UUID idOrdine) {
        return ResponseEntity.ok(
                ordineMapper.toDTO(ordineService.trovaOrdinePerId(idOrdine))
        );
    }

    /**
     * USER/ADMIN: storico ordini di un utente
     */
    @GetMapping("/utente/{idUtente}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<OrdineResponseDTO>> getOrdiniPerUtente(@PathVariable UUID idUtente) {
        return ResponseEntity.ok(
                ordineMapper.toDTOs(ordineService.trovaOrdiniPerIdUtente(idUtente))
        );
    }

    /**
     * USER/ADMIN: storico ordini di un utente filtrati per stato
     */
    @GetMapping("/utente/{idUtente}/stato")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<OrdineResponseDTO>> getOrdiniPerUtenteEStato(
            @PathVariable UUID idUtente,
            @RequestParam Ordine.StatoOrdine stato) {

        return ResponseEntity.ok(
                ordineMapper.toDTOs(
                        ordineService.trovaOrdiniPerIdUtenteEStatoOrdine(idUtente, stato)
                )
        );
    }

    /**
     * ADMIN: ordini per stato (es. tutti IN_ELABORAZIONE)
     */
    @GetMapping("/stato")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrdineResponseDTO>> getOrdiniPerStato(
            @RequestParam Ordine.StatoOrdine stato) {

        return ResponseEntity.ok(
                ordineMapper.toDTOs(ordineService.trovaOrdiniPerStato(stato))
        );
    }


    /**
     * USER: crea un ordine a partire dal carrello ATTIVO dell'utente
     * body minimal: idIndirizzo di spedizione
     */
    @PostMapping("/utente/{idUtente}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<OrdineResponseDTO> creaOrdine(
            @PathVariable UUID idUtente,
            @RequestParam UUID idIndirizzoSpedizione) {

        return ResponseEntity.ok(
                ordineMapper.toDTO(ordineService.creaOrdine(idUtente, idIndirizzoSpedizione)
            )
        );
    }


    /**
     * USER/ADMIN: modifica indirizzo di spedizione (solo se IN_ELABORAZIONE)
     */
    @PutMapping("/{idOrdine}/indirizzo")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<OrdineResponseDTO> modificaIndirizzoSpedizione(
            @PathVariable UUID idOrdine,
            @RequestParam UUID idNuovoIndirizzo) {

        return ResponseEntity.ok(
                ordineMapper.toDTO(ordineService.modificaIndirizzoSpedizione(idOrdine, idNuovoIndirizzo)
                )
        );
    }

    /**
     * ADMIN: modifica stato ordine (usa la logica di transizione nel service)
     */
    @PutMapping("/{idOrdine}/stato")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrdineResponseDTO> modificaStatoOrdine(
            @PathVariable UUID idOrdine,
            @RequestParam Ordine.StatoOrdine nuovoStato) {

        return ResponseEntity.ok(
                ordineMapper.toDTO(
                        ordineService.modificaStatoOrdine(idOrdine, nuovoStato)
                )
        );
    }

    /**
     * USER/ADMIN: annulla un ordine (se non CONSEGNATO o già ANNULLATO)
     */
    @PostMapping("/{idOrdine}/annulla")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<OrdineResponseDTO> annullaOrdine(@PathVariable UUID idOrdine) {
        return ResponseEntity.ok(ordineMapper.toDTO(ordineService.annullaOrdine(idOrdine)));
    }
}

