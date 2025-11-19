package com.enterprisex.wallsofwonder.oms.Entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name="order_items_pick_list",schema="oms")
public class OrderItemsPickListEntity extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "order_item_id")
    private UUID orderItemId;
    
    @Column(name = "order_serial_id")
    private String orderSerialId;

    @Column(name = "line_serial_id")
    private String lineSerialId;
    
    @Column(name = "user_id")
    private long userId;
    
    @Column(name = "product_id")
    private long productId;

    @Column(name = "dispatch_id")
    private long dispatchId;
    
    @Column(name = "status")
    private String status;

    @Column(name="tracking_id")
    private String trackingId;

    @Column(name="tracking_note")
    private String trackingNote;

    @Column(name="status_updated_at")
    private Timestamp statusUpdatedAt;
    
    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name="updated_at")
    private Timestamp updatedAt;


    
    

}