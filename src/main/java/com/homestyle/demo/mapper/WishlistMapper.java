package com.homestyle.demo.mapper;

import org.mapstruct.Mapper;
import com.homestyle.demo.dto.request.WishlistRequestDTO;
import com.homestyle.demo.dto.response.WishlistResponseDTO;
import com.homestyle.demo.entity.Wishlist;


@Mapper(componentModel = "spring")
public interface WishlistMapper {
	
	// RequestDTO → Entity
	Wishlist toEntity(WishlistRequestDTO dto);

    // Entity → ResponseDTO
	WishlistResponseDTO toDTO(Wishlist wishlist);

}//WishlistMapper
