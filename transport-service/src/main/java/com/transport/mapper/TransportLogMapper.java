package com.transport.mapper;

import com.api.domain.po.Transport;
import com.api.domain.po.TransportLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransportLogMapper extends BaseMapper<TransportLog> {
}
