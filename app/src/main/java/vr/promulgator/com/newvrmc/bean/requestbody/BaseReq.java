package vr.promulgator.com.newvrmc.bean.requestbody;

import vr.promulgator.com.newvrmc.utils.SpUtils;

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
