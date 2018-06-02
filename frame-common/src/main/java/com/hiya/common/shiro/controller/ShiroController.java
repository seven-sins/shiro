package com.hiya.common.shiro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiya.common.base.Response;

@RestController
public class ShiroController {

	@GetMapping("/401")
	public Response<?> unauthorized(){
		return new Response<>(401, "无权限访问");
	}
}
