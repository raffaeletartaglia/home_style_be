package com.homestyle.demo.mapper;

import com.homestyle.demo.dto.request.UtenteRequestDTO;
import com.homestyle.demo.dto.response.UtenteResponseDTO;
import com.homestyle.demo.entity.Utente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UtenteMapper {


    // RequestDTO → Entity
    Utente toEntity(UtenteRequestDTO dto);

    // Entity → ResponseDTO
    UtenteResponseDTO toDTO(Utente utente);
    
}//UtenteMapper 