package com.homestyle.demo.controller;

import com.homestyle.demo.dto.response.WishlistResponseDTO;
import com.homestyle.demo.entity.Wishlist;
import com.homestyle.demo.mapper.WishlistMapper;
import com.homestyle.demo.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    private final WishlistMapper wishlistMapper;

    /**
     * USER/ADMIN: recupera la wishlist di un utente
     */
    @GetMapping("/utente/{idUtente}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<WishlistResponseDTO>> getWishlistPerUtente(@PathVariable UUID idUtente) {
        return ResponseEntity.ok(
                wishlistMapper.toDTOs(wishlistService.trovaWishlistPerUtente(idUtente))
        );
    }

    /**
     * USER/ADMIN: aggiunge un prodotto alla wishlist (o aggiorna solo la priorità se già presente)
     * priorita è opzionale: se assente, il service usa MEDIA come default.
     */
    @PostMapping("/utente/{idUtente}/prodotto/{idProdotto}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<WishlistResponseDTO> aggiungiAWishlist(
            @PathVariable UUID idUtente,
            @PathVariable UUID idProdotto,
            @RequestParam(required = false) Wishlist.Priorita priorita) {

        Wishlist wishlist = wishlistService.aggiungiAWishlist(idUtente, idProdotto, priorita);
        return ResponseEntity.ok(wishlistMapper.toDTO(wishlist));
    }

    /**
     * USER/ADMIN: aggiorna la priorità di un elemento wishlist
     */
    @PutMapping("/{idWishlist}/priorita")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<WishlistResponseDTO> aggiornaPriorita(
            @PathVariable UUID idWishlist,
            @RequestParam Wishlist.Priorita nuovaPriorita) {

        Wishlist wishlist = wishlistService.aggiornaPriorita(idWishlist, nuovaPriorita);
        return ResponseEntity.ok(wishlistMapper.toDTO(wishlist));
    }

    /**
     * USER/ADMIN: rimuove un singolo elemento dalla wishlist
     */
    @DeleteMapping("/{idWishlist}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> rimuoviDaWishlist(@PathVariable UUID idWishlist) {
        wishlistService.rimuoviDaWishlist(idWishlist);
        return ResponseEntity.noContent().build();
    }

    /**
     * USER/ADMIN: svuota la wishlist dell'utente
     */
    @DeleteMapping("/utente/{idUtente}/svuota")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> svuotaWishlistPerUtente(@PathVariable UUID idUtente) {
        wishlistService.svuotaWishlistPerUtente(idUtente);
        return ResponseEntity.noContent().build();
    }
}//WishlistController
