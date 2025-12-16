package com.enterprisex.wallsofwonder.oms.Repositories;


import com.enterprisex.wallsofwonder.oms.Entities.CartItemWithProductEntity;
import com.enterprisex.wallsofwonder.oms.Entities.CartsItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface CartItemRepository extends JpaRepository<CartsItemsEntity, UUID> {

    String GET_CART_ITEMS_WITH_VARIANTS = "select CONCAT(c.id,'_',c.cart_id) as unique_id,c.id,c.cart_id,c.variant_id, c.quantity, pv.title , pv.name," +
            "pv.sale_price, pv.max_retail_price,pv.url_key,p.short_description, p.full_description," +
            "p.image,meta_title, meta_description, meta_keyword, p.main_category, " +
            "p.sub_category,pv.stock_status from oms.cart_items c " +
            "inner join catalog.product_variants pv on c.variant_id = pv.id  " +
            "inner join catalog.products p on p.id = pv.product_id where c.cart_id = ?1";
    @Query(value = GET_CART_ITEMS_WITH_VARIANTS,nativeQuery = true)
    List<CartItemWithProductEntity> findByCartId(UUID cartId);

    @Query(value = "select * from oms.cart_items c where c.cart_id = cast(?1 as uuid)  and c.variant_id = ?2",nativeQuery = true)
    CartsItemsEntity findByVariantIdAndCartId(UUID cartId, Long id);
}
