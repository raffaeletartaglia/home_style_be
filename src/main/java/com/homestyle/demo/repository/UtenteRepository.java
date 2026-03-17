package com.homestyle.demo.repository;

import com.homestyle.demo.entity.Utente;
import com.homestyle.demo.entity.Utente.Ruolo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {

    Optional<Utente> findByEmail(String email);

    @Query("SELECT u.id FROM Utente u WHERE u.email = :email")
    
    boolean existByEmail(String email);
    
    boolean existByNumeroTelefono(String numeroTelefono);
    
    List<Utente> findByRuolo(Ruolo ruolo);
    
    Optional<Utente> findByNumeroTelefono(String numeroTelefono);

}//UtenteRepository