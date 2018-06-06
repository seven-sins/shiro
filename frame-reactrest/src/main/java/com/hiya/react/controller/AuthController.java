package com.hiya.react.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hiya.common.base.BaseController;
import com.hiya.common.base.Response;
import com.hiya.common.service.RedisService;
import com.hiya.common.shiro.ShiroCommon;
import com.hiya.common.shiro.utils.JWTUtil;
import com.hiya.object.sys.po.User;
import com.hiya.react.service.UserService;

/**
 * @author seven sins
 * @date 2018年5月30日 下午8:19:16
 */
@RestController
public class AuthController extends BaseController {

	@Autowired
	UserService userService;
	@Autowired
	RedisService redisService;
	
	@PostMapping("/auth/login")
	public Response<?> login(@RequestBody User user) {
		User userBean = userService.findByUsername(user.getUsername());
		if(userBean == null) {
			throw new AuthenticationException("用户未找到");
		}
		if(!userBean.getPassword().equals(user.getPassword())) {
			throw new AuthenticationException("用户或密码错误");
		}
		/**
		 * 将当前登录用户信息存入redis
		 * key: ShiroCommon.USER_PREFIX + username
		 */
		String key = ShiroCommon.USER_PREFIX + userBean.getUsername();
		redisService.delete(key);
		redisService.add(key, userBean);
		
		return new Response<>().data(JWTUtil.sign(user.getUsername(), user.getPassword()));
	}
	
	@GetMapping("/auth/logout")
	public Response<?> logout(){
		Subject subject = SecurityUtils.getSubject();
		if(subject != null) {
			String username = (String) subject.getPrincipal();
			String key = ShiroCommon.USER_PREFIX + username;
			redisService.delete(key);
			//
			subject.logout();
		}
		return Response.success();
	}
}
