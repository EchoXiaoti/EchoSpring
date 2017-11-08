package com.echo.commons.utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * @author Echo-cxt
 * @create 2017-11-08 3:00 PM
 **/
public abstract class SecurityUtil {
    public static final String DSA = "DSA";
    public static final String UTF8 = "UTF8";
    public static final String AES = "AES";
    public static final String DSASIGN = "SHA1withDSA";

    public SecurityUtil(){}

    public static SecretKey generateSecretKey(String keyMaterial){
        IllegalArgumentException illegalArgumentException = null;
        if(keyMaterial == null){
            illegalArgumentException = new IllegalArgumentException("keyMaterial参数不能为空");
        }else if(!keyMaterial.matches("[A-Za-z0-9]{16}")){
            illegalArgumentException = new IllegalArgumentException("keyMaterial参数只能由字母或数字组成，并且长度位16个长度");
        }
        if(illegalArgumentException != null)
            throw illegalArgumentException;
        SecretKey key = null;
        try{
            key = new SecretKeySpec(keyMaterial.getBytes("UTF8"), "AES");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return key;
    }

    public static KeyPair makeKeyPair(){
        try{
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
            keyGen.initialize(1024);
            return keyGen.generateKeyPair();
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("没有DSA算法，详细信息: " + e.getMessage());
        }
    }

    public static String outputfilter(String value){
        if(value == null){
            return null;
        }
        StringBuffer result = new StringBuffer(value.length());
        for(int i = 0; i < value.length(); i++){
            switch (value.charAt(i)) {
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                case '"':
                    result.append("&quot;");
                    break;
                case '\'':
                    result.append("&#39;");
                    break;
                case '%':
                    result.append("&#37;");
                    break;
                case ';':
                    result.append("&#59;");
                    break;
                case '(':
                    result.append("&#40;");
                    break;
                case ')':
                    result.append("&#41;");
                    break;
                case '&':
                    result.append("&amp;");
                    break;
                case '+':
                    result.append("&#43;");
                    break;
                case '#':
                case '$':
                case '*':
                case ',':
                case '-':
                case '.':
                case '/':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case ':':
                case '=':
                default:
                    result.append(value.charAt(i));
            }
        }
        return result.toString();
    }
}
