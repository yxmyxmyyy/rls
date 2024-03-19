package com.item.service;

import com.api.domain.dto.ItemDTO;
import com.api.domain.po.Item;
import com.api.domain.po.VehicleLoad;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IItemService extends IService<Item> {

    boolean deductStock(Integer id,List<VehicleLoad> vehicleLoads);

    List<VehicleLoad> mergeVehicleLoads(List<VehicleLoad> vehicleLoads);

    List<Item> processVehicleLoads(ItemDTO itemDTO);

    Page<Item> find(Item Item, Integer pageNum, Integer pageSize);
}
