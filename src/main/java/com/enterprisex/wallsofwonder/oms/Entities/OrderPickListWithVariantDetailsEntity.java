package com.enterprisex.wallsofwonder.oms.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class OrderPickListWithVariantDetailsEntity extends AbstractEntity {

    @Id
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
    private Long userId;

    @Column(name = "variant_id")
    private Long variantId;

    @Column(name = "dispatch_id")
    private Long dispatchId;

    @Column(name = "status")
    private String status;

    @Column(name = "tracking_id")
    private String trackingId;

    @Column(name = "tracking_note")
    private String trackingNote;

    @Column(name = "status_updated_at")
    private Timestamp statusUpdatedAt;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "variant_title")
    private String variantTitle;

    @Column(name = "variant_sale_price")
    private BigDecimal variantSalePrice;
}
