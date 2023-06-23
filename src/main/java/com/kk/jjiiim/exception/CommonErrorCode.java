package com.kk.jjiiim.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCode implements ErrorCode{


    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력 값이 유효하지 않습니다."),
    ALREADY_REGISTERED_PHONE_NUMBER(HttpStatus.CONFLICT, "이미 등록된 전화 번호입니다."),
    ALREADY_REGISTERED_LOGIN_ID(HttpStatus.CONFLICT, "이미 등록된 아이디입니다."),
    PASSWORD_UN_MATCHED(HttpStatus.BAD_REQUEST, "비밀번호와 비밀번호 확인이 서로 맞지 않습니다.")

    ,

    ;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    private final String errorCode = "A-" + "0".repeat(Math.max(4-String.valueOf(this.ordinal() + 1).length(), 0)) + (this.ordinal() + 1);

    CommonErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }



    public String getErrorName() {
        return this.name();
    }
}