package com.echo.commons.utils;

import com.echo.commons.exception.BaseException;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil extends EncryptUtil{

    private static Logger logger = LoggerFactory.getLogger(RSAUtil.class);

    public static final String KEY_ALGORITHM = "RSA";

    public static final String YZT_ALGORITHM = "RSAHEX";

    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "";

    private static final String PRIVATE_KEY = "";

    /**
     * RSA最大加密铭文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 使用私钥解密
     * @param base64String
     * @return
     */
    public static String decryptWithBase64(String base64String){
        if(StringUtils.isBlank(base64String)){
            return base64String;
        }
        String data = "";
        try {
            RSAPrivateKey rsaPrivateKey = loadPrivateKey(PRIVATE_KEY);
            byte[] binaryData = decrypt(rsaPrivateKey, Base64.decodeBase64(base64String));
            data = new String(binaryData, "UTF-8");
        } catch (Exception e) {
            logger.error("RSA解密异常", e);
            throw new BaseException("RSA解密异常");
        }
        return data;
    }

    public static String encryptWithBase64(String string){
        String data = "";
        try{
            RSAPublicKey rsaPublicKey = loadPublicKey(PUBLIC_KEY);
            byte[]  binaryData = encrypt(rsaPublicKey, string.getBytes("UTF-8"));
            data = Base64.encodeBase64String(binaryData);
        }catch(Exception e){
            logger.error("RSA加密异常", e);
        }
        return data;
    }
    /**
     * 解密过程
     * @param privateKey
     * @param cipherData
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception{
        if(privateKey == null)
            throw new Exception("解密私钥为空，请设置");
        Cipher cipher = null;
        try{
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        }catch (NoSuchAlgorithmException e){
            throw new Exception("无此解密算法");
        }catch (NoSuchPaddingException e){
            throw new Exception("语法错误");
        }catch (InvalidKeyException e){
            throw new Exception("解密私钥非法，请检查");
        }catch (IllegalBlockSizeException e){
            throw new Exception("密文长度非法");
        }catch (BadPaddingException e){
            throw new Exception("密文数据已损坏");
        }

    }

    /**
     * 加密过程
     * @param publicKey
     * @param plainTextData
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception{
        if(publicKey == null)
            throw new Exception("加密公钥为空，请设置");
        Cipher cipher = null;
        try{
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        }catch (NoSuchAlgorithmException e){
            throw new Exception("无此解密算法");
        }catch (NoSuchPaddingException e){
            throw new Exception("语法错误");
        }catch (InvalidKeyException e){
            throw new Exception("解密私钥非法，请检查");
        }catch (IllegalBlockSizeException e){
            throw new Exception("密文长度非法");
        }catch (BadPaddingException e){
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 从字符串中加载公钥
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception{
        try{
            byte[] buffer = Base64.decodeBase64(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey)keyFactory.generatePublic(keySpec);
        }catch (NoSuchAlgorithmException e){
            throw new Exception("无此算法");
        }catch (InvalidKeySpecException e){
            throw new Exception("公钥非法");
        }catch (NullPointerException e){
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 加载私钥串
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws  Exception{
        try{
            byte[] buffer = Base64.decodeBase64(privateKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            return (RSAPrivateKey) keyFactory.generatePublic(keySpec);
        }catch (NoSuchAlgorithmException e){
            throw new Exception("无此算法");
        }catch (InvalidKeySpecException e){
            throw new Exception("公钥非法");
        }catch (NullPointerException e){
            throw new Exception("公钥数据为空");
        }
    }
}
