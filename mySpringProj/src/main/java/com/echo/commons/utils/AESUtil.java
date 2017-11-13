package com.echo.commons.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * AES 加解密工具类
 *
 * @author Echo-cxt
 * @create 2017-11-13 9:50 AM
 **/
public class AESUtil {

    /** 密钥算法 **/
    private static final String KEY_ALGORITHM = "AES";

    /**
     * AES解密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static String decrypt(byte[] data, byte[] key) throws Exception{
        //还原密钥
        Key k = new SecretKeySpec(key, KEY_ALGORITHM);
        //创建解密器
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        //初始化，设置解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        return new String(cipher.doFinal(data), Constants.UTF8);
    }

    /**
     * AES解密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws Exception{
        return decrypt(Base64.decodeBase64(data), key.getBytes(Constants.UTF8));
    }

    /**
     * AES加密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception{
        Key k = new SecretKeySpec(key, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        //初始化，设置加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    public static String encrypt(String data, String key) throws Exception{
        byte[] dataBytes = data.getBytes(Constants.UTF8);
        byte[] keyBytes = key.getBytes(Constants.UTF8);
        return Base64.encodeBase64String(encrypt(dataBytes, keyBytes));
    }
}
