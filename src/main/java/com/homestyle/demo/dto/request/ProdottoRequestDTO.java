package com.homestyle.demo.dto.request;
import lombok.Data;
import java.util.UUID;

import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
public class ProdottoRequestDTO {
	
	private String marca;
	private String nomeProdotto;
	private UUID categoriaId;
	private String colore;
	private String modello;
	private float prezzo;
	private String descrizione;
	private Boolean includeMontaggio;
	
	//campi gestione magazzino(SOLO ADMIN)
	
	private Integer sogliaRiordino;
	private Integer quantitaRiordinoStandard;
	private LocalDate dataProssimaDisponibilita;
	
	

}//ProdottoRequest
