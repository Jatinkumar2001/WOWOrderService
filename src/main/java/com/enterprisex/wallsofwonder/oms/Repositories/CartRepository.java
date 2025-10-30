package com.enterprisex.wallsofwonder.oms.Repositories;


import com.enterprisex.wallsofwonder.oms.Entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CartRepository extends JpaRepository<CartEntity, UUID> {
    @Query(value="select * from oms.carts c where c.user_id = :id and is_active is true order by created_at desc limit 1",nativeQuery = true)
    CartEntity findByUserId(Long id);
}
