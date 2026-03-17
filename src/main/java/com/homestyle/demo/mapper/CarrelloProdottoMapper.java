package com.homestyle.demo.mapper;
import org.mapstruct.Mapper;
import com.homestyle.demo.dto.request.CarrelloProdottoRequestDTO;
import com.homestyle.demo.dto.response.CarrelloProdottoResponseDTO;
import com.homestyle.demo.entity.CarrelloProdotto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarrelloProdottoMapper {
	
	//RequestDTO -> Entity
	CarrelloProdotto toEntity(CarrelloProdottoRequestDTO dto);

	List<CarrelloProdotto> toEntities(List<CarrelloProdottoRequestDTO> dtos);
	
	//Entity -> ResponseDTO
	CarrelloProdottoResponseDTO toDTO(CarrelloProdotto carrelloProdotto);

	List<CarrelloProdottoResponseDTO> toDTOs(List<CarrelloProdotto> carrelloProdotti);
}//CarrelloProdottoMapper
