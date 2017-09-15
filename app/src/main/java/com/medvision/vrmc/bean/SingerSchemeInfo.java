package com.medvision.vrmc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by raytine on 2017/8/25.
 */

public class SingerSchemeInfo implements Serializable{


    /**
     * token : null
     * id : 8a2b4be85e0d92c2015e188c3a22006a
     * contentIds : null
     * name : unlock
     * contents : [{"id":"2c90e40a5c2e17f2015c39b886470062","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"命名理解-失认、失用及命名训练","coverPic":"http://static.med-vision.cn/prod%2F1502095727656.png","clicks":null,"duration":0,"isCollected":1},{"id":"2c90e40a5c2e17f2015c39d22dd50067","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"声音识别-声音理解判断训练","coverPic":"http://static.med-vision.cn/prod%2F1502095611435.png","clicks":null,"duration":0,"isCollected":1},{"id":"2c90e40a5c2e17f2015c3d89d46c0080","contentType":1,"type":7,"typeName":"外部游戏","price":0,"name":"切水果-文体训练","coverPic":"http://static.med-vision.cn/prod%2F1502088614413.png","clicks":null,"duration":0,"isCollected":1}]
     */

    private Object token;
    private String id;
    private Object contentIds;
    private String name;
    private List<ContentsBean> contents;

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
         * id : 2c90e40a5c2e17f2015c39b886470062
         * contentType : 1
         * type : 6
         * typeName : 游戏
         * price : 0
         * name : 命名理解-失认、失用及命名训练
         * coverPic : http://static.med-vision.cn/prod%2F1502095727656.png
         * clicks : null
         * duration : 0
         * isCollected : 1
         */

        private String id;
        private int contentType;
        private int type;
        private String typeName;
        private int price;
        private String name;
        private String coverPic;
        private int clicks;
        private int duration;
        private int isCollected;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getContentType() {
            return contentType;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
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

        public String getCoverPic() {
            return coverPic;
        }

        public void setCoverPic(String coverPic) {
            this.coverPic = coverPic;
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

        public int getIsCollected() {
            return isCollected;
        }

        public void setIsCollected(int isCollected) {
            this.isCollected = isCollected;
        }
    }
}
