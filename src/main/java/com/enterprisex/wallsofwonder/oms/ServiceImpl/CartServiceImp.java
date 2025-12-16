package com.enterprisex.wallsofwonder.oms.ServiceImpl;

import com.enterprisex.wallsofwonder.oms.Convertors.CartConverter;
import com.enterprisex.wallsofwonder.oms.Convertors.CartItemConverter;
import com.enterprisex.wallsofwonder.oms.DTO.CartItemsDTO;
import com.enterprisex.wallsofwonder.oms.DTO.Request.CartRequest;
import com.enterprisex.wallsofwonder.oms.Entities.CartEntity;
import com.enterprisex.wallsofwonder.oms.Entities.CartItemWithProductEntity;
import com.enterprisex.wallsofwonder.oms.Entities.CartsItemsEntity;
import com.enterprisex.wallsofwonder.oms.Repositories.CartItemRepository;
import com.enterprisex.wallsofwonder.oms.Repositories.CartRepository;
import com.enterprisex.wallsofwonder.oms.Service.CartService;
import com.enterprisex.wallsofwonder.oms.UserContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImp implements CartService {


    @Autowired
    private CartItemRepository cartsItemsRepository;
    @Autowired
    private CartItemConverter cartItemConverter;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartConverter cartConverter;

    @Autowired
    private CartItemRepository cartItemRepository;


    @Override
    public List<CartItemsDTO> findCartItems() {

        CartEntity cart = cartRepository.findByUserId(UserContext.getUser().getId());
        if (cart == null) return new ArrayList<>();
        List<CartItemWithProductEntity> cartItemResponse = cartItemRepository.findByCartId(cart.getId());
        return cartItemConverter.entityToDto(cartItemResponse);
    }

    @Transactional
    @Override
    public List<CartItemsDTO> addToCart(CartRequest body) {
        CartEntity cart = cartRepository.findByUserId(UserContext.getUser().getId());
        if (cart == null) {
            CartEntity userCart = new CartEntity();
            userCart.setUserId(UserContext.getUser().getId());
            userCart.setIsActive(true);
            cart = cartRepository.save(userCart);
        }
        CartsItemsEntity cartsItems = cartsItemsRepository.findByVariantIdAndCartId(cart.getId(), body.getVariantId());

        CartItemsDTO cartsItemsDTO;
        if (cartsItems == null) {
            cartsItemsDTO = cartItemConverter.requestToDto(body);
            cartsItemsDTO.setCartId(cart.getId());
        } else {
            cartsItemsDTO = cartItemConverter.entityToDto(cartsItems);
            int quantity = body.getQuantity() + cartsItemsDTO.getQuantity();
            cartsItemsDTO.setQuantity(quantity);
        }
        cartsItemsRepository.save(cartItemConverter.dtoToEntity(cartsItemsDTO));
        return findCartItems();
    }

    @Override
    public List<CartItemsDTO> removeQtyCart(CartRequest body) {
        CartEntity cart = cartRepository.findByUserId(UserContext.getUser().getId());
        CartsItemsEntity cartsItems = cartsItemsRepository.findByVariantIdAndCartId(cart.getId(), body.getVariantId());
        if (cartsItems != null) {
            if (cartsItems.getQuantity() == 1) {
                cartsItemsRepository.delete(cartsItems);
            } else {
                CartItemsDTO cartsItemsDTO = cartItemConverter.entityToDto(cartsItems);
                int quantity = cartsItemsDTO.getQuantity() - 1;
                cartsItemsDTO.setQuantity(quantity);
                cartsItemsRepository.save(cartItemConverter.dtoToEntity(cartsItemsDTO));
            }
        }
        return findCartItems();
    }

    @Override
    public List<CartItemsDTO> deleteItemCart(CartRequest body) throws Exception {
        CartEntity cart = cartRepository.findByUserId(UserContext.getUser().getId());
        CartsItemsEntity cartsItems = cartsItemsRepository.findByVariantIdAndCartId(cart.getId(), body.getVariantId());
        if (cartsItems == null) throw new Exception("Item not found");
        cartsItemsRepository.delete(cartsItems);
        return findCartItems();
    }

    @Override
    public List<CartItemsDTO> getAllCartByCustomer(Long customerId) {
        CartEntity cart = cartRepository.findByUserId(customerId);
        if (cart == null) return new ArrayList<>();
        List<CartItemWithProductEntity> cartItemResponse = cartItemRepository.findByCartId(cart.getId());
        return cartItemConverter.entityToDto(cartItemResponse);
    }

}
