package com.cs.networklibrary.entity;

/**
 * 下载进度对象
 * Created by raytine on 2017/6/27.
 */

public class BGADownloadProgressEvent {
    /**
     * 文件总大小
     */
    private long mTotal;
    /**
     * 当前下载进度
     */
    private long mProgress;

    public BGADownloadProgressEvent(long total, long progress) {
        mTotal = total;
        mProgress = progress;
    }

    /**
     * 获取文件总大小
     *
     * @return
     */
    public long getTotal() {
        return mTotal;
    }

    /**
     *
     * @return
     */
    public long getProgress() {
        return mProgress;
    }

    /**
     * 是否还没有下载完成
     *
     * @return
     */
    public boolean isNotDownloadFinished() {
        return mTotal != mProgress;
    }
}
