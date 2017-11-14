package com.cs.networklibrary.http;

/**
 * Created by chenshuai12619 on 16/3/10.
 */
public class ApiException extends RuntimeException {

	public ApiException(String detailMessage) {
		super(detailMessage);
	}
}

