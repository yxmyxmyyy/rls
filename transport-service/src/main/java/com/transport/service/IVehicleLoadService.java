package com.transport.service;

import com.api.domain.dto.AllocationResultDTO;
import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLoad;
import com.api.domain.vo.TransportVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IVehicleLoadService extends IService<VehicleLoad> {

    AllocationResultDTO allocateCargo(List<Vehicle> vehicles, List<VehicleLoad> cargo);

    List<VehicleLoad> getByTaskId(Long taskId);

}
