package com.vehicle.service;

import com.api.domain.po.Vehicle;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IVehicleService extends IService<Vehicle> {

    void decreaseStatusByIds(List<Long> idList);
}
