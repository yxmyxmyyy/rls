package com.vehicle.controller;

import com.api.domain.po.Vehicle;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.exception.BizIllegalException;
import com.vehicle.service.IVehicleLogService;
import com.vehicle.service.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/VehicleLog")
@RequiredArgsConstructor
public class VehicleLogController {

    private final IVehicleLogService vehicleLogService;

    @GetMapping("/getVehicleLog/{taskId}")
    public List<List<Double>> getVehicleLog(@PathVariable Long taskId) {
        return vehicleLogService.getPathByTaskId(taskId);
    }


    
}
