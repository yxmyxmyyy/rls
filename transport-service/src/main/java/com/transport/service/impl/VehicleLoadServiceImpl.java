package com.transport.service.impl;


import com.api.domain.dto.AllocationResultDTO;
import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLoad;
import com.api.domain.vo.TransportVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.transport.mapper.VehicleLoadMapper;
import com.transport.service.IVehicleLoadService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class VehicleLoadServiceImpl extends ServiceImpl<VehicleLoadMapper, VehicleLoad> implements IVehicleLoadService {

    public AllocationResultDTO allocateCargo(List<Vehicle> vehicles, List<VehicleLoad> cargo) {
        // 按载重能力降序排序卡车
        vehicles.sort((v1, v2) -> v2.getCapacity().compareTo(v1.getCapacity()));

        List<VehicleLoad> allocatedLoads = new ArrayList<>();
        List<Vehicle> usedVehicles = new ArrayList<>();

        double totalCargoWeight = cargo.stream().mapToDouble(VehicleLoad::getWeight).sum();

        for (Vehicle vehicle : vehicles) {
            if (totalCargoWeight <= 0) break; // 如果所有货物已分配，则停止

            double availableCapacity = vehicle.getCapacity();
            List<VehicleLoad> loadsForThisVehicle = new ArrayList<>();

            Iterator<VehicleLoad> iterator = cargo.iterator();
            while (iterator.hasNext()) {
                VehicleLoad load = iterator.next();
                // 检查是否应继续使用当前车辆或转到下一辆车
                if (totalCargoWeight <= availableCapacity - 400) {
                    break; // 转到下一辆车
                }

                if (load.getWeight() <= availableCapacity) {
                    // 货物可以完全装入卡车
                    loadsForThisVehicle.add(new VehicleLoad(null, vehicle.getVehicleId(), null, load.getProductId(), load.getProductName(), load.getWeight()));
                    availableCapacity -= load.getWeight();
                    totalCargoWeight -= load.getWeight();
                    iterator.remove();
                } else {
                    // 货物需要分割
                    if (availableCapacity > 400) { // 确保车辆至少还有400kg以上的容量，才考虑分割装载
                        loadsForThisVehicle.add(new VehicleLoad(null, vehicle.getVehicleId(), null, load.getProductId(), load.getProductName(), availableCapacity));
                        load.setWeight(load.getWeight() - availableCapacity);
                        totalCargoWeight -= availableCapacity;
                    }
                    break; // 当前卡车已满载或保留至少400kg空间
                }
            }

            if (!loadsForThisVehicle.isEmpty()) {
                allocatedLoads.addAll(loadsForThisVehicle);
                vehicle.setStatus("使用中");
                usedVehicles.add(vehicle);
            }
        }

        return new AllocationResultDTO(allocatedLoads, usedVehicles);
    }








}
