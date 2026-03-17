package com.homestyle.demo.mapper;
import org.mapstruct.Mapper;

import com.homestyle.demo.dto.request.CartaPagamentoRequestDTO;
import com.homestyle.demo.dto.response.CartaPagamentoResponseDTO;
import com.homestyle.demo.entity.CartaPagamento;



@Mapper(componentModel = "string")
public interface CartaPagamentoMapper {
	
	 // RequestDTO → Entity
    CartaPagamento toEntity(CartaPagamentoRequestDTO dto);

    // Entity → ResponseDTO
    CartaPagamentoResponseDTO toDTO(CartaPagamento cartaPagamento);
	
}//CartaPagamentoMapper
