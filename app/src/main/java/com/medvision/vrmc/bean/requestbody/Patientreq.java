package com.medvision.vrmc.bean.requestbody;

/**
 * Created by raytine on 2017/7/17.
 */

public class Patientreq extends BaseReq {
    private String patientId;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
