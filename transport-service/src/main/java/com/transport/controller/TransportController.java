package com.transport.controller;


import com.api.client.IVehicleClient;
import com.api.domain.dto.AllocationResultDTO;
import com.api.domain.po.Transport;
import com.api.domain.vo.TransportVO;
import com.api.domain.vo.VehicleTypeVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.domain.R;
import com.common.exception.BadRequestException;
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
            // 如果处理成功，返回200状态码，消息"OK"，以及处理结果
            return R.ok(result);
        } catch (BadRequestException e) {
            // 如果在处理中抛出了BadRequestException异常，使用异常中的状态码和消息构造错误响应
            return R.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            // 对于其他类型的异常，返回500状态码和"服务器内部错误"消息
            // 注意：实际生产中，可能需要更精细的异常处理策略
            return R.error(500, "服务器内部错误");
        }
    }

    //结算订单
    @GetMapping("/end/{id}")
    public R<Void> end(@PathVariable Long id) {
        try {
            boolean result = TransportService.endTransport(id);
            if (result) {
                return R.ok();
            } else {
                return R.error(500, "服务器内部错误");
            }
        } catch (CommonException e) {
            return R.error(e);
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

    // 分页查询
    @GetMapping("/find")
    public IPage<Transport> find(Integer pageNum, Integer pageSize) {
        IPage<Transport> ip = new Page<>(pageNum, pageSize);
        return TransportService.page(ip);
    }
}
