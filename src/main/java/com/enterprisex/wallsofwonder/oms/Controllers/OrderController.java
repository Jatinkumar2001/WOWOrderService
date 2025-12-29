package com.enterprisex.wallsofwonder.oms.Controllers;


import com.enterprisex.wallsofwonder.oms.DTO.OrderDTO;
import com.enterprisex.wallsofwonder.oms.DTO.Request.CartItemsRequest;
import com.enterprisex.wallsofwonder.oms.DTO.Request.PlaceOrderRequest;
import com.enterprisex.wallsofwonder.oms.DTO.Request.ValidateCartRequest;
import com.enterprisex.wallsofwonder.oms.DTO.Response.PaginationResponseHandler;
import com.enterprisex.wallsofwonder.oms.DTO.Response.ResponseHandler;
import com.enterprisex.wallsofwonder.oms.DTO.Response.ValidateCartInventoryResponse;
import com.enterprisex.wallsofwonder.oms.Enums.OrderStatus;
import com.enterprisex.wallsofwonder.oms.Service.OrderService;
import com.enterprisex.wallsofwonder.oms.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping("/validateCartItemsAvailability")
    public ResponseEntity<?> validateCartItemsAvailability(@RequestBody ValidateCartRequest cartData) {
        try {
            ValidateCartInventoryResponse response = orderService.validateCartInventory(cartData);
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage("Cart Validated Successfully");
            responseHandler.setIsSuccess(true);
            responseHandler.setData(response);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage(e.getMessage());
            responseHandler.setIsSuccess(false);
            responseHandler.setData(null);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        }
    }

    @PostMapping("/generateOrder")
    public ResponseEntity<?> generateOrder(@RequestBody CartItemsRequest productRequests) {
        try {

            OrderDTO order = orderService.addOrder(productRequests);
            String amountInPaise = Util.convertRupeeToPaise("" + order.getTotalAmount());
            // Create an order in RazorPay and get the order id
//            OrderEntity paymentOrder = createRazorPayOrder(amountInPaise);
//            PaymentOrderResponse payOrderResponse = getOrderResponse((String) paymentOrder.get("id"), amountInPaise);
//            order.setPaymentOrderDetails(payOrderResponse);
//            orderService.savePaymentOrder(payOrderResponse.getRazorpayOrderId(), productRequests.getCustomerId());
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage("Order generated successfully");
            responseHandler.setIsSuccess(true);
            responseHandler.setData(order);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage(e.getMessage());
            responseHandler.setIsSuccess(false);
            responseHandler.setData(null);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        }

    }

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
        try {

            OrderDTO order = orderService.processOrder(placeOrderRequest.getOrderId(), OrderStatus.CONFIRMED);
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage("Order placed successfully");
            responseHandler.setIsSuccess(true);
            responseHandler.setData(order);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage(e.getMessage());
            responseHandler.setIsSuccess(false);
            responseHandler.setData(null);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        }


    }

    @GetMapping("/user")
    public ResponseEntity<?> getCustomerOrder(@RequestParam Map<String, String> currentPage) {

        try {
            int pageNumber = currentPage.containsKey("page") ? Integer.parseInt(currentPage.get("page")) : 0;
            int pageSize = currentPage.containsKey("per_page") ? Integer.parseInt(currentPage.get("per_page")) : 10;
            String status = currentPage.getOrDefault("status", null);
            Map<String, Integer> currentPageResponse = new HashMap<>();
            currentPageResponse.put("page", pageNumber);
            currentPageResponse.put("per_page", pageSize);
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("created_at").descending());
            PaginationResponseHandler responseHandler = orderService.getUserOrder(pageable, status);
            responseHandler.setMessage("Successfully retrieved data!");
            responseHandler.setSuccess(true);
            responseHandler.setCurrentPage(currentPageResponse);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage(e.getMessage());
            responseHandler.setIsSuccess(false);
            responseHandler.setData(null);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        }
    }

}
