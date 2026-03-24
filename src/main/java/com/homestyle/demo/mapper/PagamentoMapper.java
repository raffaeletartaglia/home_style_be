package com.homestyle.demo.mapper;

import org.mapstruct.Mapper;

import com.homestyle.demo.dto.request.PagamentoRequestDTO;
import com.homestyle.demo.dto.response.PagamentoResponseDTO;
import com.homestyle.demo.entity.Pagamento;

@Mapper(componentModel = "spring")
public interface PagamentoMapper {
	
	//RequestDTO -> Entity
	Pagamento toEntity(PagamentoRequestDTO dto);
	
	//Entity -> ResponseDTO
	PagamentoResponseDTO toDTO(Pagamento pagamento);
	

}//PagamentoMapper
