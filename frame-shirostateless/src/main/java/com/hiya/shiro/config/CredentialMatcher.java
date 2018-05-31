package com.hiya.shiro.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 密码匹配
 * @author seven sins
 * @date 2018年5月30日 下午7:54:47
 */
public class CredentialMatcher extends SimpleCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String password = new String(usernamePasswordToken.getPassword());
		String dbPassword = (String) info.getCredentials();
		if(StringUtils.isBlank(password) || StringUtils.isBlank(dbPassword)) {
			return false;
		}
		
		return super.equals(password, dbPassword);
	}

}
