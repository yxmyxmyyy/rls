package com.vehicle.controller;

import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLoad;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.service.IVehicleLoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@CrossOrigin
@RequestMapping("/VehicleLoad")
@RequiredArgsConstructor
public class VehicleLoadController {

    private final IVehicleLoadService VehicleLoadService;

    // 新增
    @PostMapping("/insert")
    public boolean insert(@RequestBody VehicleLoad vehicleload) {
        return VehicleLoadService.saveOrUpdate(vehicleload);
    }

    // 删除
    @DeleteMapping("/delete/{id}")
    public boolean del(@PathVariable Serializable id) {
        return VehicleLoadService.removeById(id);
    }

    // 修改
    @PutMapping("/update")
    public boolean updatePassWd(@RequestBody VehicleLoad vehicleload) {
        return VehicleLoadService.updateById(vehicleload);
    }

    // 分页查询
    @GetMapping("/find")
    public IPage<VehicleLoad> find(Integer pageNum, Integer pageSize) {
        IPage<VehicleLoad> ip = new Page<>(pageNum, pageSize);
        return VehicleLoadService.page(ip);
    }
    
}
