package com.medvision.vrmc.bean.requestbody;

/**
 * Created by cs on 16/9/28.
 */

public class SmsReq {
	private String mobile;
	private int type;
	public SmsReq() {
	}

	public SmsReq(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}

	public SmsReq(String mobile, int type) {
		this.mobile = mobile;
		this.type = type;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
