package com.kk.jjiiim.exception;

import static com.kk.jjiiim.exception.CommonErrorCode.*;

public class CommonApiException extends AbstractApiException{
    private CommonApiException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static CommonApiException alreadyRegisteredPhoneNumber(){
        return new CommonApiException(ALREADY_REGISTERED_PHONE_NUMBER);
    }
    public static CommonApiException alreadyRegisteredLoginId(){
        return new CommonApiException(ALREADY_REGISTERED_LOGIN_ID);
    }
    public static CommonApiException passwordUnMatched(){
        return new CommonApiException(PASSWORD_UN_MATCHED);
    }
    public static CommonApiException loginIdNotFound(){
        return new CommonApiException(LOGIN_ID_NOT_FOUND);
    }

}
