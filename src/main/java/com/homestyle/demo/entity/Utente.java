package com.homestyle.demo.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "utente")
public class Utente {
	
	public enum Ruolo{
		USER,
		ADMIN
	}//Ruolo
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "cognome", nullable = false)
	private String cognome;
	
	@Column(name = "email", nullable= false, unique = true)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ruolo", nullable = false)
	private Ruolo ruolo;
	
	@Column(name = "data_creazione", updatable = false)
	private LocalDateTime dataCreazione;

	@PrePersist
	protected void onCreate() {
	    this.dataCreazione = LocalDateTime.now();
	}
	
	@Column(name = "numero_telefono")
	private String numeroTelefono;
	
	//RELAZIONI
	
	//UTENTE->ORDINE (1:n)
	@OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Ordine> ordini = new ArrayList<>();
	
	//Utente->Carrello (1:1, lato non proprietario)
	@OneToOne(mappedBy="utente", cascade = CascadeType.ALL, orphanRemoval = true)
	private Carrello carrello;
	
	//Utente->Recensioni(1:n)
	@OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Recensione> recensioni = new ArrayList<>();
	
	//Utente _>WishList(1:n)
	@OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Wishlist> wishlist = new ArrayList<>();
	
	//Utente->Prenotazione(1:n)
	@OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Prenotazione> prenotazioni = new ArrayList<>();
	
	//UTENTE->Carte Pagamento(1:n)
	@OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartaPagamento> cartePagamento = new ArrayList<>();
	
	//Utente->Indirizzo
	@OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Indirizzo> indirizzi = new ArrayList<>();	
}//Utente
