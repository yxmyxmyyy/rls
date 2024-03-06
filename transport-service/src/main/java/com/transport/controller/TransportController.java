package com.transport.controller;


import com.api.domain.po.Transport;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.transport.service.ITransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@CrossOrigin
@RequestMapping("/Transport")
@RequiredArgsConstructor
public class TransportController {

    private final ITransportService TransportService;

    // 新增
    @PostMapping("/insert")
    public boolean insert(@RequestBody Transport transport) {
        return TransportService.saveOrUpdate(transport);
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
