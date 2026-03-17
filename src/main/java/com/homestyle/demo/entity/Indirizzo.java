package com.homestyle.demo.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.*;

import com.homestyle.demo.entity.Utente.Ruolo;

@Data
@Entity
@Table(name = "indirizzo")
public class Indirizzo {
	
	public enum Tipo{
		RESIDENZA,
		SPEDIZIONE,
		FATTURAZIONE
	}//Tipo
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "utente_id", nullable = false)
	private Utente utente;
	
	@Column(name = "nazione")
	private String nazione;
	
	@Column(name = "via")
	private String via;
	
	@Column(name = "numero_civico")
	private String numeroCivico;
	
	@Column(name = "citta")
	private String citta;
	
	@Column(name = "provincia")
	private String provincia;
	
	@Column(name = "cap")
	private String cap;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false)
	private Tipo tipo;
	
	//RELAZIONE
	//indirizzo->Ordine(1:n)
	@OneToMany(mappedBy = "indirizzoSpedizione", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Ordine> ordini = new ArrayList<>();
	
	@OneToMany(mappedBy = "indirizzoReso", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reso> resi = new ArrayList<>();

}//Indirizzo
