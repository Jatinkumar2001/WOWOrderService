package com.enterprisex.wallsofwonder.oms.Controllers;


import com.enterprisex.wallsofwonder.oms.DTO.CartItemsDTO;
import com.enterprisex.wallsofwonder.oms.DTO.Request.CartRequest;
import com.enterprisex.wallsofwonder.oms.DTO.Response.ResponseHandler;
import com.enterprisex.wallsofwonder.oms.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;
    
    
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest request){
        try {

            ResponseHandler responseHandler = new ResponseHandler();
            List<CartItemsDTO> list = cartService.addToCart(request);
            responseHandler.setMessage("Cart Retrieved successfully");
            responseHandler.setIsSuccess(true);
            responseHandler.setData(list);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);

        } catch (Exception e) {
        	e.printStackTrace();
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage(e.getMessage());
            responseHandler.setIsSuccess(false);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        }

    }

    @GetMapping("get")
    public ResponseEntity<?> getCartItems(){
        try {
            ResponseHandler responseHandler = new ResponseHandler();
            List<CartItemsDTO> list = cartService.findCartItems();
            responseHandler.setMessage("Cart Retrieved successfully");
            responseHandler.setIsSuccess(true);
            responseHandler.setData(list);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);

        } catch (Exception e) {
        	e.printStackTrace();
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage(e.getMessage());
            responseHandler.setIsSuccess(false);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        }
    }

    @PostMapping("remove")
    public ResponseEntity<?> removeCartItemAndQuantity(@RequestBody   CartRequest request){
        try {
            ResponseHandler responseHandler = new ResponseHandler();
            List<CartItemsDTO> CartItemsDTOS = cartService.removeQtyCart(request);
            responseHandler.setMessage("Item removed from cart successfully");
            responseHandler.setIsSuccess(true);
            responseHandler.setData(CartItemsDTOS);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);

        } catch (Exception e) {
        	e.printStackTrace();
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage(e.getMessage());
            responseHandler.setIsSuccess(false);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        }
    }

    @PostMapping("delete")
    public ResponseEntity<?> removeCartItem(@RequestBody  CartRequest request){
        try {
            ResponseHandler responseHandler = new ResponseHandler();
            List<CartItemsDTO> CartItemsDTOS = cartService.deleteItemCart(request);
            responseHandler.setMessage("Item removed from cart successfully");
            responseHandler.setIsSuccess(true);
            responseHandler.setData(CartItemsDTOS);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage(e.getMessage());
            responseHandler.setIsSuccess(false);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        }
    }

    @GetMapping("{customerId}")
    public ResponseEntity<?> getAllCartByCustomer(@PathVariable("customerId") Long customerId){
        try {
            ResponseHandler responseHandler = new ResponseHandler();
            List<CartItemsDTO> list = cartService.getAllCartByCustomer(customerId);
            responseHandler.setMessage("Cart Retrieved successfully");
            responseHandler.setIsSuccess(true);
            responseHandler.setData(list);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage(e.getMessage());
            responseHandler.setIsSuccess(false);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        }
    }


}
