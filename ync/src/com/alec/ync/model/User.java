package com.alec.ync.model;

import java.io.Serializable;

/**
 * 用户登录信息
 * @author long
 *
 */
public class User implements Serializable{
	/**
	 */
	private static final long serialVersionUID = 1L;
	private String user_name;
	private String email;
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
