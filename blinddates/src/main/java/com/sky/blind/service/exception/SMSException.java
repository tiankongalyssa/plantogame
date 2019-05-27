package com.sky.blind.service.exception;

public class SMSException extends ServiceException{
    public SMSException() {
    }

    public SMSException(String message) {
        super(message);
    }

    public SMSException(String message, Throwable cause) {
        super(message, cause);
    }

    public SMSException(Throwable cause) {
        super(cause);
    }

    public SMSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
