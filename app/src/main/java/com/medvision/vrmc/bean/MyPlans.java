package com.medvision.vrmc.bean;

import java.util.List;

/**
 * 我的方案
 * Created by raytine on 2017/10/31.
 */

public class MyPlans {

    /**
     * token : null
     * id : 2c90e5c75f70c413015f70cfcf250009
     * contentIds : null
     * name : 失眠症治疗方案
     * contents : [{"id":"2c90e40a5bd24694015bd747886c000b","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"ceshi","coverPic":"http://static.med-vision.cn/prod%2F1493965440988.jpg","clicks":0,"duration":0,"isCollected":0},{"id":"2c90e40a5c2e17f2015c360cd879000c","contentType":1,"type":1,"typeName":"视频","price":0,"name":"教室演讲1-认知行为治疗","coverPic":"http://static.med-vision.cn/prod%2F1495555430463.png","clicks":0,"duration":8,"isCollected":0},{"id":"2c90e40a5c2e17f2015c377669bc0010","contentType":1,"type":1,"typeName":"视频","price":0,"name":"太阳总会升起-抑郁症医学共振音乐","coverPic":"http://static.med-vision.cn/prod%2F1501918033772.png","clicks":0,"duration":31,"isCollected":0},{"id":"2c90e40a5c2e17f2015c38b441670027","contentType":1,"type":1,"typeName":"视频","price":0,"name":"教室演讲2-认知行为治疗","coverPic":"http://static.med-vision.cn/prod%2F1495599956271.png","clicks":0,"duration":8,"isCollected":0},{"id":"2c90e40a5c2e17f2015c38b47aef002b","contentType":1,"type":1,"typeName":"视频","price":0,"name":"教室演讲3-认知行为治疗","coverPic":"http://static.med-vision.cn/prod%2F1495599971034.png","clicks":0,"duration":8,"isCollected":0},{"id":"2c90e40a5c2e17f2015c39132f360039","contentType":1,"type":1,"typeName":"视频","price":0,"name":"教室演讲4-认知行为治疗","coverPic":"http://static.med-vision.cn/prod%2F1502094239757.png","clicks":0,"duration":8,"isCollected":0},{"id":"2c90e40a5c2e17f2015c395dc526003f","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"篮球运动-文体训练","coverPic":"http://static.med-vision.cn/prod%2F1502096250158.png","clicks":0,"duration":0,"isCollected":0},{"id":"2c90e40a5c2e17f2015c396c0efd0048","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"情绪识别-表情辨别，情感认知训练","coverPic":"http://static.med-vision.cn/prod%2F1502095479856.png","clicks":0,"duration":0,"isCollected":0},{"id":"2c90e40a5c2e17f2015c397088cc004c","contentType":1,"type":1,"typeName":"视频","price":0,"name":"人脸识别","coverPic":"http://static.med-vision.cn/prod%2F1495612295318.png","clicks":0,"duration":0,"isCollected":0},{"id":"2c90e40a5c2e17f2015c398367b60050","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"注意干扰-Stroop任务训练","coverPic":"http://static.med-vision.cn/prod%2F1502096089783.png","clicks":0,"duration":0,"isCollected":0},{"id":"2c90e40a5c2e17f2015c3984c7b70054","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"瞬间记忆-工作记忆力训练","coverPic":"http://static.med-vision.cn/prod%2F1502096047000.png","clicks":0,"duration":0,"isCollected":0},{"id":"2c90e40a5c2e17f2015c39aae91b005a","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"位移追踪-视觉注意力训练","coverPic":"http://static.med-vision.cn/prod%2F1502095869591.png","clicks":0,"duration":0,"isCollected":0},{"id":"2c90e40a5c2e17f2015c39b837af005e","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"人脸识别-失认、视觉记忆力训练","coverPic":"http://static.med-vision.cn/prod%2F1502095414730.png","clicks":0,"duration":0,"isCollected":0},{"id":"2c90e40a5c2e17f2015c39b886470062","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"命名理解-失认、失用及命名训练","coverPic":"http://static.med-vision.cn/prod%2F1502095727656.png","clicks":0,"duration":0,"isCollected":0},{"id":"2c90e40a5c2e17f2015c39d22dd50067","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"声音识别-声音理解判断训练","coverPic":"http://static.med-vision.cn/prod%2F1502095611435.png","clicks":0,"duration":0,"isCollected":0},{"id":"2c90e40a5c2e17f2015c3d89d46c0080","contentType":1,"type":7,"typeName":"外部游戏","price":0,"name":"切水果-文体训练","coverPic":"http://static.med-vision.cn/prod%2F1502088614413.png","clicks":0,"duration":0,"isCollected":0},{"id":"2c90e40a5c5df86c015c6173de930030","contentType":1,"type":1,"typeName":"视频","price":0,"name":"换个视角-音乐认知治疗","coverPic":"http://static.med-vision.cn/prod%2F1502088602516.png","clicks":0,"duration":5,"isCollected":0},{"id":"2c90e40a5c9bf7a3015ca07c1f1f00b9","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"荷塘夜色-催眠治疗失眠","coverPic":"http://static.med-vision.cn/prod%2F1502088416741.png","clicks":0,"duration":0,"isCollected":1},{"id":"2c90e40a5d7de393015d9cfe99da0bee","contentType":1,"type":1,"typeName":"视频","price":0,"name":"光照放松法-自主训练","coverPic":"http://static.med-vision.cn/prod%2F1502088347774.png","clicks":0,"duration":0,"isCollected":0},{"id":"2c90e40a5d7de393015d9cff30350bf1","contentType":1,"type":1,"typeName":"视频","price":0,"name":"情景放松法-指导性想象放松法","coverPic":"http://static.med-vision.cn/prod%2F1502088233374.png","clicks":0,"duration":0,"isCollected":0},{"id":"2c90e40a5d7de393015db00300330c5e","contentType":1,"type":1,"typeName":"视频","price":0,"name":"月夜听涛-催眠治疗焦虑抑郁失眠","coverPic":"http://static.med-vision.cn/prod%2F1502093899842.png","clicks":0,"duration":0,"isCollected":0},{"id":"2c90e40a5d7de393015db0395bcb0c88","contentType":1,"type":1,"typeName":"视频","price":0,"name":"草原雪山-基础放松医学共振音乐","coverPic":"http://static.med-vision.cn/prod%2F1502080070938.png","clicks":0,"duration":0,"isCollected":0},{"id":"2c90e40a5d7de393015db089b48f0ce0","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"意念射箭-视觉注意训练","coverPic":"http://static.med-vision.cn/prod%2F1501905400921.jpg","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85a6de554015a6de683a90000","contentType":1,"type":1,"typeName":"视频","price":10,"name":"呼吸放松法-自主训练","coverPic":"http://static.med-vision.cn/prod%2F1502093537073.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85a6de554015a6de982f00004","contentType":1,"type":1,"typeName":"视频","price":10,"name":"乘地铁1（被害妄想认知治疗）","coverPic":"http://static.med-vision.cn/prod%2F1493716083566.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85a6de554015a6debd25b0009","contentType":1,"type":1,"typeName":"视频","price":10,"name":"乘地铁2（被害妄想认知治疗）","coverPic":"http://static.med-vision.cn/prod%2F1493716068047.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85a6de554015a6df0a836000d","contentType":1,"type":1,"typeName":"视频","price":10,"name":"乘地铁3（被害妄想认知治疗）","coverPic":"http://static.med-vision.cn/prod%2F1493716059200.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85a6de554015a6df331a90011","contentType":1,"type":1,"typeName":"视频","price":10,"name":"乘地铁4（被害妄想认知治疗）","coverPic":"http://static.med-vision.cn/prod%2F1493716050226.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85a6de554015a6df575990015","contentType":1,"type":1,"typeName":"视频","price":10,"name":"换个视角（音乐认知治疗）","coverPic":"http://static.med-vision.cn/prod%2F1493715825654.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85a6de554015a6dfc7b01001e","contentType":1,"type":1,"typeName":"视频","price":0,"name":"月夜朦胧(无唤醒)-催眠治疗失眠","coverPic":"http://static.med-vision.cn/prod%2F1502094683003.png","clicks":0,"duration":28,"isCollected":0},{"id":"8a2b4be85a831b44015a83e6c1940008","contentType":1,"type":7,"typeName":"外部游戏","price":0,"name":"海底观鱼-改变认知、调节情绪","coverPic":"http://static.med-vision.cn/prod%2F1502096694278.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85a8400df015a87bbda42002a","contentType":1,"type":7,"typeName":"外部游戏","price":0,"name":"摩天大楼-认知行为治疗","coverPic":"http://static.med-vision.cn/prod%2F1502096647456.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85a87bfc7015a8a74161d003c","contentType":1,"type":1,"typeName":"视频","price":7,"name":"海阔天空-焦虑症医学共振音乐","coverPic":"http://static.med-vision.cn/prod%2F1502092918876.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ac5f110015acfd5aecf0002","contentType":1,"type":6,"typeName":"游戏","price":10,"name":"注意干扰（注意力训练）","coverPic":"http://static.dosnsoft.com/prod%2F1489545568695.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ac5f110015ad0399b720006","contentType":1,"type":1,"typeName":"视频","price":10,"name":"乘电梯1（认知行为治疗）","coverPic":"http://static.med-vision.cn/prod%2F1493715958275.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ac5f110015ad03ff79c000a","contentType":1,"type":1,"typeName":"视频","price":0,"name":"乘电梯2（认知行为治疗）","coverPic":"http://static.med-vision.cn/prod%2F1493715948456.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ac5f110015ad0409bfd000e","contentType":1,"type":1,"typeName":"视频","price":0,"name":"乘电梯3（认知行为治疗）","coverPic":"http://static.med-vision.cn/prod%2F1493715937963.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ac5f110015ad04cf46f0012","contentType":1,"type":1,"typeName":"视频","price":0,"name":"办公室面试（认知行为治疗）","coverPic":"http://static.med-vision.cn/prod%2F1493708785709.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ac5f110015ad073f8630016","contentType":1,"type":1,"typeName":"视频","price":0,"name":"美女对视-认知行为治疗","coverPic":"http://static.med-vision.cn/prod%2F1495549649308.png","clicks":0,"duration":7,"isCollected":0},{"id":"8a2b4be85ad075b9015ad08d24490004","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"情景对话-PQRST练习法","coverPic":"http://static.med-vision.cn/prod%2F1502096373824.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ad09ff1015ad12336a60008","contentType":1,"type":1,"typeName":"视频","price":0,"name":"基础放松-处方音乐","coverPic":"http://static.dosnsoft.com/prod%2F1489567430107.bmp","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ad129ad015ad14d221c000a","contentType":1,"type":1,"typeName":"视频","price":0,"name":"心景认知","coverPic":"http://static.dosnsoft.com/prod%2F1489570177365.bmp","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ad129ad015ad17eece8001c","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"声调识别-听力障碍及反应速度训练","coverPic":"http://static.med-vision.cn/prod%2F1502096344792.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ad129ad015ad2107a040040","contentType":1,"type":1,"typeName":"视频","price":0,"name":"放下执念（音乐认知治疗）","coverPic":"http://static.med-vision.cn/prod%2F1493715647936.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ad129ad015ad27d90c00045","contentType":1,"type":1,"typeName":"视频","price":0,"name":"淡定（音乐认知治疗）","coverPic":"http://static.med-vision.cn/prod%2F1493715634549.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ad129ad015ad283461c0049","contentType":1,"type":1,"typeName":"视频","price":0,"name":"月夜观海-催眠治疗失眠","coverPic":"http://static.med-vision.cn/prod%2F1502094669289.png","clicks":0,"duration":27,"isCollected":0},{"id":"8a2b4be85ad129ad015ad28be6ff004d","contentType":1,"type":6,"typeName":"游戏","price":0,"name":"注意干扰","coverPic":"http://static.med-vision.cn/prod%2F1495613549635.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ad129ad015ad28cad540051","contentType":1,"type":1,"typeName":"视频","price":0,"name":"空山古刹（支持性音乐治疗）","coverPic":"http://static.med-vision.cn/prod%2F1493715474163.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ad129ad015ad2a0de0c0055","contentType":1,"type":1,"typeName":"视频","price":0,"name":"水清明净（支持性音乐治疗）","coverPic":"http://static.med-vision.cn/prod%2F1493715454657.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ad129ad015ad2a258470059","contentType":1,"type":1,"typeName":"视频","price":0,"name":"水应万变（音乐认知治疗）","coverPic":"http://static.med-vision.cn/prod%2F1493715617613.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ad129ad015ad2b86a77005d","contentType":1,"type":1,"typeName":"视频","price":0,"name":"我心飞翔（支持性音乐治疗）","coverPic":"http://static.med-vision.cn/prod%2F1493715436874.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ad129ad015ad2ce79940061","contentType":1,"type":1,"typeName":"视频","price":0,"name":"银河尘起-支持性音乐治疗","coverPic":"http://static.med-vision.cn/prod%2F1502093087849.png","clicks":0,"duration":5,"isCollected":0},{"id":"8a2b4be85ad129ad015ad2db23f10065","contentType":1,"type":1,"typeName":"视频","price":0,"name":"渐进性放松法-渐进性肌肉放松法","coverPic":"http://static.med-vision.cn/prod%2F1502093484657.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ad129ad015ad2efb7a30069","contentType":1,"type":1,"typeName":"视频","price":0,"name":"云栖竹径（支持性音乐治疗）","coverPic":"http://static.dosnsoft.com/prod%2F1489597609841.bmp","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ad129ad015ad30a696f006d","contentType":1,"type":1,"typeName":"视频","price":0,"name":"自然之声-支持性音乐治疗","coverPic":"http://static.med-vision.cn/prod%2F1502093009874.png","clicks":0,"duration":6,"isCollected":0},{"id":"8a2b4be85ad129ad015ad44048520071","contentType":1,"type":1,"typeName":"视频","price":0,"name":"内观念头-认知心理治疗","coverPic":"http://static.med-vision.cn/prod%2F1502093778522.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ad129ad015ad49ffd3c0077","contentType":1,"type":1,"typeName":"视频","price":0,"name":"月夜观海(无唤醒)-催眠治疗失眠","coverPic":"http://static.med-vision.cn/prod%2F1502094709610.png","clicks":0,"duration":24,"isCollected":0},{"id":"8a2b4be85ad4be47015ad500e652003a","contentType":1,"type":1,"typeName":"视频","price":0,"name":"内观呼吸-认知心理治疗","coverPic":"http://static.med-vision.cn/prod%2F1502093724268.png","clicks":0,"duration":22,"isCollected":0},{"id":"8a2b4be85ad4be47015ad504911c003e","contentType":1,"type":7,"typeName":"外部游戏","price":0,"name":"游龙画圈-改善感觉运动、认知综合","coverPic":"http://static.med-vision.cn/prod%2F1495681438697.png","clicks":0,"duration":0,"isCollected":0},{"id":"8a2b4be85ad4be47015ad5c13a90005e","contentType":1,"type":1,"typeName":"视频","price":0,"name":"宽恕原谅-认知心理治疗","coverPic":"http://static.med-vision.cn/prod%2F1502093697752.png","clicks":0,"duration":20,"isCollected":0},{"id":"8a2b4be85ad4be47015ad6230e8b0066","contentType":1,"type":1,"typeName":"视频","price":0,"name":"内观情绪-认知心理治疗","coverPic":"http://static.med-vision.cn/prod%2F1502093653707.png","clicks":0,"duration":0,"isCollected":0}]
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

    public static class ContentsBean {
        /**
         * id : 2c90e40a5bd24694015bd747886c000b
         * contentType : 1
         * type : 6
         * typeName : 游戏
         * price : 0
         * name : ceshi
         * coverPic : http://static.med-vision.cn/prod%2F1493965440988.jpg
         * clicks : 0
         * duration : 0
         * isCollected : 0
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
