package com.homestyle.demo.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ProdottoResponseDTO {
	
	private UUID id;
	private String marca;
	private String nomeProdotto;
	private String colore;
	private String modello;
	private float prezzo;
	private String descrizione;
	private Boolean includeMontaggio;
	private LocalDate dataProssimaDisponibilita;
	
	private CategoriaResponseDTO categoria;

}//ProdottoResponse
