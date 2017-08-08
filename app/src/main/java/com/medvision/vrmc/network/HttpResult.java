package com.medvision.vrmc.network;

import com.cs.networklibrary.entity.HttpResultBase;

/**
 * Created by cs on 16/9/28.
 */

public class HttpResult<T> implements HttpResultBase<T> {
    private String code;
    private String message;
    private T data;

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public boolean isSuccess() {
        return "0".equals(getCode());
    }
}

