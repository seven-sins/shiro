package com.hiya.react.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hiya.common.base.BaseController;
import com.hiya.common.base.Response;
import com.hiya.object.sys.po.User;
import com.hiya.react.service.UserService;

/**
 * @author seven sins
 * 2018年6月5日 下午7:13:03
 */
@RestController
public class UserController extends BaseController {

	@Autowired
	UserService userService;
	
	@PostMapping("/api/user/create")
	public Response<?> create(@Valid @RequestBody User user){
		userService.insert(user);
		
		return Response.success();
	}
}
