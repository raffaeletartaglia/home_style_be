package com.homestyle.demo.mapper;

import org.mapstruct.Mapper;

import com.homestyle.demo.dto.request.DettaglioOrdineRequestDTO;
import com.homestyle.demo.dto.response.DettaglioOrdineResponseDTO;
import com.homestyle.demo.entity.DettaglioOrdine;

import java.util.List;


@Mapper(componentModel = "string")
public interface DettaglioOrdineMapper {
	
			//RequestDTO -> Entity
			DettaglioOrdine toEntity(DettaglioOrdineRequestDTO dto);
			
			//Entity -> ResponseDTO
			DettaglioOrdineResponseDTO toDTO(DettaglioOrdine dettaglioOrdine);

			List<DettaglioOrdineResponseDTO> toDTOs(List<DettaglioOrdine> dettaglioOrdine);
	
}//DettaglioOrdineMapper
