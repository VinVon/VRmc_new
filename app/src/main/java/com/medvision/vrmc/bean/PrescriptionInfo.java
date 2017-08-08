package com.medvision.vrmc.bean;

import java.io.Serializable;

/**
 * 开处方返回信息
 * Created by raytine on 2017/5/9.
 */

public class PrescriptionInfo implements Serializable {
    private String prescriptionId ;
    private String billno ;

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }
}
