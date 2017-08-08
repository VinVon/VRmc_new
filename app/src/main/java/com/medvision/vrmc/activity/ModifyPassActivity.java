package com.medvision.vrmc.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.medvision.vrmc.AuthorizationActivity;
import com.medvision.vrmc.R;
import com.medvision.vrmc.imp.ChangePassimp;
import com.medvision.vrmc.presenter.ModifyPassPresenter;
import com.medvision.vrmc.utils.SpUtils;

import com.medvision.vrmc.bean.LocalInfo;
import com.medvision.vrmc.utils.ToastCommom;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by raytine on 2017/4/25.
 */

public class ModifyPassActivity extends BaseActivity implements ChangePassimp {
    @BindView(R.id.old_pass)
    EditText oldPass;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.new_pass)
    EditText newPass;
    @BindView(R.id.ll_2)
    LinearLayout ll2;
    @BindView(R.id.new1_pass)
    EditText new1Pass;
    @BindView(R.id.modify_yes)
    Button modifyYes;
    @BindView(R.id.modify_back)
    ImageView modifyBack;
    private ModifyPassPresenter modifyPassPresenter;
    private String Token;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_activity);
        ButterKnife.bind(this);
        modifyPassPresenter = new ModifyPassPresenter(this);

        newPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pass = oldPass.getText().toString().trim();
                if (s.toString().trim().equals(pass)) {
                    ToastCommom.createInstance().ToastShow(ModifyPassActivity.this, "新密码不能与原密码一致");
                    return;
                }
            }
        });
    }

    @OnClick({R.id.modify_yes,R.id.modify_back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.modify_back:
                finish();
                break;
            case R.id.modify_yes:
                String text1 = oldPass.getText().toString().trim();
                String text2 = newPass.getText().toString().trim();
                String text3 = new1Pass.getText().toString().trim();
                if (text1.isEmpty()) {
                    ToastCommom.createInstance().ToastShow(ModifyPassActivity.this, "原密码不能为空");
                    return;
                }
                if (text2.isEmpty()) {
                    ToastCommom.createInstance().ToastShow(ModifyPassActivity.this, "新密码不能为空");
                    return;
                }
                if (text2.length()<6||text2.length()>16) {
                    ToastCommom.createInstance().ToastShow(ModifyPassActivity.this, "新密码不符合规范");
                    return;
                }
                if (text3.isEmpty()) {
                    ToastCommom.createInstance().ToastShow(ModifyPassActivity.this, "请输入密码确认");
                    return;
                }
                if (!text2.equals(text3)) {
                    ToastCommom.createInstance().ToastShow(ModifyPassActivity.this, "两次输入的新密码不一致");
                    return;
                }
                if (text1.equals(text2)) {
                    ToastCommom.createInstance().ToastShow(ModifyPassActivity.this, "新密码不能与原密码一致");
                    return;
                }
                Map<String, String> modify = new HashMap<>();
                modify.put("password", text2);
                modify.put("oldPassword", text1);
                LocalInfo user = SpUtils.getInstance().getUser();
                modify.put("token", user.getToken());
                modify.put("username", user.getUsername());
                modifyPassPresenter.modify(modify);
                break;
        }
    }

    @Override
    public void changeSuccess(String st) {
        LocalInfo user = SpUtils.getInstance().getUser();
        user.setFirstLogin(true);
        SpUtils.getInstance().saveUser(user);
        startActivity(new Intent(ModifyPassActivity.this, AuthorizationActivity.class));
        SweetAlertDialog ss = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(st)
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
        ss.show();

    }

    @Override
    public void changeFailed(String st) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(st)
                .show();
    }

    @Override
    public void showProgressDialog() {
        dialog = ProgressDialog.show(ModifyPassActivity.this, "修改密码", "Please wait...", true, false);
    }

    @Override
    public void hideProgressDialog() {
        dialog.dismiss();
    }

    @Override
    public void tokenchange() {

    }
}
