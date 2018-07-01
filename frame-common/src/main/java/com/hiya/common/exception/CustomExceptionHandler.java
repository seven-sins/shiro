package com.hiya.common.exception;

import java.util.List;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hiya.common.base.Response;

/**
 * 全局异常处理
 * @author seven sins
 * @date 2018年6月2日 下午3:52:36
 */
@RestControllerAdvice
public class CustomExceptionHandler {

	/**
	 * 捕获shiro异常
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ShiroException.class)
	public Response<?> handle401(ShiroException e) {
		return Response.create(401, e.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AuthenticationException.class)
	public Response<?> handleAuthException(AuthenticationException e){
		return Response.create(401, e.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UnauthenticatedException.class)
	public Response<?> unauthenticated(UnauthenticatedException e) {
		return Response.create(401, e.getMessage());
	}
	
	/**
	 * 自定义异常捕获
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HiyaException.class)
	public Response<?> hiyaException(HiyaException e){
		return Response.create(e.getCode(), e.getMessage());
	}
	
	/**
	 * bean验证异常捕获
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Response<?> argumentException(MethodArgumentNotValidException e){
		BindingResult result = e.getBindingResult();
		List<ObjectError> error = result.getAllErrors();
		StringBuilder sb = new StringBuilder();
		for(ObjectError item: error){
			sb.append(item.getDefaultMessage());
		}
		
		return Response.create(400, sb.toString());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	public Response<?> globalException(Exception e){
		return Response.create(400, e.getMessage());
	}
}
