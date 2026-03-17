package com.homestyle.demo.dto.response;

import lombok.Data;
import java.util.UUID;

@Data
public class CategoriaResponseDTO {
	
	private UUID id;
	private String nomeCategoria;
	private String descrizione;

}//CategoriaResponse
