package com.gstool.common.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    UNAUTHORIZED("401", "还未授权，不能访问"),
    FORBIDDEN("403", "没有权限，禁止访问"),
    INTERNAL_SERVER_ERROR("500", "服务器异常，请稍后再试"),
    PACKAGE_ERROR("555", "包装类服务器异常，请稍后再试"),
    ;

    private final String  code;
    private final String msg;

}
