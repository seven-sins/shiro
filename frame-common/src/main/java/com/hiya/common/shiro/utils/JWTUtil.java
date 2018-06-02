package com.hiya.common.shiro.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hiya.common.shiro.ShiroCommon;

/**
 * @author seven sins
 * @date 2018年6月2日 下午3:22:19
 */
public class JWTUtil {

	/**
	 * 校验token是否正确
	 * 
	 * @param token密钥
	 * @param secret用户的密码
	 * @return 是否正确
	 */
	public static boolean verify(String token, String username, String secret) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
			verifier.verify(token);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 * 
	 * @return token中包含的用户名
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 生成签名, 5min后过期
	 * 
	 * @param username用户名
	 * @param secret用户的密码
	 * @return 加密的token
	 */
	public static String sign(String username, String secret) {
		try {
			Date date = new Date(System.currentTimeMillis() + ShiroCommon.EXPIRE_TIME);
			Algorithm algorithm = Algorithm.HMAC256(secret);
			// 附带username信息
			return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
