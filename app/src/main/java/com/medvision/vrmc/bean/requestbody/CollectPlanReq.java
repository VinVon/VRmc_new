package com.medvision.vrmc.bean.requestbody;

/**
 * Created by raytine on 2017/10/31.
 */

public class CollectPlanReq extends BaseReq{
    private String planId;

    public CollectPlanReq(String planId) {
        this.planId = planId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
