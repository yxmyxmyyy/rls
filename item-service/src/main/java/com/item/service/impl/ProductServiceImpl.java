package com.item.service.impl;



import com.api.domain.po.Item;
import com.api.domain.po.Product;
import com.api.domain.po.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.item.mapper.ItemMapper;
import com.item.mapper.ProductMapper;
import com.item.service.IItemService;
import com.item.service.IProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    public Page<Product> find(Product product, Integer pageNum, Integer pageSize) {
        // 创建Page对象，其中current是当前页数，size是每页显示记录的数量
        Page<Product> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Product> qw = new QueryWrapper<>();
        // 根据条件添加查询条件，这里省略了空值检查，实际使用时应该加上
        if (product.getProductId() != null){
            qw.eq("product_id", product.getProductId());
        }
        if (product.getProductName() != null && !product.getProductName().isEmpty()) {
            qw.eq("product_name", product.getProductName());
        }
        qw.select("product_id", "product_name", "create_time");
        // 执行分页和条件查询
        return page(page, qw);
    }
}
