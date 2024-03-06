package com.transport.service.impl;


import com.api.domain.po.Transport;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.transport.mapper.TransportMapper;
import com.transport.service.ITransportService;
import org.springframework.stereotype.Service;

@Service
public class TransportServiceImpl extends ServiceImpl<TransportMapper, Transport> implements ITransportService {
}
