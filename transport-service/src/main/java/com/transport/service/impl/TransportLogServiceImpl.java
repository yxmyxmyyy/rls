package com.transport.service.impl;


import com.alibaba.fastjson.JSON;
import com.api.client.IItemClient;
import com.api.client.IVehicleClient;
import com.api.domain.dto.AllocationResultDTO;
import com.api.domain.dto.ItemDTO;
import com.api.domain.dto.TransportDTO;
import com.api.domain.po.Transport;
import com.api.domain.po.TransportLog;
import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLoad;
import com.api.domain.vo.TransportVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.exception.BadRequestException;
import com.transport.mapper.TransportLogMapper;
import com.transport.mapper.TransportMapper;
import com.transport.service.ITransportLogService;
import com.transport.service.ITransportService;
import com.transport.service.IVehicleLoadService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportLogServiceImpl extends ServiceImpl<TransportLogMapper, TransportLog> implements ITransportLogService {
    //创建订单
    @Async
    public void insert(Long id,TransportVO transportVO) {
        TransportLog tl = new TransportLog();
        tl.setTaskId(id);
        tl.setWarehouseId(transportVO.getOriginWarehouseId());
        String jsonString = JSON.toJSONString(transportVO.getVehicleLoad());
        tl.setContent(jsonString);
        tl.setType(0);
        save(tl);
    }
}
