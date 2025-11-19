package com.enterprisex.wallsofwonder.oms.DTO.Request;

import lombok.Data;

@Data
public class StockMasterRequest {
    private Long id;
    private Long productId;
    private Integer quantity;
    private String actionType;
}
