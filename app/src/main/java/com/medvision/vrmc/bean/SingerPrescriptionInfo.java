package com.medvision.vrmc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by raytine on 2017/5/15.
 */

public class SingerPrescriptionInfo implements Serializable{

    /**
     * creator : 8a2b4be85c1f33e1015c1ff9e15b0017
     * disease : vb
     * hidden : 0
     * patientId : 8a2b4be85c0fc553015c0feb17c50010
     * suggestion : ff
     * source : 1
     * doctor : {"id":"8a2b4be85c1f33e1015c1ff9e15b0017","creator":null,"createdAt":"2017-05-19 17:11:29","updator":"8a2b4be85c1f33e1015c1ff9e15b0017","updatedAt":"2017-06-23 17:51:28","username":"15771013663","realname":"向文圣","password":"$2a$10$CTQJzoZg/wo3ln3pB59DbOde/U99w/A/M56sxcFBS4yJdy6vI.BMm","email":null,"mobile":"15771013663","enabled":1,"organId":null,"gender":2,"position":null,"avatar":null,"weixinid":null,"cpOrganId":null,"accountNonLocked":1,"attempts":3,"hidden":0,"regAt":"2017-05-19 17:11:29","lastLoginAt":"2017-06-23 17:50:53"}
     * createdAt : 2017-06-23 17:51:28
     * total : 0.01
     * doctorName : 向文圣
     * doctorId : 8a2b4be85c1f33e1015c1ff9e15b0017
     * hospitalId : null
     * contents : [{"id":"8a2b4be857d718e70157db749f56000b","helpCode":"picture1","name":"测试图片集1","type":3,"isFree":0,"price":0.01,"description":"<p>测试图片集1<\/p>","remark":"测试图片集1","status":2,"coverPic":"http://static.dosnsoft.com/test%2F1476855635742.png","hidden":0,"clicks":0,"duration":0,"frequency":1,"period":1,"periodUnit":0,"times":1,"useTimes":0}]
     * patient : {"token":null,"userId":"8a2b4be85c0fc553015c0feb17c50010","realname":"15101693304","email":null,"mobile":"15101693304","medicareType":null,"medicareCardNumber":null,"idNumber":null,"age":null,"address":null,"emergencyContact":null,"emergencyContactPhone":null,"gender":null}
     * admissionNumber : null
     * updator : 8a2b4be85c1f33e1015c1ff9e15b0017
     * outpatientNumber : null
     * id : 8a2b4be85cd34c39015cd45d0f6b0017
     * payStatus : 1
     * billno : X17062317510079
     * status : 1
     * updatedAt : 2017-06-23 17:51:28
     */

    private String creator;
    private String disease;
    private int hidden;
    private String patientId;
    private String suggestion;
    private int source;
    private DoctorBean doctor;
    private String createdAt;
    private double total;
    private String doctorName;
    private String doctorId;
    private Object hospitalId;
    private PatientBean patient;
    private Object admissionNumber;
    private String updator;
    private Object outpatientNumber;
    private String id;
    private int payStatus;
    private String billno;
    private int status;
    private String updatedAt;
    private List<ContentsBean> contents;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public int getHidden() {
        return hidden;
    }

    public void setHidden(int hidden) {
        this.hidden = hidden;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public DoctorBean getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorBean doctor) {
        this.doctor = doctor;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Object getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Object hospitalId) {
        this.hospitalId = hospitalId;
    }

    public PatientBean getPatient() {
        return patient;
    }

    public void setPatient(PatientBean patient) {
        this.patient = patient;
    }

