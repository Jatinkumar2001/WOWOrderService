package com.enterprisex.wallsofwonder.oms.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCartInventoryResponse {

    private boolean valid;
    List<ValidateCartResponseItemInventory> cartItemInventories;
}
