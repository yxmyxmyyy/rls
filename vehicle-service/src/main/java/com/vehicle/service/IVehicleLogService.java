package com.vehicle.service;

import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IVehicleLogService extends IService<VehicleLog> {

    List<List<Double>> getPathByTaskId(Long taskId);

}
