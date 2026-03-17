package com.homestyle.demo.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class WishlistResponseDTO {

    private UUID id;

    private UUID utenteId;

    private UUID prodottoId;

    private LocalDateTime dataAggiunta;

    private String priorita;

}//WishlistResponseDTO 