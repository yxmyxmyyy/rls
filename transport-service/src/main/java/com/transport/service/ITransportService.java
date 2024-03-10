package com.transport.service;

import com.api.domain.po.Transport;
import com.api.domain.po.VehicleLoad;
import com.api.domain.vo.TransportVO;
import com.api.domain.vo.VehicleTypeVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ITransportService extends IService<Transport> {
    List<VehicleTypeVO> processNewTransport(TransportVO transportVO);

    boolean endTransport(Long id);


}
