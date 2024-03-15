package com.item.controller;

import com.api.domain.po.Product;
import com.api.domain.po.Product;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.domain.R;
import com.common.exception.BadRequestException;
import com.common.exception.BizIllegalException;
import com.item.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService ProductService;

    //分页查询
    @PostMapping("/find")
    public R<Page<Product>> find(@RequestBody Product Product, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        try {
            return R.ok(ProductService.find(Product,pageNum, pageSize));
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }

    //新增或修改
    @PostMapping("/saveOrUpdate")
    public R<String> saveOrUpdate(@RequestBody Product Product) {
        try {
            ProductService.saveOrUpdate(Product);
            return R.ok("ok");
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }

    //批量删除
    @DeleteMapping("/delete")
    public R<String> delete(@RequestBody List<Serializable> ids) {
        try {
            ProductService.removeByIds(ids);
            return R.ok("删除成功");
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }

    //删除一个
    @DeleteMapping("/deleteOne/{id}")
    public R<String> deleteOne(@PathVariable Serializable id) {
        try {
            ProductService.removeById(id);
            return R.ok("删除成功");
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }

    //查询全部
    @GetMapping("/findAll")
    public R<List<Product>> findAll() {
        try {
            return R.ok(ProductService.list());
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }
    
}
