package com.medvision.vrmc.bean;

/**
 * Created by raytine on 2017/7/15.
 */

public class MyPatients {

    /**
     * realnameFirstSpell : L
     * name : 同学
     * remark : 路口
     * id : 8a2b4be85d45ac77015d4e654c22001e
     */

    private String realnameFirstSpell;
    private String name;
    private String remark;
    private String id;
    private boolean showLine = false;

    public boolean isShowLine() {
        return showLine;
    }

    public void setShowLine(boolean showLine) {
        this.showLine = showLine;
    }

    public String getRealnameFirstSpell() {
        return realnameFirstSpell;
    }

    public void setRealnameFirstSpell(String realnameFirstSpell) {
        this.realnameFirstSpell = realnameFirstSpell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
