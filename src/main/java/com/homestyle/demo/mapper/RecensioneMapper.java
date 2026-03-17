package com.homestyle.demo.mapper;
import org.mapstruct.Mapper;


import com.homestyle.demo.dto.request.RecensioneRequestDTO;
import com.homestyle.demo.dto.response.RecensioneResponseDTO;
import com.homestyle.demo.entity.Recensione;


@Mapper(componentModel = "spring")
public interface RecensioneMapper {
	
	// RequestDTO → Entity
	Recensione toEntity(RecensioneRequestDTO dto);

    // Entity → ResponseDTO
	RecensioneResponseDTO toDTO(Recensione recensione);
	
	

}//RecensioneMapper
