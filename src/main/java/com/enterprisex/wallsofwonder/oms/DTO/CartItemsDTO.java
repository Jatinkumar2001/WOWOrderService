package com.enterprisex.wallsofwonder.oms.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class CartItemsDTO {

    private UUID id;
    private UUID cartId;
    private Long productId;
    private Integer quantity;
    private ProductDTO product;


}