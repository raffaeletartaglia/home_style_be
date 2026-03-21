package com.homestyle.demo.controller;
import com.homestyle.demo.dto.request.CategoriaRequestDTO;
import com.homestyle.demo.dto.request.UtenteRequestDTO;
import com.homestyle.demo.dto.response.CategoriaResponseDTO;
import com.homestyle.demo.dto.response.UtenteResponseDTO;
import com.homestyle.demo.entity.Categoria;
import com.homestyle.demo.entity.Utente;
import com.homestyle.demo.mapper.UtenteMapper;
import com.homestyle.demo.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/utente")
@RequiredArgsConstructor
public class UtenteController {

    private UtenteMapper utenteMapper;
    private UtenteService utenteService;

    @GetMapping("/{idUtente}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UtenteResponseDTO> trovaUtentePerId(@PathVariable UUID idUtente){
        return ResponseEntity.ok(
                utenteMapper.toDTO(
                        utenteService.prendiDatiUtente(idUtente)
                )
        );
    }//trovaUtentePerId

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UtenteResponseDTO> registraUtente(@RequestBody UtenteRequestDTO requestDTO){
        Utente nuovo = utenteMapper.toEntity(requestDTO);
        Utente salvato = utenteService.registrazioneUtente(nuovo);
        return ResponseEntity.ok(utenteMapper.toDTO(salvato));
    }//registraUtente

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UtenteResponseDTO> modificaUtente(@PathVariable UUID idUtente, @RequestBody UtenteRequestDTO requestDTO) {
        return ResponseEntity.ok(
                utenteMapper.toDTO(
                        utenteService.modificaUtente(idUtente, utenteMapper.toEntity(requestDTO))));
    }//modificaUtente



}//UtenteController
