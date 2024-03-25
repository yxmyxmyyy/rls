package com.vehicle.controller;

import com.api.domain.po.Vehicle;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.exception.BadRequestException;
import com.common.exception.BizIllegalException;
import com.vehicle.service.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/Vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final IVehicleService VehicleService;


    //使用车
    @PutMapping("/use")
    public boolean use(@RequestBody List<Long> idList) {
        VehicleService.decreaseStatusByIds(idList);
        return true;
    }

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
    public boolean update(@RequestBody List<Vehicle> vehicle) {
        return VehicleService.updateBatchById(vehicle);
    }

    // 查询全部空闲
    @GetMapping("/findAll")
    public List<Vehicle> findAll() {
        // 创建QueryWrapper对象
        QueryWrapper<Vehicle> queryWrapper = new QueryWrapper<>();
        // 添加条件，假设空闲的状态值为"空闲"
        queryWrapper.eq("status", 1);
        //降序
        queryWrapper.orderByDesc("capacity");
        //判断是否为空，如果为空抛出异常
        List<Vehicle> vehicles = VehicleService.list(queryWrapper);
        if (vehicles.isEmpty()) {
            throw new BizIllegalException("没有空闲车辆");
        }
        return vehicles;
    }
    // 分页查询
    @GetMapping("/find")
    public IPage<Vehicle> find(Integer pageNum, Integer pageSize) {
        IPage<Vehicle> ip = new Page<>(pageNum, pageSize);
        return VehicleService.page(ip);
    }
    
}
