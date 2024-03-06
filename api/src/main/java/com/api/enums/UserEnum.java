package com.api.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

@Getter
public enum UserEnum implements IEnum<Integer> {
    ONE(1, "县级管理员"),
    TWO(2, "乡级管理员"),
    THREE(3, "村级物流人员");

    private final int value;
    private final String desc;

    UserEnum(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
