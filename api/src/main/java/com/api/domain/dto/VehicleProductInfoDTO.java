package com.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleProductInfoDTO {
    private String productId;
    private String productName;
    private Double weight;

}
