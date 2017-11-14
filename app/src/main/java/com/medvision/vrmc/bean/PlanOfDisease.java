package com.medvision.vrmc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 病症对应的方案列表对象
 * Created by raytine on 2017/10/31.
 */

public class PlanOfDisease implements Serializable{

    /**
     * token : null
     * paging : 0
     * sortName : null
     * sortOrder : desc
     * id : 2c90e5c75f6cb36c015f6ffa9c630003
     * instruction : 系统推荐方案001
     * times : 3
     * scenes : 5
     * isCollected : 0
     * contents : [{"coverPic":"http://static.med-vision.cn/prod%2F1502088416741.png","price":0,"isCollected":0,"clicks":0,"name":"荷塘夜色-催眠治疗失眠","duration":0,"id":"2c90e40a5c9bf7a3015ca07c1f1f00b9"},{"coverPic":"http://static.med-vision.cn/prod%2F1502088602516.png","price":0,"isCollected":0,"clicks":0,"name":"换个视角-音乐认知治疗","duration":5,"id":"2c90e40a5c5df86c015c6173de930030"},{"coverPic":"http://static.med-vision.cn/prod%2F1502088614413.png","price":0,"isCollected":0,"clicks":0,"name":"切水果-文体训练","duration":0,"id":"2c90e40a5c2e17f2015c3d89d46c0080"},{"coverPic":"http://static.med-vision.cn/prod%2F1502095611435.png","price":0,"isCollected":0,"clicks":0,"name":"声音识别-声音理解判断训练","duration":0,"id":"2c90e40a5c2e17f2015c39d22dd50067"},{"coverPic":"http://static.med-vision.cn/prod%2F1502095727656.png","price":0,"isCollected":0,"clicks":0,"name":"命名理解-失认、失用及命名训练","duration":0,"id":"2c90e40a5c2e17f2015c39b886470062"},{"coverPic":"http://static.med-vision.cn/prod%2F1502095414730.png","price":0,"isCollected":0,"clicks":0,"name":"人脸识别-失认、视觉记忆力训练","duration":0,"id":"2c90e40a5c2e17f2015c39b837af005e"},{"coverPic":"http://static.med-vision.cn/prod%2F1502095727656.png","price":0,"isCollected":0,"clicks":0,"name":"命名理解-失认、失用及命名训练","duration":0,"id":"2c90e40a5c2e17f2015c39b886470062"},{"coverPic":"http://static.med-vision.cn/prod%2F1502095611435.png","price":0,"isCollected":0,"clicks":0,"name":"声音识别-声音理解判断训练","duration":0,"id":"2c90e40a5c2e17f2015c39d22dd50067"},{"coverPic":"http://static.med-vision.cn/prod%2F1502088614413.png","price":0,"isCollected":0,"clicks":0,"name":"切水果-文体训练","duration":0,"id":"2c90e40a5c2e17f2015c3d89d46c0080"},{"coverPic":"http://static.med-vision.cn/prod%2F1502088602516.png","price":0,"isCollected":0,"clicks":0,"name":"换个视角-音乐认知治疗","duration":5,"id":"2c90e40a5c5df86c015c6173de930030"},{"coverPic":"http://static.med-vision.cn/prod%2F1502096047000.png","price":0,"isCollected":0,"clicks":0,"name":"瞬间记忆-工作记忆力训练","duration":0,"id":"2c90e40a5c2e17f2015c3984c7b70054"},{"coverPic":"http://static.med-vision.cn/prod%2F1502095869591.png","price":0,"isCollected":0,"clicks":0,"name":"位移追踪-视觉注意力训练","duration":0,"id":"2c90e40a5c2e17f2015c39aae91b005a"},{"coverPic":"http://static.med-vision.cn/prod%2F1502095414730.png","price":0,"isCollected":0,"clicks":0,"name":"人脸识别-失认、视觉记忆力训练","duration":0,"id":"2c90e40a5c2e17f2015c39b837af005e"},{"coverPic":"http://static.med-vision.cn/prod%2F1502095727656.png","price":0,"isCollected":0,"clicks":0,"name":"命名理解-失认、失用及命名训练","duration":0,"id":"2c90e40a5c2e17f2015c39b886470062"},{"coverPic":"http://static.med-vision.cn/prod%2F1502095611435.png","price":0,"isCollected":0,"clicks":0,"name":"声音识别-声音理解判断训练","duration":0,"id":"2c90e40a5c2e17f2015c39d22dd50067"}]
     * diseaseId : 8a2b4be85a20f78b015a5921ef15000e
     * diseaseName : 失眠症
     * planId : null
     * contentIds : null
     * name : 系统推荐方案001
     */

    private Object token;
    private int paging;
    private Object sortName;
    private String sortOrder;
    private String id;
    private String instruction;
    private int times;
    private int scenes;
    private int isCollected;
    private String diseaseId;
    private String diseaseName;
    private Object planId;
    private Object contentIds;
    private String name;
    private String createdAt;
    private List<ContentsBean> contents;

    public String getCreateAt() {
        return createdAt;
    }

    public void setCreateAt(String createAt) {
        this.createdAt = createAt;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public int getPaging() {
        return paging;
    }

    public void setPaging(int paging) {
        this.paging = paging;
    }

    public Object getSortName() {
        return sortName;
    }

    public void setSortName(Object sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getScenes() {
        return scenes;
    }

    public void setScenes(int scenes) {
        this.scenes = scenes;
    }

    public int getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(int isCollected) {
        this.isCollected = isCollected;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public Object getPlanId() {
        return planId;
    }

    public void setPlanId(Object planId) {
        this.planId = planId;
    }

    public Object getContentIds() {
        return contentIds;
    }

    public void setContentIds(Object contentIds) {
        this.contentIds = contentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ContentsBean> getContents() {
        return contents;
    }

    public void setContents(List<ContentsBean> contents) {
        this.contents = contents;
    }

    public static class ContentsBean implements Serializable{
        /**
         * coverPic : http://static.med-vision.cn/prod%2F1502088416741.png
         * price : 0
         * isCollected : 0
         * clicks : 0
         * name : 荷塘夜色-催眠治疗失眠
         * duration : 0
         * id : 2c90e40a5c9bf7a3015ca07c1f1f00b9
         */

        private String coverPic;
        private int price;
        private int isCollected;
        private int clicks;
        private String name;
        private int duration;
        private String id;

        public String getCoverPic() {
            return coverPic;
        }

        public void setCoverPic(String coverPic) {
            this.coverPic = coverPic;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getIsCollected() {
            return isCollected;
        }

        public void setIsCollected(int isCollected) {
            this.isCollected = isCollected;
        }

        public int getClicks() {
            return clicks;
        }

        public void setClicks(int clicks) {
            this.clicks = clicks;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
