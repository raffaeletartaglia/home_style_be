package com.homestyle.demo.mapper;

import org.mapstruct.Mapper;
import com.homestyle.demo.dto.request.ResoRequestDTO;
import com.homestyle.demo.dto.response.ResoResponseDTO;
import com.homestyle.demo.entity.Reso;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResoMapper {
	
	// RequestDTO → Entity
	Reso toEntity(ResoRequestDTO dto);

	List<Reso> toEntities(List<ResoResponseDTO> dtos);

    // Entity → ResponseDTO
	ResoResponseDTO toDTO(Reso reso);

	List<ResoResponseDTO> toDTOs(List<Reso> resi);

}//ResoMapper
