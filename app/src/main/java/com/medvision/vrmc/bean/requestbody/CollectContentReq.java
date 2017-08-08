package com.medvision.vrmc.bean.requestbody;

/**
 * Created by raytine on 2017/5/17.
 */

public class CollectContentReq extends BaseReq{
    private String contentId;

    public CollectContentReq() {
    }

    public CollectContentReq(String contentId) {
        this.contentId = contentId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
}
