package com.homestyle.demo.dto.request;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CartaPagamentoRequestDTO {
	
	public enum TipoCarta {

	    VISA,
	    MASTERCARD,
	    MAESTRO

	}//TipoCarta

    private UUID utenteId;

    private String numeroCarta;

    private String intestatario;

    private LocalDate scadenza;

    private String cvv;

    private TipoCarta tipoCarta;

}//CartaPagamentoRequestDTO
