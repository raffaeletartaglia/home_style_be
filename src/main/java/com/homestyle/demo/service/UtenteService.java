package com.homestyle.demo.service;

import java.util.UUID;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.homestyle.demo.entity.Utente;
import com.homestyle.demo.repository.UtenteRepository;
import com.homestyle.demo.entity.Ordine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import utils.ControlliUtils;
import exception.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UtenteService {

    private final UtenteRepository utenteRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    
    public Utente prendiDatiUtente(UUID idUtente) {
        log.info("Recupero dati utente con id={}", idUtente);

        ControlliUtils.controlloIdValido(idUtente, "Utente");

        return utenteRepo.findById(idUtente)
                .orElseThrow(() -> {
                    log.error("Utente non trovato con id={}", idUtente);
                    return new EntitaNonTrovataException("Utente non trovato");
                });
    }//prendiDatiUtente

    
    public Utente registrazioneUtente(Utente utente) {
        log.info("Registrazione nuovo utente con email={}", utente.getEmail());

        if (!controllaNomeUtente(utente.getNome())) {
            log.error("Nome non valido: {}", utente.getNome());
            throw new ValoreNonValidoException("Nome non valido");
        }

        if (!controllaCognomeUtente(utente.getCognome())) {
            log.error("Cognome non valido: {}", utente.getCognome());
            throw new ValoreNonValidoException("Cognome non valido");
        }

        if (!controllaEmailUtente(utente.getEmail())) {
            log.error("Email non valida: {}", utente.getEmail());
            throw new ValoreNonValidoException("Email non valida");
        }

        if (!controllaPasswordUtente(utente.getPassword())) {
            log.error("Password non valida");
            throw new ValoreNonValidoException("Password non valida");
        }

        if (utente.getNumeroTelefono() != null &&
            !controlloNumeroTelefonoUtente(utente.getNumeroTelefono())) {
            log.error("Numero telefono non valido: {}", utente.getNumeroTelefono());
            throw new ValoreNonValidoException("Numero di telefono non valido");
        }

        if (utenteRepo.existByEmail(utente.getEmail())) {
            log.error("Email già registrata: {}", utente.getEmail());
            throw new EmailEsistenteException("Email già registrata");
        }

        if (utente.getNumeroTelefono() != null &&
            utenteRepo.existByNumeroTelefono(utente.getNumeroTelefono())) {
            log.error("Numero telefono già registrato: {}", utente.getNumeroTelefono());
            throw new NumeroEsistenteException("Numero telefono già registrato");
        }

        utente.setPassword(passwordEncoder.encode(utente.getPassword()));

        Utente salvato = utenteRepo.save(utente);

        log.info("Utente registrato con successo id={}", salvato.getId());

        return salvato;
    }//registrazioneUtente

    
    public Utente modificaUtente(UUID idUtente, Utente utente) {
        log.info("Modifica utente con id={}", idUtente);

        ControlliUtils.controlloIdValido(idUtente, "Utente");

        Utente vecchioUtente = utenteRepo.findById(idUtente)
                .orElseThrow(() -> {
                    log.error("Utente non trovato con id={}", idUtente);
                    return new EntitaNonTrovataException("Utente non trovato");
                });

        if (!controllaNomeUtente(utente.getNome())) {
            log.error("Nome non valido: {}", utente.getNome());
            throw new ValoreNonValidoException("Nome non valido");
        }

        if (!controllaCognomeUtente(utente.getCognome())) {
            log.error("Cognome non valido: {}", utente.getCognome());
            throw new ValoreNonValidoException("Cognome non valido");
        }

        if (utente.getNumeroTelefono() != null &&
            !controlloNumeroTelefonoUtente(utente.getNumeroTelefono())) {
            log.error("Numero telefono non valido: {}", utente.getNumeroTelefono());
            throw new ValoreNonValidoException("Numero di telefono non valido");
        }

        if (utente.getNumeroTelefono() != null &&
            utenteRepo.existByNumeroTelefono(utente.getNumeroTelefono()) &&
            !utente.getNumeroTelefono().equals(vecchioUtente.getNumeroTelefono())) {

            log.error("Numero telefono già registrato: {}", utente.getNumeroTelefono());
            throw new NumeroEsistenteException("Numero telefono già registrato");
        }

        vecchioUtente.setNome(utente.getNome());
        vecchioUtente.setCognome(utente.getCognome());
        vecchioUtente.setNumeroTelefono(utente.getNumeroTelefono());

        Utente aggiornato = utenteRepo.save(vecchioUtente);

        log.info("Utente aggiornato con successo id={}", aggiornato.getId());

        return aggiornato;
    }//modificaUtente

    
    public Utente modificaEmailUtente(UUID idUtente, String nuovaEmail) {
        log.info("Modifica email utente id={} nuovaEmail={}", idUtente, nuovaEmail);

        ControlliUtils.controlloIdValido(idUtente, "Utente");

        Utente utente = utenteRepo.findById(idUtente)
                .orElseThrow(() -> {
                    log.error("Utente non trovato con id={}", idUtente);
                    return new EntitaNonTrovataException("Utente non trovato");
                });

        if (!controllaEmailUtente(nuovaEmail)) {
            log.error("Email non valida: {}", nuovaEmail);
            throw new ValoreNonValidoException("Email non valida");
        }

        if (utenteRepo.existByEmail(nuovaEmail) &&
            !utente.getEmail().equals(nuovaEmail)) {

            log.error("Email già registrata: {}", nuovaEmail);
            throw new EmailEsistenteException("Email già registrata");
        }

        utente.setEmail(nuovaEmail);

        Utente aggiornato = utenteRepo.save(utente);

        log.info("Email aggiornata con successo per utente id={}", aggiornato.getId());

        return aggiornato;
    }//modificaEmailUtente

   
    public Utente modificaPasswordUtente(UUID idUtente, String nuovaPassword) {
        log.info("Modifica password utente id={}", idUtente);

        ControlliUtils.controlloIdValido(idUtente, "Utente");

        Utente utente = utenteRepo.findById(idUtente)
                .orElseThrow(() -> {
                    log.error("Utente non trovato con id={}", idUtente);
                    return new EntitaNonTrovataException("Utente non trovato");
                });

        if (!controllaPasswordUtente(nuovaPassword)) {
            log.error("Password non valida");
            throw new ValoreNonValidoException("Password non valida");
        }

        utente.setPassword(passwordEncoder.encode(nuovaPassword));

        Utente aggiornato = utenteRepo.save(utente);

        log.info("Password aggiornata con successo per utente id={}", aggiornato.getId());

        return aggiornato;
    }//modificaPassword

    
    public void deleteUtente(UUID idUtente) {
        log.info("Eliminazione utente id={}", idUtente);

        ControlliUtils.controlloIdValido(idUtente, "Utente");

        Utente utente = utenteRepo.findById(idUtente)
                .orElseThrow(() -> {
                    log.error("Utente non trovato con id={}", idUtente);
                    return new EntitaNonTrovataException("Utente non trovato");
                });

        boolean ordiniInLavorazione = utente.getOrdini().stream()
                .anyMatch(ordine -> ordine.getStatoOrdine() == Ordine.StatoOrdine.IN_ELABORAZIONE);

        if (ordiniInLavorazione) {
            log.error("Impossibile eliminare utente id={} - ordini in lavorazione presenti", idUtente);
            throw new ValoreNonValidoException(
                "Impossibile eliminare l'utente: ci sono ordini in lavorazione");
        }

        utenteRepo.deleteById(idUtente);

        log.info("Utente eliminato con successo id={}", idUtente);
    }//deleteUtente

    
    private boolean controllaNomeUtente(String nomeUtente) {
        log.debug("Controllo nome utente: {}", nomeUtente);
        return nomeUtente != null && !nomeUtente.isEmpty() && nomeUtente.length() < 100;
    }

    private boolean controllaCognomeUtente(String cognomeUtente) {
        log.debug("Controllo cognome utente: {}", cognomeUtente);
        return cognomeUtente != null && !cognomeUtente.isEmpty() && cognomeUtente.length() < 100;
    }

    private boolean controllaEmailUtente(String emailUtente) {
        log.debug("Controllo email utente: {}", emailUtente);
        return ControlliUtils.emailValida(emailUtente) && emailUtente.length() < 150;
    }

    private boolean controllaPasswordUtente(String passUtente) {
        log.debug("Controllo password utente");
        return ControlliUtils.passValida(passUtente) && passUtente.length() < 255;
    }

    private boolean controlloNumeroTelefonoUtente(String numeroTelUtente) {
        log.debug("Controllo numero telefono: {}", numeroTelUtente);
        return ControlliUtils.numeroTelefonoValido(numeroTelUtente) && numeroTelUtente.length() < 20;
    }

}//UtenteService