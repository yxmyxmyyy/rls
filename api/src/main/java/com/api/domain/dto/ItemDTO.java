package com.api.domain.dto;

import com.api.domain.po.VehicleLoad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO implements Serializable {
    private Integer warehouseId;
    private List<VehicleLoad> vehicleLoad;
}


