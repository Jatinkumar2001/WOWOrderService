package com.enterprisex.wallsofwonder.oms.Repositories;

import com.enterprisex.wallsofwonder.oms.Entities.StockMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockMasterRepository extends JpaRepository<StockMasterEntity,Long> {
    StockMasterEntity findByVariantId(Long productVariantId);

    @Query(value="SELECT sum(quantity) - sum(blocked_quantity) FROM invt.stock_master "
            + "where variant_id = ?1 "
            + "group by variant_id", nativeQuery= true)
    Long findOmniStock(Long sku);
}
