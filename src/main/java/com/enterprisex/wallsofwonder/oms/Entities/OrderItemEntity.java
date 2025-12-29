package com.enterprisex.wallsofwonder.oms.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name="order_items",schema="oms")
@DynamicInsert
public class OrderItemEntity extends AbstractEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;

    @JoinColumn(name = "order_id")
    @ManyToOne
    private OrderEntity order;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "title")
    private String title;

    @Column(name = "variant_id")
    private Long variantId;

    @Column(name = "sale_price")
    private Double salePrice;

    @Column(name = "max_retail_price")
    private Double maxRetailPrice;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "image")
    private String image;

    @Column(name = "main_category")
    private String mainCategory;

    @Column(name = "sub_category")
    private String subCategory;

    @Column(name = "attribute_1")
    private String attribute1;

    @Column(name = "attribute_2")
    private String attribute2;

    @Column(name = "attribute_3")
    private String attribute3;

    @Column(name = "attribute_4")
    private String attribute4;

    @Column(name = "attribute_5")
    private String attribute5;

    @Column(name = "attribute_6")
    private String attribute6;

    private String userTrackingId;
}