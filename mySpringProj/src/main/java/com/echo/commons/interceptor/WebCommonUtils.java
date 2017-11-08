package com.echo.commons.interceptor;

import com.echo.commons.exception.BaseException;
import com.echo.commons.utils.ContentTypeUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * web请求设置工具
 *
 * @author Echo-cxt
 * @create 2017-11-08 1:21 PM
 **/
public class WebCommonUtils implements InitializingBean, ApplicationContextAware{

    private static final String HEADER_PRAGMA = "Pragma";
    private static final String HEADER_EXPIRES = "Expires";
    private static final String HEADER_CACHE_CONTROL = "Cache-Control";

    private String defaultCharset;
    private boolean preventCaching = true;
    private boolean enableResolveCharset = true;
    private boolean enableSetterCommonAttrs = false;
    private boolean cookieCrossDomainSupport = false;
    private boolean disableRequestLog = false;

    private ApplicationContext applicationContext;
    private ContentTypeUtil contentTypeUtil = new ContentTypeUtil();

    protected Log logger = LogFactory.getLog(getClass());
    private Map<Object, Object> _config;
    private Object _locked = new Object();

    public WebCommonUtils(){}

    public MediaType getMediaType(ServletRequest request){
        return this.contentTypeUtil.getMediaType(request);
    }

    public String resolveCharset(HttpServletRequest request, HttpServletResponse response){
        String requestCharset = null;
        if(this.enableResolveCharset){
            requestCharset = this.contentTypeUtil.getCharset(request, this.defaultCharset);
        }else{
            requestCharset = this.defaultCharset;
        }
        if(requestCharset != null){
            try{
                request.setCharacterEncoding(requestCharset);
                response.setCharacterEncoding(requestCharset);
            }catch (UnsupportedEncodingException e){
                throw new BaseException(e.getMessage(), e);
            }
        }
        return requestCharset;
    }

    public void resolveCrossDomainHeader(HttpServletResponse response){
        if(this.cookieCrossDomainSupport){
            response.setHeader("P3P", "CP=CAO PSA OUR");
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
    }

    public void preventCaching(HttpServletResponse response){
        if(this.preventCaching){
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 1L);
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.logger.info("config[defaultCharset=" + this.defaultCharset + ",preventCaching="
                + this.preventCaching + ", cookieCrossDomainSupport=" + this.cookieCrossDomainSupport
                + ",disableRequestLog=" + this.disableRequestLog + "]");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public String getDefaultCharset() {
        return defaultCharset;
    }

    public void setDefaultCharset(String defaultCharset) {
        this.defaultCharset = defaultCharset;
    }

    public boolean isPreventCaching() {
        return preventCaching;
    }

    public void setPreventCaching(boolean preventCaching) {
        this.preventCaching = preventCaching;
    }

    public boolean isEnableResolveCharset() {
        return enableResolveCharset;
    }

    public void setEnableResolveCharset(boolean enableResolveCharset) {
        this.enableResolveCharset = enableResolveCharset;
    }

    public boolean isEnableSetterCommonAttrs() {
        return enableSetterCommonAttrs;
    }

    public void setEnableSetterCommonAttrs(boolean enableSetterCommonAttrs) {
        this.enableSetterCommonAttrs = enableSetterCommonAttrs;
    }

    public boolean isCookieCrossDomainSupport() {
        return cookieCrossDomainSupport;
    }

    public void setCookieCrossDomainSupport(boolean cookieCrossDomainSupport) {
        this.cookieCrossDomainSupport = cookieCrossDomainSupport;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public boolean isDisableRequestLog() {
        return disableRequestLog;
    }

    public void setDisableRequestLog(boolean disableRequestLog) {
        this.disableRequestLog = disableRequestLog;
    }
}
