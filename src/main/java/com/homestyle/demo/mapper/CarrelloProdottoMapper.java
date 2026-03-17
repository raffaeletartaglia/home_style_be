package com.homestyle.demo.mapper;
import org.mapstruct.Mapper;
import com.homestyle.demo.dto.request.CarrelloProdottoRequestDTO;
import com.homestyle.demo.dto.response.CarrelloProdottoResponseDTO;
import com.homestyle.demo.entity.CarrelloProdotto;

@Mapper(componentModel = "string")
public interface CarrelloProdottoMapper {
	
	//RequestDTO -> Entity
	CarrelloProdotto toEntity(CarrelloProdottoRequestDTO dto);
	
	//Entity -> ResponseDTO
	CarrelloProdottoResponseDTO toDTO(CarrelloProdotto carrelloProdotto);

}//CarrelloProdottoMapper
