package com.medvision.vrmc.bean.requestbody;

import java.io.Serializable;

/**
 * Created by raytine on 2017/5/9.
 */

public class AddPrescriptionsReq implements Serializable {
    private String contentId;
    private int frequency;
    private int period;
    private int periodUnit;
    private int times;

    public AddPrescriptionsReq() {
    }

    public AddPrescriptionsReq(String contentId, int frequency, int period, int periodUnit,int times) {
        this.contentId = contentId;
        this.frequency = frequency;
        this.period = period;
        this.periodUnit = periodUnit;
        this.times = times;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(int periodUnit) {
        this.periodUnit = periodUnit;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
