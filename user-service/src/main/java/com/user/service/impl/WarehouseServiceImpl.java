package com.user.service.impl;


import com.api.domain.po.Warehouse;
import com.api.domain.vo.WarehouseVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.user.mapper.WarehouseMapper;
import com.user.service.IWarehouseService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements IWarehouseService {

    // 辅助方法，递归收集ID
    public void collectIdsToDelete(WarehouseVO warehouseVO, Set<Integer> idsToDelete) {
        if (warehouseVO != null) {
            idsToDelete.add(warehouseVO.getId());
            List<WarehouseVO> children = warehouseVO.getChildren();
            if (children != null && !children.isEmpty()) {
                for (WarehouseVO child : children) {
                    collectIdsToDelete(child, idsToDelete);
                }
            }
        }
    }
}
