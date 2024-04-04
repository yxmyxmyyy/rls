package com.transport.controller;

import com.api.client.IVehicleClient;
import com.api.domain.dto.VehicleProductInfoDTO;
import com.api.domain.po.Item;
import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLoad;
import com.api.domain.vo.VehicleGroupVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.domain.R;
import com.transport.service.IVehicleLoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/VehicleLoad")
@RequiredArgsConstructor
public class VehicleLoadController {

    private final IVehicleLoadService VehicleLoadService;

    private final IVehicleClient vehicleClient;

    // 新增
    @PostMapping("/insert")
    public boolean insert(@RequestBody List<VehicleLoad> vehicleload) {
        return VehicleLoadService.saveOrUpdateBatch(vehicleload);
    }

    // 删除
    @DeleteMapping("/delete/{id}")
    public boolean del(@PathVariable Serializable id) {
        return VehicleLoadService.removeById(id);
    }

    //批量删除
    @DeleteMapping("/deleteBatch")
    public boolean delBatch(@RequestBody List<Long> ids) {
        return VehicleLoadService.removeByIds(ids);
    }

    // 修改
    @PutMapping("/update")
    public boolean updatePassWd(@RequestBody VehicleLoad vehicleload) {
        return VehicleLoadService.updateById(vehicleload);
    }

    // 分页查询
    @GetMapping("/find")
    public R<IPage<VehicleLoad>> find(Integer pageNum, Integer pageSize) {
        IPage<VehicleLoad> ip = new Page<>(pageNum, pageSize);
        return R.ok(VehicleLoadService.page(ip));
    }

    //根据taskid查询
    @GetMapping("/findByTaskId/{taskId}")
    public R<List<VehicleGroupVO>> findByTaskId(@PathVariable Long taskId) {
        return R.ok(VehicleLoadService.findByTaskId(taskId));

    }
}
