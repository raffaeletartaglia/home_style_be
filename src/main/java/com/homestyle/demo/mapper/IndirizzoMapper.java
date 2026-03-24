package com.homestyle.demo.mapper;

import org.mapstruct.Mapper;

import com.homestyle.demo.dto.request.IndirizzoRequestDTO;
import com.homestyle.demo.dto.response.IndirizzoResponseDTO;
import com.homestyle.demo.entity.Indirizzo;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IndirizzoMapper {
	
	 // RequestDTO → Entity
    Indirizzo toEntity(IndirizzoRequestDTO dto);

    // Entity → ResponseDTO
    IndirizzoResponseDTO toDTO(Indirizzo indirizzo);

    // Entity → ResponseDTO
    List<IndirizzoResponseDTO> toDTOs(List<Indirizzo> indirizzo);
	
}//IndirizzoMapper
