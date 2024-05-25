package com.vehicle.controller;

import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLog;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.domain.R;
import com.common.exception.BizIllegalException;
import com.vehicle.service.IVehicleLogService;
import com.vehicle.service.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/VehicleLog")
@RequiredArgsConstructor
public class VehicleLogController {

    private final IVehicleLogService vehicleLogService;

    private final RocketMQTemplate rocketMQTemplate;

    @PostMapping("/addVehicleLog")
    public R<Void> addVehicleLog(@RequestBody VehicleLog vehicleLog) {
        vehicleLogService.save(vehicleLog);
        rocketMQTemplate.convertAndSend("VehicleLog-topic", vehicleLog);
        return R.ok();
    }

    @GetMapping("/getVehicleLog/{taskId}")
    public R<List<List<Double>>> getVehicleLog(@PathVariable Long taskId) {
        return R.ok((vehicleLogService.getPathByTaskId(taskId)));
    }


    
}
