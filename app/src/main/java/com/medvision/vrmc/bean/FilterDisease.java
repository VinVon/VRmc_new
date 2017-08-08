package com.medvision.vrmc.bean;

/**
 * 添加患者获取病症选项
 * Created by raytine on 2017/7/11.
 */

public class FilterDisease {

    /**
     * id : xj_home_d00000000000000000003000
     * disease : 精神分裂症
     */

    private String diseaseId ;
    private String diseaseName;

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }
}
