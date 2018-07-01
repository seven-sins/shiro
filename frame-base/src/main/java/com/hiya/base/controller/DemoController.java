package com.hiya.base.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiya.common.base.BaseController;
import com.hiya.common.base.Response;

@RestController
public class DemoController extends BaseController {

	@GetMapping("/rest/base/test")
	@RequiresPermissions("base")
	public Response<?> find() {
		return Response.SUCCESS;
	}
}
