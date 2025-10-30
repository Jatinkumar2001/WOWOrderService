package com.enterprisex.wallsofwonder.oms.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name="carts",schema="oms")
public class CartEntity extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;
    private Long userId;
    private Boolean isActive = false;
    @OneToMany(mappedBy="cartId")
    private List<CartsItemsEntity> cartItems;

}