package com.vehicle.service.impl;


import com.api.domain.po.Item;
import com.api.domain.po.Vehicle;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.mapper.VehicleMapper;
import com.vehicle.service.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle> implements IVehicleService {

    private final VehicleMapper vehicleMapper;


    public void decreaseStatusByIds(List<Long> idList) {
        vehicleMapper.decreaseStatusByIds(idList);
    }

    public Page<Vehicle> find(Vehicle Vehicle, Integer pageNum, Integer pageSize) {
        // 创建Page对象，其中current是当前页数，size是每页显示记录的数量
        Page<Vehicle> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Vehicle> qw = new QueryWrapper<>();
        // 根据条件添加查询条件，这里省略了空值检查，实际使用时应该加上
        if (Vehicle.getVehicleId() != null){
            qw.eq("vehicle_id", Vehicle.getVehicleId());
        }
        if (Vehicle.getType() != null && !Vehicle.getType().isEmpty()) {
            qw.eq("type", Vehicle.getType());
        }
        if (Vehicle.getCapacity() != null){
            qw.eq("capacity", Vehicle.getCapacity());
        }
        if (Vehicle.getStatus() != null){
            qw.eq("status", Vehicle.getStatus());
        }
        if (Vehicle.getLicensePlate() != null && !Vehicle.getLicensePlate().isEmpty()) {
            qw.eq("license_plate", Vehicle.getLicensePlate());
        }
        // 执行分页和条件查询
        return page(page, qw);
    }

}
