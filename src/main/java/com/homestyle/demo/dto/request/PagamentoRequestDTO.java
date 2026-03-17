package com.homestyle.demo.dto.request;

import lombok.Data;

import java.util.UUID;

import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PagamentoRequestDTO {
	
	@NotNull
    private UUID ordineId;
	
	@NotNull
    private UUID modalitaPagamentoId;

    private UUID cartaPagamentoId; // opzionale

    private Boolean pagamentoEffettuato;

    private Integer numeroRate;

    private Integer rataCorrente;

    private BigDecimal importo;

    private BigDecimal importoRata;

    private LocalDateTime dataPagamento;

    private String fattura;

}//PagamentoRequestDTO
