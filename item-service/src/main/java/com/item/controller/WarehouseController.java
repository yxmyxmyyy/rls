package com.item.controller;


import com.api.domain.po.Warehouse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.item.service.IWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@CrossOrigin
@RequestMapping("/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final IWarehouseService warehouseService;

    // 新增
    @PostMapping("/insert")
    public boolean insert(@RequestBody Warehouse warehouse) {
        return warehouseService.saveOrUpdate(warehouse);
    }

    // 删除
    @DeleteMapping("/delete/{id}")
    public boolean del(@PathVariable Serializable id) {
        return warehouseService.removeById(id);
    }

    // 修改
    @PutMapping("/update")
    public boolean updatePassWd(@RequestBody Warehouse warehouse) {
        return warehouseService.updateById(warehouse);
    }

    // 分页查询
    @GetMapping("/find")
    public IPage<Warehouse> find(Integer pageNum, Integer pageSize) {
        IPage<Warehouse> ip = new Page<>(pageNum, pageSize);
        return warehouseService.page(ip);
    }
    
}
