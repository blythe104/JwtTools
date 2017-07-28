package com.xin;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Base64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**
 * 静态字符串，加密解密jwt
 * 验证返回的加密数据是否正确
 * TODO
 * @author 002619
 * @date 2017年7月11日上午10:46:17
 */

public class JwtUtil {
	/**
	 * 设置加密密钥
	 * @param key
	 * @return
	 */
	static String keys;
	public static  void setSecretKey(String key){
		keys=key;
	}
	
	public static String getSecretKey(){
		return keys;
	}
	/**
	 * 由字符串生成加密key
	 * @return
	 */
	public static SecretKey generalKey(){
		String stringKey=getSecretKey();
		byte[] encodedKey = stringKey.getBytes();
	    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	    return key;
	}

	/**
	 * 创建jwt
	 * @param id
	 * @param subject
	 * @param ttlMillis
	 * @return
	 * @throws Exception
	 */
	public static String createJWT(String id, String subject, long ttlMillis) throws Exception {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		SecretKey key = generalKey();
		JwtBuilder builder = Jwts.builder()
			.setId(id)
			.setIssuedAt(now)
			.setSubject(subject)
		    .signWith(signatureAlgorithm, key);
		if (ttlMillis >= 0) {
		    long expMillis = nowMillis + ttlMillis;
		    Date exp = new Date(expMillis);
		    builder.setExpiration(exp);
		}
		return builder.compact();
	}
	
	/**
	 * 解密jwt
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception{
		SecretKey key = generalKey();
		Claims claims = Jwts.parser()         
		   .setSigningKey(key)
		   .parseClaimsJws(jwt).getBody();
		return claims;
	}
	
	
}
