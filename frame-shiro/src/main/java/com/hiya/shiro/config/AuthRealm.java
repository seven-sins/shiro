package com.hiya.shiro.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.hiya.object.sys.po.User;
import com.hiya.shiro.service.UserService;

/**
 * @author seven sins
 * @date 2018年5月30日 下午7:54:42
 */
public class AuthRealm extends AuthorizingRealm {

	UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取当前登录用户
		// User user = (User) principals.fromRealm(this.getClass().getName()).iterator().next();
		List<String> permissionList = new ArrayList<>();
		
		// 设置用户权限
		permissionList.add("base");
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(permissionList);
		
		return info;
	}

	/**
	 * 认证登录
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();
		User user = userService.findByUsername(username);
		
		return new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());
	}

}
