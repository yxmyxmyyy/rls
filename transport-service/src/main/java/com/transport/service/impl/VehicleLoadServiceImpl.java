package com.transport.service.impl;


import com.api.client.IVehicleClient;
import com.api.domain.dto.AllocationResultDTO;
import com.api.domain.dto.VehicleProductInfoDTO;
import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLoad;
import com.api.domain.vo.TransportVO;
import com.api.domain.vo.VehicleGroupVO;
import com.api.domain.vo.VehicleTypeVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.common.exception.BizIllegalException;
import com.transport.mapper.VehicleLoadMapper;
import com.transport.service.IVehicleLoadService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleLoadServiceImpl extends ServiceImpl<VehicleLoadMapper, VehicleLoad> implements IVehicleLoadService {

    private final IVehicleClient vehicleClient;
    @GlobalTransactional
    public AllocationResultDTO allocateCargo(List<Vehicle> vehicles, List<VehicleLoad> cargo) {
        // Initialize the map for vehicle counting by capacity
        Map<Double, Integer> vehicleCountMap = new HashMap<>();

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
                usedVehicles.add(vehicle);
                // Update the vehicle count map
                vehicleCountMap.put(vehicle.getCapacity(), vehicleCountMap.getOrDefault(vehicle.getCapacity(), 0) + 1);
            }
        }

        // After attempting to allocate all cargo, if there's still cargo left, throw an exception
        if (!cargo.isEmpty()) {
            throw new BizIllegalException("剩余车辆无法装载全部货物");
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

    public List<VehicleGroupVO> findByTaskId(@PathVariable Long taskId) {
        List<VehicleLoad> vehicleLoads = getByTaskId(taskId);

        Map<Long, VehicleGroupVO> vehicleGroupMap = new HashMap<>();

        for (VehicleLoad load : vehicleLoads) {
            VehicleGroupVO vehicleGroup = vehicleGroupMap.computeIfAbsent(load.getVehicleId(), VehicleGroupVO::new);

            VehicleProductInfoDTO productInfo = new VehicleProductInfoDTO();
            productInfo.setProductId(load.getProductId().toString());
            productInfo.setProductName(load.getProductName());
            productInfo.setWeight(load.getWeight());

            vehicleGroup.addProductInfo(productInfo);
        }

        // Step 2: Fetch additional vehicle details
        List<Long> vehicleIds = vehicleGroupMap.keySet().stream()
                .distinct() // This is technically unnecessary for a Set but shown here for completeness
                .collect(Collectors.toList());
        List<Vehicle> vehicles = vehicleClient.findMore(vehicleIds); // Replace vehicleClient.findMore with your actual method call
        // Step 3: Enrich VehicleGroupVO with vehicle details
        for (Vehicle vehicle : vehicles) {
            VehicleGroupVO vehicleGroup = vehicleGroupMap.get(vehicle.getVehicleId());
            if (vehicleGroup != null) {
                vehicleGroup.setLicensePlate(vehicle.getLicensePlate());
                vehicleGroup.setType(vehicle.getType());
                vehicleGroup.setCapacity(vehicle.getCapacity());
            }
        }

        return new ArrayList<>(vehicleGroupMap.values());
    }

}

