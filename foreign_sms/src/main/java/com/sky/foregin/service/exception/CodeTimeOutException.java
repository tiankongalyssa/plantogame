package com.sky.foregin.service.exception;

public class CodeTimeOutException extends ServiceException{
    public CodeTimeOutException() {
    }

    public CodeTimeOutException(String message) {
        super(message);
    }

    public CodeTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeTimeOutException(Throwable cause) {
        super(cause);
    }

    public CodeTimeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
