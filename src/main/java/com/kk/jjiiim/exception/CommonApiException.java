package com.kk.jjiiim.exception;

public class CommonApiException extends AbstractApiException{
    public CommonApiException(ErrorCode errorCode) {
        super(errorCode);
    }
}
