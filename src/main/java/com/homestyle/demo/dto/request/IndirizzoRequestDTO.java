package com.homestyle.demo.dto.request;

import lombok.Data;
import java.util.UUID;
import com.homestyle.demo.entity.Indirizzo;

@Data
public class IndirizzoRequestDTO {
	

    private UUID utenteId;

    private String nazione;

    private String via;

    private String numeroCivico;

    private String citta;

    private String provincia;

    private String cap;

    private Indirizzo.Tipo tipo;
    
}//IndirizzoRequestDTO