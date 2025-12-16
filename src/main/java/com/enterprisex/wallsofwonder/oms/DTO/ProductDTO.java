package com.enterprisex.wallsofwonder.oms.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDTO implements Serializable {

    private Long id;
    private Boolean active;
    private String name;
    private Double maxRetailPrice;
    private Double salePrice;
    private Double discountedPrice;
    private String slug;
    private String title;
    private String urlKey;
    private String stockStatus;
    private String shortDescription;
    private String fullDescription;
    private String image;
    private String metaTitle;
    private String metaDescription;
    private String metaKeyword;
    private String mainCategory;
    private String subCategory;
    private String categoryUrlKey;
    private Boolean isPublished;
}
