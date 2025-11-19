package com.enterprisex.wallsofwonder.oms.Service;

import com.enterprisex.wallsofwonder.oms.DTO.Request.ItemDataRequest;
import com.enterprisex.wallsofwonder.oms.DTO.Request.StockMasterRequest;
import com.enterprisex.wallsofwonder.oms.DTO.StockMasterDTO;
import com.enterprisex.wallsofwonder.oms.Entities.StockMasterEntity;
import com.enterprisex.wallsofwonder.oms.Enums.StockAction;

import java.util.List;

public interface StockMasterService {
    StockMasterDTO getStock(Long productId);

    StockMasterDTO addUpdateStock(StockMasterRequest request);
    List<StockMasterEntity> addAndUpdateInventory(List<ItemDataRequest> itemsData, StockAction action);
    Long findOmniStock(Long sku);
}
