package com.item.service.impl;


import com.api.domain.po.Item;
import com.api.domain.po.Product;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.item.mapper.ItemMapper;
import com.item.mapper.ProductMapper;
import com.item.service.IItemService;
import com.item.service.IProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
}
