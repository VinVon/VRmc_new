package com.medvision.vrmc.bean.requestbody;

/**
 * Created by cs on 16/9/28.
 */

public class RegisterReq {
	private String username;
	private String password;
	private String captcha;

	public RegisterReq(String username, String password, String captcha) {
		this.username = username;
		this.password = password;
		this.captcha = captcha;
	}

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

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}
