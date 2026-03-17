package com.homestyle.demo.mapper;
import org.mapstruct.Mapper;
import com.homestyle.demo.dto.request.CarrelloRequestDTO;
import com.homestyle.demo.dto.response.CarrelloResponseDTO;
import com.homestyle.demo.entity.Carrello;



@Mapper(componentModel = "string")
public interface CarrelloMapper {
	
	 // RequestDTO → Entity
    Carrello toEntity(CarrelloRequestDTO dto);

    // Entity → ResponseDTO
    CarrelloResponseDTO toDTO(Carrello carrello);
	
	
}//CarrelloMapper
