package com.vehicle.service.impl;


import com.api.domain.po.Transport;
import com.api.domain.po.TransportLog;
import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLog;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vehicle.mapper.VehicleLogMapper;
import com.vehicle.mapper.VehicleMapper;
import com.vehicle.service.IVehicleLogService;
import com.vehicle.service.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleLogServiceImpl extends ServiceImpl<VehicleLogMapper, VehicleLog> implements IVehicleLogService {


    private final VehicleLogMapper vehicleLogMapper;

    public List<List<Double>> getPathByTaskId(Long taskId) {
        QueryWrapper<VehicleLog> qw = new QueryWrapper<>();
        qw.eq("task_id", taskId);
        List<VehicleLog> logs = list(qw);
        List<List<Double>> path = new ArrayList<>();
        for (VehicleLog log : logs) {
            List<Double> point = Arrays.asList(log.getLng(), log.getLat(), log.getTemp());
            path.add(point);
        }
        return path;
    }




}
