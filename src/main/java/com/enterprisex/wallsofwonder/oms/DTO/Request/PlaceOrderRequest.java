package com.enterprisex.wallsofwonder.oms.DTO.Request;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class PlaceOrderRequest implements Serializable{
    private String orderId;
	String paymentMode;
    PaymentTransactionInfo paymentTransactionInfo;
    
}
