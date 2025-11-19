package com.enterprisex.wallsofwonder.oms.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCartRequest {

    private long pinCode;
    List<ValidateCartItemInventoryRequest> cartItems;
}
