package vr.promulgator.com.newvrmc.bean;

import java.io.Serializable;

/**
 * Created by raytine on 2017/2/9.
 */

public class LoginInfo implements Serializable{


    /**
     * success : true
     * code : 0
     * message : 登录成功
     * data : {"userId":"8a2b4be85b6a59c0015b6a5af7cb0002","username":"appcontrol","realname":"APP控制端用户","token":"8a2b4be85b9f23c9015b9f5aab4b0001","vrRoomId":"8a2b4be859b48ead0159b49136b00002","vrRoomName":"浙一VR室"}
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
         * userId : 8a2b4be85b6a59c0015b6a5af7cb0002
         * username : appcontrol
         * realname : APP控制端用户
         * token : 8a2b4be85b9f23c9015b9f5aab4b0001
         * vrRoomId : 8a2b4be859b48ead0159b49136b00002
         * vrRoomName : 浙一VR室
         */

        private String userId;
        private String username;
        private String realname;
        private String token;
        private String vrRoomId;
        private String vrRoomName;

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

        public String getVrRoomId() {
            return vrRoomId;
        }

        public void setVrRoomId(String vrRoomId) {
            this.vrRoomId = vrRoomId;
        }

        public String getVrRoomName() {
            return vrRoomName;
        }

        public void setVrRoomName(String vrRoomName) {
            this.vrRoomName = vrRoomName;
        }
    }
}
