package com.hiya.common.base;

import java.util.List;

/**
 * 请求的响应
 * 
 * @author seven sins
 * @date 2018年1月1日 下午7:39:06
 */
public class Response<T> {

	static final String SUCCESS = "操作成功";
	static final String FAILURE = "操作失败";

	private Integer code = 200;

	private T data;

	private List<T> list;

	private String message;
	
	public Response() {
	}

	public Response(T t) {
		this.data = t;
	}
	
	public Response(List<T> t) {
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
	
	public static Response<?> success() {
		Response<?> response = new Response<>();
		response.code = 200;
		response.message = SUCCESS;
		return response;
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
	
}
