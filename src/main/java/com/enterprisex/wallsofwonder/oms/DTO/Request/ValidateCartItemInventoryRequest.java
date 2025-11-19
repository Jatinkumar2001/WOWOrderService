package com.enterprisex.wallsofwonder.oms.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCartItemInventoryRequest {

    private long productId;
    private int quantity;
    private double price;
}
