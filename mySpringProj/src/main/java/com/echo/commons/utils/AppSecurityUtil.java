package com.echo.commons.utils;

import org.apache.commons.collections4.MapUtils;
import org.apache.log4j.NDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        logger.info("Decrypt AESKey success, AESKey:{}", aesKey);
        SetAESKey(aesKey);
        return aesKey;
    }

}
