package com.medvision.vrmc.bean;

import java.util.List;

/**
 * Created by raytine on 2017/5/24.
 */

public class HistoryPrescriptionInfo {

    /**
     * id : 8a2b4be85cd34c39015cd45d0f6b0017
     * contents : [{"id":"8a2b4be857d718e70157db749f56000b","creator":"00000000000000000000000000000001","createdAt":"2016-10-19 13:40:36","updator":"00000000000000000000000000000001","updatedAt":"2017-06-23 17:34:50","helpCode":"picture1","name":"测试图片集1","type":3,"isFree":0,"price":0.01,"description":"<p>测试图片集1<\/p>","remark":"测试图片集1","status":2,"coverPic":"http://static.dosnsoft.com/test%2F1476855635742.png","hidden":0,"clicks":0,"duration":0,"videoupdateAt":"2016-10-19 00:00:00","otherdisease":""}]
     * disease : vb
     * suggestion : ff
     * payStatus : 1
     * total : 0.01
     * createdAt : 2017-06-23 17:51:28
     * updatedAt : 2017-06-23 17:51:28
     */

    private String id;
    private String disease;
    private String suggestion;
    private int payStatus;
    private int status;
    private double total;
    private String createdAt;
    private String updatedAt;
    private List<ContentsBean> contents;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public static class ContentsBean {
        /**
         * id : 8a2b4be857d718e70157db749f56000b
         * creator : 00000000000000000000000000000001
         * createdAt : 2016-10-19 13:40:36
         * updator : 00000000000000000000000000000001
         * updatedAt : 2017-06-23 17:34:50
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
         * videoupdateAt : 2016-10-19 00:00:00
         * otherdisease :
         */

        private String id;
        private String creator;
        private String createdAt;
        private String updator;
        private String updatedAt;
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
        private String videoupdateAt;
        private String otherdisease;

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

        public String getVideoupdateAt() {
            return videoupdateAt;
        }

        public void setVideoupdateAt(String videoupdateAt) {
            this.videoupdateAt = videoupdateAt;
        }

        public String getOtherdisease() {
            return otherdisease;
        }

        public void setOtherdisease(String otherdisease) {
            this.otherdisease = otherdisease;
        }
    }
}
