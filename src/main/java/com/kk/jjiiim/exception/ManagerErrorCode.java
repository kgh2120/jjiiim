package com.kk.jjiiim.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ManagerErrorCode  implements ErrorCode{


    ALREADY_REGISTERED_STORE_NAME(HttpStatus.CONFLICT, "이미 등록된 매장 명입니다.")
    ,

    ;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    private final String errorCode = "M-" + "0".repeat(Math.max(4-String.valueOf(this.ordinal() + 1).length(), 0)) + (this.ordinal() + 1);

    ManagerErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }



    public String getErrorName() {
        return this.name();
    }
}
