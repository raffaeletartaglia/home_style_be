package com.homestyle.demo.mapper;
import org.mapstruct.Mapper;
import com.homestyle.demo.dto.response.MovimentoMagazzinoResponseDTO;
import com.homestyle.demo.entity.MovimentoMagazzino;



@Mapper(componentModel = "string")
public interface MovimentoMagazzinoMapper {
	

    // Entity → ResponseDTO
	MovimentoMagazzinoResponseDTO toDTO(MovimentoMagazzino movimentoMagazzino);

}//MovimentoMagazzinoMapper
