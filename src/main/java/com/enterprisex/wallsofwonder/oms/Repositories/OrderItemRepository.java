package com.enterprisex.wallsofwonder.oms.Repositories;

import com.enterprisex.wallsofwonder.oms.Entities.OrderItemEntity;
import com.enterprisex.wallsofwonder.oms.Entities.OrderWithItemsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {


    String ORDER_META_WITH_SKU = "        SELECT " +
            "            om.id," +
            "            om.user_tracking_id," +
            "            om.coupon_id," +
            "            om.user_id," +
            "            om.order_status," +
            "            om.payment_method," +
            "            om.payment_mode," +
            "            om.payment_transaction_id," +
            "            om.order_delivered_user_date," +
            "            om.created_at," +
            "            om.updated_at," +
            "            STRING_AGG(oim.variant_id::text, ',') AS items," +
            "            SUM(oim.sale_price) AS total_price," +
            "            SUM(oim.quantity) AS total_quantity" +
            "        FROM oms.orders om" +
            "        JOIN oms.order_items oim " +
            "            ON om.user_tracking_id = oim.user_tracking_id" +
            "        WHERE om.user_id = ?1" +
            "        GROUP BY " +
            "            om.id," +
            "            om.user_tracking_id," +
            "            om.coupon_id," +
            "            om.user_id," +
            "            om.order_status," +
            "            om.payment_method," +
            "            om.payment_mode," +
            "            om.payment_transaction_id," +
            "            om.order_delivered_user_date," +
            "            om.created_at," +
            "            om.updated_at";
    String ORDER_META_WITH_SKU_COUNT = "SELECT COUNT(DISTINCT om.user_tracking_id) " +
            "        FROM oms.orders om " +
            "        JOIN oms.order_items oim " +
            "            ON om.user_tracking_id = oim.user_tracking_id " +
            "        WHERE om.user_id = ?1";

    String ORDER_META_WITH_SKU_WITH_STORE = "select om.id,om.user_tracking_id,om.coupon_id,om.user_id,om.order_status,om.payment_method,om.payment_mode, "
            +"om.payment_transaction_id,om.order_delivered_user_date,om.created_at,om.updated_at, STRING_AGG(oim.variant_id::text,',') as items , "
            +"sum(oim.billing_price) as total_price, sum(oim.quantity) as total_quantity  from oms.orders_meta om "
            +"inner join oms.order_items_meta oim on om.user_tracking_id =  oim.user_tracking_id where oim.store_id = ?1 group by om.id";

    String ORDER_META_WITH_SKU_WITH_STORE_COUNT = "select count(DISTINCT om.user_tracking_id) from oms.orders_meta"
            +"inner join oms.order_items_meta oim on om.user_tracking_id =  oim.user_tracking_id where om.user_id = ?1 ";

    @Query(value = ORDER_META_WITH_SKU,countQuery =ORDER_META_WITH_SKU_COUNT , nativeQuery = true)
    Page<OrderWithItemsEntity> findByUserId(Long userId, Pageable pageable);

//    @Query(value = ORDER_META_WITH_SKU_WITH_STORE,countQuery =ORDER_META_WITH_SKU_WITH_STORE_COUNT , nativeQuery = true)
//    Page<OrderMetaWithSkuEntity> findByStoreId(Long storeId, Pageable pageable);
}
