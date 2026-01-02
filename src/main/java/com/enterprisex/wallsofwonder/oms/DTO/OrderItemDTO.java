package com.enterprisex.wallsofwonder.oms.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;


@Data
public class OrderItemDTO {

	private UUID id;
	
    private Long variantId;

    private UUID orderId;

    private Double price;

    private int quantity;

    private String  title;

    @JsonProperty("salePrice")
    private Double billingPrice;
    private String name;

}