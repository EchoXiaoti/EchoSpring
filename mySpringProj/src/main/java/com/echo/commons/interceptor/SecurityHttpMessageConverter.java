package com.echo.commons.interceptor;

import com.alibaba.fastjson.JSON;
import com.echo.commons.exception.BaseException;
import com.echo.commons.utils.AppSecurityUtil;
import com.echo.commons.utils.Constants;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import sun.nio.ch.IOUtil;

import java.io.IOException;
import java.util.Collections;

/**
 * 安全加密消息转换
 *
 * @author Echo-cxt
 * @create 2017-11-10 3:42 PM
 **/
public class SecurityHttpMessageConverter extends AbstractHttpMessageConverter<Object>{

    private static Logger logger = LoggerFactory.getLogger(SecurityHttpMessageConverter.class);

    /** Native发送的是加密后的文本格式 **/
    public SecurityHttpMessageConverter(){
        super(new MediaType(MediaType.TEXT_PLAIN, Collections.singletonMap("charst", Constants.UTF8)));
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        String encodingInput = IOUtils.toString(httpInputMessage.getBody(), Constants.UTF8);
        String inputData = encodingInput == null ? null : encodingInput.replaceAll("\n", "");
        try {
            inputData = AppSecurityUtil.decryptAESKey(encodingInput);
        } catch (Exception e) {
            throw new BaseException("解密消息异常: " + e.getMessage(), e);
        }
        inputData = StringUtils.defaultIfEmpty(inputData, "{}");

        Object obj = null;

        try{
            obj = JSON.parseObject(inputData, aClass);
        }catch (Exception e){
            throw new BaseException("请求包体JSON反序列化成对象异常：" + e.getMessage(), e);
        }

        return obj;
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {

    }
}
