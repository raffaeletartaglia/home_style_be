package com.homestyle.demo.mapper;
import org.mapstruct.Mapper;

import com.homestyle.demo.dto.request.OrdineRequestDTO;
import com.homestyle.demo.dto.response.OrdineResponseDTO;
import com.homestyle.demo.entity.Ordine;


@Mapper(componentModel = "string")
public interface OrdineMapper {
	
		//RequestDTO -> Entity
		Ordine toEntity(OrdineRequestDTO dto);
		
		//Entity -> ResponseDTO
		OrdineResponseDTO toDTO(Ordine ordine);

}//OrdineMapper
