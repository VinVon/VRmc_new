package com.medvision.vrmc.bean;

import java.io.Serializable;

/**
 * 认证状态
 * Created by raytine on 2017/8/17.
 */

public class CertifiStatus implements Serializable {
    private int status;
    private boolean istrue = false;//成功后是否点击过

    public CertifiStatus(int status, boolean istrue) {
        this.status = status;
        this.istrue = istrue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean istrue() {
        return istrue;
    }

    public void setIstrue(boolean istrue) {
        this.istrue = istrue;
    }
}
