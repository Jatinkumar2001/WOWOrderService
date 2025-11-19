package com.enterprisex.wallsofwonder.oms.DTO;


import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class PickListDTO implements Serializable {

    private UUID id;
    private UUID orderId;
    private String status;
    private String trackingId;
    private String trackingNote;
    private UUID orderItemId;
    private String orderSerialId;
    private String lineSerialId;
    private long customerId;
    private long variantSku;
    private long dispatchId;
    private Timestamp statusUpdatedAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private OrderDTO order;
    private String shipmentSummaryLogDetails;

//    private List<Object> orderStatus = Arrays.asList(OrderStatusesEnum.values());

}
