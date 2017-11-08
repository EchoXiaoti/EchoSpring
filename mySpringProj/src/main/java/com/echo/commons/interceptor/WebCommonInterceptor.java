package com.echo.commons.interceptor;

import com.echo.commons.utils.MDCUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web拦截器
 *
 * @author Echo-cxt
 * @create 2017-11-08 4:25 PM
 **/
public class WebCommonInterceptor extends HandlerInterceptorAdapter implements Ordered {

    private WebCommonUtils webCommonUtils;
    private Log logger = LogFactory.getLog(getClass());

    public WebCommonInterceptor(){}

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String requestId = MDCUtil.generateRequestId();
        MDCUtil.set(requestId);
        return true;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
