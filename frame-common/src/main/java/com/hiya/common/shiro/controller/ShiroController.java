package com.hiya.common.shiro.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiya.common.base.Response;

/**
 * @author seven sins
 * 2018年6月5日 下午8:14:32
 */
@RestController
public class ShiroController {

	@GetMapping("/401")
	public Response<?> unauthorized(String errorMsg){
		if(StringUtils.isBlank(errorMsg)) {
			return Response.create(401, "无权限访问");
		} else {
			return Response.create(401, errorMsg);
		}
	}
}
