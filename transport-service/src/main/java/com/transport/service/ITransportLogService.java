package com.transport.service;

import com.api.domain.dto.TransportDTO;
import com.api.domain.po.Transport;
import com.api.domain.po.TransportLog;
import com.api.domain.vo.TransportVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ITransportLogService extends IService<TransportLog> {
    void insert(Long id, TransportVO transportVO);


}
