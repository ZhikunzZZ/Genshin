package com.gstool.common.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCodeEnums {

    SUCCESS("200", "请求成功"),
    FAIL("0", "请求失败"),
    EXCEPTION("-999" , "请求异常"),
    PARAM_ERROR("-2", "参数错误")
    ;

    private final String code;
    private final String msg;

}
