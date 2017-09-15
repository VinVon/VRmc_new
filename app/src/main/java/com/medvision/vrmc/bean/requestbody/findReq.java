package com.medvision.vrmc.bean.requestbody;

/**
 * Created by raytine on 2017/8/17.
 */

public class findReq {

    /**
     * captcha : string
     * password : string
     * username : string
     */

    private String captcha;
    private String password;
    private String username;

    public findReq(String captcha, String password, String username) {
        this.captcha = captcha;
        this.password = password;
        this.username = username;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
