package com.enterprisex.wallsofwonder.oms.DTO.Response;

import com.enterprisex.wallsofwonder.oms.Enums.ValidationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCartResponseItemInventory {

    private long variantId;

    private long quantity;

    private Double price;

    private Boolean valid;
    private ValidationStatus status;
}
