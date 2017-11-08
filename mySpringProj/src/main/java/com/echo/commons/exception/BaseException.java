package com.echo.commons.exception;

public class BaseException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public BaseException(String msg){
        super(msg);
    }

    public BaseException(Throwable th){
        super(th);
    }

    public BaseException(String msg, Throwable th){
        super(msg, th);
    }
}
