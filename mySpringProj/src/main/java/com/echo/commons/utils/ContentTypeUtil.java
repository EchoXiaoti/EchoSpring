package com.echo.commons.utils;

import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import java.nio.charset.Charset;

/**
 * Http-ContentType工具类
 *
 * @author Echo-cxt
 * @create 2017-11-08 1:28 PM
 **/
public class ContentTypeUtil {
    public ContentTypeUtil(){}

    public MediaType getMediaType(ServletRequest request){
        String contentType = request.getContentType();
        if(!StringUtils.hasText(contentType)){
            contentType = "*";
        }
        if(StringUtils.hasText(contentType)){
            return MediaType.parseMediaType(contentType);
        }
        return null;
    }

    public String getCharset(ServletRequest request, String defaultCharset){
        MediaType mediaType = getMediaType(request);
        if(mediaType != null){
            Charset charset = mediaType.getCharSet();
            if(charset != null){
                return charset.displayName();
            }
        }
        return defaultCharset;
    }
}
