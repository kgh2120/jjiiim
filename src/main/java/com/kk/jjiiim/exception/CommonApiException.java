package com.kk.jjiiim.exception;

import static com.kk.jjiiim.exception.CommonErrorCode.*;

public class CommonApiException extends AbstractApiException{
    private CommonApiException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static CommonApiException alreadyRegisteredPhoneNumber(){
        return new CommonApiException(ALREADY_REGISTERED_PHONE_NUMBER);
    }
}
