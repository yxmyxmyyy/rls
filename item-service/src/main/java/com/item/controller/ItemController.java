package com.item.controller;

import com.api.domain.po.Item;
import com.api.domain.po.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.item.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@CrossOrigin
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final IItemService itemService;

    // 新增
    @PostMapping("/insert")
    public boolean insert(@RequestBody Item item) {
        return itemService.saveOrUpdate(item);
    }

    // 删除
    @DeleteMapping("/delete/{id}")
    public boolean del(@PathVariable Serializable id) {
        return itemService.removeById(id);
    }

    // 修改
    @PutMapping("/update")
    public boolean updatePassWd(@RequestBody Item item) {
        return itemService.updateById(item);
    }

    // 分页查询
    @GetMapping("/find")
    public IPage<Item> find(Integer pageNum, Integer pageSize) {
        IPage<Item> ip = new Page<>(pageNum, pageSize);
        return itemService.page(ip);
    }
    
}
