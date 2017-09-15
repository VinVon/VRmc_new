package com.medvision.vrmc.bean.requestbody;

/**
 * Created by raytine on 2017/8/26.
 */

public class schemedeleteReq extends BaseReq {
    private String planId;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
