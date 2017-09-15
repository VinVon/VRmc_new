package com.medvision.vrmc.bean.requestbody;

/**
 * Created by raytine on 2017/7/17.
 */

public class ModifPatientreq extends BaseReq {
    private String remark;
    private String clinichistoryNo;
    private String name;
    private String birthday;
    private int sex;
    private String phone;
    private int maritalStatus;
    private int educationDegree;
    private String medicalInsuranceCardNo;
    private String diseaseId;
    private String patientId;
    private String ts;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}
