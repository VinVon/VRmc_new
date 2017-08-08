package com.medvision.vrmc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by raytine on 2017/4/14.
 */

public class UserList implements Serializable{


    /**
     * success : true
     * code : 0
     * message : 获取Vr室管理员成功
     * data : [{"creator":"00000000000000000000000000000001","user_username":"apptest","remark":"","type":3,"userId":"8a2b4be85b556f26015b564d63f20006","createdAt":"2017-04-10 13:19:24","user_realname":"apptest","updator":"00000000000000000000000000000001","id":"8a2b4be85b556f26015b564d64020008","vrRoomId":"8a2b4be859b48ead0159b49136b00002","user":{"id":"8a2b4be85b556f26015b564d63f20006","creator":"00000000000000000000000000000001","createdAt":"2017-04-10 13:19:24","updator":"00000000000000000000000000000001","updatedAt":"2017-04-14 11:11:15","username":"apptest","realname":"apptest","password":"$2a$10$oE/w.lqckwkPJemg2cIj7en9IwBKtdAW1QavnQr7BQ7PhAICJ7a8C","email":null,"mobile":"apptest","enabled":1,"organId":null,"gender":null,"position":null,"avatar":null,"weixinid":null,"cpOrganId":null,"accountNonLocked":0,"attempts":33,"hidden":0,"regAt":null,"lastLoginAt":"2017-04-14 11:11:15"},"vrRoom":{"id":"8a2b4be859b48ead0159b49136b00002","creator":"00000000000000000000000000000001","createdAt":"2017-01-19 10:32:13","updator":"00000000000000000000000000000001","updatedAt":"2017-01-19 10:32:13","name":"浙一VR室","region":"330106","address":"西湖边","status":2,"scheduleInfo":null,"remark":"","hidden":0},"updatedAt":"2017-04-14 10:46:20"}]
     */

    private boolean success;
    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * creator : 00000000000000000000000000000001
         * user_username : apptest
         * remark :
         * type : 3
         * userId : 8a2b4be85b556f26015b564d63f20006
         * createdAt : 2017-04-10 13:19:24
         * user_realname : apptest
         * updator : 00000000000000000000000000000001
         * id : 8a2b4be85b556f26015b564d64020008
         * vrRoomId : 8a2b4be859b48ead0159b49136b00002
         * user : {"id":"8a2b4be85b556f26015b564d63f20006","creator":"00000000000000000000000000000001","createdAt":"2017-04-10 13:19:24","updator":"00000000000000000000000000000001","updatedAt":"2017-04-14 11:11:15","username":"apptest","realname":"apptest","password":"$2a$10$oE/w.lqckwkPJemg2cIj7en9IwBKtdAW1QavnQr7BQ7PhAICJ7a8C","email":null,"mobile":"apptest","enabled":1,"organId":null,"gender":null,"position":null,"avatar":null,"weixinid":null,"cpOrganId":null,"accountNonLocked":0,"attempts":33,"hidden":0,"regAt":null,"lastLoginAt":"2017-04-14 11:11:15"}
         * vrRoom : {"id":"8a2b4be859b48ead0159b49136b00002","creator":"00000000000000000000000000000001","createdAt":"2017-01-19 10:32:13","updator":"00000000000000000000000000000001","updatedAt":"2017-01-19 10:32:13","name":"浙一VR室","region":"330106","address":"西湖边","status":2,"scheduleInfo":null,"remark":"","hidden":0}
         * updatedAt : 2017-04-14 10:46:20
         */

        private String creator;
        private String user_username;
        private String remark;
        private int type;
        private String userId;
        private String createdAt;
        private String user_realname;
        private String updator;
        private String id;
        private String vrRoomId;
        private UserBean user;
        private VrRoomBean vrRoom;
        private String updatedAt;

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getUser_username() {
            return user_username;
        }

        public void setUser_username(String user_username) {
            this.user_username = user_username;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUser_realname() {
            return user_realname;
        }

        public void setUser_realname(String user_realname) {
            this.user_realname = user_realname;
        }

        public String getUpdator() {
            return updator;
        }

        public void setUpdator(String updator) {
            this.updator = updator;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVrRoomId() {
            return vrRoomId;
        }

        public void setVrRoomId(String vrRoomId) {
            this.vrRoomId = vrRoomId;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public VrRoomBean getVrRoom() {
            return vrRoom;
        }

        public void setVrRoom(VrRoomBean vrRoom) {
            this.vrRoom = vrRoom;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public static class UserBean implements Serializable{
            /**
             * id : 8a2b4be85b556f26015b564d63f20006
             * creator : 00000000000000000000000000000001
             * createdAt : 2017-04-10 13:19:24
             * updator : 00000000000000000000000000000001
             * updatedAt : 2017-04-14 11:11:15
             * username : apptest
             * realname : apptest
             * password : $2a$10$oE/w.lqckwkPJemg2cIj7en9IwBKtdAW1QavnQr7BQ7PhAICJ7a8C
             * email : null
             * mobile : apptest
             * enabled : 1
             * organId : null
             * gender : null
             * position : null
             * avatar : null
             * weixinid : null
             * cpOrganId : null
             * accountNonLocked : 0
             * attempts : 33
             * hidden : 0
             * regAt : null
             * lastLoginAt : 2017-04-14 11:11:15
             */

            private String id;
            private String creator;
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
            private Object gender;
            private Object position;
            private Object avatar;
            private Object weixinid;
            private Object cpOrganId;
            private int accountNonLocked;
            private int attempts;
            private int hidden;
            private Object regAt;
            private String lastLoginAt;

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

            public Object getGender() {
                return gender;
            }

            public void setGender(Object gender) {
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

            public Object getRegAt() {
                return regAt;
            }

            public void setRegAt(Object regAt) {
                this.regAt = regAt;
            }

            public String getLastLoginAt() {
                return lastLoginAt;
            }

            public void setLastLoginAt(String lastLoginAt) {
                this.lastLoginAt = lastLoginAt;
            }
        }

        public static class VrRoomBean implements Serializable{
            /**
             * id : 8a2b4be859b48ead0159b49136b00002
             * creator : 00000000000000000000000000000001
             * createdAt : 2017-01-19 10:32:13
             * updator : 00000000000000000000000000000001
             * updatedAt : 2017-01-19 10:32:13
             * name : 浙一VR室
             * region : 330106
             * address : 西湖边
             * status : 2
             * scheduleInfo : null
             * remark :
             * hidden : 0
             */

            private String id;
            private String creator;
            private String createdAt;
            private String updator;
            private String updatedAt;
            private String name;
            private String region;
            private String address;
            private int status;
            private Object scheduleInfo;
            private String remark;
            private int hidden;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getScheduleInfo() {
                return scheduleInfo;
            }

            public void setScheduleInfo(Object scheduleInfo) {
                this.scheduleInfo = scheduleInfo;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getHidden() {
                return hidden;
            }

            public void setHidden(int hidden) {
                this.hidden = hidden;
            }
        }
    }
}
