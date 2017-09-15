package com.medvision.vrmc.bean.requestbody;

/**
 * Created by raytine on 2017/8/26.
 */

public class schememodifyReq extends BaseReq{
    private String name;
    private String planId;
    private String contentIds;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

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
