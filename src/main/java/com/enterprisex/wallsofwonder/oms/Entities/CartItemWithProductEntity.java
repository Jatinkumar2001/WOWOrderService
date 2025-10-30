package com.enterprisex.wallsofwonder.oms.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;


@Data
@Entity
public class CartItemWithProductEntity implements Serializable {

    @Id
    private String uniqueId;
    private UUID cartId;
//    private Long userId;
    private UUID id;
    private String title;
    private Long productId;
    private Double salePrice;
    private BigInteger maxRetailPrice;
    private String urlKey;
    private String shortDescription;
    private String fullDescription;
    private String image;
    private String metaTitle;
    private String metaDescription;
    private String metaKeyword;
    private String stockStatus;
    private boolean active;
    private boolean isPublished;
    private Integer quantity;
    private BigDecimal discountedPrice;
//    private Boolean isDiscountShow;
    private String mainCategory;
    private String subCategory;

}
