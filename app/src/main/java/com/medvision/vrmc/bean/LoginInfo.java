package com.medvision.vrmc.bean;

import java.io.Serializable;

/**
 * Created by raytine on 2017/2/9.
 */

public class LoginInfo implements Serializable{


    /**
     * success : true
     * code : 0
     * message : 登录成功
     * data : {"password":"123456","encryptPw":"$2a$10$CTQJzoZg/wo3ln3pB59DbOde/U99w/A/M56sxcFBS4yJdy6vI.BMm","hospital":"医院测试2","userId":"8a2b4be85c1f33e1015c1ff9e15b0017","username":"15771013663","realname":"向文圣","token":"8a2b4be85d45ac77015d45b0c9080007"}
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

    public static class DataBean implements Serializable{
        /**
         * password : 123456
         * encryptPw : $2a$10$CTQJzoZg/wo3ln3pB59DbOde/U99w/A/M56sxcFBS4yJdy6vI.BMm
         * hospital : 医院测试2
         * userId : 8a2b4be85c1f33e1015c1ff9e15b0017
         * username : 15771013663
         * realname : 向文圣
         * token : 8a2b4be85d45ac77015d45b0c9080007
         */

        private String hospital;
        private String userId;
        private String username;
        private String realname;
        private String token;

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
