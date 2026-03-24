package com.homestyle.demo.controller;
import com.homestyle.demo.dto.request.CartaPagamentoRequestDTO;
import com.homestyle.demo.dto.response.CartaPagamentoResponseDTO;
import com.homestyle.demo.mapper.CartaPagamentoMapper;
import com.homestyle.demo.service.CartaPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carta-pagamento")
@RequiredArgsConstructor
public class CartaPagamentoController {

    private final CartaPagamentoMapper cartaPagamentoMapper;
    private final CartaPagamentoService cartaPagamentoService;


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartaPagamentoResponseDTO> addCartaPagamento(
            @RequestBody CartaPagamentoRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                cartaPagamentoMapper.toDTO(
                        cartaPagamentoService.addCartaPagamento(
                                cartaPagamentoMapper.toEntity(requestDTO)
                        )
                )
        );
    }//addCartaPagamento


    @GetMapping("/utente")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CartaPagamentoResponseDTO>> getCarteByUtente(
            @RequestBody CartaPagamentoRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                cartaPagamentoMapper.toDTOs(
                        cartaPagamentoService.getCarteByUtente(requestDTO.getUtenteId())
                )
        );
    }//getCarteByUtente


    @GetMapping("/{cartaId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CartaPagamentoResponseDTO> getCartaById(
            @PathVariable UUID cartaId
    ) {
        return ResponseEntity.ok(
                cartaPagamentoMapper.toDTO(
                        cartaPagamentoService.getCartaById(cartaId)
                )
        );
    }//getCartaById


    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteCartaPagamento(@PathVariable UUID idCartaPagamento) {
        cartaPagamentoService.deleteCartaPagamento(idCartaPagamento);
        return ResponseEntity.noContent().build();
    }//deleteCartaPagamento

}//CartaPagamentoController

