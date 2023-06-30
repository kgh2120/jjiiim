package com.kk.jjiiim.exception;

import static com.kk.jjiiim.exception.ManagerErrorCode.*;

public class ManagerApiException extends AbstractApiException{
    private ManagerApiException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static ManagerApiException alreadyRegisteredStoreName(){
        return new ManagerApiException(ALREADY_REGISTERED_STORE_NAME);
    }
}
