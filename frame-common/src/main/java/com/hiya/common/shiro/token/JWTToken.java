package com.hiya.common.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author seven sins
 * @date 2018年6月2日 下午3:24:25
 */
public class JWTToken implements AuthenticationToken {
	
	private static final long serialVersionUID = 132702428094130784L;
	// 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
