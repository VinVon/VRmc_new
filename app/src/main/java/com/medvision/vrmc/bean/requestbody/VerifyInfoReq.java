package com.medvision.vrmc.bean.requestbody;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by cs on 2016/10/11.
 */

public class VerifyInfoReq extends BaseReq implements Serializable{
	private String realname;
	private int gender;
	private String region;//城市
	private int professionalTitleId ;//职称ID
	private int workplaceType;
	private String hospital;
	private String department;//科室
	private String position; //职位
	@SerializedName("headPictureUrl")
	private String headPictureId; //个人照片ID
	private String introduction;
	private String expertise;  //个人特长
	@SerializedName("psychologicalConsultantImageUrl")
	private String psychologicalConsultantImageId; //心理咨询师证图片ID
	@SerializedName("employeeImageUrl")
	private String employeeImageId; //工作证图片ID
	@SerializedName("doctorProfessionImageUrl")
	private String doctorProfessionImageId; //医师职业证图片ID
	@SerializedName("professionalQualificationImageUrl")
	private String professionalQualificationImageId; //职称资格证图片ID

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public int getSex() {
		return gender;
	}

	public void setSex(int sex) {
		this.gender = sex;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getProfessionalTitleId() {
		return professionalTitleId;
	}

	public void setProfessionalTitleId(int professionalTitleId) {
		this.professionalTitleId = professionalTitleId;
	}

	public int getWorkplaceType() {
		return workplaceType;
	}

	public void setWorkplaceType(int workplaceType) {
		this.workplaceType = workplaceType;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getHeadPictureId() {
		return headPictureId;
	}

	public void setHeadPictureId(String headPictureId) {
		this.headPictureId = headPictureId;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public String getPsychologicalConsultantImageId() {
		return psychologicalConsultantImageId;
	}

	public void setPsychologicalConsultantImageId(String psychologicalConsultantImageId) {
		this.psychologicalConsultantImageId = psychologicalConsultantImageId;
	}

	public String getEmployeeImageId() {
		return employeeImageId;
	}

	public void setEmployeeImageId(String employeeImageId) {
		this.employeeImageId = employeeImageId;
	}

	public String getDoctorProfessionImageId() {
		return doctorProfessionImageId;
	}

	public void setDoctorProfessionImageId(String doctorProfessionImageId) {
		this.doctorProfessionImageId = doctorProfessionImageId;
	}

	public String getProfessionalQualificationImageId() {
		return professionalQualificationImageId;
	}

	public void setProfessionalQualificationImageId(String professionalQualificationImageId) {
		this.professionalQualificationImageId = professionalQualificationImageId;
	}
}
