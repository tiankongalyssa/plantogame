package com.sky.blind.service.exception;

public class LoginDateException extends ServiceException{
    public LoginDateException() {
    }

    public LoginDateException(String message) {
        super(message);
    }

    public LoginDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginDateException(Throwable cause) {
        super(cause);
    }

    public LoginDateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
