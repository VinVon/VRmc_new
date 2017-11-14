package com.medvision.vrmc.bean.requestbody;

/**
 * 分页请求
 * Created by raytine on 2017/10/31.
 */

public class PagingReq extends BaseReq {
    private int paging;

    public PagingReq(int paging) {
        this.paging = paging;
    }

    public int getPaging() {
        return paging;
    }

    public void setPaging(int paging) {
        this.paging = paging;
    }
}
