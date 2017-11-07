package com.echo.commons.web;

import com.echo.commons.base.exception.WebException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseController {

    public BaseController() {}

    protected Log logger = LogFactory.getLog(getClass());

    protected final void _throwEx(String msg){
        throw new WebException(msg);
    }

    protected final void _throwEx(Throwable th){
        _throwEx(null,th);
    }

    protected final void _throwEx(String msg, Throwable th){
        if(msg == null)
            msg = th.getMessage();
        throw new WebException(msg, th);
    }

    public Log getLogger(){
        return this.logger;
    }

    public void setLogger(Log logger){
        this.logger = logger;
    }
}
