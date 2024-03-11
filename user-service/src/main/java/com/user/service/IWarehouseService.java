package com.user.service;

import com.api.domain.po.Warehouse;
import com.api.domain.vo.WarehouseVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface IWarehouseService extends IService<Warehouse> {

    void collectIdsToDelete(WarehouseVO warehouseVO, Set<Integer> idsToDelete);


}
