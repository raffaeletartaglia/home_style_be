package com.homestyle.demo.dto.response;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrdineResponseDTO {
	
	public enum StatoOrdine {

	    IN_ELABORAZIONE,
	    SPEDITO,
	    CONSEGNATO,
	    ANNULLATO

	}//StatoOrdine
	
	private UUID id;

    private StatoOrdine statoOrdine;

    private LocalDateTime dataOrdine;

    private LocalDate dataPrevistaConsegna;

    private IndirizzoResponseDTO indirizzoSpedizione;

    private List<DettaglioOrdineResponseDTO> prodotti;
    
    

}//OrdineResponseDTO
