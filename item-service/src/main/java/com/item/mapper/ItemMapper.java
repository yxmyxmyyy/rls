package com.item.mapper;

import com.api.domain.po.Item;
import com.api.domain.po.VehicleLoad;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Set;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {
    // 批量检查库存
    @Select("<script>" +
            "SELECT product_id, stock FROM items WHERE warehouse_id = #{warehouseId} AND product_id IN " +
            "<foreach item='productId' collection='productIds' open='(' separator=',' close=')'>" +
            "#{productId}" +
            "</foreach>" +
            "</script>")
    List<Item> checkStockBatch(@Param("warehouseId") Long warehouseId, @Param("productIds") List<Long> productIds);

    // 批量扣减库存
    @Update("<script>" +
            "UPDATE items" +
            " SET stock = CASE" +
            "<foreach collection='vehicleLoads' item='load' index='index' separator=' '>" +
            " WHEN product_id = #{load.productId} THEN stock - #{load.weight}" +
            "</foreach>" +
            " END" +
            " WHERE warehouse_id = #{warehouseId}" +
            " AND product_id IN" +
            "<foreach collection='vehicleLoads' item='load' index='index' open='(' separator=',' close=')'>" +
            " #{load.productId}" +
            "</foreach>" +
            "</script>")
    int decreaseStockBatch(@Param("warehouseId") Long warehouseId, @Param("vehicleLoads") List<VehicleLoad> vehicleLoads);

    //检测库存
    @Select("<script>" +
            "SELECT product_id FROM items WHERE warehouse_id = #{warehouseId} AND product_id IN " +
            "<foreach item='productId' collection='productIds' open='(' separator=',' close=')'>" +
            "#{productId}" +
            "</foreach>" +
            "</script>")
    Set<Long> findExistingProductIds(@Param("warehouseId") Long warehouseId, @Param("productIds") Set<Long> productIds);

    //批量新增库存
    @Update("<script>" +
            "UPDATE items" +
            " SET stock = CASE" +
            "<foreach collection='vehicleLoads' item='load' index='index' separator=' '>" +
            " WHEN product_id = #{load.productId} THEN stock + #{load.weight}" +
            "</foreach>" +
            " END" +
            " WHERE warehouse_id = #{warehouseId}" +
            " AND product_id IN" +
            "<foreach collection='vehicleLoads' item='load' index='index' open='(' separator=',' close=')'>" +
            "#{load.productId}" +
            "</foreach>" +
            "</script>")
    int increaseStockBatch(@Param("warehouseId") Long warehouseId, @Param("vehicleLoads") List<VehicleLoad> vehicleLoads);
}
