package com.medvision.vrmc;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cs.networklibrary.http.HttpMethods;
import com.medvision.vrmc.R;
import com.medvision.vrmc.bean.LoginInfo;
import com.medvision.vrmc.imp.LoginView;

import com.medvision.vrmc.presenter.LoginPresenter;
import com.medvision.vrmc.bean.IConstant;
import com.medvision.vrmc.bean.LocalInfo;
import com.medvision.vrmc.network.UserService;
import com.medvision.vrmc.utils.SpUtils;
import com.medvision.vrmc.utils.ToastCommom;
import com.medvision.vrmc.MainActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * 授权页
 * Created by 123 on 2017/2/4.
 */

public class AuthorizationActivity extends AppCompatActivity implements LoginView {
    @BindView(R.id.phonenumber)
    EditText phonenumber;
    @BindView(R.id.hospital)
    EditText hospital;
    @BindView(R.id.img_1)
    Button img1;
    @BindView(R.id.login_help)
    TextView loginHelp;
    private ProgressDialog dialog;
    private String path = "http://test.med-vision.cn/api/v1/appVrRoom/login";
    private String device_model = "";
    private String version_release = "";
    private String version;
    private String device_id = "";
    private String username;
    private String passworld;
    private boolean isfirst;
    private LoginPresenter loginPresenter;
    private UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorrization);
        ButterKnife.bind(this);
        userService  = HttpMethods.getInstance().getClassInstance(UserService.class);
        getData();
//        if(isfirst){
//        }else{
//            phonenumber.setText(username);
//            hospital.setText(passworld);
//        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        } else {
            device_id = IConstant.devoceId(this);//String
        }
        version = IConstant.getVersion(this);
        device_model = IConstant.getModel();
        version_release = IConstant.getVersionRelease();
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = phonenumber.getText().toString();
                passworld = hospital.getText().toString();
                Map<String, String> priArgs = new HashMap<>();
                priArgs.put("appId", device_id);
                priArgs.put("appVersion", version);
                priArgs.put("channel", "null");
                priArgs.put("deviceModel", device_model);
                priArgs.put("deviceSystem", "android");
                priArgs.put("deviceVersion", version_release);
                priArgs.put("password", passworld);
                priArgs.put("username", username);
//                UserReq req  = new UserReq();
//                req.setAppId(device_id);
//                req.setAppVersion(version);
//                req.setChannel("null");
//                req.setDeviceModel(device_model);
//                req.setDeviceSystem("android");
//                req.setDeviceVersion(version_release);
//                req.setPassword(passworld);
//                req.setUsername(username);
                loginPresenter = new LoginPresenter(AuthorizationActivity.this, priArgs);
                loginPresenter.Login();

            }
        });
    }
    private boolean isExit = false;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }

    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                ToastCommom.createInstance().ToastShow(getApplicationContext(), "再按一次退出程序");
                mHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                System.exit(0);
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    @Override
    public void updateView(LoginInfo user) {
        saveData(false, username, passworld, user.getData().getToken(), user);
        Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.login_help)
    public  void OnClick(View v){
        switch (v.getId()){
            case R.id.login_help:
                new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("联系客服").setContentText("账号问题请联系客服处理!"+"\n"+"客服电话:400-166-7866")
                        .setCancelText("取消")
                        .setConfirmText("确定")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4001667866"));
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                            }
                        }).show();
                break;
        }
    }
    @Override
    public void showProgressDialog() {
        dialog = ProgressDialog.show(AuthorizationActivity.this, "账号登录中", "Please wait...", true, false);
    }

    @Override
    public void hideProgressDialog() {
        dialog.dismiss();
    }

    @Override
    public void showError(String msg) {
        ToastCommom.createInstance().ToastShow(AuthorizationActivity.this, msg);
    }

    public void saveData(boolean st, String name, String pass, String token, LoginInfo infos) {
        LocalInfo info = new LocalInfo(name, pass, st, token);
        SpUtils instance = SpUtils.getInstance();
        instance.init(AuthorizationActivity.this);
        instance.saveUser(info);
        instance.saveLogin(infos);
    }

    public void getData() {
        SpUtils instance = SpUtils.getInstance();
        instance.init(AuthorizationActivity.this);
        LocalInfo user = instance.getUser();
        if (user == null) {
            isfirst = true;
        } else {
            isfirst = user.isFirstLogin();
            username = user.getUsername();
            passworld = user.getPassword();
        }
    }
}
