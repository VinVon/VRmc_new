/**
 * Copyright 2016 bejson.com
 */
package com.medvision.vrmc.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 内容
 * Auto-generated: 2016-10-14 14:39:49
 */
public class HomeContent implements Serializable{
	private String id;
	private int contentType;//1视频 2.音频 3,图片
	private int type;
	private String typeName;
	private double price;
	private String name;
	private String coverPic;
	private int clicks;
	private int duration;
	private int isCollected; //1 为收藏, 0 为没有收藏

	private boolean isselect;
	private String zhouqi="";
	private int frequency;
	private int period;
	private int periodUnit;

	public HomeContent() {
	}





	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getPeriodUnit() {
		return periodUnit;
	}

	public void setPeriodUnit(int periodUnit) {
		this.periodUnit = periodUnit;
	}

	public String getZhouqi() {
		return zhouqi;
	}

	public void setZhouqi(String zhouqi) {
		this.zhouqi = zhouqi;
	}

	public boolean isselect() {
		return isselect;
	}

	public void setIsselect(boolean isselect) {
		this.isselect = isselect;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public int getClicks() {
		return clicks;
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getIsCollected() {
		return isCollected;
	}

	public void setIsCollected(int isCollected) {
		this.isCollected = isCollected;
	}

}