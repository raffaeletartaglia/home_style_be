package com.homestyle.demo.dto.request;

import com.homestyle.demo.entity.ModalitaPagamento;
import lombok.Data;

@Data
public class ModalitaPagamentoRequestDTO {

    // FISICO / ONLINE
    private ModalitaPagamento.Tipo tipo;

    // es: "PayPal", "Carta di credito", "Pagamento alla consegna"
    private String descrizione;
}

