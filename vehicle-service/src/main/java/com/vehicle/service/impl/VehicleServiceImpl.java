package com.vehicle.service.impl;


import com.api.domain.po.Vehicle;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.mapper.VehicleMapper;
import com.vehicle.service.IVehicleService;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle> implements IVehicleService {
}
