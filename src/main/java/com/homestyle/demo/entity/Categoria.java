package com.homestyle.demo.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "categoria")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "nome_categoria", nullable = false, unique=true)
	private String nomeCategoria;
	
	@Column(name = "descrizione")
	private String descrizione;
	
	//RELAZIONI
	
	//categoria->Prodotto (1:n)
	@OneToMany(mappedBy="categoria",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Prodotto> prodotti = new ArrayList<>();
	
	
	

}//Categoria
