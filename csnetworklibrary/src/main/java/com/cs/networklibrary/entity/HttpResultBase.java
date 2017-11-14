package com.cs.networklibrary.entity;

/**
 * Created by chenshuai12619 on 2016/3/17 15:10.
 */
public interface HttpResultBase<T> {

	String getCode();

	String getMessage();

	T getData();

	boolean isSuccess();
}
