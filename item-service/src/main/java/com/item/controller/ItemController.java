package com.item.controller;

import com.api.domain.dto.ItemDTO;
import com.api.domain.po.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.domain.R;
import com.common.exception.BizIllegalException;
import com.item.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final IItemService itemService;

    //分页查询
    @PostMapping("/find")
    public R<Page<Item>> find(@RequestBody Item Item, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        try {
            Integer warehouseId = 1;
            Item.setWarehouseId(warehouseId);
            return R.ok(itemService.find(Item,pageNum, pageSize));
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }

    //新增或修改
    @PostMapping("/saveOrUpdate")
    public R<String> saveOrUpdate(@RequestBody List<VehicleLoad> vehicleLoad) {
        try {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setWarehouseId(1);
            itemDTO.setVehicleLoad(vehicleLoad);
            itemService.saveBatch(itemService.processVehicleLoads(itemDTO));
            return R.ok("ok");
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }

    //批量删除
    @DeleteMapping("/delete")
    public R<String> delete(@RequestBody List<Serializable> ids) {
        try {
            itemService.removeByIds(ids);
            return R.ok("删除成功");
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }

    //删除一个
    @DeleteMapping("/deleteOne/{id}")
    public R<String> deleteOne(@PathVariable Serializable id) {
        try {
            itemService.removeById(id);
            return R.ok("删除成功");
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }

    //查询全部
    @GetMapping("/findAll")
    public R<List<Item>> findAll() {
        try {
            return R.ok(itemService.list());
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }

    // 检测库存并扣减
    @PutMapping("/deductStock")
    public boolean deductStock(@RequestBody List<VehicleLoad> vehicleLoads) {
        itemService.deductStock(vehicleLoads);
        return true;
    }

    //添加存在的库存，返回不存在的库存
    @PutMapping("/insertOrUpdate")
    public boolean insertOrUpdate(@RequestBody ItemDTO itemDTO) {
        itemService.saveBatch(itemService.processVehicleLoads(itemDTO));
        return true;
    }

    //根据仓库查询
    @GetMapping("/findByWarehouse/{id}")
    public List<Item> findByWarehouse(@PathVariable Integer id) {
        // 创建QueryWrapper对象
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        // 添加条件，假设空闲的状态值为"空闲"
        queryWrapper.eq("warehouse_id", id);
        // 使用Service的lambdaQuery方法，传入构建的查询条件
        return itemService.list(queryWrapper);
    }
    
}
