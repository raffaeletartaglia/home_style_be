package com.homestyle.demo.repository;


import com.homestyle.demo.entity.DettaglioOrdine;
import com.homestyle.demo.entity.Ordine;
import com.homestyle.demo.entity.Ordine.StatoOrdine;
import com.homestyle.demo.entity.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DettaglioOrdineRepository extends JpaRepository<DettaglioOrdine, UUID> {

    // Trova tutti i dettagli di un ordine
    List<DettaglioOrdine> findByOrdine(Ordine ordine);

    // Trova il dettaglio di un prodotto specifico in un ordine
    Optional<DettaglioOrdine> findByOrdineAndProdotto(Ordine ordine, Prodotto prodotto);

    // Trova tutti i dettagli di un prodotto (utile per statistiche o report)
    List<DettaglioOrdine> findByProdotto(Prodotto prodotto);
    
    boolean existsByProdottoIdAndOrdineStatoOrdineIn(UUID prodottoId, List<StatoOrdine> stati);
    
    
}//DettaglioOrdineRepository