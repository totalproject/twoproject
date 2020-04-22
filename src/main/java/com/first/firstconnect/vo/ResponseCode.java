package com.first.firstconnect.vo;

public enum ResponseCode {
    /*成功*/
    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT"),
    NEED_LOGIN(10,"LOGIN"),
    REFRESH_TOO_FAST(20,"REFRESH_TOO_FAST"),
    VERIFICAT_CODE_ERROR(30,"VERIFICATION_CODE_ERROR");

    private final int code;
    private final String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg  = msg;
    }

    public int getCode(){return code;}
    public String getMsg(){return msg;}
}
