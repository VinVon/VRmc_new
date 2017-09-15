package com.medvision.vrmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.medvision.vrmc.R;
import com.medvision.vrmc.bean.requestbody.RegisterReq;
import com.medvision.vrmc.bean.requestbody.SmsReq;
import com.medvision.vrmc.bean.requestbody.findReq;
import com.medvision.vrmc.network.UserService;
import com.medvision.vrmc.utils.NetworkStat;
import com.medvision.vrmc.view.Navigation;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 找回密码界面
 * Created by raytine on 2017/8/17.
 */

public class FindPassActivity extends AppCompatActivity {
    @BindView(R.id.register_username_et)
    EditText registerUsernameEt;
    @BindView(R.id.register_send_sms_bt)
    Button registerSendSmsBt;
    @BindView(R.id.register_sms_et)
    EditText registerSmsEt;
    @BindView(R.id.register_pwd_et)
    EditText registerPwdEt;
    @BindView(R.id.register_confirm_pwd_et)
    EditText registerConfirmPwdEt;
    @BindView(R.id.register_submit_bt)
    Button registerSubmitBt;

    private UserService userService;
    private String smsCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpass_activtiy);
        ButterKnife.bind(this);
        Navigation.getInstance(this).setBack().setTitle("找回密码");
        userService = HttpMethods.getInstance().getClassInstance(UserService.class);
    }

    @OnClick({R.id.register_send_sms_bt, R.id.register_submit_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_send_sms_bt:
                String phone = registerUsernameEt.getText().toString();
                if (phone.isEmpty() || !NetworkStat.isMobileNO(phone)) {
                    ToastUtil.showMessage(FindPassActivity.this, "手机号不正确,请检查!");
                    return;
                }
                requestSms(new SmsReq(phone, 2));
                break;
            case R.id.register_submit_bt:
                if (checkDataComplete()) {
                    requestRegister(new findReq( registerSmsEt.getText().toString(), registerPwdEt.getText().toString(),registerUsernameEt.getText().toString()));
                }

                break;
        }
    }
    private void requestRegister(findReq registerReq) {
        userService.findPassword(registerReq)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<>(FindPassActivity.this, o -> {
                    new SweetAlertDialog(FindPassActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("密码找回成功")
                            .setConfirmText("确认")
                            .setConfirmClickListener(sweetAlertDialog -> {
                                sweetAlertDialog.dismiss();
//                                Intent i = new Intent();
//                                i.putExtra("username", registerUsernameEt.getText().toString());
//                                i.putExtra("password", registerPwdEt.getText().toString());
//                                setResult(RESULT_OK, i);
                                finish();
                            })
                            .show();

                }));
    }
    private void requestSms(SmsReq smsReq) {
        userService.requestSms(smsReq)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<>(FindPassActivity.this, o ->{
                    startTimer();
                    smsCode = o;
                }
                ));
    }
    private void startTimer() {
        registerSendSmsBt.setTextColor(getResources().getColor(R.color.gray));
        registerSendSmsBt.setText(String.format("获取验证码（%s）", "60"));
        registerSendSmsBt.setEnabled(false);
        Observable.interval(1, TimeUnit.SECONDS).take(59)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        registerSendSmsBt.setText("获取验证码");
                        registerSendSmsBt.setTextColor(getResources().getColor(R.color.colorPrimary));
                        registerSendSmsBt.setEnabled(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                        registerSendSmsBt.setText(String.format("获取验证码（%s）", (59l - aLong) + ""));
                    }
                });
    }
    private boolean checkDataComplete() {
        boolean b = true;
        if (TextUtils.isEmpty(registerUsernameEt.getText().toString())) {
            ToastUtil.showMessage(this, "手机号不能为空");
            b = false;
        }
        if (TextUtils.isEmpty(registerSmsEt.getText().toString())) {
            ToastUtil.showMessage(this, "验证码不能为空");
            b = false;
        }
//		if (!smsCode.isEmpty() && !mRegisterSmsEt.getText().toString().equals(smsCode)) {
//			ToastUtil.showMessage(this, "验证码不正确");
//			b = false;
//		}
        if (TextUtils.isEmpty(registerPwdEt.getText().toString()) || registerPwdEt.getText().toString().length() < 6) {
            ToastUtil.showMessage(this, "密码不能少于6位");
            b = false;
        }
        if (!registerPwdEt.getText().toString().equals(registerConfirmPwdEt.getText().toString())) {
            ToastUtil.showMessage(this, "两次密码输入不一致");
            b = false;
        }
        return b;
    }
}
