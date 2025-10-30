package com.enterprisex.wallsofwonder.oms.Convertors;



import com.enterprisex.wallsofwonder.oms.DTO.CartDTO;
import com.enterprisex.wallsofwonder.oms.Entities.CartEntity;
import org.springframework.stereotype.Service;

@Service
public class CartConverter {
	
	public CartDTO entityToDto(CartEntity cart) {

		CartDTO dto = new CartDTO();
		dto.setId(cart.getId());
		dto.setUserId(cart.getUserId());
		return dto;
	}


	public CartEntity dtoToEntity(CartDTO cart){

		CartEntity dto = new CartEntity();
		dto.setUserId(cart.getUserId());
		dto.setIsActive(cart.isActive());
		return dto;
	}



}
