package com.item.controller;

import com.api.domain.po.Product;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.item.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@CrossOrigin
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService ProductService;

    // 新增
    @PostMapping("/insert")
    public boolean insert(@RequestBody Product product) {
        return ProductService.saveOrUpdate(product);
    }

    // 删除
    @DeleteMapping("/delete/{id}")
    public boolean del(@PathVariable Serializable id) {
        return ProductService.removeById(id);
    }

    // 修改
    @PutMapping("/update")
    public boolean updatePassWd(@RequestBody Product product) {
        return ProductService.updateById(product);
    }

    // 分页查询
    @GetMapping("/find")
    public IPage<Product> find(Integer pageNum, Integer pageSize) {
        IPage<Product> ip = new Page<>(pageNum, pageSize);
        return ProductService.page(ip);
    }
    
}
