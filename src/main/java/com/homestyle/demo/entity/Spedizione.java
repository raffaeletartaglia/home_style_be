package com.homestyle.demo.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "spedizione")
public class Spedizione {

    public enum StatoSpedizione {
        PREPARAZIONE,
        SPEDITO,
        IN_TRANSITO,
        CONSEGNATO
    }//StatoSpedizione

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id")
    private UUID id;

    
    @ManyToOne
    @JoinColumn(name = "ordine_id", nullable = false)
    private Ordine ordine;

    @Column(name = "corriere")
    private String corriere;

    @Column(name = "codice_tracking")
    private String codiceTracking;

    @Column(name = "data_spedizione")
    private LocalDateTime dataSpedizione;

    @Column(name = "data_consegna_effettiva")
    private LocalDateTime dataConsegnaEffettiva;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_spedizione")
    private StatoSpedizione statoSpedizione;
    
}//Spedizione