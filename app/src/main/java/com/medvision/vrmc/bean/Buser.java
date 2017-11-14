package com.medvision.vrmc.bean;

/**
 * 用来内容详情与内容通信的对象
 * Created by raytine on 2017/5/18.
 */

public class Buser {
    private String ContenteId;
    private boolean is;

    public Buser(String contenteId, boolean is) {
        ContenteId = contenteId;
        this.is = is;
    }

    public String getContenteId() {
        return ContenteId;
    }

    public void setContenteId(String contenteId) {
        ContenteId = contenteId;
    }

    public boolean is() {
        return is;
    }

    public void setIs(boolean is) {
        this.is = is;
    }
}
