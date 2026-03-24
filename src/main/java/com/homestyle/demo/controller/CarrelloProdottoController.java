package com.homestyle.demo.controller;
import com.homestyle.demo.dto.request.CarrelloProdottoRequestDTO;
import com.homestyle.demo.dto.request.CategoriaRequestDTO;
import com.homestyle.demo.dto.response.CarrelloProdottoResponseDTO;
import com.homestyle.demo.dto.response.CategoriaResponseDTO;
import com.homestyle.demo.dto.response.UtenteResponseDTO;
import com.homestyle.demo.entity.CarrelloProdotto;
import com.homestyle.demo.mapper.CarrelloProdottoMapper;
import com.homestyle.demo.service.CarrelloProdottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carrello-prodotto")
@RequiredArgsConstructor
public class CarrelloProdottoController {

    private CarrelloProdottoMapper carrelloProdottoMapper;
    private CarrelloProdottoService carrelloProdottoService;

    @GetMapping("/{idCarrelloProdotto}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CarrelloProdottoResponseDTO> trovaCarrelloProdottoPerId(@PathVariable UUID idCarrelloProdotto){
        return ResponseEntity.ok(
                carrelloProdottoMapper.toDTO(
                        carrelloProdottoService.trovaCarrelloProdottoPerId(idCarrelloProdotto)
                )
        );
    }//trovaCarrelloProdottoPerId

    @GetMapping("/{idCarrelloProdotto}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CarrelloProdottoResponseDTO>> trovaProdottiDelCarrello (@RequestBody CarrelloProdottoRequestDTO requestDTO) {
        return ResponseEntity.ok(
                carrelloProdottoMapper.toDTOs(
                        carrelloProdottoService.trovaProdottiDelCarrello(requestDTO.getCarrelloId())
                )
        );
    }//trovaProdottiDelCarrello
    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CarrelloProdottoResponseDTO> aggiornaQuantita(@PathVariable UUID idCarrelloProdotto, @RequestParam Integer quantita){
        return ResponseEntity.ok(
                carrelloProdottoMapper.toDTO(
                        carrelloProdottoService.aggiornaQuantita(idCarrelloProdotto,quantita)
                )
        );
    }// aggiornaQuantita
    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CarrelloProdottoResponseDTO> rimuoviProdottoDalCarrello(@PathVariable UUID idCarrelloProdotto){
        carrelloProdottoService.rimuoviProdottoDalCarrello(idCarrelloProdotto);
        return ResponseEntity.noContent().build();
    }//rimuoviProdottoDalCarrello

    @GetMapping("/{idUtente}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BigDecimal> calcolaTotaleCarrello(@PathVariable UUID idCarrelloProdotto){
        return ResponseEntity.ok(
               (carrelloProdottoService.calcolaTotaleCarrello(idCarrelloProdotto))
        );
    }//calcolaTotaleCarrello


}//CarrelloProdottoController
