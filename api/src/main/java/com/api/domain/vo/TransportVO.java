package com.api.domain.vo;

import com.api.domain.po.VehicleLoad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportVO implements Serializable {
    private String description;
    private Integer originWarehouseId;
    private Integer destinationWarehouseId;
    private String status;
    private List<VehicleLoad> VehicleLoad;
}


