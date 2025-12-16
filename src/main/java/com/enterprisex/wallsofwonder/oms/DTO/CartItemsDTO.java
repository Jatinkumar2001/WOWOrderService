package com.enterprisex.wallsofwonder.oms.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class CartItemsDTO {

    private UUID id;
    private UUID cartId;
    private Long variantId;
    private Integer quantity;
    private ProductDTO variant;


}