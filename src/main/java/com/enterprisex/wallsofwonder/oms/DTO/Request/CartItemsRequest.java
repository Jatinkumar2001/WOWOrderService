package com.enterprisex.wallsofwonder.oms.DTO.Request;


import com.enterprisex.wallsofwonder.oms.DTO.CartItemsDTO;
import com.enterprisex.wallsofwonder.oms.DTO.UserAddressDTO;
import lombok.Data;

import java.util.List;

@Data

public class CartItemsRequest {
  private List<CartItemsDTO> data;
  private Long storeId;
  private String coupon;
  private Long userId;
  private String paymentMode;
  private UserAddressDTO userDeliveryAddress;




}