package com.echo.commons.utils;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;

public class MD5SignUtil {
	
	public static String signDataWithMD5Key(String baseStr, String md5Key){
		String signature = null;
		String urlparam = md5Key + "" + baseStr + "" + md5Key;	//组装MD5的加签参数
		try {
			/*
			 * MD系列可以使用如下算法（字母不区分大小写）md5摘要长度128为，16个字节一下算法需要安装bouncy
			 * castle算法包md2 md4 ripemd128摘要长度128位，16个字节ripemd160
			 * 摘要长度160位，20哥字节
			 */
			MessageDigest md5Digest = MessageDigest.getInstance("MD5");
			byte[] digByteResult = md5Digest.digest(urlparam.getBytes());
			signature = new String(Hex.encodeHex(digByteResult));
		} catch (Exception e) {
			return null;
		}
		return signature;
		
	}

}
