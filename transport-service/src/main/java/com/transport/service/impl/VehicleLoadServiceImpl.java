package com.transport.service.impl;


import com.api.domain.dto.AllocationResultDTO;
import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLoad;
import com.api.domain.vo.TransportVO;
import com.api.domain.vo.VehicleTypeVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.transport.mapper.VehicleLoadMapper;
import com.transport.service.IVehicleLoadService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VehicleLoadServiceImpl extends ServiceImpl<VehicleLoadMapper, VehicleLoad> implements IVehicleLoadService {

    public AllocationResultDTO allocateCargo(List<Vehicle> vehicles, List<VehicleLoad> cargo) {
        // Initialize the map for vehicle counting by capacity
        Map<Double, Integer> vehicleCountMap = new HashMap<>();

        // Sort vehicles by capacity in descending order
        vehicles.sort((v1, v2) -> v2.getCapacity().compareTo(v1.getCapacity()));

        List<VehicleLoad> allocatedLoads = new ArrayList<>();
        List<Vehicle> usedVehicles = new ArrayList<>();

        double totalCargoWeight = cargo.stream().mapToDouble(VehicleLoad::getWeight).sum();

        for (Vehicle vehicle : vehicles) {
            if (totalCargoWeight <= 0) break;

            double availableCapacity = vehicle.getCapacity();
            List<VehicleLoad> loadsForThisVehicle = new ArrayList<>();
            Iterator<VehicleLoad> iterator = cargo.iterator();
            boolean vehicleUsed = false;

            while (iterator.hasNext()) {
                VehicleLoad load = iterator.next();
                if (totalCargoWeight <= availableCapacity - 800) {
                    break;
                }

                if (load.getWeight() <= availableCapacity) {
                    loadsForThisVehicle.add(new VehicleLoad(null, vehicle.getVehicleId(), null, load.getProductId(), load.getProductName(), load.getWeight()));
                    availableCapacity -= load.getWeight();
                    totalCargoWeight -= load.getWeight();
                    iterator.remove();
                    vehicleUsed = true;
                } else {
                    if (availableCapacity > 0) {
                        loadsForThisVehicle.add(new VehicleLoad(null, vehicle.getVehicleId(), null, load.getProductId(), load.getProductName(), availableCapacity));
                        load.setWeight(load.getWeight() - availableCapacity);
                        totalCargoWeight -= availableCapacity;
                        vehicleUsed = true;
                    }
                    break;
                }
            }

            if (vehicleUsed) {
                allocatedLoads.addAll(loadsForThisVehicle);
                vehicle.setStatus("使用中");
                usedVehicles.add(vehicle);
                // Update the vehicle count map
                vehicleCountMap.put(vehicle.getCapacity(), vehicleCountMap.getOrDefault(vehicle.getCapacity(), 0) + 1);
            }
        }

        // Convert the map to a list of VehicleTypeVO
        List<VehicleTypeVO> vehicleCountByType = vehicleCountMap.entrySet().stream()
                .map(entry -> new VehicleTypeVO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return new AllocationResultDTO(allocatedLoads, usedVehicles, vehicleCountByType);
    }

    public List<VehicleLoad> getByTaskId(Long taskId) {
        // 创建QueryWrapper对象
        QueryWrapper<VehicleLoad> queryWrapper = new QueryWrapper<>();
        // 添加条件，假设空闲的状态值为"空闲"
        queryWrapper.eq("task_id", taskId);
        // 使用Service的lambdaQuery方法，传入构建的查询条件
        return list(queryWrapper);
    }

}

