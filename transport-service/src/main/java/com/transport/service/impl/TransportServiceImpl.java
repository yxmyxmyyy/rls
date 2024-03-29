package com.transport.service.impl;


import com.alibaba.fastjson.JSON;
import com.api.client.IItemClient;
import com.api.client.IVehicleClient;
import com.api.domain.dto.AllocationResultDTO;
import com.api.domain.dto.ItemDTO;
import com.api.domain.dto.TransportDTO;
import com.api.domain.po.*;
import com.api.domain.vo.TransportVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.exception.BadRequestException;
import com.transport.mapper.TransportMapper;
import com.transport.service.ITransportLogService;
import com.transport.service.ITransportService;
import com.transport.service.IVehicleLoadService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportServiceImpl extends ServiceImpl<TransportMapper, Transport> implements ITransportService {

    private final IVehicleClient vehicleClient;

    private final IItemClient itemClient;

    private final IVehicleLoadService vehicleLoadService;

    private final ITransportLogService TransportLogService;

    private final RocketMQTemplate rocketMQTemplate;

    @Async
    public void MqSend(Long id,TransportVO transportVO) {
        TransportDTO transportDTO = new TransportDTO();
        transportDTO.setTransportVO(transportVO);
        transportDTO.setId(id);
        rocketMQTemplate.convertAndSend("transport-topic", transportDTO);
    }

    @Async
    public void insert(Long id,TransportVO transportVO) {
        Transport ts = new Transport();
        ts.setTaskId(id);
        ts.setDescription(transportVO.getDescription());
        ts.setOriginWarehouseId(transportVO.getOriginWarehouseId());
        ts.setDestinationWarehouseId(transportVO.getDestinationWarehouseId());
        ts.setDescription("进行中");
        ts.setStatus("进行中");
        save(ts);
    }
    @GlobalTransactional
    public void processNewTransport(TransportDTO transportDTO) {
        List<Vehicle> vehicles = vehicleClient.findAll();
        if (vehicles.isEmpty()){
            throw new BadRequestException("没有空余车辆");
        }
        AllocationResultDTO allocationResult = vehicleLoadService.allocateCargo(vehicles, transportDTO.getTransportVO().getVehicleLoad());

        //尝试更新车辆状态
        List<Long> vehicleIds = allocationResult.getVehicles().stream().map(Vehicle::getVehicleId).toList();
        //分配车辆
        if(!vehicleClient.use(vehicleIds)){
            throw new BadRequestException("分配车辆失败");
        }
        allocationResult.getVehicleLoads().forEach(vehicleLoad -> vehicleLoad.setTaskId(transportDTO.getId()));
        vehicleLoadService.saveBatch(allocationResult.getVehicleLoads());
    }

    //失败订单处理
    @GlobalTransactional
    public void processFailedTransport(TransportDTO transportDTO) {
        TransportVO transportVO = transportDTO.getTransportVO();
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setWarehouseId(transportVO.getOriginWarehouseId());
        itemDTO.setVehicleLoad(transportVO.getVehicleLoad());
        itemClient.insertOrUpdate(itemDTO);
        Transport ts = new Transport();
        ts.setTaskId(transportDTO.getId());
        ts.setDescription(transportVO.getDescription());
        ts.setStatus("已取消");
        updateById(ts);
        TransportLog tl = new TransportLog();
        tl.setTaskId(ts.getTaskId());
        tl.setWarehouseId(transportVO.getOriginWarehouseId());
        String jsonString = JSON.toJSONString(transportVO.getVehicleLoad());
        tl.setContent(jsonString);
        tl.setType(1);
        TransportLogService.save(tl);
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
            vehicle.setStatus(1);
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
        transport.setCreatedAt(null);
        transport.setUpdatedAt(null);
        return updateById(transport);

    }

    public Page<Transport> find(Transport Transport, Integer pageNum, Integer pageSize) {
        // 创建Page对象，其中current是当前页数，size是每页显示记录的数量
        Page<Transport> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Transport> qw = new QueryWrapper<>();
        // 根据条件添加查询条件，这里省略了空值检查，实际使用时应该加上
        if (Transport.getTaskId() != null){
            qw.eq("task_id", Transport.getTaskId());
        }
        if (Transport.getDestinationWarehouseId() != null){
            qw.eq("destination_warehouse_id", Transport.getDestinationWarehouseId());
        }
        if (Transport.getStatus() != null && !Transport.getStatus().isEmpty()) {
            qw.eq("status", Transport.getStatus());
        }
        if (Transport.getOriginWarehouseId() !=null){
            qw.eq("origin_warehouse_id", Transport.getOriginWarehouseId());
        }
        // 执行分页和条件查询
        return page(page, qw);
    }
}
