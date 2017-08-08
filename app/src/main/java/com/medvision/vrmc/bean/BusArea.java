package com.medvision.vrmc.bean;

/**
 * Created by raytine on 2017/7/21.
 */

public class BusArea {
    private String str;
    private boolean isselect;

    public BusArea(String str, boolean isselect) {
        this.str = str;
        this.isselect = isselect;
    }

    public boolean isselect() {
        return isselect;
    }

    public void setIsselect(boolean isselect) {
        this.isselect = isselect;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
