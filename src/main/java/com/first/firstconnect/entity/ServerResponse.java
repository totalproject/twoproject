package com.first.firstconnect.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.first.firstconnect.vo.ResponseCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * 数据通用响应对象
 */
@Getter
//保证序列化json的时候，如果对象为null key会消失
public class ServerResponse<T> implements Serializable {
    /**
     * 返回的响应码
     */
    private int status;
    /**
     * 返回的响应信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;

    /**
     * 返回的数据对象
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    //@JsonIgnore
    private T data;


    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    //成功的静态方法
    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }


    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }


    //------------------------------------------------------------------
    //错误的静态方法
    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMsg());
    }

    public static <T> ServerResponse<T> createByErrorLogin() {
        return new ServerResponse<T>(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.ERROR.getMsg());
    }

    public static <T> ServerResponse<T> createByRefreshErrorMsg(String errorMsg) {
        return new ServerResponse<T>(ResponseCode.REFRESH_TOO_FAST.getCode(), errorMsg);
    }

    public static <T> ServerResponse<T> createByErrorMsg(String errorMsg) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), errorMsg);
    }

    public static <T> ServerResponse<T> createByErrorCodeMsg(int errorcode, String errorMsg) {
        return new ServerResponse<T>(errorcode, errorMsg);
    }

//    public static <T> ServerResponse<T> createByErrorCodeMsg(String errorMsg) {
//        return new ServerResponse<T>(ResponseCode.VERIFICATION_CODE_ERROR.getCode(), errorMsg);
//    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }


}
