package com.api.domain.vo;

import com.api.domain.dto.VehicleProductInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleGroupVO {
    private Long vehicleId;
    private String licensePlate;
    private String type;
    private Double capacity;
    private List<VehicleProductInfoDTO> products;

    public VehicleGroupVO(Long vehicleId) {
        this.vehicleId = vehicleId;
        this.products = new ArrayList<>();
    }

    // Method to add product info
    public void addProductInfo(VehicleProductInfoDTO productInfo) {
        this.products.add(productInfo);
    }

}
