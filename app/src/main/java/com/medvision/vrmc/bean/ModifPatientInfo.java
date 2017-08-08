package com.medvision.vrmc.bean;

/**
 * 修改患者信息后返回的数据
 * Created by raytine on 2017/7/19.
 */

public class ModifPatientInfo {

    /**
     * success : true
     * code : 0
     * message : 成功
     * data : {"ts":"dasdad"}
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
         * ts : dasdad
         */

        private String ts;

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }
    }
}
