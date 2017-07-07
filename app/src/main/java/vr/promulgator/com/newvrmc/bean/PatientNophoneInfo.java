package vr.promulgator.com.newvrmc.bean;

/**
 * 病案号返回的患者
 * Created by raytine on 2017/7/4.
 */

public class PatientNophoneInfo {

    /**
     * success : true
     * code : 0
     * message : 根据条件查询患者信息成功
     * data : {"token":null,"userId":"8a2b4be85cbe5f7b015cbea32d2d001e","realname":"测试患者001","email":null,"mobile":"15657153212","recordno":"blh001","medicareType":0,"medicareCardNumber":"yb001","idNumber":"33068219931228801","age":0,"address":"杭州市","emergencyContact":null,"emergencyContactPhone":"15657153212","gender":1}
     */

    private boolean success;
    private int code;
    private String message;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : null
         * userId : 8a2b4be85cbe5f7b015cbea32d2d001e
         * realname : 测试患者001
         * email : null
         * mobile : 15657153212
         * recordno : blh001
         * medicareType : 0
         * medicareCardNumber : yb001
         * idNumber : 33068219931228801
         * age : 0
         * address : 杭州市
         * emergencyContact : null
         * emergencyContactPhone : 15657153212
         * gender : 1
         */

        private Object token;
        private String userId;
        private String realname;
        private Object email;
        private String mobile;
        private String recordno;
        private int medicareType;
        private String medicareCardNumber;
        private String idNumber;
        private int age;
        private String address;
        private Object emergencyContact;
        private String emergencyContactPhone;
        private int gender;

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

        public String getRecordno() {
            return recordno;
        }

        public void setRecordno(String recordno) {
            this.recordno = recordno;
        }

        public int getMedicareType() {
            return medicareType;
        }

        public void setMedicareType(int medicareType) {
            this.medicareType = medicareType;
        }

        public String getMedicareCardNumber() {
            return medicareCardNumber;
        }

        public void setMedicareCardNumber(String medicareCardNumber) {
            this.medicareCardNumber = medicareCardNumber;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getEmergencyContact() {
            return emergencyContact;
        }

        public void setEmergencyContact(Object emergencyContact) {
            this.emergencyContact = emergencyContact;
        }

        public String getEmergencyContactPhone() {
            return emergencyContactPhone;
        }

        public void setEmergencyContactPhone(String emergencyContactPhone) {
            this.emergencyContactPhone = emergencyContactPhone;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }
    }
}
