package com.vehicle.service.impl;


import com.api.domain.po.Vehicle;
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

}
