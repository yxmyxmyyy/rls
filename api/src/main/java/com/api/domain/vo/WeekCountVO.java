package com.api.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeekCountVO {
    private List<Integer> inProgressData;
    private List<Integer> endProgressData;

}
