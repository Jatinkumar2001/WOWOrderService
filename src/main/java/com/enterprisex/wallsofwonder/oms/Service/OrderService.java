package com.enterprisex.wallsofwonder.oms.Service;
import com.enterprisex.wallsofwonder.oms.DTO.OrderDTO;
import com.enterprisex.wallsofwonder.oms.DTO.Request.CartItemsRequest;
import com.enterprisex.wallsofwonder.oms.DTO.Request.ValidateCartRequest;
import com.enterprisex.wallsofwonder.oms.DTO.Response.PaginationResponseHandler;
import com.enterprisex.wallsofwonder.oms.DTO.Response.ValidateCartInventoryResponse;
import com.enterprisex.wallsofwonder.oms.Enums.OrderStatus;
import org.springframework.data.domain.Pageable;

import java.net.URISyntaxException;


public interface OrderService {
    OrderDTO addOrder(CartItemsRequest requestCartsItemsDTO);
    ValidateCartInventoryResponse validateCartInventory(ValidateCartRequest cartData);
    OrderDTO processOrder(String orderId, OrderStatus status);


    PaginationResponseHandler getUserOrder(Long customerId, Pageable pageable, String status) throws URISyntaxException;

}


