package com.enterprisex.wallsofwonder.oms.Repositories;

import com.enterprisex.wallsofwonder.oms.Entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    OrderEntity findByUserTrackingId(String userTrackingId);

    @Query(value = "SELECT * FROM oms.orders o WHERE o.user_tracking_id IN (:trackingIds) AND o.user_id IN (:userIds)", nativeQuery = true)
    List<OrderEntity> getOrderByCustomerIdAndUserTrackingId(
            @Param("trackingIds") List<String> trackingIds,
            @Param("userIds") List<Long> userIds
    );
}
