package com.enterprisex.wallsofwonder.oms.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class OrderPickListWithVariantDetailsDTO {

    private UUID id;

    private UUID orderId;


    private UUID orderItemId;


    private String orderSerialId;


    private String lineSerialId;


    private long userId;


    private long variantSku;

    private long dispatchId;

    private String status;

    private String trackingId;

    private String trackingNote;

    private Timestamp statusUpdatedAt;

    private Timestamp createdAt;

    private Timestamp updatedAt;
    private String variantTitle;
    private String variantProductType;
    private BigDecimal variantSalePrice;
    private String  variantSizeCode;
//    private ReturnPolicy returnPolicy;
}
