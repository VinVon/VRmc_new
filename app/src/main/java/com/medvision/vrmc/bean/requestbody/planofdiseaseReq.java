package com.medvision.vrmc.bean.requestbody;

/**
 * 根据病症id获取方案列表
 * Created by raytine on 2017/10/31.
 */

public class planofdiseaseReq extends BaseReq {
    private String diseaseId = null;
    private int paging ;
    public planofdiseaseReq() {
    }

    public planofdiseaseReq(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public planofdiseaseReq(int paging) {
        this.paging = paging;
    }

    public planofdiseaseReq(String diseaseId, int paging) {
        this.diseaseId = diseaseId;
        this.paging = paging;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public int getPaging() {
        return paging;
    }

    public void setPaging(int paging) {
        this.paging = paging;
    }
}
