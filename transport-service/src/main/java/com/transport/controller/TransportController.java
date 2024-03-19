package com.transport.controller;


import com.api.client.IVehicleClient;
import com.api.domain.dto.AllocationResultDTO;
import com.api.domain.po.Item;
import com.api.domain.po.Transport;
import com.api.domain.vo.TransportVO;
import com.api.domain.vo.VehicleTypeVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.domain.R;
import com.common.exception.BadRequestException;
import com.common.exception.BizIllegalException;
import com.common.exception.CommonException;
import com.transport.service.ITransportService;
import com.transport.service.IVehicleLoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/Transport")
@RequiredArgsConstructor
public class TransportController {

    private final ITransportService TransportService;

    //新增订单
    @PostMapping("/new")
    public R<List<VehicleTypeVO>> insert1(@RequestBody TransportVO transportVO) {
        try {
            List<VehicleTypeVO> result = TransportService.processNewTransport(transportVO);
            return R.ok(result);
        }  catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    //结算订单
    @PutMapping("/end/{id}")
    public R<String> end(@PathVariable Long id) {
        try {
            TransportService.endTransport(id);
            return R.ok("结算成功");
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }
    // 删除
    @DeleteMapping("/delete/{id}")
    public boolean del(@PathVariable Serializable id) {
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
        try {
            Integer warehouseId = 1;
            Transport.setDestinationWarehouseId(warehouseId);
            return R.ok(TransportService.find(Transport,pageNum, pageSize));
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }

    //分页查询出库
    @PostMapping("/findout")
    public R<Page<Transport>> findout(@RequestBody Transport Transport, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        try {
            Integer warehouseId = 1;
            Transport.setOriginWarehouseId(warehouseId);
            return R.ok(TransportService.find(Transport,pageNum, pageSize));
        } catch (Exception e) {
            throw new BizIllegalException("服务器内部错误");
        }
    }
}
