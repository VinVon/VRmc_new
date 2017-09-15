package com.medvision.vrmc.bean.requestbody;

/**
 * Created by raytine on 2017/8/23.
 */

public class schemeReq extends BaseReq{
    private String name;
    private String contentIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentIds() {
        return contentIds;
    }

    public void setContentIds(String contentIds) {
        this.contentIds = contentIds;
    }
}
