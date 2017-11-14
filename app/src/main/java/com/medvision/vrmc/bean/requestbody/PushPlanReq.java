package com.medvision.vrmc.bean.requestbody;

/**
 * Created by raytine on 2017/11/1.
 */

public class PushPlanReq extends BaseReq {
    private String patientId;
    private String name;
    private String instruction;
    private int times;
    private int scenes;
    private String contents;
    private String diseaseId;

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public PushPlanReq() {
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public void setScenes(int scenes) {
        this.scenes = scenes;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "PushPlanReq{" +
                "patientId='" + patientId + '\'' +
                ", name='" + name + '\'' +
                ", instruction='" + instruction + '\'' +
                ", times=" + times +
                ", scenes=" + scenes +
                ", contents='" + contents + '\'' +
                ", diseaseId='" + diseaseId + '\'' +
                '}';
    }
}
