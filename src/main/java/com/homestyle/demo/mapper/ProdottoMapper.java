package com.homestyle.demo.mapper;

import org.mapstruct.Mapper;



import com.homestyle.demo.dto.request.ProdottoRequestDTO;
import com.homestyle.demo.dto.response.ProdottoResponseDTO;
import com.homestyle.demo.entity.Prodotto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdottoMapper {
	
	 // RequestDTO → Entity
    Prodotto toEntity(ProdottoRequestDTO dto);

    // Entity → ResponseDTO
    ProdottoResponseDTO toDTO(Prodotto prodotto);

    // Entity → ResponseDTO
    List<ProdottoResponseDTO> toDTOs(List<Prodotto> prodotto);

}//ProdottoMapper
