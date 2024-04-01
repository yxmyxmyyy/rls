package com.transport.controller;


import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.api.client.IItemClient;
import com.api.domain.dto.TransportDTO;
import com.api.domain.po.Transport;
import com.api.domain.po.TransportLog;
import com.api.domain.vo.TransportVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.domain.R;
import com.common.exception.BadRequestException;
import com.transport.service.ITransportLogService;
import com.transport.service.ITransportService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import java.io.Serializable;
import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping("/Transport")
@RequiredArgsConstructor
public class TransportController {

    private final ITransportService TransportService;

    private final ITransportLogService TransportLogService;

    private final IItemClient itemClient;


    //新增订单
    @PostMapping("/new")
    public R<String> insert1(@RequestBody TransportVO transportVO) {
        Integer warehouseId = 20;
        transportVO.setOriginWarehouseId(warehouseId);
        boolean item = itemClient.deductStock(transportVO.getOriginWarehouseId(),transportVO.getVehicleLoad());
        if (!item){
            throw new BadRequestException("库存不足");
        }
        Long id = IdUtil.getSnowflakeNextId();
        TransportService.insert(id,transportVO);
        TransportService.MqSend(id,transportVO);
        TransportLogService.insert(id,transportVO);
        return R.ok("已创建订单，请稍后在订单详细查询状态");
    }

    @GetMapping("/test/{id}")
    public R<TransportLog> test(@PathVariable Long id){
        return R.ok(TransportLogService.getById(id));
    }

    //结算订单
    @PutMapping("/end/{id}")
    public R<String> end(@PathVariable Long id) {
        TransportService.endTransport(id);
        return R.ok("结算成功");
    }
    // 删除
    @DeleteMapping("/deleteOne/{id}")
    public boolean del(@PathVariable Serializable id) {
        TransportService.clearPageFindCache();
        return TransportService.removeById(id);
    }

    // 修改
    @PutMapping("/update")
    public boolean updatePassWd(@RequestBody Transport transport) {
        return TransportService.updateById(transport);
    }

    //分页查询入库
    @PostMapping("/findin")
    public R<Page<Transport>> findin(@RequestBody Transport Transport, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Integer warehouseId = 20;
        Transport.setDestinationWarehouseId(warehouseId);
        return R.ok(TransportService.find(Transport,pageNum, pageSize));
    }

    //分页查询出库
    @PostMapping("/findout")
    public R<Page<Transport>> findout(@RequestBody Transport Transport, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Integer warehouseId = 20;
        Transport.setOriginWarehouseId(warehouseId);
        return R.ok(TransportService.find(Transport,pageNum, pageSize));
    }
}
