package com.hiya.shiro.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hiya.common.base.BaseController;
import com.hiya.common.base.Response;
import com.hiya.object.sys.po.User;
import com.hiya.shiro.service.UserService;

/**
 * @author seven sins
 * @date 2017年12月30日 下午4:44:21
 */
@RestController
public class UserController extends BaseController {

	@Autowired
	UserService userService;
	
	@GetMapping("/rest/sys/user")
	@RequiresPermissions("base")
	public Response<User> find(User user) {
		return new Response<>(200, userService.find(user));
	}
	
	@GetMapping("/rest/sys/user/{id}")
	public Response<User> get(@PathVariable("id") Long id) {
		return new Response<>(200, userService.get(id));
	}
	
	@PostMapping("/rest/sys/user")
	public Response<?> create(@RequestBody User user) {
		userService.insert(user);
		
		return Response.SUCCESS;
	}
	
	@PutMapping("/rest/sys/user/{id}")
	public Response<?> update(@PathVariable("id") Long id, @RequestBody User user) {
		user.setId(id);
		userService.update(user);
		
		return Response.SUCCESS;
	}
	
}
