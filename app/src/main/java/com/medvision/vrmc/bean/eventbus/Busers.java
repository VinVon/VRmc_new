package com.medvision.vrmc.bean.eventbus;

import com.medvision.vrmc.bean.HomeContent;

/**
 * Created by raytine on 2017/11/3.
 */

public class Busers {
    private boolean is;
    private HomeContent homeContent;

    public Busers( HomeContent homeContent,boolean is) {
        this.is = is;
        this.homeContent = homeContent;
    }

    public boolean is() {
        return is;
    }

    public void setIs(boolean is) {
        this.is = is;
    }

    public HomeContent getHomeContent() {
        return homeContent;
    }

    public void setHomeContent(HomeContent homeContent) {
        this.homeContent = homeContent;
    }
}
