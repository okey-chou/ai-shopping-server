package com.okeychou.aishop.common.result;

public class Result<T> {
    private int code;
    private String message;
    private T data;

    private Result(int code,String message,T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    //成功且带数据
    public static <T>Result<T> success(T data){
        return new Result<>(ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage(),data);
    }

    //失败:按枚举报错
    public static <T>Result<T> fail(ResultCode resultCode){
        return new Result<>(resultCode.getCode(),
                resultCode.getMessage(),null);
    }

    //失败:自定义编号和提示
    public static <T>Result<T> fail(int code, String message){
        return new Result<>(code,message,null);
    }

    public int getCode() {return code;}
    public String getMessage() {return message;}
    public T getData() {return data;}
}
