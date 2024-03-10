package com.transport.service.impl;


import com.api.client.IItemClient;
import com.api.client.IVehicleClient;
import com.api.domain.dto.AllocationResultDTO;
import com.api.domain.dto.ItemDTO;
import com.api.domain.po.Item;
import com.api.domain.po.Transport;
import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLoad;
import com.api.domain.vo.TransportVO;
import com.api.domain.vo.VehicleTypeVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.exception.BadRequestException;
import com.common.exception.CommonException;
import com.transport.mapper.TransportMapper;
import com.transport.service.ITransportService;
import com.transport.service.IVehicleLoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransportServiceImpl extends ServiceImpl<TransportMapper, Transport> implements ITransportService {

    private final IVehicleClient vehicleClient;

    private final IItemClient itemClient;

    private final IVehicleLoadService vehicleLoadService;

    public List<VehicleTypeVO> processNewTransport(TransportVO transportVO) {
        //扣减库存
        try {
            itemClient.deductStock(transportVO.getVehicleLoad());
        } catch (Exception e) {
            throw new BadRequestException("库存不足");
        }
        //创建订单
        Transport ts = new Transport();
        ts.setDescription(transportVO.getDescription());
        ts.setOriginWarehouseId(transportVO.getOriginWarehouseId());
        ts.setDestinationWarehouseId(transportVO.getDestinationWarehouseId());
        ts.setStatus("进行中");
        save(ts);
        //分配车辆
        AllocationResultDTO allocationResult = vehicleLoadService.allocateCargo(vehicleClient.findAll(), transportVO.getVehicleLoad());
        allocationResult.getVehicleLoads().forEach(vehicleLoad -> vehicleLoad.setTaskId(ts.getTaskId()));
        vehicleLoadService.saveBatch(allocationResult.getVehicleLoads());
        //更新车辆状态
        allocationResult.getVehicles().forEach(vehicle -> vehicle.setStatus("在途"));
        vehicleClient.update(allocationResult.getVehicles());
        //返回分配结果
        return allocationResult.getVehicleCountByType();
    }

    //结束订单
    public boolean endTransport(Long id) {
        //修改订单为已完成
        Transport transport = getById(id);
        transport.setStatus("已完成");



        List<VehicleLoad> load = vehicleLoadService.getByTaskId(id);

        //获取所有车辆id并合并
        List<Long> vehicleIds = load.stream().map(VehicleLoad::getVehicleId).toList();
        //创建车辆对象并修改状态
        List<Vehicle> vehicles = vehicleIds.stream().map(vehicleId -> {
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleId(vehicleId);
            vehicle.setStatus("空闲");
            return vehicle;
        }).toList();

        List<Long> loadIds = load.stream().map(VehicleLoad::getLoadId).toList();

        //产品入库
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setWarehouseId(transport.getDestinationWarehouseId());
        itemDTO.setVehicleLoad(load);
        itemClient.insertOrUpdate(itemDTO);


        vehicleClient.update(vehicles);
        vehicleLoadService.removeBatchByIds(loadIds);

        return updateById(transport);

    }
}
