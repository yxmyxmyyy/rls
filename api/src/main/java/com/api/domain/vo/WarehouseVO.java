package com.api.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseVO implements Serializable {
    private Integer id;
    private List<WarehouseVO> children;
    private String name;
    private Integer parentId;
    private Integer type;
    private Integer sort;
    private Long phone;
    private String principal;
    private Date createTime;
    private String location;
    private Integer status;
    private String remark;
}

