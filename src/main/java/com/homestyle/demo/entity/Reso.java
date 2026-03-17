package com.homestyle.demo.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "reso")
public class Reso {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="id")
    private UUID id;

    // 1:1 con DettaglioOrdine 
    @OneToOne
    @JoinColumn(name = "dettaglio_ordine_id", nullable = false, unique = true)
    private DettaglioOrdine dettaglioOrdine;

    @Column(name = "data_reso_prevista")
    private LocalDate dataResoPrevista;

    @Column(name = "ora_ritiro_reso")
    private LocalTime oraRitiroReso;

    @Column(name = "motivo")
    private String motivo;

    // molti resi possono usare lo stesso indirizzo
    @ManyToOne
    @JoinColumn(name = "indirizzo_id")
    private Indirizzo indirizzoReso;

    // Reso -> MovimentoMagazzino 1:N
    @OneToMany(mappedBy = "reso")
    private List<MovimentoMagazzino> movimentoMagazzini = new ArrayList<>();

}//Reso