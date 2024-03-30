package com.vehicle.mapper;

import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface VehicleLogMapper extends BaseMapper<VehicleLog> {

    @Update("<script>" +
            "UPDATE vehicle " +
            "SET status = status - 1 " +
            "WHERE vehicle_id IN " +
            "<foreach item='item' collection='idList' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    void decreaseStatusByIds(@Param("idList") List<Long> idList);

}
