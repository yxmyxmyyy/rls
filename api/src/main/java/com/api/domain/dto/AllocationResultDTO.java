package com.api.domain.dto;

import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLoad;
import com.api.domain.vo.VehicleTypeVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllocationResultDTO {
    private List<VehicleLoad> vehicleLoads;
    private List<Vehicle> vehicles;
    private List<VehicleTypeVO> vehicleCountByType;
}
