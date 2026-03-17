package com.homestyle.demo.mapper;

import org.mapstruct.Mapper;
import com.homestyle.demo.dto.request.ResoRequestDTO;
import com.homestyle.demo.dto.response.ResoResponseDTO;
import com.homestyle.demo.entity.Reso;

@Mapper(componentModel = "spring")
public interface ResoMapper {
	
	// RequestDTO → Entity
	Reso toEntity(ResoRequestDTO dto);

    // Entity → ResponseDTO
	ResoResponseDTO toDTO(Reso reso);

}//ResoMapper
