package com.medvision.vrmc.bean;

import java.util.List;

/**
 * HomeContent 的集合,用于开处方在场景间多选传递
 * Created by raytine on 2017/7/19.
 */

public class HomeContentList {
    private List<HomeContent> contents;

    public List<HomeContent> getContents() {
        return contents;
    }

    public void setContents(List<HomeContent> contents) {
        this.contents = contents;
    }
}
