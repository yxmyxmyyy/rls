package com.api.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFindDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long warehouseId;
    private String username;
    private Long phone;
    private Integer status;
}
