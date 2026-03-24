package com.homestyle.demo.controller;

import com.homestyle.demo.dto.request.IndirizzoRequestDTO;
import com.homestyle.demo.dto.response.IndirizzoResponseDTO;
import com.homestyle.demo.mapper.IndirizzoMapper;
import com.homestyle.demo.service.IndirizzoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/indirizzo")
@RequiredArgsConstructor
public class IndirizzoController {

    private final IndirizzoMapper indirizzoMapper;
    private final IndirizzoService indirizzoService;


    @GetMapping("/{idIndirizzo}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<IndirizzoResponseDTO> trovareIndirizzoDallId(
            @PathVariable UUID idIndirizzo
    ) {
        return ResponseEntity.ok(
                indirizzoMapper.toDTO(
                        indirizzoService.trovareIndirizzoDallId(idIndirizzo)
                )
        );
    }//trovareIndirizzoDallId

    // === TROVA TUTTI GLI INDIRIZZI DI UN UTENTE ===
    @GetMapping("/utente")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<IndirizzoResponseDTO>> trovaTuttiGliIndirizziDallIdUtente(
            @PathVariable UUID idIndirizzo
    ) {
        return ResponseEntity.ok(
                indirizzoMapper.toDTOs(
                        indirizzoService.trovaTuttiGliIndirizziDallIdUtente(idIndirizzo)
                )
        );
    }//trovaTuttiGliIndirizziDallIdUtente


    @GetMapping("/tipo")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<IndirizzoResponseDTO>> trovareIndirizziPerTipo(
            @PathVariable UUID idUtente,@RequestBody IndirizzoRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                indirizzoMapper.toDTOs(
                        indirizzoService.trovareIndirizziPerTipo(
                                idUtente,
                                requestDTO.getTipo()
                        )
                )
        );
    }//trovareIndirizziPerTipo


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<IndirizzoResponseDTO>> prendiTuttiGliIndirizzi() {
        return ResponseEntity.ok(
                indirizzoMapper.toDTOs(
                        indirizzoService.prendiTuttiGliIndirizzi()
                )
        );
    }//prendiTuttiGliIndirizzi


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<IndirizzoResponseDTO> aggiungiUnIndirizzo(
            @PathVariable UUID idUtente, @RequestBody IndirizzoRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                indirizzoMapper.toDTO(
                        indirizzoService.aggiungiUnIndirizzo(
                                idUtente,
                                indirizzoMapper.toEntity(requestDTO)
                        )
                )
        );
    }//aggiungiUnIndirizzo

    // === AGGIUNGI LISTA DI INDIRIZZI A UN UTENTE ===
    @PostMapping("/lista")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<IndirizzoResponseDTO>> aggiungiIndirizzi(
            @RequestBody List<IndirizzoRequestDTO> requestDTOs
    ) {
        // qui assumo che tu voglia passare lo stesso utente per tutti
        UUID idUtente = requestDTOs.get(0).getUtenteId();
        return ResponseEntity.ok(
                indirizzoMapper.toDTOs(
                        indirizzoService.aggiungiIndirizzi(
                                idUtente,
                                requestDTOs.stream()
                                        .map(indirizzoMapper::toEntity)
                                        .toList()
                        )
                )
        );
    }//aggiungiIndirizzi


    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<IndirizzoResponseDTO> modificaIndirizzo(
            @PathVariable UUID idIndirizzo, @PathVariable UUID idUtente,  @RequestBody IndirizzoRequestDTO requestDTO) {
        return ResponseEntity.ok(
                indirizzoMapper.toDTO(
                        indirizzoService.modificaIndirizzo(
                                idIndirizzo,
                                idUtente,
                                indirizzoMapper.toEntity(requestDTO)
                        )
                )
        );
    }//modificaIndirizzo


    @DeleteMapping("/{idIndirizzo}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> eliminaIndirizzo(
            @PathVariable UUID idIndirizzo
    ) {
        indirizzoService.eliminaIndirizzo(idIndirizzo);
        return ResponseEntity.noContent().build();
    }//eliminaIndirizzo


    @DeleteMapping("/utente/indirizzo")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> eliminaGliIndirizziDiUnUtente(
            @PathVariable UUID idIndirizzo, @PathVariable UUID idUtente
    ) {
        indirizzoService.eliminaGliIndirizziDiUnUtente(
                idUtente,
                idIndirizzo
        );
        return ResponseEntity.noContent().build();
    }//eliminaGliIndirizziDiUnUtente


    @DeleteMapping("/utente")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> eliminaTuttiGliIndirizziDiUnUtente(@PathVariable UUID idIndirizzo) {
        indirizzoService.eliminaTuttiGliIndirizziDiUnUtente(idIndirizzo);
        return ResponseEntity.noContent().build();
    }//eliminaTuttiGliIndirizziDiUnUtente


    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminaTuttiGliIndirizzi() {
        indirizzoService.eliminaTuttiGliIndirizzi();
        return ResponseEntity.noContent().build();
    }//eliminaTuttiGliIndirizzi

}//IndirizzoController

