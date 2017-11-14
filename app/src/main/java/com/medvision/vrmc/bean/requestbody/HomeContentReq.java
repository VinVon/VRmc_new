package com.medvision.vrmc.bean.requestbody;

/**
 * Created by cs on 2016/10/14.
 */

public class HomeContentReq extends BaseReq {
	private String diseaseId;
	private String type;
	private String therapyId;
	private int paging;
	private String keyword;
	private String sortName;
	private String sortOrder;

	public String getDiseaseId() {
		return diseaseId;
	}

	public void setDiseaseId(String diseaseId) {
		this.diseaseId = diseaseId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTherapyId() {
		return therapyId;
	}

	public void setTherapyId(String therapyId) {
		this.therapyId = therapyId;
	}

	public int getPage() {
		return paging;
	}

	public void setPage(int page) {
		this.paging = page;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		return "HomeContentReq{" +
				"diseaseId='" + diseaseId + '\'' +
				", type='" + type + '\'' +
				", therapyId='" + therapyId + '\'' +
				", paging=" + paging +
				", keyword='" + keyword + '\'' +
				", sortName='" + sortName + '\'' +
				", sortOrder='" + sortOrder + '\'' +
				'}';
	}
}
