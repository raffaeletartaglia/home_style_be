package com.homestyle.demo.mapper;
import org.mapstruct.Mapper;


import com.homestyle.demo.dto.response.SpedizioneResponseDTO;
import com.homestyle.demo.entity.Spedizione;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpedizioneMapper {
	
	
	//Entity -> ResponseDTO
	SpedizioneResponseDTO toDTO(Spedizione spedizione);

    List<SpedizioneResponseDTO> toDTOs(List<Spedizione> spediziones);
}//SpedizioneMapper
