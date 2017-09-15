package com.medvision.vrmc.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.app.Service;
/**
 * Created by raytine on 2017/2/14.
 */

public class ExitAppReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (context != null) {

            if (context instanceof Activity) {
                MyLog.i("exit", ((Activity) context).getLocalClassName());
                ((Activity) context).finish();
            } else if (context instanceof FragmentActivity) {
                MyLog.i("exit", ((Activity) context).getLocalClassName());
                ((FragmentActivity) context).finish();
            } else if (context instanceof Service) {
                MyLog.i("exit", ((Activity) context).getLocalClassName());
                ((Service) context).stopSelf();
            }
        }
    }
}
