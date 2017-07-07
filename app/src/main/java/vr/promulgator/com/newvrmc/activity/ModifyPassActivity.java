package vr.promulgator.com.newvrmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vr.promulgator.com.newvrmc.R;
import vr.promulgator.com.newvrmc.imp.ChangePassimp;
import vr.promulgator.com.newvrmc.presenter.ModifyPassPresenter;
import vr.promulgator.com.newvrmc.utils.SpUtils;
import vr.promulgator.com.newvrmc.utils.ToastCommom;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import vr.promulgator.com.newvrmc.AuthorizationActivity;


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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_activity);
        ButterKnife.bind(this);
        modifyPassPresenter = new ModifyPassPresenter(this);
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
                if (text3.isEmpty()) {
                    ToastCommom.createInstance().ToastShow(ModifyPassActivity.this, "请输入密码确认");
                    return;
                }
                if (!text2.equals(text3)) {
                    ToastCommom.createInstance().ToastShow(ModifyPassActivity.this, "两次输入的新密码不一致");
                    return;
                }
                Map<String, String> modify = new HashMap<>();
                modify.put("newPassword", text2);
                modify.put("oldPassword", text1);
                Token = SpUtils.getInstance().getUser().getToken();
                modify.put("token", Token);
                modifyPassPresenter.modify(modify);
                break;
        }
    }

    @Override
    public void changeSuccess(String st) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(st)
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        startActivity(new Intent(ModifyPassActivity.this, AuthorizationActivity.class));
                    }
                })
                .show();

    }

    @Override
    public void changeFailed(String st) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(st)
                .show();
    }

    @Override
    public void tokenchange() {

    }
}
