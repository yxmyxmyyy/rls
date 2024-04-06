package com.api.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("vehicle_load")
public class VehicleLoad implements Serializable, Cloneable {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private Long loadId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long vehicleId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long productId;
    private String productName;
    private Double weight;

    public VehicleLoad deepCopy() {
        return new VehicleLoad(this.loadId, this.vehicleId, this.taskId, this.productId, this.productName, this.weight);
    }

    @Override
    public VehicleLoad clone() {
        try {
            return (VehicleLoad) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can never happen
        }
    }


}


