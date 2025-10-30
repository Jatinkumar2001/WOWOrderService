package com.enterprisex.wallsofwonder.oms.Convertors;

import com.enterprisex.wallsofwonder.oms.DTO.CartItemsDTO;
import com.enterprisex.wallsofwonder.oms.DTO.ProductDTO;
import com.enterprisex.wallsofwonder.oms.DTO.Request.CartRequest;
import com.enterprisex.wallsofwonder.oms.Entities.CartItemWithProductEntity;
import com.enterprisex.wallsofwonder.oms.Entities.CartsItemsEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemConverter {

    public CartItemsDTO entityToDto(CartsItemsEntity items){
        if (items == null) return null;
        CartItemsDTO dto = new CartItemsDTO();
        dto.setCartId(items.getCartId());
        dto.setQuantity(items.getQuantity());
        dto.setProductId(items.getProductId());
        dto.setId(items.getId());
        return  dto;
    }

    public CartItemsDTO requestToDto(CartRequest items){
        if (items == null) return null;
        CartItemsDTO dto = new CartItemsDTO();
        dto.setQuantity(items.getQuantity());
        dto.setProductId(items.getProductId());
        return  dto;
    }

    public CartsItemsEntity dtoToEntity(CartItemsDTO items){

        if (items == null) return null;
        CartsItemsEntity dto = new CartsItemsEntity();
        dto.setCartId(items.getCartId());
        dto.setQuantity(items.getQuantity());
        dto.setProductId(items.getProductId());
        dto.setId(items.getId());
        return  dto;
    }
    public CartItemsDTO entityToDto(CartItemWithProductEntity cartItemResponse){
        if (cartItemResponse == null) return  null;
        CartItemsDTO dto  = new CartItemsDTO();
        dto.setProductId(cartItemResponse.getProductId());
        dto.setQuantity(cartItemResponse.getQuantity());
        dto.setCartId(cartItemResponse.getCartId());
        ProductDTO productVariantDto = new ProductDTO();
        productVariantDto.setActive(cartItemResponse.isActive());
        productVariantDto.setSalePrice(cartItemResponse.getSalePrice());
        productVariantDto.setTitle(cartItemResponse.getTitle());
        productVariantDto.setUrlKey(cartItemResponse.getUrlKey());
        productVariantDto.setStockStatus(cartItemResponse.getStockStatus());
        productVariantDto.setIsPublished(cartItemResponse.isPublished());
        productVariantDto.setShortDescription(cartItemResponse.getShortDescription());
        productVariantDto.setFullDescription(cartItemResponse.getFullDescription());
        productVariantDto.setImage(cartItemResponse.getImage());
        productVariantDto.setMetaTitle(cartItemResponse.getMetaTitle());
        productVariantDto.setMetaDescription(cartItemResponse.getMetaDescription());
        productVariantDto.setMetaKeyword(cartItemResponse.getMetaKeyword());
        dto.setProduct(productVariantDto);
        return dto;
    }
    public List<CartItemsDTO> entityToDto(List<CartItemWithProductEntity> cartItemResponse){
        if (cartItemResponse == null) return  new ArrayList<>();
        return cartItemResponse.stream().map(this::entityToDto).collect(Collectors.toList());
    }


}
