package com.cs.networklibrary.http;

import android.util.Log;

import com.cs.networklibrary.entity.HttpResultBase;
import com.google.gson.Gson;

import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class HttpResultFunc<T> implements Func1<HttpResultBase<T>, T> {

    @Override
    public T call(HttpResultBase<T> httpResult) {
	    if (!httpResult.isSuccess()) {
		    throw new RuntimeException(httpResult.getMessage());
	    }
        return httpResult.getData();
    }
}