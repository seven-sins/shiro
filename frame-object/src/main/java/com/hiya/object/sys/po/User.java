package com.hiya.object.sys.po;

import java.io.Serializable;

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
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 年龄
	 */
	private Integer age;

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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
