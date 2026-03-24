package com.homestyle.demo.controller;

import com.homestyle.demo.dto.request.PagamentoRequestDTO;
import com.homestyle.demo.dto.response.PagamentoResponseDTO;
import com.homestyle.demo.mapper.PagamentoMapper;
import com.homestyle.demo.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pagamento")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoMapper pagamentoMapper;
    private final PagamentoService pagamentoService;


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PagamentoResponseDTO> creaPagamento(
            @RequestBody PagamentoRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                pagamentoMapper.toDTO(
                        pagamentoService.creaPagamento(
                                pagamentoMapper.toEntity(requestDTO)
                        )
                )
        );
    }//creaPagamento


    @GetMapping("/{pagamentoId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PagamentoResponseDTO> getPagamentoById(
            @PathVariable UUID pagamentoId
    ) {
        return ResponseEntity.ok(
                pagamentoMapper.toDTO(
                        pagamentoService.getPagamentoById(pagamentoId)
                )
        );
    }//getPagamentoById


    @GetMapping("/ordine/{ordineId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PagamentoResponseDTO> getPagamentoByOrdine(
            @PathVariable UUID ordineId
    ) {
        return ResponseEntity.ok(
                pagamentoMapper.toDTO(
                        pagamentoService.getPagamentoByOrdine(ordineId)
                )
        );
    }//getPagamentoByOrdine


    @PutMapping("/annulla/{pagamentoId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PagamentoResponseDTO> annullaPagamento(
            @PathVariable UUID pagamentoId
    ) {
        return ResponseEntity.ok(
                pagamentoMapper.toDTO(
                        pagamentoService.annullaPagamento(pagamentoId)
                )
        );
    }//annullaPagamento


    @PutMapping("/effettua/{pagamentoId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PagamentoResponseDTO> effettuaPagamento(
            @PathVariable UUID pagamentoId
    ) {
        return ResponseEntity.ok(
                pagamentoMapper.toDTO(
                        pagamentoService.effettuaPagamento(pagamentoId)
                )
        );
    }//effettuaPagamento


    @PutMapping("/rata/{pagamentoId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PagamentoResponseDTO> pagaRata(
            @PathVariable UUID pagamentoId
    ) {
        return ResponseEntity.ok(
                pagamentoMapper.toDTO(
                        pagamentoService.pagaRata(pagamentoId)
                )
        );
    }//pagaRata

}//PagamentoController

