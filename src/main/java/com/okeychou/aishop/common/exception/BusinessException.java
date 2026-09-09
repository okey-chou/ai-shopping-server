package com.okeychou.aishop.common.exception;

import com.okeychou.aishop.common.result.ResultCode;

public class BusinessException extends RuntimeException{
    private final ResultCode resultCode;
    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }
    public ResultCode getResultCode() {return resultCode;}
}
