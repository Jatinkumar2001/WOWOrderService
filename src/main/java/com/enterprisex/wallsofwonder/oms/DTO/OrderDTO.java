package com.enterprisex.wallsofwonder.oms.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDTO {
    private Long id;
    private String userTrackingId;
    private UUID couponId;
    private Long userId;
    private String orderStatusId;
    private String paymentMethod;
    private BigDecimal discount;
    private String couponCode;
    private String paymentMode;
    private String paymentTransactionId;

    private Timestamp orderApprovedAt;


    private Timestamp orderDeliveredCarrierDate;

    private Timestamp orderDeliveredCustomerDate;

    private String firstName;

    private String lastName;

    private String signupType;

    private String email;

    private String socialId;

    private String socialUrl;

    private String phone;

    private Timestamp registeredAt;

    private String role;

    private String profileStatus;

    private String addressLine1;

    private String addressLine2;

    private String phoneNumber;


    private String dialCode;


    private String landmark;

    private String postalCode;

    private String city;


    private String state;
    private String addressType;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<OrderItemDTO> orderItems = new ArrayList<>();

    public Double getTotalAmount(){
        double total = 0.0;
        if(orderItems !=null){
            for(OrderItemDTO orderItem: orderItems){
                total = total + (orderItem.getPrice()*orderItem.getQuantity());
            }
        }
        return total;
    }
}
