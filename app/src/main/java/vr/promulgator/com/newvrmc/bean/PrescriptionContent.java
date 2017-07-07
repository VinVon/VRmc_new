package vr.promulgator.com.newvrmc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 处方内容列表
 * Created by 123 on 2017/2/4.
 */

public class PrescriptionContent implements Serializable{


    /**
     * success : true
     * code : 0
     * message : 获取患者处方内容列表成功
     * data : [{"unitPrice":400,"content_name":"测试视频2","prescriptionId":"8a2b4be85b556f26015b564ec3a1000a","period":2,"creator":"8a2b4be859b48ead0159b491616a0003","contentId":"8a2b4be857e696e20157f99ab81f0006","periodUnit":1,"frequency":4,"content_helpCode":"video2","createdAt":"2017-04-10 13:20:54","times":8,"content_type":1,"price":3200,"lastUseAt":"2017-04-18 10:52:30","updator":"8a2b4be859b48ead0159b491616a0003","id":"8a2b4be85b556f26015b564ec3d8000b","content_coverPic":"http://static.dosnsoft.com/test%2F1477361448881.png","status":null,"useTimes":5,"updatedAt":"2017-04-18 10:52:30"},{"unitPrice":0,"content_name":"测试视频1","prescriptionId":"8a2b4be85b556f26015b564ec3a1000a","period":2,"creator":"8a2b4be859b48ead0159b491616a0003","contentId":"8a2b4be857e696e20157f999a4cf0002","periodUnit":1,"frequency":4,"content_helpCode":"video1","createdAt":"2017-04-10 13:20:54","times":8,"content_type":1,"price":0,"lastUseAt":"2017-04-17 11:06:24","updator":"8a2b4be859b48ead0159b491616a0003","id":"8a2b4be85b556f26015b564ec3da000c","content_coverPic":"http://static.dosnsoft.com/test%2F1477361378308.jpg","status":null,"useTimes":4,"updatedAt":"2017-04-17 11:06:24"},{"unitPrice":15,"content_name":"测试音频2","prescriptionId":"8a2b4be85b556f26015b564ec3a1000a","period":2,"creator":"8a2b4be859b48ead0159b491616a0003","contentId":"8a2b4be857d718e70157db72c2a70009","periodUnit":1,"frequency":4,"content_helpCode":"audio2","createdAt":"2017-04-10 13:20:54","times":8,"content_type":2,"price":120,"lastUseAt":"2017-04-11 16:09:50","updator":"8a2b4be859b48ead0159b491616a0003","id":"8a2b4be85b556f26015b564ec3da000d","content_coverPic":"http://static.dosnsoft.com/test%2F1476855513736.png","status":null,"useTimes":2,"updatedAt":"2017-04-11 16:09:50"},{"unitPrice":0,"content_name":"测试音频1","prescriptionId":"8a2b4be85b556f26015b564ec3a1000a","period":2,"creator":"8a2b4be859b48ead0159b491616a0003","contentId":"8a2b4be857d718e70157db71ea0b0007","periodUnit":1,"frequency":4,"content_helpCode":"audio1","createdAt":"2017-04-10 13:20:54","times":8,"content_type":2,"price":0,"lastUseAt":"2017-04-11 15:42:30","updator":"8a2b4be859b48ead0159b491616a0003","id":"8a2b4be85b556f26015b564ec3da000e","content_coverPic":"http://static.dosnsoft.com/test%2F1476855457995.png","status":null,"useTimes":1,"updatedAt":"2017-04-11 15:42:30"}]
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
         * unitPrice : 400
         * content_name : 测试视频2
         * prescriptionId : 8a2b4be85b556f26015b564ec3a1000a
         * frequency : 4
         * period : 2
         * periodUnit : 1
         * creator : 8a2b4be859b48ead0159b491616a0003
         * contentId : 8a2b4be857e696e20157f99ab81f0006
         * content_helpCode : video2
         * createdAt : 2017-04-10 13:20:54
         * times : 8
         * content_type : 1
         * price : 3200
         * lastUseAt : 2017-04-18 10:52:30
         * updator : 8a2b4be859b48ead0159b491616a0003
         * id : 8a2b4be85b556f26015b564ec3d8000b
         * content_coverPic : http://static.dosnsoft.com/test%2F1477361448881.png
         * status : null
         * useTimes : 5
         * updatedAt : 2017-04-18 10:52:30
         */

        private int unitPrice;
        private String content_name;
        private String prescriptionId;
        private int period;
        private String creator;
        private String contentId;
        private int periodUnit;
        private int frequency;
        private String content_helpCode;
        private String createdAt;
        private int times;
        private int content_type;
        private int price;
        private String lastUseAt;
        private String updator;
        private String id;
        private String content_coverPic;
        private Object status;
        private int useTimes;
        private String updatedAt;

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

        public String getPrescriptionId() {
            return prescriptionId;
        }

        public void setPrescriptionId(String prescriptionId) {
            this.prescriptionId = prescriptionId;
        }

        public int getPeriod() {
            return period;
        }

        public void setPeriod(int period) {
            this.period = period;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getContentId() {
            return contentId;
        }

        public void setContentId(String contentId) {
            this.contentId = contentId;
        }

        public int getPeriodUnit() {
            return periodUnit;
        }

        public void setPeriodUnit(int periodUnit) {
            this.periodUnit = periodUnit;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public String getContent_helpCode() {
            return content_helpCode;
        }

        public void setContent_helpCode(String content_helpCode) {
            this.content_helpCode = content_helpCode;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
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

        public String getLastUseAt() {
            return lastUseAt;
        }

        public void setLastUseAt(String lastUseAt) {
            this.lastUseAt = lastUseAt;
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

        public String getContent_coverPic() {
            return content_coverPic;
        }

        public void setContent_coverPic(String content_coverPic) {
            this.content_coverPic = content_coverPic;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public int getUseTimes() {
            return useTimes;
        }

        public void setUseTimes(int useTimes) {
            this.useTimes = useTimes;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
