package com.item.controller;

import com.api.domain.dto.ItemDTO;
import com.api.domain.po.Item;
import com.api.domain.po.User;
import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLoad;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.item.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final IItemService itemService;

    // 新增
    @PostMapping("/insert")
    public boolean insert(@RequestBody List<Item> item) {
        return itemService.saveOrUpdateBatch(item);
    }

    // 删除
    @DeleteMapping("/delete/{id}")
    public boolean del(@PathVariable Serializable id) {
        return itemService.removeById(id);
    }

    // 修改
    @PutMapping("/update")
    public boolean updatePassWd(@RequestBody Item item) {
        return itemService.updateById(item);
    }

    // 分页查询
    @GetMapping("/find")
    public IPage<Item> find(Integer pageNum, Integer pageSize) {
        IPage<Item> ip = new Page<>(pageNum, pageSize);
        return itemService.page(ip);
    }

    // 检测库存并扣减
    @PutMapping("/deductStock")
    public boolean deductStock(@RequestBody List<VehicleLoad> vehicleLoads) {
        itemService.deductStock(vehicleLoads);
        return true;
    }

    //添加存在的库存，返回不存在的库存
    @PutMapping("/insertOrUpdate")
    public boolean insertOrUpdate(@RequestBody ItemDTO itemDTO) {
        itemService.saveBatch(itemService.processVehicleLoads(itemDTO));
        return true;
    }

    //根据仓库查询
    @GetMapping("/findByWarehouse/{id}")
    public List<Item> findByWarehouse(@PathVariable Integer id) {
        // 创建QueryWrapper对象
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        // 添加条件，假设空闲的状态值为"空闲"
        queryWrapper.eq("warehouse_id", id);
        // 使用Service的lambdaQuery方法，传入构建的查询条件
        return itemService.list(queryWrapper);
    }
    
}
