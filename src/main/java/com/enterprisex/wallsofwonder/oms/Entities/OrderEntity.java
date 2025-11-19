package com.enterprisex.wallsofwonder.oms.Entities;



import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name="orders",schema="oms")
public class OrderEntity extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;
    private String userTrackingId;
    private UUID couponId;
    private Long userId;
    private String orderStatus;
    private String paymentMethod;
    private String paymentMode;
    private String paymentTransactionId;
    private Timestamp orderDeliveredUserDate;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "signup_type")
    String signupType;

    @Column(name = "email")
    String email;

    @Column(name = "social_id")
    String socialId;

    @Column(name = "social_url")
    String socialUrl;

    @Column(name = "phone")
    String phone;

    @Column(name="registered_at")
    Timestamp registeredAt;

    @Column(name = "profile_status")
    String profileStatus;

    @Column(name = "address_line1")
    String addressLine1;

    @Column(name = "address_line2")
    String addressLine2;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "dial_code")
    String dialCode;

    @Column(name = "landmark")
    String landmark;

    @Column(name = "postal_code")
    String postalCode;

    @Column(name = "city")
    String city;

    @Column(name = "state")
    String state;

    @Column(name = "addressType")
    String addressType;

    @OneToMany(mappedBy = "order")
    private List<OrderItemEntity> orderItems = new ArrayList<>();

}