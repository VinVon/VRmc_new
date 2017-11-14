package com.medvision.vrmc.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by raytine on 2017/9/18.
 * activity管理类
 */

public class ActivityManager {
    private static volatile ActivityManager mInstance;
    private Stack<Activity> mActivits ;

    private ActivityManager() {
        mActivits = new Stack<>();
    }

    public static ActivityManager getInstance(){
        if (mInstance == null){
            synchronized (ActivityManager.class){
                if (mInstance == null){
                    mInstance = new ActivityManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 添加activity
     * @param mActivity
     */
    public void attach(Activity mActivity){
        mActivits.add(mActivity);
    }

    /**
     * 删除activity
     * @param mActivity
     */
    public void deAttach(Activity mActivity){
        int size = mActivits.size();
        for (int i = 0; i < size; i++) {
            Activity activity = mActivits.get(i);
            if (activity.getClass().getCanonicalName().equals(mActivity.getClass().getCanonicalName())){
                mActivits.remove(i);
                i--;
                size--;
            }
        }
    }
    /**
     * 关闭activity
     * @param mActivity
     */
    public void finish(Class<? extends Activity> mActivity){
        int size = mActivits.size();
        for (int i = 0; i < size; i++) {
            Activity activity = mActivits.get(i);
            if (activity.getClass().getCanonicalName().equals(mActivity.getName())){
                MyLog.e("finish",mActivity.getClass().getCanonicalName());
                mActivits.remove(i);
                activity.finish();
                i--;
                size--;
            }
        }
    }

    /**
     * 获取当前的Activity
     * @return
     */
    public Activity CurrentActivity(){
        return mActivits.lastElement();
    }
}
