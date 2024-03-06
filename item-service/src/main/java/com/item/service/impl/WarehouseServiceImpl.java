package com.item.service.impl;


import com.api.domain.po.Item;
import com.api.domain.po.Warehouse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.item.mapper.ItemMapper;
import com.item.mapper.WarehouseMapper;
import com.item.service.IItemService;
import com.item.service.IWarehouseService;
import org.springframework.stereotype.Service;

@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements IWarehouseService {
}
