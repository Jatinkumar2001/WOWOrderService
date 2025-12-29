package com.enterprisex.wallsofwonder.oms.ServiceImpl;

import com.enterprisex.wallsofwonder.oms.Convertors.OrderConvertor;
import com.enterprisex.wallsofwonder.oms.DTO.OrderDTO;
import com.enterprisex.wallsofwonder.oms.DTO.OrderItemDTO;
import com.enterprisex.wallsofwonder.oms.DTO.OrderPickListWithVariantDetailsDTO;
import com.enterprisex.wallsofwonder.oms.DTO.Request.CartItemsRequest;
import com.enterprisex.wallsofwonder.oms.DTO.Request.ItemDataRequest;
import com.enterprisex.wallsofwonder.oms.DTO.Request.ValidateCartItemInventoryRequest;
import com.enterprisex.wallsofwonder.oms.DTO.Request.ValidateCartRequest;
import com.enterprisex.wallsofwonder.oms.DTO.Response.OrderWithItemsResponse;
import com.enterprisex.wallsofwonder.oms.DTO.Response.PaginationResponseHandler;
import com.enterprisex.wallsofwonder.oms.DTO.Response.ValidateCartInventoryResponse;
import com.enterprisex.wallsofwonder.oms.DTO.Response.ValidateCartResponseItemInventory;
import com.enterprisex.wallsofwonder.oms.Entities.*;
import com.enterprisex.wallsofwonder.oms.Enums.OrderStatus;
import com.enterprisex.wallsofwonder.oms.Enums.StockAction;
import com.enterprisex.wallsofwonder.oms.Enums.ValidationStatus;
import com.enterprisex.wallsofwonder.oms.Repositories.OrderItemRepository;
import com.enterprisex.wallsofwonder.oms.Repositories.OrderPickListRepository;
import com.enterprisex.wallsofwonder.oms.Repositories.OrderRepository;
import com.enterprisex.wallsofwonder.oms.Service.OrderService;
import com.enterprisex.wallsofwonder.oms.Service.StockMasterService;
import com.enterprisex.wallsofwonder.oms.UserContext;
import com.enterprisex.wallsofwonder.oms.Util.OrderTrackingIdGenerator;
import com.enterprisex.wallsofwonder.oms.Util.PaginationUtil;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    protected static Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private OrderConvertor orderConvertor;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private StockMasterService stockMasterService;

    @Autowired
    private OrderPickListRepository orderPickListRepository;

    @Autowired
    private OrderTrackingIdGenerator generateTrackingId;

    @Transactional
    @Override
    public OrderDTO addOrder(CartItemsRequest productRequests) {
        OrderDTO response;
//        OrderMetaEntity orderMeta = new OrderMetaEntity();
//        List<OrderItemDTO> OrderItemDTOList = productRequests.getData().stream().map(o->orderConvertor.cartItemToOrderItem(o)).toList();
//        orderMeta.setUserTrackingId(generateTrackingId.generate(null,this));
//        orderMeta.setPaymentMethod("UPI");
//        List<OrderItemMetaEntity> listOfOrderItems = OrderItemDTOList.stream().map(x -> mapper.map(x, OrderItemMetaEntity.class)).toList();
//        orderMeta.setUserId(productRequests.getUserId());
//        if (productRequests.getPaymentMode().equalsIgnoreCase("COD")) {
//            orderMeta.setOrderStatus(OrderStatusEnum.PENDING_COD_VERIFICATION.getCode());
//        } else {
//            orderMeta.setOrderStatus(OrderStatusEnum.PENDING_PAYMENT.getCode());
//        }
//
//        orderMeta.setPaymentMode(productRequests.getPaymentMode());
//        for (OrderItemMetaEntity orderItemMeta : listOfOrderItems) {
//            orderItemMeta.setOrder(orderMeta);
//            orderItemMeta.setUserTrackingId(orderMeta.getUserTrackingId());
//        }
//        orderMeta.getOrder().addAll(listOfOrderItems);
//        orderMeta.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
//
//        orderMeta = orderMetaRepository.save(orderMeta);
//
//        List<ItemDataRequest> orderItems = new ArrayList<>();
//        for (OrderItemMetaEntity orderItemMeta : orderMeta.getOrder()) {
//            orderItemMeta.setUserTrackingId(orderMeta.getUserTrackingId());
//            ItemDataRequest id = new ItemDataRequest();
//            id.setQuantity(orderItemMeta.getQuantity());
//            id.setVariantId(orderItemMeta.getVariantId());
//            id.setStoreId(orderItemMeta.getStoreId());
//            orderItems.add(id);
//        }
//        orderMeta = orderMetaRepository.save(orderMeta);

        OrderEntity order = orderConvertor.buildOrderEntity(productRequests);
        order.setUserTrackingId(generateTrackingId.generate(null, this));
        order.setPaymentMode("UPI");
        order.setOrderDeliveredUserDate(new Timestamp(new Date().getTime() + 600000));
        OrderEntity savedOrder = orderRepository.save(order);
        for (OrderItemEntity orderItem : order.getOrderItems()) {
            orderItem.setUserTrackingId(orderItem.getUserTrackingId());
            orderItem.setOrder(savedOrder);
        }
        List<ItemDataRequest> orderItems = new ArrayList<>();
        for (OrderItemEntity orderItemMeta : order.getOrderItems()) {
//            orderItemMeta.setUserTrackingId(orderMeta.getUserTrackingId());
            ItemDataRequest id = new ItemDataRequest();
            id.setQuantity(orderItemMeta.getQuantity());
            id.setVariantId(orderItemMeta.getVariantId());
            orderItems.add(id);
        }
        orderItemRepository.saveAll(order.getOrderItems());
//        StoreMaster serviceAblePinCode = storeMasterRepository.findByPinCode(productRequests.getCustomerDeliveryAddress().getPinCode());

        stockMasterService.addAndUpdateInventory(
                orderItems, StockAction.BLOCK);
        response = mapper.map(order, OrderDTO.class);
        return response;
    }

    @Override
    public ValidateCartInventoryResponse validateCartInventory(ValidateCartRequest cartData) {
        ValidateCartInventoryResponse response = new ValidateCartInventoryResponse();
        List<ValidateCartResponseItemInventory> responseArray = new ArrayList<>();
        for (ValidateCartItemInventoryRequest cartItem : cartData.getCartItems()) {
            Long inventoryData = stockMasterService.findOmniStock(cartItem.getVariantId());
//            PriceListMaster priceMasterDataForSku = priceListMasterRepo.findBySku(cartItem.getVariantSku());
            ValidationStatus status = ValidationStatus.AVAILABLE;
            if (inventoryData == null)
                inventoryData = 0L;
            if (inventoryData < cartItem.getQuantity()) {
                status = ValidationStatus.OUT_OF_STOCK;
            }
//            else if (priceMasterDataForSku.getSalePrice() > cartItem.getPrice()) {
//                status = ValidationStatus.PRICE_CHANGED;
//            }
//            double price = priceMasterDataForSku.getSalePrice();

            long qty = (inventoryData > cartItem.getQuantity()) ? cartItem.getQuantity() : inventoryData;
            ValidateCartResponseItemInventory cartItemRes = new ValidateCartResponseItemInventory();
            cartItemRes.setVariantId(cartItem.getVariantId());
            cartItemRes.setQuantity(qty);
            cartItemRes.setPrice(cartItem.getPrice());
            cartItemRes.setStatus(status);
            responseArray.add(cartItemRes);
        }
        response.setValid(responseArray.stream().allMatch(item -> item.getStatus() == ValidationStatus.AVAILABLE));
        response.setCartItemInventories(responseArray);
        return response;
    }

    @Override
    public OrderDTO processOrder(String orderId, OrderStatus status) {


        Optional<OrderEntity> optionalOrder = orderRepository.findById(UUID.fromString(orderId));
        if (optionalOrder.isPresent()) {
            OrderEntity order = optionalOrder.get();
            order.setOrderStatus(status.getCode());
            order = orderRepository.save(order);

            List<OrderItemsPickListEntity> orderPicklist = this.generatePicklist(order);

            order.setOrderStatus(OrderStatus.PICKLIST_GENERATED.name());
            order = orderRepository.save(order);

            order = orderRepository.save(order);
            order.setOrderItems(null);
            OrderDTO response = mapper.map(order, OrderDTO.class);


//            if (order.getUserTrackingId() != null && response.getPaymentMode() != null) {
//            new OrderSmsSender(order, orderMeta.getUserTrackingId(), response.getPaymentMode()).start();
//            }
            return response;
        }
        throw new RuntimeException("Order Not Found");
    }

    @Override
    public PaginationResponseHandler getUserOrder(Pageable pageable, String status) throws URISyntaxException {

        PaginationResponseHandler responseHandler = new PaginationResponseHandler();
        Long userId = UserContext.getUser().getId();
        // 1️⃣ Fetch paginated orders
        Page<OrderWithItemsEntity> ordersPage =
                orderItemRepository.findByUserId(userId, pageable);

        if (ordersPage.isEmpty()) {
            responseHandler.setData(List.of());
            responseHandler.setTotalNumberOfElement(0);
            responseHandler.setTotalNumberOfPages(0);
            return responseHandler;
        }

        // 2️⃣ Fetch pick list (already filtered in SQL)
        List<OrderPickListWithVariantDetailsDTO> pickListDtos =
                orderPickListRepository.findByUserId(userId,status)
                        .stream()
                        .map(e -> mapper.map(e, OrderPickListWithVariantDetailsDTO.class))
                        .peek(p -> p.setStatus(getStatusForCustomer(p.getStatus())))
                        .toList();

        // 3️⃣ Build pick list map → orderId → items
        Map<UUID, List<OrderPickListWithVariantDetailsDTO>> pickListByOrderId =
                pickListDtos.stream()
                        .collect(Collectors.groupingBy(
                                OrderPickListWithVariantDetailsDTO::getOrderId
                        ));

        // 4️⃣ Extract trackingIds & userIds
        List<String> trackingIds = ordersPage.stream()
                .map(OrderWithItemsEntity::getUserTrackingId)
                .distinct()
                .toList();

        List<Long> userIds = ordersPage.stream()
                .map(OrderWithItemsEntity::getUserId)
                .distinct()
                .toList();

        // 5️⃣ Fetch order master data
        Map<String, OrderDTO> orderDtoMap =
                orderRepository.getOrderByCustomerIdAndUserTrackingId(trackingIds, userIds)
                        .stream()
                        .map(o -> mapper.map(o, OrderDTO.class))
                        .collect(Collectors.toMap(
                                o -> o.getUserTrackingId() + "_" + o.getUserId(),
                                o -> o
                        ));

        // 6️⃣ Build final response
        List<OrderWithItemsResponse> response = new ArrayList<>();

        for (
                OrderWithItemsEntity entity : ordersPage) {

            List<OrderPickListWithVariantDetailsDTO> pickList =
                    pickListByOrderId.getOrDefault(entity.getId(), List.of());

            if (status != null && pickList.isEmpty()) {
                continue;
            }

            OrderWithItemsResponse dto = new OrderWithItemsResponse();

            dto.setOrder(
                    orderDtoMap.get(
                            entity.getUserTrackingId() + "_" + entity.getUserId()
                    )
            );

            dto.setId(entity.getId());
            dto.setUserTrackingId(entity.getUserTrackingId());
            dto.setCouponId(entity.getCouponId());
            dto.setUserId(entity.getUserId());
            dto.setCreatedAt(entity.getCreatedAt());
            dto.setSkuItems(entity.getItems());
            dto.setTotalPrice(entity.getTotalPrice());
            dto.setTotalQuantity(entity.getTotalQuantity());
            dto.setPickListItemsWithVariantDetails(pickList);

            response.add(dto);
        }

        // 7️⃣ Pagination metadata
        responseHandler.setData(response);
        responseHandler.setTotalNumberOfElement(ordersPage.getTotalElements());
        responseHandler.setTotalNumberOfPages(ordersPage.getTotalPages());
        responseHandler.setNextPage(
                PaginationUtil.generatePaginationData(
                        ordersPage,
                        pageable.getPageNumber(),
                        pageable.getPageSize()
                )
        );

        return responseHandler;
    }

    public List<OrderItemsPickListEntity> generatePicklist(OrderEntity order) {
        List<OrderItemsPickListEntity> orderPicklist = new ArrayList<>();
        int lineIndex = 1;
        List<ItemDataRequest> stockItems = new ArrayList<>();
        for (OrderItemEntity item : order.getOrderItems()) {
            ItemDataRequest stockItem = new ItemDataRequest();
            stockItem.setVariantId(item.getVariantId());
            stockItem.setQuantity(item.getQuantity());
            stockItems.add(stockItem);
            for (int i = 0; i < item.getQuantity(); i++) {
                OrderItemsPickListEntity plitem = orderConvertor.cartItemToPicklistItem(order, item);
                String lineSerialNo = String.format("%06d", (lineIndex++));
                plitem.setLineSerialId(lineSerialNo);
                plitem.setStatus(OrderStatus.PENDING.name());
                plitem.setStatusUpdatedAt(new Timestamp(System.currentTimeMillis()));
                orderPicklist.add(plitem);
            }
        }
        stockMasterService.addAndUpdateInventory(
                stockItems, StockAction.REMOVE);
        return orderPickListRepository.saveAll(orderPicklist);
    }

    private String getStatusForCustomer(String status) {
        OrderStatus statusEnum = OrderStatus.PROCESSING;
        try {
            statusEnum = OrderStatus.valueOf(status.toUpperCase());


            switch (statusEnum) {
                case PENDING_CONFIRMATION:
                case PENDING_PAYMENT:
                case PENDING_COD_VERIFICATION:
                case PAYMENT_ERROR:
                    return OrderStatus.PENDING_CONFIRMATION.name();

                case CONFIRMED:
                case PAYMENT_VERIFIED:
                case COD_VERIFIED:
                case PICKLIST_GENERATED:
                case PUSHED_TO_ERP:
                case ERROR_IN_PUSH_TO_ERP:
                case PROCESSING:
                case PICKED:
                case PACKED:
                    return OrderStatus.CONFIRMED.name();
                case CANCELLED:
                case CANCELLED_BY_ADMIN:
                case REFUND_ERROR:
                case CANCEL_INITIATED:
                case CANCEL_ERROR:
                case CANCEL_PUSHED_TO_ERP:
                case ERROR_IN_CANCEL_PUSHED_TO_ERP:
                    return OrderStatus.CANCELLED.name();

                default:
                    return statusEnum.name();

            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        return status.toUpperCase();
    }

}

