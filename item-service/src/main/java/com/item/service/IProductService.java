package com.item.service;

import com.api.domain.po.Item;
import com.api.domain.po.Product;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IProductService extends IService<Product> {

    Page<Product> find(Product product, Integer pageNum, Integer pageSize);
}
