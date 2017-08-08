package com.medvision.vrmc.bean;

import java.util.List;

/**
 * Created by raytine on 2017/7/18.
 */

public class FilterType {

    /**
     * diseaseName : 所有病症
     * diseaseId :
     * therapiesArray : [{"therapyName":"特殊工娱治疗（指绘画、书法治疗) ","therapyId":"2c90e40a5c2e17f2015c42902aed00be"},{"therapyName":"工娱治疗","therapyId":"8a2b4be85a20f78b015a5924681c001a"},{"therapyName":"音乐治疗","therapyId":"8a2b4be85a65ee42015a6dd905400002"},{"therapyName":"行为观察和治疗","therapyId":"2c90e40a5c2e17f2015c42bf8da800c2"},{"therapyName":"认知心理治疗(CPT)","therapyId":"8a2b4be85a20f78b015a5925d64e0020"},{"therapyName":"催眠治疗","therapyId":"8a2b4be85a20f78b015a5925a133001f"},{"therapyName":"暴露疗法和半暴露疗法","therapyId":"8a2b4be85a6ef292015a7bf853810023"},{"therapyName":"认知知觉功能障碍训练","therapyId":"8a2b4be85a20f78b015a5924dee4001c"},{"therapyName":"偏瘫肢体综合训练","therapyId":"8a2b4be85a20f78b015a5924a1a9001b"},{"therapyName":"手功能训练","therapyId":"8a2b4be85a20f78b015a59251522001d"},{"therapyName":"听力整合及语言训练","therapyId":"8a2b4be85a20f78b015a59256dee001e"},{"therapyName":"作业疗法","therapyId":"8a2b4be85a20f78b015a5926499b0022"},{"therapyName":"职业功能训练","therapyId":"8a2b4be85a20f78b015a59260d3c0021"},{"therapyName":"松驰治疗","therapyId":"8a2b4be85a6ef292015a7bf89ac90024"},{"therapyName":"心理治疗","therapyId":"2c90e40a5c2e17f2015c4267242100b4"},{"therapyName":"心理咨询","therapyId":"2c90e40a5c2e17f2015c4267895b00b5"},{"therapyName":"运动疗法","therapyId":"2c90e40a5c2e17f2015c4267f6ed00b6"},{"therapyName":"言语训练","therapyId":"2c90e40a5c2e17f2015c42ba69cc00c1"}]
     */

    private String diseaseName;
    private String diseaseId;
    private List<TherapiesArrayBean> therapiesArray;

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public List<TherapiesArrayBean> getTherapiesArray() {
        return therapiesArray;
    }

    public void setTherapiesArray(List<TherapiesArrayBean> therapiesArray) {
        this.therapiesArray = therapiesArray;
    }

    public static class TherapiesArrayBean {
        /**
         * therapyName : 特殊工娱治疗（指绘画、书法治疗)
         * therapyId : 2c90e40a5c2e17f2015c42902aed00be
         */

        private String therapyName;
        private String therapyId;

        public String getTherapyName() {
            return therapyName;
        }

        public void setTherapyName(String therapyName) {
            this.therapyName = therapyName;
        }

        public String getTherapyId() {
            return therapyId;
        }

        public void setTherapyId(String therapyId) {
            this.therapyId = therapyId;
        }
    }
}
