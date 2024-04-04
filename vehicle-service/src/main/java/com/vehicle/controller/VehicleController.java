package com.vehicle.controller;

import com.api.domain.po.Item;
import com.api.domain.po.Vehicle;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.domain.R;
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
        if (vehicle.getCapacity()<1000){
            vehicle.setType("小货车");
        }else if (vehicle.getCapacity()<2000){
            vehicle.setType("中货车");
        }else {
            vehicle.setType("大货车");
        }
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

    //查找多个
    @PostMapping("/findMore")
    public List<Vehicle> findMore(@RequestBody List<Long> ids) {
        return VehicleService.listByIds(ids);
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
    @PostMapping("/find")
    public R<Page<Vehicle>> find(@RequestBody Vehicle vehicle, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return R.ok(VehicleService.find(vehicle,pageNum, pageSize));
    }
    
}
