package com.echo.commons.interceptor;

import com.echo.commons.exception.WebException;
import com.echo.commons.utils.SecurityUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.regex.Pattern;

/**
 * Jsonp跨域
 *
 * @author Echo-cxt
 * @create 2017-11-08 2:26 PM
 **/
public class JsonpSupportInterceptor extends HandlerInterceptorAdapter implements InitializingBean{

    public JsonpSupportInterceptor(){}

    private final Log logger = LogFactory.getLog(getClass());

    private String callbackParameterName = "callback";

    private boolean enable = true;

    private String defaultPattern = "^\\w+$";

    private Pattern pattern = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.pattern = Pattern.compile(this.defaultPattern);
    }

    public boolean preHandler(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String params = null;
        if((this.enable) && ((params = request.getParameter(this.callbackParameterName)) != null) && ((params = params.trim()).length() > 0)){
            if(this.pattern.matcher(params).matches()){
                Writer out = response.getWriter();
                out.write(SecurityUtil.outputfilter(params));
                out.write(40);
            }else{
                this.logger.error("***********jsonp callback function name must be matched by [^\\w+$] *******");
            }
        }
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        String params = null;
        if((this.enable) && ((params = request.getParameter(this.callbackParameterName)) != null) && ((params = params.trim()).length() > 0)){
            try{
                Writer out = response.getWriter();
                out.write(");");
                out.flush();
            }catch (IOException e){
                throw new WebException(e.getMessage(), e);
            }
        }
    }

    public String getCallbackParameterName() {
        return callbackParameterName;
    }

    public void setCallbackParameterName(String callbackParameterName) {
        this.callbackParameterName = callbackParameterName;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getDefaultPattern() {
        return defaultPattern;
    }

    public void setDefaultPattern(String defaultPattern) {
        this.defaultPattern = defaultPattern;
    }
}
