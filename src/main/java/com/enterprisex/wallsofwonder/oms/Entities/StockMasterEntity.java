package com.enterprisex.wallsofwonder.oms.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "stock_master",schema="invt")
@Data
@DynamicInsert
public class StockMasterEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "stock_master_id_seq")
    @SequenceGenerator(name = "stock_master_id_seq", sequenceName = "invt.stock_master_id_seq", allocationSize = 1)
    private Long id;
    private Long productId;
    private Integer quantity;
    @Column(name = "blocked_quantity", nullable = false)
    private int blockedQty;
}
