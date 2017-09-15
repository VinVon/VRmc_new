package com.medvision.vrmc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.medvision.vrmc.R;
import com.medvision.vrmc.bean.CertificationInfo;
import com.medvision.vrmc.bean.requestbody.BaseReq;
import com.medvision.vrmc.network.UserService;
import com.medvision.vrmc.view.Navigation;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.view.CircleTransform;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 用户基本信息界面
 * Created by raytine on 2017/8/16.
 */

public class AdminInfo extends AppCompatActivity {
    @BindView(R.id.admin_headImg)
    ImageView adminHeadImg;
    @BindView(R.id.admin_name)
    TextView adminName;
    @BindView(R.id.admin_sex)
    TextView adminSex;
    @BindView(R.id.admin_hospital)
    TextView adminHospital;
    @BindView(R.id.admin_zensuo)
    TextView adminZensuo;
    @BindView(R.id.admin_keshi)
    TextView adminKeshi;
    @BindView(R.id.admin_zhichen)
    TextView adminZhichen;
    @BindView(R.id.admin_city)
    TextView adminCity;
    @BindView(R.id.ll_hops)
    LinearLayout llHops;
    @BindView(R.id.ll_zensuo)
    LinearLayout llZensuo;
    @BindView(R.id.ll_keshi)
    LinearLayout llKeshi;
    private UserService userService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_info_activity);
        ButterKnife.bind(this);
        Navigation.getInstance(this).setBack().setTitle("基本信息");
        userService = HttpMethods.getInstance().getClassInstance(UserService.class);
        initData();
    }

    private void initData() {
        userService.requestCertificateInfo(new BaseReq())
                .map(new HttpResultFunc<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ProgressSubscriber<CertificationInfo>(this, o -> {
                    Picasso.with(AdminInfo.this).load(o.getHeadPictureUrl()).transform(new CircleTransform()).fit().into(adminHeadImg);
                    adminName.setText(o.getRealname());
                    if (o.getGender() == 1) {
                        adminSex.setText("男");
                    } else {
                        adminSex.setText("女");
                    }
                    if (o.getWorkplaceType() == 1) {
                        adminHospital.setText(o.getHospital());
                        adminKeshi.setText(o.getDepartment());
                       llZensuo.setVisibility(View.GONE);
                    } else {
                        llHops.setVisibility(View.GONE);
                        llKeshi.setVisibility(View.GONE);
                        adminZensuo.setText(o.getHospital());
                    }
                    adminZhichen.setText(o.getProfessionalTitle());
                    adminCity.setText(o.getRegionFullName());
                }));
    }
}
