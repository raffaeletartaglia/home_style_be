package com.homestyle.demo.mapper;

import org.mapstruct.Mapper;


import com.homestyle.demo.dto.request.CategoriaRequestDTO;
import com.homestyle.demo.dto.response.CategoriaResponseDTO;
import com.homestyle.demo.entity.Categoria;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    // RequestDTO → Entity
    Categoria toEntity(CategoriaRequestDTO dto);

    // Entity → ResponseDTO
    CategoriaResponseDTO toDTO(Categoria categoria);
    
}//CategoriaMapper
