package com.hiya.shiro.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hiya.common.base.BaseController;
import com.hiya.common.base.Response;
import com.hiya.object.sys.po.User;

/**
 * @author seven sins
 * @date 2018年5月30日 下午8:19:16
 */
@RestController
public class AuthController extends BaseController {

	@PostMapping("/login")
	public Object login() {
		return "login";
	}
	
	@GetMapping("/index")
	public Object index() {
		return "index";
	}
	
	@PostMapping("/loginUser")
	public Object loginUser(@RequestBody User user, HttpSession session) {
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			User currentLoginUser = (User) subject.getPrincipal();
			session.setAttribute("user", currentLoginUser);
			
			return SUCCESS;
		}catch(Exception e) {
			return new Response<>(400, "fail");
		}
	}
}
