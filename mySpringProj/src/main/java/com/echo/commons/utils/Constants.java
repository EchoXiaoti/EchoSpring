package com.echo.commons.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统枚举值
 *
 * @author Echo-cxt
 * @create 2017-11-07 5:00 PM
 **/
public class Constants {

    private static Logger logger = LoggerFactory.getLogger(Constants.class);

    public static class Jsonp{
        /** JSONP默认回调函数名 **/
        public final static String CALL_BACK_NAME = "callback";

        public static String getJsonpCallback(HttpServletRequest request){
            String callback = null;

            callback = request.getParameter(CALL_BACK_NAME);
            if(StringUtils.isBlank(callback)){
                return null;
            }

            callback = StringEscapeUtils.escapeHtml(callback);
            callback = StringEscapeUtils.escapeJavaScript(callback);

            return callback;
        }
    }
}
