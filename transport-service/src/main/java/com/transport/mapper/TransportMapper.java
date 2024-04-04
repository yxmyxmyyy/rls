package com.transport.mapper;

import com.api.domain.po.Transport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;

@Mapper
public interface TransportMapper extends BaseMapper<Transport> {

    // 统计指定日期新增订单数量
    @Select("SELECT COUNT(*) FROM transport_tasks WHERE DATE(created_at) = #{date}")
    int countNewOrdersByDate(@Param("date") LocalDate date);

    // 统计指定日期完结订单数量
    @Select("SELECT COUNT(*) FROM transport_tasks WHERE DATE(updated_at) = #{date} AND status = '已完成'")
    int countFinishedOrdersByDate(@Param("date") LocalDate date);
}
