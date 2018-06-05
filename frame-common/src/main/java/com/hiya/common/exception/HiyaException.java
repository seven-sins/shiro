package com.hiya.common.exception;

/**
 * 自定义异常
 * @author seven sins
 * 2018年6月5日 下午7:57:40
 */
public class HiyaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * 错误代码
	 */
	private Integer code = 400;
	/**
	 * 错误信息
	 */
	private String message;
	
	public HiyaException(String message) {
		this.message = message;
	}
	
	public HiyaException(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Integer getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	
	
}
