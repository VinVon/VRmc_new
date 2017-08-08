package com.medvision.vrmc.bean.requestbody;

/**
 * Created by raytine on 2017/5/24.
 */

public class HistoryPrescriptionReq extends BaseReq {
    private int paging ;
    private String patientId;

    public HistoryPrescriptionReq() {
    }

    public HistoryPrescriptionReq(String patientId, int paging) {
        this.patientId = patientId;
        this.paging = paging;
    }

    public int getDoctorId() {
        return paging;
    }

    public void setDoctorId(int paging) {
        this.paging = paging;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
