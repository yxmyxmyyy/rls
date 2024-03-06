package com.vehicle.service.impl;


import com.api.domain.po.VehicleLoad;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.mapper.VehicleLoadMapper;
import com.vehicle.service.IVehicleLoadService;
import org.springframework.stereotype.Service;

@Service
public class VehicleLoadServiceImpl extends ServiceImpl<VehicleLoadMapper, VehicleLoad> implements IVehicleLoadService {
}
