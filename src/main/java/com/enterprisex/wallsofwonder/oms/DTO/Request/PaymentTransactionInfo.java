package com.enterprisex.wallsofwonder.oms.DTO.Request;

import lombok.Data;

@Data
public class PaymentTransactionInfo {
	private String razorpayOrderId;
	private String razorpayPaymentId;
	private String razorpaySignature;
}
