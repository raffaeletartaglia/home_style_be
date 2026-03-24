package com.homestyle.demo.mapper;

import com.homestyle.demo.dto.request.UtenteRequestDTO;
import com.homestyle.demo.dto.response.UtenteResponseDTO;
import com.homestyle.demo.entity.Utente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtenteMapper {


    // RequestDTO → Entity
    Utente toEntity(UtenteRequestDTO dto);

    // Entity → ResponseDTO
    UtenteResponseDTO toDTO(Utente utente);
    
}//UtenteMapper 