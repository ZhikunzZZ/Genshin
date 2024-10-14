package com.gstool.common.model.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ResultDTO<T> implements Serializable {

    private String code;
    private T data;
    private String message;

    public static <T> ResultDTO<T> of(String code, String message, T data) {
        ResultDTO<T> r = new ResultDTO<>();
        r.setCode(code);
        r.setMessage(message);
        r.setData(data);
        return r;
    }

    public static <T> ResultDTO<T> ok() {
        return ok(null);
    }

    public static <T> ResultDTO<T> ok(T data) {
        return success(data);
    }

    public static <T> ResultDTO<T> success(T data) {
        return success("" , data);
    }

    public static <T> ResultDTO<T> success(String message) {
        return success(message, null);
    }

    public static <T> ResultDTO<T> success(String message , T data) {
        return of(ResultCodeEnums.SUCCESS.getCode(), message, data);
    }

    public static <T> ResultDTO<T> failed(T data) {
        return failed("" , data);
    }

    public static <T> ResultDTO<T> failed(String message) {
        return failed(message , null);
    }

    public static <T> ResultDTO<T> failed(String message , T data) {
        return of(ResultCodeEnums.FAIL.getCode(), message, data);
    }

    public static <T> ResultDTO<T> exception(T data) {
        return exception("" , data);
    }

    public static <T> ResultDTO<T> exception(String message) {
        return exception(message , null);
    }

    public static <T> ResultDTO<T> exception(String message , T data) {
        return of(ResultCodeEnums.EXCEPTION.getCode(), message, data);
    }

    public static <T> ResultDTO<T> error() {
        return error(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResultDTO<T> error(String msg) {
        return error(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), msg);
    }

    public static <T> ResultDTO<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> ResultDTO<T> error(String code, String msg) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    public static <T> ResultDTO<T> error(String code, String msg, T data) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setCode(code);
        result.setMessage(msg);
        result.setData(data);
        return result;
    }

}
