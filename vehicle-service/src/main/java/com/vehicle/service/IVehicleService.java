package com.vehicle.service;

import com.api.domain.po.Vehicle;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IVehicleService extends IService<Vehicle> {

    void decreaseStatusByIds(List<Long> idList);

    public Page<Vehicle> find(Vehicle Vehicle, Integer pageNum, Integer pageSize);
}
