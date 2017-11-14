package com.cs.networklibrary.util;

import com.cs.common.utils.RxBus;
import com.cs.networklibrary.entity.BGADownloadProgressEvent;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 获取下载进度工具
 * Created by raytine on 2017/6/27.
 */

public class ProgressUtils {
    private static RxBus mRxBus = RxBus.getInstance();
    static Observable<BGADownloadProgressEvent> getDownloadEventObservable() {
        return mRxBus.toObserverable(BGADownloadProgressEvent.class).observeOn(AndroidSchedulers.mainThread());
    }
}
