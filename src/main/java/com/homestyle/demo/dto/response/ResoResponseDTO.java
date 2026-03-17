package com.homestyle.demo.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class ResoResponseDTO {

    private UUID id;

    private UUID dettaglioOrdineId;

    private LocalDate dataResoPrevista;

    private LocalTime oraRitiroReso;

    private String motivo;

    private UUID indirizzoId;

}//ResoResponseDTO