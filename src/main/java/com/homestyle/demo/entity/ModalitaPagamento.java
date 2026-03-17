package com.homestyle.demo.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.UUID;
import java.util.*;

@Data
@Entity
@Table(name = "modalita_pagamento")
public class ModalitaPagamento {
	
	public enum Tipo{
		FISICO,
		ONLINE
	}//Tipo
	
	@Id
	@GeneratedValue(strategy =  GenerationType.UUID)
	@Column(name = "id")
	private UUID id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false)
	private Tipo tipo;
	
	@Column(name = "descrizione")
	private String descrizione;

	//RELAZIONI
	// ModalitaPagamento->Pagamento (1:n)
    @OneToMany(mappedBy = "modalitaPagamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamento> pagamenti = new ArrayList<>();
	
}//ModalitaPagamento
