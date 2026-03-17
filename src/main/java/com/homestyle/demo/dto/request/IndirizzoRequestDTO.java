package com.homestyle.demo.dto.request;

import lombok.Data;
import java.util.UUID;

@Data
public class IndirizzoRequestDTO {
	
	public enum TipoIndirizzo {

	    RESIDENZA,
	    SPEDIZIONE,
	    FATTURAZIONE

	}//TipoIndirizzo

    private UUID utenteId;

    private String nazione;

    private String via;

    private String numeroCivico;

    private String citta;

    private String provincia;

    private String cap;

    private TipoIndirizzo tipo; // RESIDENZA, SPEDIZIONE, FATTURAZIONE
    
}//IndirizzoRequestDTO