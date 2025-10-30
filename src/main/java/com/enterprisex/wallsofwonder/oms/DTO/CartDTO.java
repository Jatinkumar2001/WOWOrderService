package com.enterprisex.wallsofwonder.oms.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class CartDTO  implements Serializable {
    private UUID id;
    private long userId;
    private boolean isActive;
    List<CartItemsDTO> itemsDTOList;

}