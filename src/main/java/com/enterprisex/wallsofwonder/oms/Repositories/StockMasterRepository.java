package com.enterprisex.wallsofwonder.oms.Repositories;

import com.enterprisex.wallsofwonder.oms.Entities.StockMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockMasterRepository extends JpaRepository<StockMasterEntity,Long> {
    StockMasterEntity findByVariantIdAndStoreId(Long productVariantId, Long storeId);

    @Query(value="SELECT sum(quantity) - sum(blocked_quantity) FROM invt.stock_master "
            + "where variant_id = ?1 and store_id = ?2 "
            + "group by variant_id", nativeQuery= true)
    Long findOmniStock(Long sku,Long storeId);
}
