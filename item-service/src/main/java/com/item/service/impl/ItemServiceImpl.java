package com.item.service.impl;


import com.api.domain.dto.ItemDTO;
import com.api.domain.dto.VehicleLoadDTO;
import com.api.domain.po.Item;
import com.api.domain.po.VehicleLoad;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.item.mapper.ItemMapper;
import com.item.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements IItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Transactional
    public boolean deductStock(List<VehicleLoad> vehicleLoads) {
//        Long warehouseId = UserContext.getWarehouseId(); // 获取当前用户的warehouseId
        Long warehouseId = 1L;
        // 将VehicleLoad转换为productId列表
        List<Long> productIds = vehicleLoads.stream().map(VehicleLoad::getProductId).collect(Collectors.toList());

        // 检查库存
        List<Item> items = itemMapper.checkStockBatch(warehouseId, productIds);
        for (VehicleLoad load : vehicleLoads) {
            Item item = items.stream().filter(i -> i.getProductId().equals(load.getProductId())).findFirst().orElse(null);
            if (item == null || item.getStock() < load.getWeight()) {
                // 库存不足
                throw new IllegalStateException("Stock insufficient for productId: " + load.getProductId());
            }
        }

        // 扣减库存
        int updatedRows = itemMapper.decreaseStockBatch(warehouseId, vehicleLoads);
        if (updatedRows != vehicleLoads.size()) {
            // 处理扣减库存失败的情况，可能是并发操作导致的库存已变化
            throw new IllegalStateException("Failed to decrease stock properly");
        }

        return true;
    }

    public List<VehicleLoad> mergeVehicleLoads(List<VehicleLoad> vehicleLoads) {
        return new ArrayList<>(vehicleLoads.stream()
                .collect(Collectors.toMap(
                        VehicleLoad::getProductId,
                        vl -> vl,
                        (vl1, vl2) -> {
                            vl1.setWeight(vl1.getWeight() + vl2.getWeight());
                            return vl1;
                        }))
                .values());
    }

    @Transactional
    public List<Item> processVehicleLoads(ItemDTO itemDTO) {
        Long id = itemDTO.getWarehouseId();
        List<VehicleLoad> mergedVehicleLoads = mergeVehicleLoads(itemDTO.getVehicleLoad());

        // 获取所有的productId
        Set<Long> productIds = mergedVehicleLoads.stream().map(VehicleLoad::getProductId).collect(Collectors.toSet());

        // 查询数据库确定哪些productId存在
        Set<Long> existingProductIds = itemMapper.findExistingProductIds(id, productIds);

        List<VehicleLoad> existingProducts = new ArrayList<>();
        List<Item> nonExistingProducts = new ArrayList<>();

        // 分类存在和不存在的产品，并为不存在的产品创建Item对象
        for (VehicleLoad load : mergedVehicleLoads) {
            if (existingProductIds.contains(load.getProductId())) {
                existingProducts.add(load);
            } else {
                Item item = new Item();
                item.setWarehouseId(id);
                item.setProductId(load.getProductId());
                item.setProductName(load.getProductName());
                item.setStock(load.getWeight()); // 假设初始库存量等于载重量
                nonExistingProducts.add(item);
            }
        }

        // 对存在的产品批量更新库存
        if (!existingProducts.isEmpty()) {
            itemMapper.increaseStockBatch(id, existingProducts);
        }

        // 返回不存在的产品列表，现在是List<Item>
        return nonExistingProducts;
    }

}
