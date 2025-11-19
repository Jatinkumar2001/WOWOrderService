package com.enterprisex.wallsofwonder.oms.Entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class OrderWithItemsEntity extends AbstractEntity implements Serializable {

    @Id
    private UUID id;

    @Column(name="user_tracking_id")
    private String userTrackingId;

    @Column(name = "coupon_id")
    private UUID couponId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name="payment_method")
    private String paymentMethod;

    @Column(name="payment_mode")
    private String paymentMode;

    @Column(name="payment_transaction_id")
    private String paymentTransactionId;

    @Column(name="order_delivered_user_date")
    private Timestamp orderDeliveredCustomerDate;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name="updated_at")
    private Timestamp updatedAt;

    @Column(name="items")
    private String items;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

}