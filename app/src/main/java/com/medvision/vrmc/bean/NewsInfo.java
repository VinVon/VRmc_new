package com.medvision.vrmc.bean;

/**
 * Created by raytine on 2017/8/23.
 */

public class NewsInfo {

    /**
     * releasetime : 2017-07-01
     * coverurl : http://static.med-vision.cn/test%2F1503458465824.jpg
     * name : 杭州七院引进VR技术用于精神心理康复
     * linkurl : http://mp.weixin.qq.com/s?__biz=MzU0NjAzNTQ1NQ==&mid=2247483667&idx=1&sn=8fe5888df89ecf43abfee67f9c50f1d0&scene=19#wechat_redirect
     */

    private String releasetime;
    private String coverurl;
    private String name;
    private String linkurl;

    public String getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(String releasetime) {
        this.releasetime = releasetime;
    }

    public String getCoverurl() {
        return coverurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }
}
