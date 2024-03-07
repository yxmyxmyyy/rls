package com.transport.service.impl;


import com.api.domain.po.Transport;
import com.api.domain.vo.TransportVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.transport.mapper.TransportMapper;
import com.transport.service.ITransportService;
import org.springframework.stereotype.Service;

@Service
public class TransportServiceImpl extends ServiceImpl<TransportMapper, Transport> implements ITransportService {
    public boolean insertTransport(TransportVO transportVO){
        Transport ts=new Transport();
        ts.setStatus("进行中");
        ts.setOriginWarehouseId(transportVO.getOriginWarehouseId());
        ts.setDestinationWarehouseId(transportVO.getDestinationWarehouseId());
        return save(ts);
    }
}
