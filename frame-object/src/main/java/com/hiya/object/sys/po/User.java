package com.hiya.object.sys.po;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * @author seven sins
 * @date 2017年12月30日 下午4:13:46
 */
public class User implements Serializable {
	private static final long serialVersionUID = 3194849030213056994L;

	private Long id;
	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空")
	private String username;
	/**
	 * 密码
	 */
	@NotBlank(message = "密码不能为空")
	private String password;
	/**
	 * 年龄
	 */
	private Integer userAge;
	/**
	 * 用户类型
	 */
	@Range(min = 1, max = 2, message = "1: 管理员, 2: 普通用户")
	@NotNull(message = "用户类型不能为空")
	private Integer userType;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
