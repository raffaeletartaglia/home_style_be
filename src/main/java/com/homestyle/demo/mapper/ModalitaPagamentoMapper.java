package com.homestyle.demo.mapper;

import com.homestyle.demo.dto.request.ModalitaPagamentoRequestDTO;
import com.homestyle.demo.dto.response.ModalitaPagamentoResponseDTO;
import com.homestyle.demo.entity.ModalitaPagamento;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModalitaPagamentoMapper {

    // RequestDTO → Entity (create/update)
    ModalitaPagamento toEntity(ModalitaPagamentoRequestDTO dto);

    // Entity → ResponseDTO (output verso client)
    ModalitaPagamentoResponseDTO toDTO(ModalitaPagamento modalitaPagamento);

    // Lista di entity → lista di ResponseDTO
    List<ModalitaPagamentoResponseDTO> toDTOs(List<ModalitaPagamento> modalitaPagamento);

}//ModalitaPagamentoMapper

