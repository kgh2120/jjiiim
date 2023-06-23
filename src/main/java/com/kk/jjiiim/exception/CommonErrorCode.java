package com.kk.jjiiim.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCode implements ErrorCode{


    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력 값이 유효하지 않습니다."),
    ALREADY_REGISTERED_PHONE_NUMBER(HttpStatus.CONFLICT, "이미 등록된 전화 번호입니다."),
    ALREADY_REGISTERED_LOGIN_ID(HttpStatus.CONFLICT, "이미 등록된 아이디입니다."),
    PASSWORD_UN_MATCHED(HttpStatus.BAD_REQUEST, "비밀번호와 비밀번호 확인이 서로 맞지 않습니다."),
    LOGIN_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "아이디가 존재하지 않습니다."),


    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."), // v
    JWT_NOT_VERIFIED(HttpStatus.FORBIDDEN, "토큰의 시그내처가 유효하지 않습니다."), // v
    JWT_MALFORMED(HttpStatus.FORBIDDEN, "토큰의 형식이 잘못되었습니다. 토큰은 [header].[payload].[secret]의 형식이어야 합니다."), // v
    JWT_UNSUPPORTED(HttpStatus.FORBIDDEN, "지원하지 않는 종류의 토큰입니다.")

    ,

    ;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    private final String errorCode = "C-" + "0".repeat(Math.max(4-String.valueOf(this.ordinal() + 1).length(), 0)) + (this.ordinal() + 1);

    CommonErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }



    public String getErrorName() {
        return this.name();
    }
}