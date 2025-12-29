package com.enterprisex.wallsofwonder.oms.Repositories;

import com.enterprisex.wallsofwonder.oms.Entities.OrderItemsPickListEntity;
import com.enterprisex.wallsofwonder.oms.Entities.OrderPickListWithVariantDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderPickListRepository extends JpaRepository<OrderItemsPickListEntity, UUID> {

    String ORDER_META_WITH_SKU =
            "        SELECT  " +
            "            oipt.id," +
            "            oipt.order_id," +
            "            oipt.order_item_id," +
            "            oipt.user_id," +
            "            oipt.variant_id," +
            "            oipt.tracking_note, " +
            "            oipt.dispatch_id, " +
            "            oipt.status, " +
            "            oipt.tracking_id, " +
            "            oipt.status_updated_at, " +
            "            oipt.created_at, " +
            "            oipt.updated_at, " +
            "            oipt.line_serial_id, " +
            "            oipt.order_serial_id, " +
            "            pv.title AS variant_title, " +
            "            pv.sale_price AS variant_sale_price " +
            "        FROM oms.order_items_pick_list oipt " +
            "        JOIN catalog.product_variants pv  " +
            "            ON oipt.variant_id = pv.id " +
            "        WHERE oipt.user_id = ?1 " +
            "        AND (:status IS NULL OR oipt.status = ?2)";


    @Query(value = ORDER_META_WITH_SKU , nativeQuery = true)
    List<OrderPickListWithVariantDetailsEntity> findByUserId(Long userId,String status);

    String ORDER_META_WITH_SKU_AND_STORE = "select oipt.id, oipt.order_id,order_item_id,oipt.user_id,oipt.variant_id,oipt.tracking_note,oipt.dispatch_id,oipt.status, " +
            "oipt.tracking_id,oipt.status_updated_at,oipt.created_at,oipt.updated_at, " +
            "oipt.line_serial_id,oipt.order_serial_id , pv.title as variant_title, pv.article_type as variant_product_type, " +
            "pv.sale_price as variant_sale_price , pv.size_code as  variant_size_code from oms.order_items_pick_list oipt " +
            "inner join catalog.product_variants pv on oipt.variant_id = pv.id "
            +"inner join oms.order_items_meta oim on oipt.order_serial_id = oim.user_tracking_id "
            +"where oim.store_id = ?1";

    String ORDER_META_WITH_SKU_AND_STORE_COUNT = "select count(*) from oms.order_items_pick_list oipt " +
            "inner join catalog.product_variants pv on oipt.variant_id = pv.id "
            +"inner join oms.order_items_meta oim on oipt.order_serial_id = oim.user_tracking_id "
            +"where oim.store_id = ?1";

    @Query(value = ORDER_META_WITH_SKU_AND_STORE,countQuery =ORDER_META_WITH_SKU_AND_STORE_COUNT , nativeQuery = true)
    List<OrderPickListWithVariantDetailsEntity> findByStoreId(Long storeId);
}
