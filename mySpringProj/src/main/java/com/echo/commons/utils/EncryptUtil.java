package com.echo.commons.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 基础加密组件
 * @author xiaoti
 *
 */
public class EncryptUtil {

	private static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);
	
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";
	
	/**
	 * MAC算法可选以下多种算法
	 * HmacMD5
	 * HmacSHA1
	 * HmacSHA256
	 * HmacSHA384
	 * HmacSHA512
	 */
	public static final String KEY_MAC = "HmacMD5";
	
	/**
	 * BASE64解密
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception{
		return Base64.decodeBase64(key);
	}
	
	/**
	 * BASE64加密
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception{
		return Base64.encodeBase64String(key);
	}
	
	/**
	 * MD5加密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception{
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		return md5.digest();
	}
	
	/**
	 * SHA加密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception{
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);
		return sha.digest();
	}
	
	/**
	 * 初始化HMAC密钥
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey() throws Exception{
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}
	
	/**
	 * HMAC加密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception{
		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}
	
	/**
	 * BASE64加密
	 * @param str
	 * @return
	 */
	public static String base64Encode(String str){
		return Base64.encodeBase64String(str.getBytes());
	}
	
	/**
	 * Base64 加密， 使用UTF-8编码
	 * @param str
	 * @return
	 */
	public static String base64EncodeUTF8(String str){
		try {
			return Base64.encodeBase64String(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("to do encoding for '" + str + "', encoding UTF-8 is not supported", e);
			return null;
		}
	}
	
	/**
	 * Base64 解密
	 * @param str
	 * @return
	 */
	public static String Base64Decode(String str){
		return new String(Base64.decodeBase64(str));
	}
	
	/**
	 * base64解密 
	 * @param str
	 * @param charset	字符集
	 * @return
	 */
	public static String base64Decode(String str, Charset charset){
		return new String(Base64.decodeBase64(str), charset);
	}
	
	/**
	 * 计算字符串的MD5值
	 * @param data
	 * @return
	 */
	public static String EncodeByMd5(String data){
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(data.getBytes("UTF-8"));
			byte[] digest = md5.digest();
			return new String(Hex.encodeHex(digest)).toUpperCase();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RuntimeException("计算MD5值异常");
		}
		
	}
}
