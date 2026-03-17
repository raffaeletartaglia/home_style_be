package com.homestyle.demo.entity;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString(exclude = {"dettagliOrdine", "pagamento", "spedizione", "movimentiMagazzino"})
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "ordine")
public class Ordine {

    public enum StatoOrdine {
        IN_ELABORAZIONE,
        SPEDITO,
        CONSEGNATO,
        ANNULLATO
    }//StatoOrdine

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_ordine", nullable = false)
    private StatoOrdine statoOrdine;

    @Column(name = "data_ordine", updatable = false)
    private LocalDateTime dataOrdine;

    @Column(name = "prezzo_totale", nullable = false)
    private BigDecimal prezzoTotale;

    @PrePersist
    protected void onCreate() {
        this.dataOrdine = LocalDateTime.now();
    }

    @Column(name = "data_prevista_consegna")
    private LocalDate dataPrevistaConsegna;

    @ManyToOne
    @JoinColumn(name = "indirizzo_spedizione_id", nullable = true)
    private Indirizzo indirizzoSpedizione;

    // RELAZIONI

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DettaglioOrdine> dettagliOrdine = new ArrayList<>();

    @OneToOne(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    private Pagamento pagamento;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Spedizione> spedizione;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovimentoMagazzino> movimentiMagazzino = new ArrayList<>();
    
}//Ordine
