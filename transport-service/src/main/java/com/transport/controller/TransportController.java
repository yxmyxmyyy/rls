package com.transport.controller;


import com.api.client.IVehicleClient;
import com.api.domain.dto.AllocationResultDTO;
import com.api.domain.po.Transport;
import com.api.domain.po.VehicleLoad;
import com.api.domain.vo.TransportVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.transport.service.ITransportService;
import com.transport.service.IVehicleLoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@CrossOrigin
@RequestMapping("/Transport")
@RequiredArgsConstructor
public class TransportController {

    private final ITransportService TransportService;

    private final IVehicleLoadService VehicleLoadService;

    private final IVehicleClient vehicleClient;

    // 新增
    @PostMapping("/insert")
    public boolean insert(@RequestBody Transport transport) {
        return TransportService.saveOrUpdate(transport);
    }

    //新增订单
    @PostMapping("/new")
    public boolean insert1(@RequestBody TransportVO transportVO){
        Transport ts = new Transport();
        ts.setDescription(transportVO.getDescription());
        ts.setOriginWarehouseId(transportVO.getOriginWarehouseId());
        ts.setDestinationWarehouseId(transportVO.getDestinationWarehouseId());
        ts.setStatus("进行中");
        TransportService.save(ts);
        System.out.println(vehicleClient.findAll());
        System.out.println(transportVO.getVehicleLoad());
        AllocationResultDTO allocationResult = VehicleLoadService.allocateCargo(vehicleClient.findAll(), transportVO.getVehicleLoad());
        allocationResult.getVehicleLoads().forEach(vehicleLoad -> {
            vehicleLoad.setTaskId(ts.getTaskId()); // 更新taskId
        });
        boolean vehicleLoadsSaved = VehicleLoadService.saveBatch(allocationResult.getVehicleLoads());
        // 更新使用的车辆的状态，并保存
        allocationResult.getVehicles().forEach(vehicle -> {
            vehicle.setStatus("在途");
        });
        vehicleClient.update(allocationResult.getVehicles());
        return vehicleLoadsSaved;
    }
    // 删除
    @DeleteMapping("/delete/{id}")
    public boolean del(@PathVariable Serializable id) {
        return TransportService.removeById(id);
    }

    // 修改
    @PutMapping("/update")
    public boolean updatePassWd(@RequestBody Transport transport) {
        return TransportService.updateById(transport);
    }

    // 分页查询
    @GetMapping("/find")
    public IPage<Transport> find(Integer pageNum, Integer pageSize) {
        IPage<Transport> ip = new Page<>(pageNum, pageSize);
        return TransportService.page(ip);
    }
    
}
