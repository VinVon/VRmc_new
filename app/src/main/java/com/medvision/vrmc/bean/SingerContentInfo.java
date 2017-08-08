package com.medvision.vrmc.bean;

/**
 * 获取单个内容
 * Created by raytine on 2017/5/18.
 */

public class SingerContentInfo {

    /**
     * ext : {"id":"2c90e40a5c9bf7a3015ca07c1f2f00bc","content":"http://vod.med-vision.cn/prod/WZH877h3KT.exe?Expires=1500436657&OSSAccessKeyId=LTAIJNwd9qadQHPz&Signature=SPyWUU1n9ULs5ZDisL8os%2Be7gNU%3D","videosize":null,"videopassword":0}
     * disease : 失眠症,神经衰弱
     * videoupdateAt : 2017-06-13 00:00:00
     * therapy : 催眠治疗
     * typeName :
     * description : <p>内容介绍更新中！</p>
     * type : 6
     * otherdisease :
     * duration : 0
     * createdAt : 2017-06-13 16:05:08
     * price : 0
     * name : 荷塘夜色-催眠治疗失眠
     * clicks : null
     * coverPic : http://static.med-vision.cn/prod%2F1497342555803.jpg
     * id : 2c90e40a5c9bf7a3015ca07c1f1f00b9
     * isCollected : 1
     */

    private ExtBean ext;
    private String disease;
    private String videoupdateAt;
    private String therapy;
    private String typeName;
    private String description;
    private int type;
    private String otherdisease;
    private int duration;
    private String createdAt;
    private int price;
    private String name;
    private Object clicks;
    private String coverPic;
    private String id;
    private String isCollected;

    public ExtBean getExt() {
        return ext;
    }

    public void setExt(ExtBean ext) {
        this.ext = ext;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getVideoupdateAt() {
        return videoupdateAt;
    }

    public void setVideoupdateAt(String videoupdateAt) {
        this.videoupdateAt = videoupdateAt;
    }

    public String getTherapy() {
        return therapy;
    }

    public void setTherapy(String therapy) {
        this.therapy = therapy;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOtherdisease() {
        return otherdisease;
    }

    public void setOtherdisease(String otherdisease) {
        this.otherdisease = otherdisease;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getClicks() {
        return clicks;
    }

    public void setClicks(Object clicks) {
        this.clicks = clicks;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(String isCollected) {
        this.isCollected = isCollected;
    }

    public static class ExtBean {
        /**
         * id : 2c90e40a5c9bf7a3015ca07c1f2f00bc
         * content : http://vod.med-vision.cn/prod/WZH877h3KT.exe?Expires=1500436657&OSSAccessKeyId=LTAIJNwd9qadQHPz&Signature=SPyWUU1n9ULs5ZDisL8os%2Be7gNU%3D
         * videosize : null
         * videopassword : 0
         */

        private String id;
        private String content;
        private Object videosize;
        private int videopassword;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getVideosize() {
            return videosize;
        }

        public void setVideosize(Object videosize) {
            this.videosize = videosize;
        }

        public int getVideopassword() {
            return videopassword;
        }

        public void setVideopassword(int videopassword) {
            this.videopassword = videopassword;
        }
    }
}
