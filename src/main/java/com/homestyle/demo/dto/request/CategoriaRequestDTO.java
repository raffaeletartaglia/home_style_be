package com.homestyle.demo.dto.request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;



@Data
public class CategoriaRequestDTO {

private UUID id;
	
	private String nomeCategoria;
	
	private String descrizione;
		
	private List<ProdottoRequestDTO> prodotti = new ArrayList<>();
}
