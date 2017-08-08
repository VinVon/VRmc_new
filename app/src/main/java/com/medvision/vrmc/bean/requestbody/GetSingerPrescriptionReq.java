package com.medvision.vrmc.bean.requestbody;

/**
 * Created by raytine on 2017/5/15.
 */

public class GetSingerPrescriptionReq extends BaseReq {
    private String prescriptionId;

    public GetSingerPrescriptionReq() {
    }

    public GetSingerPrescriptionReq(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }


    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
}
