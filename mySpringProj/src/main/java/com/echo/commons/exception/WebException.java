package com.echo.commons.exception;

public class WebException extends BaseException {

    private static final long serialVersionUID = 1L;

    public  WebException(String msg){
        super(msg);
    }

    public WebException(Throwable th){
        super(th.getMessage(), th);
    }

    public WebException(String msg, Throwable th){
        super(msg, th);
    }
}
