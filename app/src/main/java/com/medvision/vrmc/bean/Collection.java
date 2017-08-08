package com.medvision.vrmc.bean;

import java.io.Serializable;

/**
 * Created by cs on 2016/10/17.
 */

public class Collection implements Serializable {

	/**
	 * id : 8a2b4be857e696e20157fa858f8c0015
	 * creator : 00000000000000000000000000000001
	 * createdAt : 2016-10-25 14:27:20
	 * updator : 00000000000000000000000000000001
	 * updatedAt : 2017-06-23 17:34:26
	 * helpCode : 多图
	 * name : 111
	 * type : 3
	 * isFree : 0
	 * price : 0.01
	 * description : <p>11</p>
	 * remark : 11
	 * status : 1
	 * coverPic : http://static.dosnsoft.com/test%2F1477376839001.jpg
	 * hidden : 0
	 * clicks : 0
	 * duration : 0
	 * videoupdateAt : 2016-10-25 00:00:00
	 * otherdisease :
	 * isCollected : 1
	 */

	private String id;
	private String creator;
	private String createdAt;
	private String updator;
	private String updatedAt;
	private String helpCode;
	private String name;
	private int type;
	private int isFree;
	private double price;
	private String description;
	private String remark;
	private int status;
	private String coverPic;
	private int hidden;
	private int clicks;
	private int duration;
	private String videoupdateAt;
	private String otherdisease;
	private int isCollected;
	private boolean isselect;

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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getHelpCode() {
		return helpCode;
	}

	public void setHelpCode(String helpCode) {
		this.helpCode = helpCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIsFree() {
		return isFree;
	}

	public void setIsFree(int isFree) {
		this.isFree = isFree;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public int getHidden() {
		return hidden;
	}

	public void setHidden(int hidden) {
		this.hidden = hidden;
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

	public String getVideoupdateAt() {
		return videoupdateAt;
	}

	public void setVideoupdateAt(String videoupdateAt) {
		this.videoupdateAt = videoupdateAt;
	}

	public String getOtherdisease() {
		return otherdisease;
	}

	public void setOtherdisease(String otherdisease) {
		this.otherdisease = otherdisease;
	}

	public int getIsCollected() {
		return isCollected;
	}

	public void setIsCollected(int isCollected) {
		this.isCollected = isCollected;
	}
}
