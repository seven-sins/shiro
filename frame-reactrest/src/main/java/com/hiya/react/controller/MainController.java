package com.hiya.react.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiya.common.base.BaseController;
import com.hiya.common.base.Response;

/**
 * @author seven sins
 * @datetime 2018年7月1日 下午2:46:24
 */
@RestController
public class MainController extends BaseController {

	@GetMapping("/api/main")
	@RequiresPermissions("base")
	public Response<?> main(){
		return Response.success();
	}
}
