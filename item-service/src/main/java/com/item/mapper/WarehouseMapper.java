package com.item.mapper;

import com.api.domain.po.Item;
import com.api.domain.po.Warehouse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WarehouseMapper extends BaseMapper<Warehouse> {
}
