package com.homestyle.demo.mapper;

import org.mapstruct.Mapper;
import com.homestyle.demo.dto.request.PrenotazioneRequestDTO;
import com.homestyle.demo.dto.response.PrenotazioneResponseDTO;
import com.homestyle.demo.entity.Prenotazione;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PrenotazioneMapper {
	
	// RequestDTO → Entity
	Prenotazione toEntity(PrenotazioneRequestDTO dto);

    // Entity → ResponseDTO
	PrenotazioneResponseDTO toDTO(Prenotazione prenotazione);

	// Entity → ResponseDTO
	List<PrenotazioneResponseDTO> toDTOs(List<Prenotazione> prenotazione);
	

}//PrenotazioneMapper