    public Object getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(Object admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Object getOutpatientNumber() {
        return outpatientNumber;
    }

    public void setOutpatientNumber(Object outpatientNumber) {
        this.outpatientNumber = outpatientNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ContentsBean> getContents() {
        return contents;
    }

    public void setContents(List<ContentsBean> contents) {
        this.contents = contents;
    }

    public static class DoctorBean {
        /**
         * id : 8a2b4be85c1f33e1015c1ff9e15b0017
         * creator : null
         * createdAt : 2017-05-19 17:11:29
         * updator : 8a2b4be85c1f33e1015c1ff9e15b0017
         * updatedAt : 2017-06-23 17:51:28
         * username : 15771013663
         * realname : 向文圣
         * password : $2a$10$CTQJzoZg/wo3ln3pB59DbOde/U99w/A/M56sxcFBS4yJdy6vI.BMm
         * email : null
         * mobile : 15771013663
         * enabled : 1
         * organId : null
         * gender : 2
         * position : null
         * avatar : null
         * weixinid : null
         * cpOrganId : null
         * accountNonLocked : 1
         * attempts : 3
         * hidden : 0
         * regAt : 2017-05-19 17:11:29
         * lastLoginAt : 2017-06-23 17:50:53
         */

        private String id;
        private Object creator;
        private String createdAt;
        private String updator;
        private String updatedAt;
        private String username;
        private String realname;
        private String password;
        private Object email;
        private String mobile;
        private int enabled;
        private Object organId;
        private int gender;
        private Object position;
        private Object avatar;
        private Object weixinid;
        private Object cpOrganId;
        private int accountNonLocked;
        private int attempts;
        private int hidden;
        private String regAt;
        private String lastLoginAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getCreator() {
            return creator;
        }

        public void setCreator(Object creator) {
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getEnabled() {
            return enabled;
        }

        public void setEnabled(int enabled) {
            this.enabled = enabled;
        }

        public Object getOrganId() {
            return organId;
        }

        public void setOrganId(Object organId) {
            this.organId = organId;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public Object getPosition() {
            return position;
        }

        public void setPosition(Object position) {
            this.position = position;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public Object getWeixinid() {
            return weixinid;
        }

        public void setWeixinid(Object weixinid) {
            this.weixinid = weixinid;
        }

        public Object getCpOrganId() {
            return cpOrganId;
        }

        public void setCpOrganId(Object cpOrganId) {
            this.cpOrganId = cpOrganId;
        }

        public int getAccountNonLocked() {
            return accountNonLocked;
        }

        public void setAccountNonLocked(int accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
        }

        public int getAttempts() {
            return attempts;
        }

        public void setAttempts(int attempts) {
            this.attempts = attempts;
        }

        public int getHidden() {
            return hidden;
        }

        public void setHidden(int hidden) {
            this.hidden = hidden;
        }

        public String getRegAt() {
            return regAt;
        }

        public void setRegAt(String regAt) {
            this.regAt = regAt;
        }

        public String getLastLoginAt() {
            return lastLoginAt;
        }

        public void setLastLoginAt(String lastLoginAt) {
            this.lastLoginAt = lastLoginAt;
        }
    }

    public static class PatientBean {
        /**
         * token : null
         * userId : 8a2b4be85c0fc553015c0feb17c50010
         * realname : 15101693304
         * email : null
         * mobile : 15101693304
         * medicareType : null
         * medicareCardNumber : null
         * idNumber : null
         * age : null
         * address : null
         * emergencyContact : null
         * emergencyContactPhone : null
         * gender : null
         */

        private Object token;
        private String userId;
        private String realname;
        private Object email;
        private String mobile;
        private Object medicareType;
        private Object medicareCardNumber;
        private Object idNumber;
        private Object age;
        private Object address;
        private Object emergencyContact;
        private Object emergencyContactPhone;
        private Object gender;

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getMedicareType() {
            return medicareType;
        }

        public void setMedicareType(Object medicareType) {
            this.medicareType = medicareType;
        }

        public Object getMedicareCardNumber() {
            return medicareCardNumber;
        }

        public void setMedicareCardNumber(Object medicareCardNumber) {
            this.medicareCardNumber = medicareCardNumber;
        }

        public Object getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(Object idNumber) {
            this.idNumber = idNumber;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getEmergencyContact() {
            return emergencyContact;
        }

        public void setEmergencyContact(Object emergencyContact) {
            this.emergencyContact = emergencyContact;
        }

        public Object getEmergencyContactPhone() {
            return emergencyContactPhone;
        }

        public void setEmergencyContactPhone(Object emergencyContactPhone) {
            this.emergencyContactPhone = emergencyContactPhone;
        }

        public Object getGender() {
            return gender;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }
    }

    public static class ContentsBean {
        /**
         * id : 8a2b4be857d718e70157db749f56000b
         * helpCode : picture1
         * name : 测试图片集1
         * type : 3
         * isFree : 0
         * price : 0.01
         * description : <p>测试图片集1</p>
         * remark : 测试图片集1
         * status : 2
         * coverPic : http://static.dosnsoft.com/test%2F1476855635742.png
         * hidden : 0
         * clicks : 0
         * duration : 0
         * frequency : 1
         * period : 1
         * periodUnit : 0
         * times : 1
         * useTimes : 0
         */

        private String id;
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
        private int frequency;
        private int period;
        private int periodUnit;
        private int times;
        private int useTimes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public int getUseTimes() {
            return useTimes;
        }

        public void setUseTimes(int useTimes) {
            this.useTimes = useTimes;
        }
    }
}

