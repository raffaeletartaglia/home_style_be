package com.homestyle.demo.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "carrello_prodotto", uniqueConstraints = @UniqueConstraint(columnNames = {"carrello_id", "prodotto_id"}))
public class CarrelloProdotto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "carrello_id", nullable = false)
    private Carrello carrello;

    @ManyToOne
    @JoinColumn(name = "prodotto_id", nullable = false)
    private Prodotto prodotto;

    
    
    @Column(name ="quantita", nullable = false)
    private Integer quantita;

    @Column(name = "data_aggiunta", updatable = false)
    private LocalDateTime dataAggiunta;

    @PrePersist
    protected void onCreate() {
        this.dataAggiunta = LocalDateTime.now();
    }
    
}//CarrelloProdotto
