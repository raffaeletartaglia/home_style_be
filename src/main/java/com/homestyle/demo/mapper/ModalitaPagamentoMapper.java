package com.homestyle.demo.mapper;
import org.mapstruct.Mapper;
import com.homestyle.demo.dto.response.ModalitaPagamentoResponseDTO;
import com.homestyle.demo.entity.ModalitaPagamento;

@Mapper(componentModel = "spring")
public interface ModalitaPagamentoMapper {
	
	

    // Entity → ResponseDTO
	ModalitaPagamentoResponseDTO toDTO(ModalitaPagamento modalitaPagamento);

}//ModalitaPagamentoMapper
