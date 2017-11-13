package com.echo.commons.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.NDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 全加密工具类
 *
 * @author Echo-cxt
 * @create 2017-11-10 5:01 PM
 **/
public class AppSecurityUtil {

    private static Logger logger = LoggerFactory.getLogger(AppSecurityUtil.class);

    /** 前面几位标识AESKey的长度 **/
    private static final int LENGTH = 8;

    public static Map<String, String> StringToMap(String str){
        if((str == null) || ((str = str.trim()).length() < 3)){
            return null;
        }
        str = str.substring(1, str.length() - 1);
        Map<String, String> datas = new LinkedHashMap<String, String>();
        String[] temp = str.split(",");
        for(int i = 0; i < temp.length; i++){
            int e = temp[i].indexOf("=");
            datas.put(temp[i].substring(0, e), temp[i].substring(e + 1));
        }
        return datas;
    }

    public static void MapToString(StringBuilder buf, Map<String, String> datas){
        if(datas == null)
            return;
        Iterator i = datas.entrySet().iterator();
        if(buf.length() > 2)
            buf.append(",");
        boolean hasNext = i.hasNext();
        while(hasNext){
            Map.Entry<String, String> e = (Map.Entry)i.next();
            buf.append(e.getKey()).append('=').append(e.getValue());
            hasNext = i.hasNext();
            if(hasNext)
                buf.append(",");
        }
    }

    public static String ToString(Map<String, String> datas){
        if(MapUtils.isEmpty(datas))
            return null;
        StringBuilder buf = new StringBuilder(24);
        buf.append("<");
        MapToString(buf, datas);
        buf.append(">");
        return buf.toString();
    }

    public static void SetAESKey(String aesKey){
        Map<String, String> map = StringToMap(NDC.get());
        map.put("K", aesKey);
        NDC.clear();
        NDC.push(ToString(map));
    }

    public static String GetAesKey(){
        Map<String, String> map = StringToMap(NDC.get());
        return map.get("K");
    }

    /**
     * 解密入参得到AESKey
     * @param input
     * @return
     * @throws Exception
     */
    public static String decryptAESKey(String input) throws Exception{
        String strLength = input.substring(0, LENGTH);
        int length = Integer.parseInt(strLength);
        String encodingAesKey = input.substring(LENGTH, LENGTH + length);
        String aesKey = RSAUtil.decryptWithBase64(encodingAesKey);

        //设置AESKey到线程
        logger.info("Decrypt AESKey success, AESKey:{}", aesKey);
        SetAESKey(aesKey);
        return aesKey;
    }

    /**
     * 入参解密
     * @param input
     * @return
     * @throws Exception
     */
    public static String decrypt(String input) throws Exception{
        String strLength = input.substring(0, LENGTH);
        int length = Integer.parseInt(strLength);
        String inputData = AESUtil.decrypt(input.substring(LENGTH + length), decryptAESKey(input));
        return inputData;
    }

    /**
     * 出餐加密
     * @param output
     * @return
     * @throws Exception
     */
    public static String encrypt(String output) throws Exception{
        String aesKey = GetAesKey();
        logger.info("Encrypt output use AESKey:{}", aesKey);
        return AESUtil.encrypt(output, aesKey);
    }

    /**
     * XSS过滤
     * @param input
     * @return
     */
    public static String htmlEscape(String input){
        if(StringUtils.isEmpty(input)){
            return input;
        }
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");
        return input;
    }

    public static String htmlUnEscape(String input){
        if(StringUtils.isEmpty(input)){
            return input;
        }
        input = input.replaceAll("&lt;", "<");
        input = input.replaceAll("&gt;", ">");
        return input;
    }

    /**
     * 对返回对象JSON序列化并加密
     * @param returnObj
     * @param encrypt
     * @return
     */
    public static String returnObj(Object returnObj, boolean encrypt){
        String str = "";
        if(returnObj instanceof String){
            str = (String)returnObj;
        }else{
            str = JSON.toJSONString(returnObj, SerializerFeature.WriteMapNullValue);
        }
        logger.info("Response output data: {}", str);

        if(encrypt){
            try {
                str = AppSecurityUtil.encrypt(str);
            } catch (Exception e) {
                throw new RuntimeException("加密返回数据异常: " + e.getMessage(), e);
            }
            logger.info("Response output: {}", str);
        }
        return str;
    }

    /**
     * 设置返回的ContentType
     * @param request
     * @param response
     * @return
     */
    public static boolean setResponseContentType(HttpServletRequest request, HttpServletResponse response){
        String reqContentType = request.getContentType();
        boolean encrypt = false;
        if(StringUtils.isNotEmpty(reqContentType) && reqContentType.indexOf("text") != -1){
            encrypt = true;
        }
        String contentType = MediaType.APPLICATION_JSON_VALUE;
        if(encrypt){
            contentType = MediaType.TEXT_PLAIN_VALUE;
        }
        response.setContentType(contentType);
        logger.info("Response with contentType: {}", contentType);
        return encrypt;
    }

    /**
     * AES密钥加密请求数据
     * @param aesKey
     * @param input
     * @return
     * @throws Exception
     */
    public static String encryptAESKey(String aesKey, String input) throws Exception{
        String encryptAESKey = RSAUtil.encryptWithBase64(aesKey);
        String lengthPerfix = StringUtils.leftPad(String.valueOf(encryptAESKey.length()), 8, '0');
        String data = AESUtil.encrypt(input, aesKey);
        return lengthPerfix + encryptAESKey + data;
    }
}
