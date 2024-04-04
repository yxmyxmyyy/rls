package com.api.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountVO {
    private Long now;
    private Long inProgress;
    private Long endProgress;
    private Long errorProgress;

}
