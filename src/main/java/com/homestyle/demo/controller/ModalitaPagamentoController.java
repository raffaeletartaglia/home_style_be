package com.homestyle.demo.controller;

import com.homestyle.demo.dto.request.ModalitaPagamentoRequestDTO;
import com.homestyle.demo.dto.response.ModalitaPagamentoResponseDTO;
import com.homestyle.demo.mapper.ModalitaPagamentoMapper;
import com.homestyle.demo.service.ModalitaPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/modalita-pagamento")
@RequiredArgsConstructor
public class ModalitaPagamentoController {

    private final ModalitaPagamentoMapper modalitaPagamentoMapper;
    private final ModalitaPagamentoService modalitaPagamentoService;


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ModalitaPagamentoResponseDTO>> getAllModalitaPagamento() {
        return ResponseEntity.ok(
                modalitaPagamentoMapper.toDTOs(modalitaPagamentoService.getAllModalitaPagamento())
        );

    }//getAllModalitaPagamento


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ModalitaPagamentoResponseDTO> getModalitaPagamentoById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                modalitaPagamentoMapper.toDTO(
                        modalitaPagamentoService.getModalitaPagamentoById(id)
                )
        );
    }//getModalitaPagamentoById


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ModalitaPagamentoResponseDTO> creaModalitaPagamento(
            @RequestBody ModalitaPagamentoRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                modalitaPagamentoMapper.toDTO(
                        modalitaPagamentoService.creaModalitaPagamento(
                                modalitaPagamentoMapper.toEntity(requestDTO)
                        )
                )
        );
    }//creaModalitaPagamento


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ModalitaPagamentoResponseDTO> modificaModalitaPagamento(
            @PathVariable UUID id,
            @RequestBody ModalitaPagamentoRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                modalitaPagamentoMapper.toDTO(
                        modalitaPagamentoService.modificaModalitaPagamento(
                                id,
                                modalitaPagamentoMapper.toEntity(requestDTO)
                        )
                )
        );
    }//modificaModalitaPagamento


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteModalitaPagamento(
            @PathVariable UUID id
    ) {
        modalitaPagamentoService.deleteModalitaPagamento(id);
        return ResponseEntity.noContent().build();
    }//deleteModalitaPagamento

}//ModalitaPagamentoController

