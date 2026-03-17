package com.homestyle.demo.controller;

import com.homestyle.demo.dto.request.CarrelloProdottoRequestDTO;
import com.homestyle.demo.dto.response.CarrelloProdottoResponseDTO;
import com.homestyle.demo.dto.response.CarrelloResponseDTO;
import com.homestyle.demo.entity.Carrello;
import com.homestyle.demo.entity.CarrelloProdotto;
import com.homestyle.demo.mapper.CarrelloMapper;
import com.homestyle.demo.mapper.CarrelloProdottoMapper;
import com.homestyle.demo.repository.CarrelloProdottoRepository;
import com.homestyle.demo.service.CarrelloProdottoService;
import com.homestyle.demo.service.CarrelloService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/carrello")
@RequiredArgsConstructor
public class CarrelloController {

    private final CarrelloService carrelloService;
    private final CarrelloProdottoService carrelloProdottoService;

    private final CarrelloMapper carrelloMapper;
    private final CarrelloProdottoMapper carrelloProdottoMapper;


    /**
     * Metodo per recuperare un carrello in base all'id dell'utente
     * @param idUtente
     * @return
     */
    @GetMapping("/utente/{idUtente}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CarrelloResponseDTO> getCarrelloAttivoPerUtente(@PathVariable UUID idUtente) {
        return ResponseEntity.ok(carrelloMapper.toDTO(carrelloService.trovaCarelloPerUtente(idUtente)));
    }

    /**
     * Metodo per recuperare un carrello mediante l'id del carrello
     * @param idCarrello
     * @return
     */
    @GetMapping("/{idCarrello}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CarrelloResponseDTO> getCarrelloPerId(@PathVariable UUID idCarrello) {
        return ResponseEntity.ok(carrelloMapper.toDTO(carrelloService.trovaCarrelloPerId(idCarrello)));
    }

    /**
     * Metodo per eliminare tutti gli elementi all'interno di un carrello
     * @param idCarrello
     * @return
     */
    @DeleteMapping("/{idCarrello}/svuota")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> svuotaCarrello(@PathVariable UUID idCarrello) {
        carrelloService.svuotaCarrello(idCarrello);
        return ResponseEntity.noContent().build();
    }

    /**
     * Elimina totalmente un carrello
     * @param idCarrello
     * @return
     */
    @DeleteMapping("/{idCarrello}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminaCarrello(@PathVariable UUID idCarrello) {
        carrelloService.eliminaCarello(idCarrello);
        return ResponseEntity.noContent().build();
    }

    /**
     * Calcolo del totale degli ordini presenti in un carrello
     * @param idCarrello
     * @return
     */
    @GetMapping("/{idCarrello}/totale")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<BigDecimal> calcolaTotale(@PathVariable UUID idCarrello) {
        return ResponseEntity.ok(carrelloProdottoService.calcolaTotaleCarrello(idCarrello));
    }


    /**
     * Restituisce la lista di prodotti presente in un carrello
     * @param idCarrello
     * @return
     */
    @GetMapping("/{idCarrello}/prodotti")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CarrelloProdottoResponseDTO>> getProdottiDelCarrello(@PathVariable UUID idCarrello) {
        return ResponseEntity.ok(carrelloProdottoMapper.toDTOs(carrelloProdottoService.trovaProdottiDelCarrello(idCarrello)));
    }


    /**
     * Aggiunge uno o più prodotti nel carrello
     * @param idUtente
     * @param requestDTO
     * @return
     */
    @PostMapping("/utente/{idUtente}/prodotti")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CarrelloResponseDTO> aggiungiProdottiAlCarrello(
            @PathVariable UUID idUtente,
            @RequestBody List<CarrelloProdottoRequestDTO> requestDTO) {

        return ResponseEntity.ok(
                carrelloMapper.toDTO(
                        carrelloService.aggiuntaProdottiCarrello(
                                idUtente, carrelloProdottoMapper.toEntities(requestDTO)
                        )
                )
        );
    }

    /**
     * Aggiorna la quantità di una singola riga del carrello
     * @param idCarrelloProdotto
     * @param quantita
     * @return
     */
    @PutMapping("/prodotti/{idCarrelloProdotto}/quantita")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CarrelloProdottoResponseDTO> aggiornaQuantita(
            @PathVariable UUID idCarrelloProdotto,
            @RequestParam Integer quantita) {


        return ResponseEntity.ok(
                carrelloProdottoMapper.toDTO(
                        (carrelloProdottoService.aggiornaQuantita(idCarrelloProdotto, quantita))
                ));
    }

    /**
     * Rimuove un elemento dal carrello
     * @param idCarrelloProdotto
     * @return
     */
    @DeleteMapping("/prodotti/{idCarrelloProdotto}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> rimuoviProdottoDalCarrello(@PathVariable UUID idCarrelloProdotto) {
        carrelloProdottoService.rimuoviProdottoDalCarrello(idCarrelloProdotto);
        return ResponseEntity.noContent().build();
    }
}
