package com.hiya.shiro.controller;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hiya.common.base.BaseController;
import com.hiya.object.sys.po.User;
import com.hiya.shiro.service.UserService;
import com.hiya.shiro.utils.JWTUtil;

/**
 * @author seven sins
 * @date 2018年5月30日 下午8:19:16
 */
@RestController
public class AuthController extends BaseController {

	@Autowired
	UserService userService;
	
	@GetMapping("/401")
	public Object unauthorized() {
		return "401";
	}
	
	@PostMapping("/loginUser")
	public Object loginUser(@RequestBody User user) {
		User userBean = userService.findByUsername(user.getUsername());
		if(userBean == null) {
			throw new AuthenticationException("用户未找到");
		}
		if(!userBean.getPassword().equals(user.getPassword())) {
			throw new AuthenticationException("用户或密码错误");
		}
		return JWTUtil.sign(user.getUsername(), user.getPassword());
	}
}
