package com.enterprisex.wallsofwonder.oms.DTO.Request;

import lombok.Data;


@Data
public class CartRequest {
    private String cartId;
    private Long variantId;
    private Integer quantity;

}
