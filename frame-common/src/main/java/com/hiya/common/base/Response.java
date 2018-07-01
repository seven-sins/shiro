package com.hiya.common.base;

import java.util.List;

/**
 * 请求响应
 * 
 * @author seven sins
 * @date 2018年1月1日 下午7:39:06
 */
public class Response<T> {
	static final String SUCCESS_MESSAGE = "操作成功";
	static final String FAILURE_MESSAGE = "操作失败";

	private Integer code = ResponseStatus.OK;

	private T data;

	private List<T> list;

	private String message;
	
	public static final Response<?> SUCCESS = success();
	
	public static final Response<?> FAILURE = failure();
	
	private static Response<?> success() {
		return new Response<>(ResponseStatus.OK, SUCCESS_MESSAGE);
	}
	
	private static Response<?> failure() {
		return new Response<>(ResponseStatus.FAILTURE, FAILURE_MESSAGE);
	}
	
	public static Response<?> create(String message) {
		return new Response<>(message);
	}
	
	public static Response<?> create(Integer code, String message) {
		return new Response<>(code, message);
	}
	
	public Response() {
		this.code = ResponseStatus.OK;
		this.message = SUCCESS_MESSAGE;
	}

	public Response(T t) {
		this.code = ResponseStatus.OK;
		this.message = SUCCESS_MESSAGE;
		this.data = t;
	}
	
	public Response(List<T> t) {
		this.code = ResponseStatus.OK;
		this.message = SUCCESS_MESSAGE;
		this.list = t;
	}

	public Response(Integer code, T data) {
		this.code = code;
		this.data = data;
	}

	public Response(Integer code, List<T> list) {
		this.code = code;
		this.list = list;
	}

	public Response(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Response<T> code(Integer code){
		this.code = code;
		return this;
	}
	
	public Response<T> message(String message){
		this.message = message;
		return this;
	}
	
	public Response<T> data(T data){
		this.data = data;
		return this;
	}
	
	public Response<T> list(List<T> list){
		this.list = list;
		return this;
	}
	
}
