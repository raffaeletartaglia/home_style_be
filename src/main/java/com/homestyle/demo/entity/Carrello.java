package com.homestyle.demo.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "carrello")
public class Carrello {

    public enum Stato {
        ATTIVO,
        CONVERTITO,
        ABBANDONATO
    }//Stato

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    //1:1 con Utente
    @OneToOne
    @JoinColumn(name = "utente_id", nullable = false, unique = true)
    private Utente utente;


    @Column(name = "data_creazione", updatable = false)
    private LocalDateTime dataCreazione;

    @PrePersist
    protected void onCreate() {
        this.dataCreazione = LocalDateTime.now();
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private Stato stato;
    
 // Carrello -> CarrelloProdotto 1:n
    @OneToMany(mappedBy = "carrello", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarrelloProdotto> prodotti = new ArrayList<>();
    
}//Carrello