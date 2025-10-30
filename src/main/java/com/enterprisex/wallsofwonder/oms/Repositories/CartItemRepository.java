package com.enterprisex.wallsofwonder.oms.Repositories;


import com.enterprisex.wallsofwonder.oms.Entities.CartItemWithProductEntity;
import com.enterprisex.wallsofwonder.oms.Entities.CartsItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface CartItemRepository extends JpaRepository<CartsItemsEntity, UUID> {

    String GET_CART_ITEMS_WITH_VARIANTS = "select CONCAT(c.id,'_',c.cart_id) as unique_id,c.id,c.cart_id,c.product_id, c.quantity, title," +
            "pv.sale_price, pv.max_retail_price,pv.url_key,pv.short_description, pv.full_description," +
            "pv.image,meta_title, meta_description, meta_keyword, pv.main_category, " +
            "pv.sub_category,pv.stock_status, pv.discounted_price , pv.active, pv.created_at, pv.is_published," +
            "pv.updated_at from oms.cart_items c " +
            "inner join catalog.products pv on c.product_id = pv.id  where c.cart_id = ?1";
    @Query(value = GET_CART_ITEMS_WITH_VARIANTS,nativeQuery = true)
    List<CartItemWithProductEntity> findByCartId(UUID cartId);

    @Query(value = "select * from oms.cart_items c where c.cart_id = cast(?1 as uuid)  and c.product_id = ?2",nativeQuery = true)
    CartsItemsEntity findByProductIdAndCartId(UUID cartId, Long id);
}
