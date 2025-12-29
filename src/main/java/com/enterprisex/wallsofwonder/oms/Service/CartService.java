package com.enterprisex.wallsofwonder.oms.Service;




import com.enterprisex.wallsofwonder.oms.DTO.CartItemsDTO;
import com.enterprisex.wallsofwonder.oms.DTO.Request.CartRequest;

import java.util.List;

public interface CartService {
	List<CartItemsDTO> findCartItems();

    List<CartItemsDTO> addToCart(CartRequest body);

    List<CartItemsDTO> removeQtyCart(CartRequest body);

    List<CartItemsDTO> deleteItemCart(CartRequest body) throws Exception;
    List<CartItemsDTO> getAllCartByCustomer(Long userId);

    void deleteCart();
}
