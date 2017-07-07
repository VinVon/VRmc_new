package vr.promulgator.com.newvrmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import vr.promulgator.com.newvrmc.MainActivity;
import vr.promulgator.com.newvrmc.presenter.LoginPresenter;
import vr.promulgator.com.newvrmc.bean.IConstant;
import vr.promulgator.com.newvrmc.bean.LocalInfo;
import vr.promulgator.com.newvrmc.utils.ToastCommom;

import java.util.HashMap;
import java.util.Map;

import vr.promulgator.com.newvrmc.AuthorizationActivity;

import vr.promulgator.com.newvrmc.R;

import vr.promulgator.com.newvrmc.bean.LoginInfo;
import vr.promulgator.com.newvrmc.imp.LoginView;
import vr.promulgator.com.newvrmc.utils.SpUtils;

/**
 * Created by raytine on 2017/2/11.
 */

public class WelcomeActivity extends BaseActivity implements LoginView{
    private static String device_model ;
    private static String version_release ;
    private static String version ;
    private static String device_id ;
    private String username;
    private String passworld;
    private boolean isfirst ;
    LoginPresenter lp ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        getData();
        handler.sendEmptyMessageDelayed(0,3000);

    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int i = checkNetWork();
            if(i == 0 ){
                ToastCommom.createInstance().ToastShow(WelcomeActivity.this,"请检查网络连接情况");
                exitApp();
            }else{
            if (isfirst){
                getHome();
            }else{
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
//                getLogin();
//                getHome();
            }
            }
            super.handleMessage(msg);
        }
    };

    public void getHome() {
        Intent intent = new Intent(WelcomeActivity.this, AuthorizationActivity.class);
        startActivity(intent);
        finish();
    }
    public void getLogin(){
        device_model = IConstant.getModel();
        version = IConstant.getVersion(this);
        device_id = IConstant.devoceId(this);
        version_release = IConstant.getVersionRelease();
        Map<String, String> priArgs = new HashMap<>();
        priArgs.put("appId", device_id);
        priArgs.put("appVersion", version);
        priArgs.put("channel", "null");
        priArgs.put("deviceModel", device_model);
        priArgs.put("deviceSystem", "android");
        priArgs.put("deviceVersion", version_release);
        priArgs.put("password", passworld);
        priArgs.put("username", username);
        lp = new LoginPresenter(WelcomeActivity.this,priArgs);
        lp.Login();
    }
    public void getData(){
        SpUtils instance = SpUtils.getInstance();
        instance.init(WelcomeActivity.this);
        LocalInfo user = instance.getUser();
       if (user == null){
           isfirst = true;
       }else{
           isfirst =  user.isFirstLogin();
           username=  user.getUsername();
           passworld= user.getPassword();
       }

    }

    @Override
    public void updateView(LoginInfo user) {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String msg) {

    }
}
