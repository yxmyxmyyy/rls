package com.vehicle.controller;

import com.api.domain.po.Vehicle;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.service.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@CrossOrigin
@RequestMapping("/Vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final IVehicleService VehicleService;

    // 新增
    @PostMapping("/insert")
    public boolean insert(@RequestBody Vehicle vehicle) {
        return VehicleService.saveOrUpdate(vehicle);
    }

    // 删除
    @DeleteMapping("/delete/{id}")
    public boolean del(@PathVariable Serializable id) {
        return VehicleService.removeById(id);
    }

    // 修改
    @PutMapping("/update")
    public boolean updatePassWd(@RequestBody Vehicle vehicle) {
        return VehicleService.updateById(vehicle);
    }

    // 分页查询
    @GetMapping("/find")
    public IPage<Vehicle> find(Integer pageNum, Integer pageSize) {
        IPage<Vehicle> ip = new Page<>(pageNum, pageSize);
        return VehicleService.page(ip);
    }
    
}
