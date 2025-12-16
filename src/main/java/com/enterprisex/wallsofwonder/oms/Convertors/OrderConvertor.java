package com.enterprisex.wallsofwonder.oms.Convertors;
import com.enterprisex.wallsofwonder.oms.DTO.CartItemsDTO;
import com.enterprisex.wallsofwonder.oms.DTO.OrderItemDTO;
import com.enterprisex.wallsofwonder.oms.DTO.Request.CartItemsRequest;
import com.enterprisex.wallsofwonder.oms.Entities.OrderEntity;
import com.enterprisex.wallsofwonder.oms.Entities.OrderItemEntity;
import com.enterprisex.wallsofwonder.oms.Entities.OrderItemsPickListEntity;
import com.enterprisex.wallsofwonder.oms.Enums.OrderStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderConvertor {

    public OrderEntity buildOrderEntity(CartItemsRequest orderRequest) {

        OrderEntity order = new OrderEntity();
        order.setAddressLine1(orderRequest.getUserDeliveryAddress().getStreetAddress());
        order.setAddressLine2(orderRequest.getUserDeliveryAddress().getLandmark());
        order.setAddressType(orderRequest.getUserDeliveryAddress().getType());
        order.setCity(orderRequest.getUserDeliveryAddress().getCity());
        order.setState(orderRequest.getUserDeliveryAddress().getState());
        order.setDialCode(orderRequest.getUserDeliveryAddress().getDialCode());
        order.setEmail(orderRequest.getUserDeliveryAddress().getEmail());
        order.setFirstName(orderRequest.getUserDeliveryAddress().getFirstName());
        order.setLastName(orderRequest.getUserDeliveryAddress().getLastName());
        order.setOrderStatus(OrderStatus.PENDING_PAYMENT.getCode());
        order.setPhone(orderRequest.getUserDeliveryAddress().getRegisterPhoneNumber());
        order.setPhoneNumber(orderRequest.getUserDeliveryAddress().getPhoneNumber());
        order.setPostalCode("" + orderRequest.getUserDeliveryAddress().getPinCode());
        order.setUserId(orderRequest.getUserId());
        order.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        List<OrderItemEntity> OrderItemDTOList = orderRequest.getData().stream().map(this::cartItemToOrderItemEntity).collect(Collectors.toList());
        order.setOrderItems(OrderItemDTOList);
        return order;
    }

    public OrderItemEntity cartItemToOrderItemEntity(CartItemsDTO dto) {
        OrderItemEntity item = new OrderItemEntity();
        item.setVariantId(dto.getVariantId());
        item.setTitle(dto.getVariant().getTitle());
        item.setShortDescription(dto.getVariant().getShortDescription());
        item.setSalePrice(dto.getVariant().getSalePrice());
        item.setPrice(dto.getVariant().getSalePrice());
        item.setQuantity(dto.getQuantity());
        item.setImage(dto.getVariant().getImage());
        item.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return item;
    }

    public OrderItemDTO cartItemToOrderItem(CartItemsDTO dtoObject) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setVariantId( dtoObject.getVariantId());
        orderItemDTO.setPrice(dtoObject.getVariant().getSalePrice());
        orderItemDTO.setQuantity(dtoObject.getQuantity());
        orderItemDTO.setBillingPrice(dtoObject.getVariant().getSalePrice());
        return orderItemDTO;
    }


    public OrderItemsPickListEntity cartItemToPicklistItem(OrderEntity order, OrderItemEntity orderItem) {

        OrderItemsPickListEntity plitem = new OrderItemsPickListEntity();
        plitem.setUserId(order.getUserId());
        plitem.setOrderId(order.getId());
        plitem.setOrderSerialId(order.getUserTrackingId());
        plitem.setOrderItemId(orderItem.getId());
        plitem.setVariantId(orderItem.getVariantId());
        plitem.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return plitem;
    }
}
