package com.medvision.vrmc.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cs.common.utils.RxBus;
import com.cs.networklibrary.entity.SubscriptionLogout;

import rx.Subscription;

import com.medvision.vrmc.utils.ActivityManager;
import com.medvision.vrmc.utils.ExitAppReceiver;
import com.medvision.vrmc.utils.SpUtils;

import com.medvision.vrmc.bean.LocalInfo;
import com.medvision.vrmc.utils.NetworkStat;

/**
 * Created by raytine on 2017/2/14.
 */

public class BaseActivity extends AppCompatActivity {
    private RxBus mRxBus = RxBus.getInstance();
    private Subscription mSubscription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerExitReceiver();
        ActivityManager.getInstance().attach(this);
        mSubscription = mRxBus.toObserverable(SubscriptionLogout.class)
                .subscribe(subscriptionLogout -> {
                            SpUtils instance = SpUtils.getInstance();
                            LocalInfo user = instance.getUser();
                            user.setFirstLogin(true);
                            instance.saveUser(user);
                            finish();
                        }
                );
    }
    private ExitAppReceiver exitReceiver = new ExitAppReceiver();
    // 自定义退出应用Action,实际应用中应该放到整个应用的Constant类中.
    private static final String EXIT_APP_ACTION = "com.micen.exit_app";

    private void registerExitReceiver() {

        IntentFilter exitFilter = new IntentFilter();
        exitFilter.addAction(EXIT_APP_ACTION);
        registerReceiver(exitReceiver, exitFilter);
    }

    private void unRegisterExitReceiver() {

        unregisterReceiver(exitReceiver);
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        ActivityManager.getInstance().deAttach(this);
        super.onDestroy();
        unRegisterExitReceiver();
    }

    public void exitApp() {
        Intent intent = new Intent();
        intent.setAction(EXIT_APP_ACTION);
        sendBroadcast(intent);
    }
    public int checkNetWork() {
        switch (NetworkStat.getNetWorkType(this)){
            case NetworkStat.NETWORKTYPE_INVALID:
                return 0;
            case NetworkStat.NETWORKTYPE_2G:
            case NetworkStat.NETWORKTYPE_3G:
            case NetworkStat.NETWORKTYPE_WAP:
                return 1;
            case NetworkStat.NETWORKTYPE_WIFI:
                return 2;
            default:
                return 0;
        }
    }
}
