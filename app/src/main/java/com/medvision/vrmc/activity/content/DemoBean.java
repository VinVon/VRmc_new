package com.medvision.vrmc.activity.content;

import java.util.List;

/**
 * Created by raytine on 2017/10/30.
 */

public class DemoBean
{
    private String times;
    private String scenes;
    private List<String> lists;

    public DemoBean(String times, String scenes, List<String> lists) {
        this.times = times;
        this.scenes = scenes;
        this.lists = lists;
    }

    public String getScenes() {
        return scenes;
    }

    public void setScenes(String scenes) {
        this.scenes = scenes;
    }

    public List<String> getLists() {
        return lists;
    }

    public void setLists(List<String> lists) {
        this.lists = lists;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
