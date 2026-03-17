package com.homestyle.demo.repository;

import com.homestyle.demo.entity.Carrello;
import com.homestyle.demo.entity.Carrello.Stato;
import com.homestyle.demo.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
public interface CarrelloRepository extends JpaRepository<Carrello, UUID> {

    // Trova il carrello di un utente
    Optional<Carrello> findByUtente(Utente utente);

    // Trova tutti i carrelli di uno stato specifico
    List<Carrello> findByStato(Carrello.Stato stato);

	Optional<Carrello> findByUtente_IdAndStato(UUID idUtente, Stato attivo);
    
}//CarrelloRepository
