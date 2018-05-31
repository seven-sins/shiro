package com.hiya.shiro.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.hiya.object.sys.po.User;
import com.hiya.shiro.service.UserService;
import com.hiya.shiro.utils.JWTUtil;

/**
 * @author seven sins
 * @date 2018年5月30日 下午7:54:42
 */
public class AuthRealm extends AuthorizingRealm {

	UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JWTToken;
	}
	
	/**
	 * 此方法调用hasRole,hasPermission的时候才会进行回调
	 * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		/**
		 * 登录成功时将用户权限列表存入redis, 进入此方法验证权限时从redis取出放入SimpleAuthorizationInfo返回
		 */
		// String username = JWTUtil.getUsername(principals.toString());
		
		List<String> permissionList = new ArrayList<>();
		// 设置用户权限
		permissionList.add("base");
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 权限
		info.addStringPermissions(permissionList);
		
		List<String> roleList = new ArrayList<>();
		// 角色
		roleList.add("admin");
		info.addRoles(roleList);
		
		return info;
	}

	/**
	 * 认证登录
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
		String token = (String) auth.getCredentials();
		// 解密获得username
		String username = JWTUtil.getUsername(token);
		
		/**
		 * 实际应用中, 用户信息可以存redis, 以下代码替换为从redis中获取当前登录用户信息
		 */
		User user = userService.findByUsername(username);
		if(user == null) {
			throw new AuthenticationException("用户不存在");
		}
		if(!JWTUtil.verify(token, username, user.getPassword())) {
			throw new AuthenticationException("用户或密码错误");
		}
		
		return new SimpleAuthenticationInfo(token, token, this.getClass().getName());
	}

}
