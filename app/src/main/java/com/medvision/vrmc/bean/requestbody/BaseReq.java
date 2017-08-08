package com.medvision.vrmc.bean.requestbody;

import com.medvision.vrmc.utils.SpUtils;

/**
 * Created by raytine on 2017/2/28.
 */

public class BaseReq {
    private String token;

    public BaseReq() {
        token = SpUtils.getInstance().getToken();
    }

    public BaseReq(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
