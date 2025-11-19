package com.enterprisex.wallsofwonder.oms.DTO.Response;

import com.enterprisex.wallsofwonder.oms.DTO.OrderDTO;
import com.enterprisex.wallsofwonder.oms.DTO.OrderPickListWithVariantDetailsDTO;
import com.enterprisex.wallsofwonder.oms.DTO.PickListDTO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
public class OrderWithItemsResponse {

    private UUID id;
    private String userTrackingId;
    private UUID couponId;

    private long userId;
    private String orderStatusId;

    private String paymentMethod;

    private String paymentMode;

    private String paymentTransactionId;

    private Timestamp orderApprovedAt;

    private Timestamp orderDeliveredCarrierDate;

    private Timestamp orderDeliveredCustomerDate;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private String skuItems;
    private Double totalPrice;
    private Integer totalQuantity;
    private OrderDTO order;
    private List<PickListDTO> pickListItems;
    private List<OrderPickListWithVariantDetailsDTO> pickListItemsWithVariantDetails;

}
