package com.enterprisex.wallsofwonder.oms.ServiceImpl;


import com.enterprisex.wallsofwonder.oms.DTO.Request.ItemDataRequest;
import com.enterprisex.wallsofwonder.oms.DTO.Request.StockMasterRequest;
import com.enterprisex.wallsofwonder.oms.DTO.StockMasterDTO;
import com.enterprisex.wallsofwonder.oms.Entities.StockMasterEntity;
import com.enterprisex.wallsofwonder.oms.Enums.Stock;
import com.enterprisex.wallsofwonder.oms.Enums.StockAction;
import com.enterprisex.wallsofwonder.oms.Repositories.StockMasterRepository;
import com.enterprisex.wallsofwonder.oms.Service.StockMasterService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockMasterServiceImpl implements StockMasterService {


    @Autowired
    private StockMasterRepository stockMasterRepository;

    @Autowired
    private ModelMapper modelMapper;

//    @Autowired
//    private ProductService productService;

    @Override
    public StockMasterDTO getStock(Long productVariantSku) {
        StockMasterEntity entity = stockMasterRepository.findByVariantId(productVariantSku);
        return modelMapper.map(entity,StockMasterDTO.class);
    }

    @Transactional
    @Override
    public StockMasterDTO addUpdateStock(StockMasterRequest request) {

        StockMasterEntity entity = stockMasterRepository.findByVariantId(request.getVariantId());
        String stockStatus = null;
        if (entity != null) {
            if (request.getActionType().equalsIgnoreCase("add")) {
                stockStatus = entity.getQuantity() ==0? Stock.IN_STOCK.name():null;
                entity.setQuantity(entity.getQuantity() + request.getQuantity());
            } else if (request.getActionType().equalsIgnoreCase("reset")) {
                entity.setQuantity(request.getQuantity());
                stockStatus = entity.getQuantity() ==0?Stock.OUT_OF_STOCK.name():null;
            } else if (request.getActionType().equalsIgnoreCase("remove")) {
                if (entity.getQuantity() - request.getQuantity() >= 0) {
                    entity.setQuantity(entity.getQuantity() - request.getQuantity());
                    stockStatus = entity.getQuantity() ==0?Stock.OUT_OF_STOCK.name():null;
                    System.out.println(entity.getQuantity()+":"+stockStatus);
                } else {
                    throw  new RuntimeException("Qty removed is more then the available qty for: " + request.getVariantId() );
                }
            }
            entity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            entity = stockMasterRepository.save(entity);

        }else {

            StockMasterEntity  stockMaster= modelMapper.map(request,StockMasterEntity.class);
            stockMaster.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            stockMaster.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            entity= stockMasterRepository.save(stockMaster);
            stockStatus = Stock.IN_STOCK.name();
        }
//        updateStock(request.getVariantId(),stockStatus);
        return modelMapper.map(entity,StockMasterDTO.class) ;
    }

    @Override
    public List<StockMasterEntity> addAndUpdateInventory(List<ItemDataRequest> itemsData, StockAction action) {
        try {
            List<StockMasterEntity> updatedData = new ArrayList<>();
//            List<StockMasterUpdateLogs> updatedDataLogs = new ArrayList<>();
            for (ItemDataRequest requestItem : itemsData) {
                StockMasterEntity availableStockMasterData = stockMasterRepository.findByVariantId(requestItem.getVariantId());
                if (availableStockMasterData != null) {
                    if (action == StockAction.ADD) {
                        availableStockMasterData.setQuantity(availableStockMasterData.getQuantity() + requestItem.getQuantity());
//                        StockMasterUpdateLogs stockMasterUpdateLog = new StockMasterUpdateLogs(storeCode, null, requestItem.getSku(), requestItem.getQuantity(), action.name(), triggerSource.name(), Timestamp.valueOf(LocalDateTime.now()));
//                        updatedDataLogs.add(stockMasterUpdateLog);

                    } else if (action == StockAction.REPLACE) {
                        availableStockMasterData.setQuantity(requestItem.getQuantity());
//                        StockMasterUpdateLogs stockMasterUpdateLog = new StockMasterUpdateLogs(storeCode, null, requestItem.getSku(), requestItem.getQuantity(), action.name(), triggerSource.name(), Timestamp.valueOf(LocalDateTime.now()));
//                        updatedDataLogs.add(stockMasterUpdateLog);

                    } else if (action == StockAction.REMOVE) {
                        availableStockMasterData.setQuantity(availableStockMasterData.getQuantity() - requestItem.getQuantity());
//                        StockMasterUpdateLogs stockMasterUpdateLog = new StockMasterUpdateLogs(storeCode, null, requestItem.getSku(), requestItem.getQuantity(), action.name(),triggerSource.name(), Timestamp.valueOf(LocalDateTime.now()));
//                        updatedDataLogs.add(stockMasterUpdateLog);
                    } else if (action == StockAction.BLOCK) {
                        availableStockMasterData.setBlockedQty(availableStockMasterData.getBlockedQty() + requestItem.getQuantity());
//                        StockMasterUpdateLogs stockMasterUpdateLog = new StockMasterUpdateLogs(storeCode, null, requestItem.getSku(), requestItem.getQuantity(), action.name(),triggerSource.name(), Timestamp.valueOf(LocalDateTime.now()));
//                        updatedDataLogs.add(stockMasterUpdateLog);
                    } else if (action == StockAction.UNBLOCK) {
                        availableStockMasterData.setBlockedQty(availableStockMasterData.getBlockedQty() - requestItem.getQuantity());
//                        StockMasterUpdateLogs stockMasterUpdateLog = new StockMasterUpdateLogs(storeCode, null, requestItem.getSku(), requestItem.getQuantity(), action.name(),triggerSource.name(), Timestamp.valueOf(LocalDateTime.now()));
//                        updatedDataLogs.add(stockMasterUpdateLog);
                    }
                    availableStockMasterData.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                    updatedData.add(availableStockMasterData);
                } else if (action == StockAction.ADD && availableStockMasterData == null) {
                    StockMasterEntity stockMaster = new StockMasterEntity();
                    stockMaster.setVariantId(requestItem.getVariantId());
                    stockMaster.setQuantity(requestItem.getQuantity());
                    stockMaster.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                    updatedData.add(stockMaster);
//                    StockMasterUpdateLogs stockMasterUpdateLog = new StockMasterUpdateLogs(storeCode, null, requestItem.getSku(), requestItem.getQuantity(), action.name(), triggerSource.name(), Timestamp.valueOf(LocalDateTime.now()));
//                    updatedDataLogs.add(stockMasterUpdateLog);
                }
            }
            if (!updatedData.isEmpty()) {
                updatedData = stockMasterRepository.saveAll(updatedData);
//                updatedDataLogs = stockMasterUpdateLogsRepository.saveAll(updatedDataLogs);
            }
            return updatedData;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long findOmniStock(Long sku) {
        return stockMasterRepository.findOmniStock(sku);
    }

//    void updateStock(Long variantId,String stockStatus){
//        if(stockStatus!=null) {
//            ProductVariantEntity productVariantEntity = productService.getProductVariantBySku(variantId);
//            productVariantEntity.setStockStatus(stockStatus);
//            productService.updateProductVariant(productVariantEntity);
//        }
//    }
}
