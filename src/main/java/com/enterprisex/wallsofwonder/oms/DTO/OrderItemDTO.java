package com.enterprisex.wallsofwonder.oms.DTO;

import lombok.Data;

import java.util.UUID;


@Data
public class OrderItemDTO {

	private Long id;
	
    private Long variantId;

    private UUID orderId;

    private Double price;

    private int quantity;

    private String  title;

    private Double billingPrice;

}