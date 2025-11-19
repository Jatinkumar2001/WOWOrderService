package com.enterprisex.wallsofwonder.oms.Repositories;

import com.enterprisex.wallsofwonder.oms.Entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    OrderEntity findByUserTrackingId(String userTrackingId);

    @Query(value = "select * from oms.orders o where o.user_tracking_id in (:userTrackingId) and o.user_id in (:customerId)",nativeQuery = true)
    List<OrderEntity> getOrderByCustomerIdAndUserTrackingId(@Param("userTrackingId") List<String> userTrackingId, @Param("customerId") List<Long> customerId);

}
