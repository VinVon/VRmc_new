package com.medvision.vrmc.bean;

import java.io.Serializable;

/**
 * Created by raytine on 2017/7/17.
 */

public class MyPatientDetil implements Serializable{

    /**
     * token : null
     * paging : 0
     * sortName : null
     * sortOrder : desc
     * roomId : null
     * clinichistoryNo : 568
     * name : 开玩笑
     * birthday : 2017-07-17 00:00:00
     * sex : 1
     * phone :
     * maritalStatus : 2
     * educationDegree : 6
     * medicalInsuranceCardNo :
     * diseaseId : xj_home_d00000000000000000006000
     * disease : 强迫症
     * remark : 摩托
     * canModify : 1
     * patientId : null
     * ts : 2017-07-17 13:21:53
     */

    private Object token;
    private int paging;
    private Object sortName;
    private String sortOrder;
    private Object roomId;
    private String clinichistoryNo;
    private String name;
    private String birthday;
    private int sex;
    private String phone;
    private int maritalStatus;
    private int educationDegree;
    private String medicalInsuranceCardNo;
    private String diseaseId;
    private String disease;
    private String remark;
    private int canModify;
    private Object patientId;
    private String ts;
    private int age;

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public int getPaging() {
        return paging;
    }

    public void setPaging(int paging) {
        this.paging = paging;
    }

    public Object getSortName() {
        return sortName;
    }

    public void setSortName(Object sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Object getRoomId() {
        return roomId;
    }

    public void setRoomId(Object roomId) {
        this.roomId = roomId;
    }

    public String getClinichistoryNo() {
        return clinichistoryNo;
    }

    public void setClinichistoryNo(String clinichistoryNo) {
        this.clinichistoryNo = clinichistoryNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(int maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public int getEducationDegree() {
        return educationDegree;
    }

    public void setEducationDegree(int educationDegree) {
        this.educationDegree = educationDegree;
    }

    public String getMedicalInsuranceCardNo() {
        return medicalInsuranceCardNo;
    }

    public void setMedicalInsuranceCardNo(String medicalInsuranceCardNo) {
        this.medicalInsuranceCardNo = medicalInsuranceCardNo;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCanModify() {
        return canModify;
    }

    public void setCanModify(int canModify) {
        this.canModify = canModify;
    }

    public Object getPatientId() {
        return patientId;
    }

    public void setPatientId(Object patientId) {
        this.patientId = patientId;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
