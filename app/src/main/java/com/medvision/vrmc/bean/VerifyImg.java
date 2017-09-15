package com.medvision.vrmc.bean;

import java.io.Serializable;

/**
 * Created by cs on 16/9/30.
 */

public class VerifyImg implements Serializable{
	private String imageUrl;

	public VerifyImg(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageId() {
		return imageUrl;
	}
	public void setImageId(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
