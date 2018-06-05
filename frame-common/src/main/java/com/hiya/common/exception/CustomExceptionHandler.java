package com.hiya.common.exception;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hiya.common.base.Response;

/**
 * 全局异常处理
 * @author seven sins
 * @date 2018年6月2日 下午3:52:36
 */
@ControllerAdvice
public class CustomExceptionHandler {

	/**
	 * 捕获shiro异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ShiroException.class)
	public Response<?> handle401(ShiroException e) {
		return new Response<>(401, e.getMessage());
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public Response<?> handleAuthException(AuthenticationException e){
		return new Response<>(401, e.getMessage());
	}
	
	@ExceptionHandler(UnauthenticatedException.class)
	public Response<?> unauthenticated(UnauthenticatedException e) {
		return new Response<>(401, e.getMessage());
	}
	
	@ExceptionHandler(HiyaException.class)
	public Response<?> hiyaException(HiyaException e){
		return new Response<>(e.getCode(), e.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public Response<?> globalException(Exception e){
		return new Response<>(400, e.getMessage());
	}
}
