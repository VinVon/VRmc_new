package com.medvision.vrmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.medvision.vrmc.R;
import com.medvision.vrmc.bean.FreshPatientList;
import com.medvision.vrmc.bean.MyPatientDetil;
import com.medvision.vrmc.bean.requestbody.Patientreq;
import com.medvision.vrmc.network.ContentService;
import com.medvision.vrmc.utils.SpUtils;
import com.medvision.vrmc.utils.ToastCommom;
import com.medvision.vrmc.view.LoveLayout;
import com.medvision.vrmc.view.Navigation;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 患者详情界面
 * Created by raytine on 2017/7/17.
 */

public class PatientDetilActivity extends AppCompatActivity {
    @BindView(R.id.xinjing_name)
    TextView xinjingName;
    @BindView(R.id.xinjing_markName)
    TextView xinjingMarkName;
    @BindView(R.id.xinjing_hosptal)
    TextView xinjingHosptal;
    @BindView(R.id.xinjing_disease)
    TextView xinjingDisease;
    @BindView(R.id.xinjing_phonenumber)
    TextView xinjingPhonenumber;
    @BindView(R.id.xinjing_sex)
    TextView xinjingSex;
    @BindView(R.id.xinjing_born)
    TextView xinjingBorn;
    @BindView(R.id.xinjing_erducation)
    TextView xinjingErducation;
    @BindView(R.id.xinjing_marry)
    TextView xinjingMarry;
    @BindView(R.id.xingjing_history)
    TextView xingjingHistory;
    @BindView(R.id.xinjing_phone)
    TextView xinjingPhone;//病历号
    @BindView(R.id.xinjing_medical_card_number)
    TextView xinjingMedicalCardNumber;
    @BindView(R.id.img_add_prescription)
    Button imgAddPrescription;
    @BindView(R.id.love_view)
    LoveLayout loveView;

