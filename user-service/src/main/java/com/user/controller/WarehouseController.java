package com.user.controller;


import com.api.domain.po.Warehouse;
import com.api.domain.vo.WarehouseVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.common.domain.R;
import com.common.exception.BadRequestException;
import com.common.exception.BizIllegalException;
import com.user.service.IWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final IWarehouseService warehouseService;

    // 新增
    @PostMapping("/saveOrUpdate")
    public R<String> saveOrUpdate(@RequestBody Warehouse warehouse) {
        if (warehouse.getType() != 1 && warehouse.getType() != 2 && warehouse.getType() != 3) {
            // 如果不在这个范围内，直接返回状态码为400的异常响应
            throw new BadRequestException("type参数不合法");
        }
        try {
            warehouseService.saveOrUpdate(warehouse);
            return R.ok("ok");
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }

    // 删除
    @DeleteMapping("/delete")
    public R<String> delete(@RequestBody WarehouseVO warehouseVO) {
        try {
            Set<Integer> idsToDelete = new HashSet<>();
            warehouseService.collectIdsToDelete(warehouseVO, idsToDelete);
            warehouseService.removeByIds(idsToDelete);
            return R.ok("ok");
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }

    // 修改
    @PutMapping("/update")
    public boolean updatePassWd(@RequestBody Warehouse warehouse) {
        return warehouseService.updateById(warehouse);
    }

    //查询全部
    @PostMapping("/findAll")
    public R<List<Warehouse>> findAll() {
        try {
            List<Warehouse> list = warehouseService.list();
            return R.ok(list);
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }

    // 分页查询
    @GetMapping("/find")
    public IPage<Warehouse> find(Integer pageNum, Integer pageSize) {
        IPage<Warehouse> ip = new Page<>(pageNum, pageSize);
        return warehouseService.page(ip);
    }
    
}
