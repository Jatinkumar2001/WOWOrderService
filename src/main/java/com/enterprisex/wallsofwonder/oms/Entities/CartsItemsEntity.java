package com.enterprisex.wallsofwonder.oms.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name="cart_items",schema="oms")
public class CartsItemsEntity extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;
    private Integer quantity;
	private Long productId;
    private UUID cartId;


}