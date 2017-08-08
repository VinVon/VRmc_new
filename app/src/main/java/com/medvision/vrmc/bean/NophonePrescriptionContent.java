package com.medvision.vrmc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 病案号处方内容列表
 * Created by raytine on 2017/7/4.
 */

public class NophonePrescriptionContent implements Serializable{

    /**
     * success : true
     * code : 0
     * message : 获取患者的内容列表成功
     * data : [{"unitPrice":0,"content_name":"篮球","prescriptionId":null,"period":null,"creator":null,"contentId":"8a2b4be85cbe5f7b015cbeab5d150027","periodUnit":null,"frequency":null,"content_helpCode":"打算","createdAt":null,"times":0,"content_type":6,"price":0,"lastUseAt":null,"updator":null,"id":null,"content_coverPic":"http://static.med-vision.cn/test%2F1497847520508.png","status":1,"useTimes":0,"updatedAt":null},{"unitPrice":0,"content_name":"111","prescriptionId":null,"period":null,"creator":null,"contentId":"8a2b4be857e696e20157f9ed14ed000c","periodUnit":null,"frequency":null,"content_helpCode":"111","createdAt":null,"times":0,"content_type":2,"price":0,"lastUseAt":null,"updator":null,"id":null,"content_coverPic":"http://static.dosnsoft.com/test%2F1477366845954.jpg","status":1,"useTimes":0,"updatedAt":null},{"unitPrice":0,"content_name":"测试视频254554","prescriptionId":null,"period":null,"creator":null,"contentId":"8a2b4be857e696e20157f99ab81f0006","periodUnit":null,"frequency":null,"content_helpCode":"video21112312","createdAt":null,"times":0,"content_type":1,"price":0,"lastUseAt":null,"updator":null,"id":null,"content_coverPic":"http://static.dosnsoft.com/test%2F1477361448881.png","status":1,"useTimes":0,"updatedAt":null},{"unitPrice":0,"content_name":"测试视频1","prescriptionId":null,"period":null,"creator":null,"contentId":"8a2b4be857e696e20157f999a4cf0002","periodUnit":null,"frequency":null,"content_helpCode":"video1","createdAt":null,"times":0,"content_type":1,"price":0,"lastUseAt":null,"updator":null,"id":null,"content_coverPic":"http://static.dosnsoft.com/test%2F1477361378308.jpg","status":1,"useTimes":0,"updatedAt":null},{"unitPrice":0,"content_name":"测试音频2","prescriptionId":null,"period":null,"creator":null,"contentId":"8a2b4be857d718e70157db72c2a70009","periodUnit":null,"frequency":null,"content_helpCode":"audio2","createdAt":null,"times":0,"content_type":2,"price":0,"lastUseAt":null,"updator":null,"id":null,"content_coverPic":"http://static.dosnsoft.com/test%2F1476855513736.png","status":1,"useTimes":0,"updatedAt":null},{"unitPrice":0,"content_name":"测试音频1","prescriptionId":null,"period":null,"creator":null,"contentId":"8a2b4be857d718e70157db71ea0b0007","periodUnit":null,"frequency":null,"content_helpCode":"audio1","createdAt":null,"times":0,"content_type":2,"price":0,"lastUseAt":null,"updator":null,"id":null,"content_coverPic":"http://static.dosnsoft.com/test%2F1476855457995.png","status":1,"useTimes":0,"updatedAt":null}]
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

    public static class DataBean implements Serializable{
        /**
         * unitPrice : 0
         * content_name : 篮球
         * prescriptionId : null
         * period : null
         * creator : null
         * contentId : 8a2b4be85cbe5f7b015cbeab5d150027
         * periodUnit : null
         * frequency : null
         * content_helpCode : 打算
         * createdAt : null
         * times : 0
         * content_type : 6
         * price : 0
         * lastUseAt : null
         * updator : null
         * id : null
         * content_coverPic : http://static.med-vision.cn/test%2F1497847520508.png
         * status : 1
         * useTimes : 0
         * updatedAt : null
         */

        private int unitPrice;
        private String content_name;
        private Object prescriptionId;
        private Object period;
        private Object creator;
        private String contentId;
        private Object periodUnit;
        private Object frequency;
        private String content_helpCode;
        private Object createdAt;
        private int times;
        private int content_type;
        private int price;
        private Object lastUseAt;
        private Object updator;
        private Object id;
        private String content_coverPic;
        private int status;
        private int useTimes;
        private Object updatedAt;

        public int getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(int unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getContent_name() {
            return content_name;
        }

        public void setContent_name(String content_name) {
            this.content_name = content_name;
        }

        public Object getPrescriptionId() {
            return prescriptionId;
        }

        public void setPrescriptionId(Object prescriptionId) {
            this.prescriptionId = prescriptionId;
        }

        public Object getPeriod() {
            return period;
        }

        public void setPeriod(Object period) {
            this.period = period;
        }

        public Object getCreator() {
            return creator;
        }

        public void setCreator(Object creator) {
            this.creator = creator;
        }

        public String getContentId() {
            return contentId;
        }

        public void setContentId(String contentId) {
            this.contentId = contentId;
        }

        public Object getPeriodUnit() {
            return periodUnit;
        }

        public void setPeriodUnit(Object periodUnit) {
            this.periodUnit = periodUnit;
        }

        public Object getFrequency() {
            return frequency;
        }

        public void setFrequency(Object frequency) {
            this.frequency = frequency;
        }

        public String getContent_helpCode() {
            return content_helpCode;
        }

        public void setContent_helpCode(String content_helpCode) {
            this.content_helpCode = content_helpCode;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public int getContent_type() {
            return content_type;
        }

        public void setContent_type(int content_type) {
            this.content_type = content_type;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public Object getLastUseAt() {
            return lastUseAt;
        }

        public void setLastUseAt(Object lastUseAt) {
            this.lastUseAt = lastUseAt;
        }

        public Object getUpdator() {
            return updator;
        }

        public void setUpdator(Object updator) {
            this.updator = updator;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getContent_coverPic() {
            return content_coverPic;
        }

        public void setContent_coverPic(String content_coverPic) {
            this.content_coverPic = content_coverPic;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUseTimes() {
            return useTimes;
        }

        public void setUseTimes(int useTimes) {
            this.useTimes = useTimes;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
