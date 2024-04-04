package com.transport.service;

import com.api.domain.dto.TransportDTO;
import com.api.domain.po.Transport;
import com.api.domain.vo.TransportVO;
import com.api.domain.vo.WeekCountVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ITransportService extends IService<Transport> {
    void processNewTransport(TransportDTO transportDTO);

    boolean endTransport(Long id);

    void processFailedTransport(TransportDTO transportDTO);

    void insert(Long id, TransportVO transportVO);

    void MqSend(Long id,TransportVO transportVO);

    Page<Transport> find(Transport Transport, Integer pageNum, Integer pageSize);

    void clearPageFindCache();

    public WeekCountVO collectWeeklyOrderData();


}