    private String patientId;
    private ContentService contentService;
    private boolean changePermissions = false;//修改患者信息权限
    private final int REQUEST_CODE = 12;
    private final int REQUEST_CODES = 14;
    private final int RESPONED_CODE = 13;
    private MyPatientDetil myPatientDetil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_patient_detil);
        ButterKnife.bind(this);
        RxBus.getDefault().register(this);
        Navigation.getInstance(this).setBacks(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientDetilActivity.this, MyPatientActivity.class));
            }
        }).setTitle("患者详情");
        patientId = getIntent().getStringExtra("patientId");
        contentService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }

    private void initData() {
        Patientreq patientreq = new Patientreq();
        patientreq.setPatientId(patientId);
        contentService.getMyPatientDetil(patientreq)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<MyPatientDetil>(this, o -> {
                    myPatientDetil = o;
                    if (o.getCanModify() == 1) {
                        changePermissions = true;
                    } else {
                        changePermissions = false;
                    }
                    xinjingName.setText(o.getName());
                    xinjingMarkName.setText(o.getRemark());
                    xinjingHosptal.setText(SpUtils.getInstance().getLogin().getData().getHospital());
                    xinjingPhone.setText(o.getClinichistoryNo());
                    xinjingDisease.setText(o.getDisease());
                    xinjingPhonenumber.setText(o.getPhone());
                    xinjingMedicalCardNumber.setText(o.getMedicalInsuranceCardNo());
                    if (o.getSex() == 0) {
                        xinjingSex.setText("保密");
                    } else if (o.getSex() == 1) {
                        xinjingSex.setText("男");
                    } else {
                        xinjingSex.setText("女");
                    }
                    xinjingBorn.setText(o.getBirthday().substring(0, 10));
                    if (o.getEducationDegree() == 1) {
                        xinjingErducation.setText("文盲");
                    } else if (o.getEducationDegree() == 2) {
                        xinjingErducation.setText("小学");
                    } else if (o.getEducationDegree() == 3) {
                        xinjingErducation.setText("初中");
                    } else if (o.getEducationDegree() == 4) {
                        xinjingErducation.setText("高中");
                    } else if (o.getEducationDegree() == 5) {
                        xinjingErducation.setText("大学");
                    } else if (o.getEducationDegree() == 6) {
                        xinjingErducation.setText("研究生及以上");
                    }
                    if (o.getMaritalStatus() == 0) {
                        xinjingMarry.setText("保密");
                    } else if (o.getMaritalStatus() == 1) {
                        xinjingMarry.setText("已婚");
                    } else if (o.getMaritalStatus() == 2) {
                        xinjingMarry.setText("未婚");
                    }
                }));
    }

    @OnClick({R.id.xinjing_name, R.id.xinjing_markName, R.id.xinjing_phone, R.id.xinjing_disease,
            R.id.xinjing_phonenumber, R.id.xinjing_sex, R.id.xinjing_born, R.id.xinjing_erducation,
            R.id.xinjing_marry, R.id.xinjing_medical_card_number, R.id.img_add_prescription, R.id.xingjing_history})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.xinjing_name:
                changeInfos(1);
                break;
            case R.id.xinjing_markName:
                changeInfos(2);
                break;
            case R.id.xinjing_phone://病历号
                changeInfos(3);
                break;
            case R.id.xinjing_medical_card_number:
                changeInfos(4);
                break;
            case R.id.xinjing_disease:
                changeInfos(5);
                break;
            case R.id.xinjing_phonenumber:
                changeInfos(6);
                break;
            case R.id.xinjing_sex:
                changeInfos(7);
                break;
            case R.id.xinjing_born:
                changeInfos(8);
                break;
            case R.id.xinjing_erducation:
                changeInfos(9);
                break;
            case R.id.xinjing_marry:
                changeInfos(10);
                break;
            case R.id.img_add_prescription:
                Intent intent = new Intent(PatientDetilActivity.this, AddPrescriptionActivity.class);
                intent.putExtra("patientId", patientId);
                startActivityForResult(intent, REQUEST_CODES);
                break;
            case R.id.xingjing_history:
                Intent intents = new Intent(PatientDetilActivity.this, HistoryPrescriptionActivity.class);
                intents.putExtra("patientId", patientId);
                startActivity(intents);
                break;
        }
    }

    /**
     * 修改患者信息
     *
     * @param i
     */
    private void changeInfos(int i) {
        if (i == 2) {//修改备注名
            Intent intent = new Intent(PatientDetilActivity.this, ModifyPatientActivity.class);
            intent.putExtra("type", i);
            intent.putExtra("patientId", patientId);
            intent.putExtra("MyPatientDetil", myPatientDetil);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            if (changePermissions) {
                Intent intent = new Intent(PatientDetilActivity.this, ModifyPatientActivity.class);
                intent.putExtra("type", i);
                intent.putExtra("patientId", patientId);
                intent.putExtra("MyPatientDetil", myPatientDetil);
                startActivityForResult(intent, REQUEST_CODE);
            } else {
                ToastCommom.createInstance().ToastShow(this, "无权限修改该患者信息");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESPONED_CODE) {
            if (requestCode == REQUEST_CODE) {
                int types = data.getIntExtra("type", 0);
                String datas = data.getStringExtra("data");
                String ts = data.getStringExtra("ts");
                String extraString = null;
                myPatientDetil.setTs(ts);
                int extraInt = 0;
                if (types == 5) {
                    extraString = data.getStringExtra("extraString");
                }
                if (types == 7 || types == 9 || types == 10) {
                    extraInt = data.getIntExtra("extraInt", 1);
                }
                RxBus.getDefault().post(new FreshPatientList());
                indentify(types, datas, extraString, extraInt);
            } else if (requestCode == REQUEST_CODES) {
                ToastUtil.showMessage(PatientDetilActivity.this, "处方添加成功");
                for (int i = 0; i < 10; i++) {
                    loveView.addImageView();
                }
            }
        }
    }

    private void indentify(int types, String datas, String extraString, int extraInt) {
        switch (types) {
            case 1:
                myPatientDetil.setName(datas);
                xinjingName.setText(datas);
                break;
            case 2:
                myPatientDetil.setRemark(datas);
                xinjingMarkName.setText(datas);
                break;
            case 3://病历号
                myPatientDetil.setClinichistoryNo(datas);
                xinjingPhone.setText(datas);
                break;
            case 4:
                myPatientDetil.setMedicalInsuranceCardNo(datas);
                xinjingMedicalCardNumber.setText(datas);
                break;
            case 5:
                myPatientDetil.setDiseaseId(extraString);
                myPatientDetil.setDisease(datas);
                xinjingDisease.setText(datas);
                break;
            case 6:
                myPatientDetil.setPhone(datas);
                xinjingPhonenumber.setText(datas);
                break;
            case 7:
                myPatientDetil.setSex(extraInt);
                xinjingSex.setText(datas);
                break;
            case 8:
                myPatientDetil.setBirthday(datas);
                xinjingBorn.setText(datas);
                break;
            case 9:
                myPatientDetil.setEducationDegree(extraInt);
                xinjingErducation.setText(datas);
                break;
            case 10:
                myPatientDetil.setMaritalStatus(extraInt);
                xinjingMarry.setText(datas);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(PatientDetilActivity.this, MyPatientActivity.class));
        }
        return true;
    }
}
