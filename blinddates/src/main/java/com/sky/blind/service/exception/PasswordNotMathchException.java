package com.sky.blind.service.exception;

public class PasswordNotMathchException extends ServiceException{
    public PasswordNotMathchException() {
        super();
    }

    public PasswordNotMathchException(String message) {
        super(message);
    }

    public PasswordNotMathchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordNotMathchException(Throwable cause) {
        super(cause);
    }

    protected PasswordNotMathchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

