package com.transport.service;

import com.api.domain.po.Transport;
import com.api.domain.vo.TransportVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ITransportService extends IService<Transport> {
    //Transport
    boolean insertTransport(TransportVO transportVO);
}
