package com.medvision.vrmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.google.gson.Gson;
import com.medvision.vrmc.R;
import com.medvision.vrmc.bean.FilterDisease;
import com.medvision.vrmc.bean.ModifPatientInfo;
import com.medvision.vrmc.bean.MyPatientDetil;
import com.medvision.vrmc.bean.requestbody.BaseReq;
import com.medvision.vrmc.bean.requestbody.ModifPatientreq;
import com.medvision.vrmc.network.ContentService;
import com.medvision.vrmc.utils.MyLog;
import com.medvision.vrmc.utils.NetworkStat;
import com.medvision.vrmc.utils.ToastCommom;
import com.medvision.vrmc.view.Navigation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 修改患者信息界面
 * Created by raytine on 2017/7/17.
 */

public class ModifyPatientActivity extends AppCompatActivity {
    @BindView(R.id.et_medical_number)
    EditText etMedicalNumber;
    @BindView(R.id.medical_number)
    LinearLayout medicalNumber;
    @BindView(R.id.et_patient_name)
    EditText etPatientName;
    @BindView(R.id.modif_ll_name)
    LinearLayout modifLlName;
    @BindView(R.id.et_patient_markname)
    EditText etPatientMarkname;
    @BindView(R.id.modif_ll_markname)
    LinearLayout modifLlMarkname;
    @BindView(R.id.et_bron_date)
    TextView etBronDate;
    @BindView(R.id.modif_ll_born)
    LinearLayout modifLlBorn;
    @BindView(R.id.tv_disease)
    TextView tvDisease;
    @BindView(R.id.modif_ll_disease)
    LinearLayout modifLlDisease;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.modif_ll_sex)
    LinearLayout modifLlSex;
    @BindView(R.id.et_phonenumber)
    EditText etPhonenumber;
    @BindView(R.id.modif_ll_phonebymber)
    LinearLayout modifLlPhonebymber;
    @BindView(R.id.tv_marry)
    TextView tvMarry;
    @BindView(R.id.modif_ll_marry)
    LinearLayout modifLlMarry;
    @BindView(R.id.tv_erducation)
    TextView tvErducation;
    @BindView(R.id.modif_ll_tv_erducation)
    LinearLayout modifLlTvErducation;
    @BindView(R.id.et_medical_card_number)
    EditText etMedicalCardNumber;
    @BindView(R.id.modif_ll_medical_card_number)
    LinearLayout modifLlMedicalCardNumber;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.modif_ll_age)
    LinearLayout modifLlAge;
    private int changType = 0;
    private ContentService contentService;
    private List<String> titles = new ArrayList<>();//病症字段集合
    private List<String> diseaseIds = new ArrayList<>();//病症字段集合
    private List<FilterDisease> diseases = new ArrayList<>();
    private List<String> sexs = new ArrayList<>();
    private List<String> marrys = new ArrayList<>();
    private List<String> educations = new ArrayList<>();
    private String patientId;
    private String diseaseId; //病症
    private String medical_nnumber; //病历号
    private String patient_name;//患者姓名
    private String patient_name_mark;//患者备注
    private String born_date; //出生日期
    private String disease; //病症
    private int sex;
    private String phone_bumber;
    private int marry = 0;
    private int education = 0;
    private String medical_card;//医保卡号
    private MyPatientDetil myPatientDetil;
    private Button btn_yes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modif_patient_activity);
        ButterKnife.bind(this);
        Navigation.getInstance(this).setBack().setTitle("修改信息").setRight("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_yes = (Button) v;
                responedResult(changType);
            }
        });
        contentService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        changType = getIntent().getIntExtra("type", 0);
        patientId = getIntent().getStringExtra("patientId");
        myPatientDetil = (MyPatientDetil) getIntent().getSerializableExtra("MyPatientDetil");
        initView(changType);
    }

    /**
     * 返回修改值
     *
     * @param changType
     */
    private void responedResult(int changType) {
        ModifPatientreq m = new ModifPatientreq();
        switch (changType) {
            case 1://改姓名
                patient_name = etPatientName.getText().toString().trim();
                if (patient_name.isEmpty()) {
                    ToastCommom.createInstance().ToastShow(this, "姓名不能为空");
                    break;
                }
                if (patient_name.equals(myPatientDetil.getName())) {
                    ToastCommom.createInstance().ToastShow(this, "请修改信息");
                    break;
                }
                m.setName(patient_name);
                m.setTs(myPatientDetil.getTs());
                m.setClinichistoryNo(myPatientDetil.getClinichistoryNo());
                m.setBirthday(myPatientDetil.getBirthday());
                m.setDiseaseId(myPatientDetil.getDiseaseId());
                m.setEducationDegree(myPatientDetil.getEducationDegree());
                m.setMaritalStatus(myPatientDetil.getMaritalStatus());
                m.setMedicalInsuranceCardNo(myPatientDetil.getMedicalInsuranceCardNo());
                m.setPhone(myPatientDetil.getPhone());
                m.setRemark(myPatientDetil.getRemark());
                m.setSex(myPatientDetil.getSex());
                m.setAge(myPatientDetil.getAge());
                modifPatient(m, patient_name);
                break;
            case 2://改备注
                patient_name_mark = etPatientMarkname.getText().toString().trim();
                if (patient_name_mark.isEmpty()) {
                    patient_name_mark = "";
                }
                if (patient_name_mark.equals(myPatientDetil.getRemark())) {
                    ToastCommom.createInstance().ToastShow(this, "请修改信息");
                    break;
                }
                m.setName(myPatientDetil.getName());
                m.setTs(myPatientDetil.getTs());
                m.setClinichistoryNo(myPatientDetil.getClinichistoryNo());
                m.setBirthday(myPatientDetil.getBirthday());
                m.setDiseaseId(myPatientDetil.getDiseaseId());
                m.setEducationDegree(myPatientDetil.getEducationDegree());
                m.setMaritalStatus(myPatientDetil.getMaritalStatus());
                m.setMedicalInsuranceCardNo(myPatientDetil.getMedicalInsuranceCardNo());
                m.setPhone(myPatientDetil.getPhone());
                m.setRemark(patient_name_mark);
                m.setSex(myPatientDetil.getSex());
                m.setAge(myPatientDetil.getAge());
                modifPatient(m, patient_name_mark);
                break;
            case 3://病历号
                medical_nnumber = etMedicalNumber.getText().toString();
                if (medical_nnumber.isEmpty()) {
                    ToastCommom.createInstance().ToastShow(this, "病历号不能为空");
                    break;
                }
                if (medical_nnumber.length() == 11) {
                    ToastCommom.createInstance().ToastShow(this, "病历号长度不能为11位");
                    break;
                }
                if (medical_nnumber.equals(myPatientDetil.getClinichistoryNo())) {
                    ToastCommom.createInstance().ToastShow(this, "请修改信息");
                    break;
                }
                m.setName(myPatientDetil.getName());
                m.setTs(myPatientDetil.getTs());
                m.setClinichistoryNo(medical_nnumber);
                m.setBirthday(myPatientDetil.getBirthday());
                m.setDiseaseId(myPatientDetil.getDiseaseId());
                m.setEducationDegree(myPatientDetil.getEducationDegree());
                m.setMaritalStatus(myPatientDetil.getMaritalStatus());
                m.setMedicalInsuranceCardNo(myPatientDetil.getMedicalInsuranceCardNo());
                m.setPhone(myPatientDetil.getPhone());
                m.setRemark(myPatientDetil.getRemark());
                m.setSex(myPatientDetil.getSex());
                m.setAge(myPatientDetil.getAge());
                modifPatient(m, medical_nnumber);
                break;
            case 4:
                medical_card = etMedicalCardNumber.getText().toString();
                if (medical_card.isEmpty()) {
                    medical_card = "";
                }
                if (medical_card.equals(myPatientDetil.getMedicalInsuranceCardNo())) {
                    ToastCommom.createInstance().ToastShow(this, "请修改信息");
                    break;
                }
                m.setName(myPatientDetil.getName());
                m.setTs(myPatientDetil.getTs());
                m.setClinichistoryNo(myPatientDetil.getClinichistoryNo());
                m.setBirthday(myPatientDetil.getBirthday());
                m.setDiseaseId(myPatientDetil.getDiseaseId());
                m.setEducationDegree(myPatientDetil.getEducationDegree());
                m.setMaritalStatus(myPatientDetil.getMaritalStatus());
                m.setMedicalInsuranceCardNo(medical_card);
                m.setPhone(myPatientDetil.getPhone());
                m.setRemark(myPatientDetil.getRemark());
                m.setSex(myPatientDetil.getSex());
                m.setAge(myPatientDetil.getAge());
                modifPatient(m, medical_card);
                break;
            case 5:
                disease = tvDisease.getText().toString();
                if (disease.isEmpty()) {
                    ToastCommom.createInstance().ToastShow(this, "病症不能为空");
                    break;
                }
                if (disease.equals(myPatientDetil.getDisease())) {
                    ToastCommom.createInstance().ToastShow(this, "请修改信息");
                    break;
                }
                m.setName(myPatientDetil.getName());
                m.setTs(myPatientDetil.getTs());
                m.setClinichistoryNo(myPatientDetil.getClinichistoryNo());
                m.setBirthday(myPatientDetil.getBirthday());
                m.setDiseaseId(diseaseId);
                m.setEducationDegree(myPatientDetil.getEducationDegree());
                m.setMaritalStatus(myPatientDetil.getMaritalStatus());
                m.setMedicalInsuranceCardNo(myPatientDetil.getMedicalInsuranceCardNo());
                m.setPhone(myPatientDetil.getPhone());
                m.setRemark(myPatientDetil.getRemark());
                m.setSex(myPatientDetil.getSex());
                m.setAge(myPatientDetil.getAge());
                modifPatient(m, disease, diseaseId);
                break;
            case 6:
                phone_bumber = etPhonenumber.getText().toString();
                if (phone_bumber.isEmpty()) {
                    phone_bumber = "";

                }
                if (phone_bumber.equals(myPatientDetil.getPhone())) {
                    ToastCommom.createInstance().ToastShow(this, "请修改信息");
                    break;
                }
                if (!NetworkStat.isMobileNO(phone_bumber) && !phone_bumber.isEmpty()) {
                    ToastUtil.showMessage(this, "手机格式不正确");
                    break;
                }
                m.setName(myPatientDetil.getName());
                m.setTs(myPatientDetil.getTs());
                m.setClinichistoryNo(myPatientDetil.getClinichistoryNo());
                m.setBirthday(myPatientDetil.getBirthday());
                m.setDiseaseId(myPatientDetil.getDiseaseId());
                m.setEducationDegree(myPatientDetil.getEducationDegree());
                m.setMaritalStatus(myPatientDetil.getMaritalStatus());
                m.setMedicalInsuranceCardNo(myPatientDetil.getMedicalInsuranceCardNo());
                m.setPhone(phone_bumber);
                m.setRemark(myPatientDetil.getRemark());
                m.setSex(myPatientDetil.getSex());
                modifPatient(m, phone_bumber);
                m.setAge(myPatientDetil.getAge());
                break;
            case 7:
                String s = tvSex.getText().toString();
                if (s.equals("女")) {
                    sex = 2;
                } else if (s.equals("男")) {
                    sex = 1;
                } else {
                    sex = 0;
                }
                if (sex == myPatientDetil.getSex()) {
                    ToastCommom.createInstance().ToastShow(this, "请修改信息");
                    break;
                }
                m.setName(myPatientDetil.getName());
                m.setTs(myPatientDetil.getTs());
                m.setClinichistoryNo(myPatientDetil.getClinichistoryNo());
                m.setBirthday(myPatientDetil.getBirthday());
                m.setDiseaseId(myPatientDetil.getDiseaseId());
                m.setEducationDegree(myPatientDetil.getEducationDegree());
                m.setMaritalStatus(myPatientDetil.getMaritalStatus());
                m.setMedicalInsuranceCardNo(myPatientDetil.getMedicalInsuranceCardNo());
                m.setPhone(myPatientDetil.getPhone());
                m.setRemark(myPatientDetil.getRemark());
                m.setSex(sex);
                m.setAge(myPatientDetil.getAge());
                modifPatient(m, s, sex);
                break;
            case 8:
                born_date = etBronDate.getText().toString();
                if (born_date.isEmpty()) {
                    ToastCommom.createInstance().ToastShow(this, "出生日期不能为空");
                    break;
                }
                if (born_date.equals(myPatientDetil.getBirthday())) {
                    ToastCommom.createInstance().ToastShow(this, "请修改信息");
                    break;
                }
                m.setName(myPatientDetil.getName());
                m.setTs(myPatientDetil.getTs());
                m.setClinichistoryNo(myPatientDetil.getClinichistoryNo());
                m.setBirthday(born_date + " 00:00:00");
                m.setDiseaseId(myPatientDetil.getDiseaseId());
                m.setEducationDegree(myPatientDetil.getEducationDegree());
                m.setMaritalStatus(myPatientDetil.getMaritalStatus());
                m.setMedicalInsuranceCardNo(myPatientDetil.getMedicalInsuranceCardNo());
                m.setPhone(myPatientDetil.getPhone());
                m.setRemark(myPatientDetil.getRemark());
                m.setSex(myPatientDetil.getSex());
                m.setAge(myPatientDetil.getAge());
                modifPatient(m, born_date);
                break;
            case 9:
                String s1 = tvErducation.getText().toString();
                if (s1.equals("文盲")) {
                    education = 1;
                } else if (s1.equals("小学")) {
                    education = 2;
                } else if (s1.equals("初中")) {
                    education = 3;
                } else if (s1.equals("高中")) {
                    education = 4;
                } else if (s1.equals("大学")) {
                    education = 5;
                } else if (s1.equals("研究生及以上")) {
                    education = 6;
                }
                if (education == myPatientDetil.getEducationDegree()) {
                    ToastCommom.createInstance().ToastShow(this, "请修改信息");
                    break;
                }
                m.setName(myPatientDetil.getName());
                m.setTs(myPatientDetil.getTs());
                m.setClinichistoryNo(myPatientDetil.getClinichistoryNo());
                m.setBirthday(myPatientDetil.getBirthday());
                m.setDiseaseId(myPatientDetil.getDiseaseId());
                m.setEducationDegree(education);
                m.setMaritalStatus(myPatientDetil.getMaritalStatus());
                m.setMedicalInsuranceCardNo(myPatientDetil.getMedicalInsuranceCardNo());
                m.setPhone(myPatientDetil.getPhone());
                m.setRemark(myPatientDetil.getRemark());
                m.setSex(myPatientDetil.getSex());
                m.setAge(myPatientDetil.getAge());
                modifPatient(m, s1, education);
                break;
            case 10:
                String s2 = tvMarry.getText().toString();
                if (s2.equals("已婚")) {
                    marry = 1;
                } else if (s2.equals("未婚")) {
                    marry = 2;
                } else {
                    marry = 0;
                }
                if (marry == myPatientDetil.getMaritalStatus()) {
                    ToastCommom.createInstance().ToastShow(this, "请修改信息");
                    break;
                }
                m.setName(myPatientDetil.getName());
                m.setTs(myPatientDetil.getTs());
                m.setClinichistoryNo(myPatientDetil.getClinichistoryNo());
                m.setBirthday(myPatientDetil.getBirthday());
                m.setDiseaseId(myPatientDetil.getDiseaseId());
                m.setEducationDegree(myPatientDetil.getEducationDegree());
                m.setMaritalStatus(myPatientDetil.getMaritalStatus());
                m.setMedicalInsuranceCardNo(myPatientDetil.getMedicalInsuranceCardNo());
                m.setPhone(myPatientDetil.getPhone());
                m.setRemark(myPatientDetil.getRemark());
                m.setSex(sex);
                m.setAge(myPatientDetil.getAge());
                modifPatient(m, s2, marry);
                break;
            case 11:
                String mAge = etAge.getText().toString();
                if (mAge.isEmpty()){
                    ToastCommom.createInstance().ToastShow(this, "年龄不能为空");
                    break;
                }
                if (Integer.valueOf(mAge) == myPatientDetil.getAge()) {
                    ToastCommom.createInstance().ToastShow(this, "请修改信息");
                    break;
                }
                m.setName(myPatientDetil.getName());
                m.setTs(myPatientDetil.getTs());
                m.setClinichistoryNo(myPatientDetil.getClinichistoryNo());
                m.setBirthday(myPatientDetil.getBirthday());
                m.setDiseaseId(myPatientDetil.getDiseaseId());
                m.setEducationDegree(myPatientDetil.getEducationDegree());
                m.setMaritalStatus(myPatientDetil.getMaritalStatus());
                m.setMedicalInsuranceCardNo(myPatientDetil.getMedicalInsuranceCardNo());
                m.setPhone(myPatientDetil.getPhone());
                m.setRemark(myPatientDetil.getRemark());
                m.setSex(sex);
                m.setAge(Integer.valueOf(mAge));
                modifPatient(m, mAge);
                break;

        }
    }

    /**
     * 修改信息
     */
    private void modifPatient(ModifPatientreq m, String dta) {
        m.setPatientId(patientId);
        Gson gson = new Gson();
        MyLog.e("---json", gson.toJson(m));
        contentService.modifMyPatientDetil(m)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<ModifPatientInfo>(this, o -> {
                    if (o.getCode() == 0) {
                        Intent intent = new Intent();
                        intent.putExtra("type", changType);
                        intent.putExtra("data", dta);
                        intent.putExtra("ts", o.getData().getTs());
                        setResult(13, intent);
                        finish();
                    } else {
                        MyLog.e("------erroe", o.getCode() + "code");
                        ToastCommom.createInstance().ToastShow(this, "患者信息修改失败");
                    }
                }));
    }

    /**
     * 修改信息,带额外的int参数
     */
    private void modifPatient(ModifPatientreq m, String dta, int data) {
        m.setPatientId(patientId);
        Gson gson = new Gson();
        MyLog.e("---json", gson.toJson(m));
        contentService.modifMyPatientDetil(m)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<ModifPatientInfo>(this, o -> {
                    if (o.getCode() == 0) {
                        Intent intent = new Intent();
                        intent.putExtra("type", changType);
                        intent.putExtra("data", dta);
                        intent.putExtra("extraInt", data);
                        intent.putExtra("ts", o.getData().getTs());
                        setResult(13, intent);
                        finish();
                    } else {
                        MyLog.e("------erroe", o.getCode() + "code");
                        ToastCommom.createInstance().ToastShow(this, "患者信息修改失败");
                    }
                }));
    }

    /**
     * 修改信息,带额外的String参数
     */
    private void modifPatient(ModifPatientreq m, String dta, String data) {
        m.setPatientId(patientId);
        Gson gson = new Gson();
        MyLog.e("---json", gson.toJson(m));
        contentService.modifMyPatientDetil(m)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<ModifPatientInfo>(this, o -> {
                    if (o.getCode() == 0) {
                        Intent intent = new Intent();
                        intent.putExtra("type", changType);
                        intent.putExtra("data", dta);
                        intent.putExtra("extraString", data);
                        intent.putExtra("ts", o.getData().getTs());
                        setResult(13, intent);
                        finish();
                    } else {
                        MyLog.e("------erroe", o.getCode() + "code");
                        ToastCommom.createInstance().ToastShow(this, "患者信息修改失败");
                    }
                }));
    }

    private void initView(int changType) {
        switch (changType) {
            case 1:
                etPatientName.setText(myPatientDetil.getName());
                modifLlName.setVisibility(View.VISIBLE);
                break;
            case 2:
                etPatientMarkname.setText(myPatientDetil.getRemark());
                modifLlMarkname.setVisibility(View.VISIBLE);
                break;
            case 3:
                etMedicalNumber.setText(myPatientDetil.getClinichistoryNo());
                medicalNumber.setVisibility(View.VISIBLE);
                break;
            case 4:
                etMedicalCardNumber.setText(myPatientDetil.getMedicalInsuranceCardNo());
                modifLlMedicalCardNumber.setVisibility(View.VISIBLE);
                break;
            case 5:
                tvDisease.setText(myPatientDetil.getDisease());
                modifLlDisease.setVisibility(View.VISIBLE);
                break;
            case 6:
                etPhonenumber.setText(myPatientDetil.getPhone());
                modifLlPhonebymber.setVisibility(View.VISIBLE);
                break;
            case 7:
                sexs.add("保密");
                sexs.add("男");
                sexs.add("女");
                if (myPatientDetil.getSex() == 0) {
                    tvSex.setText("保密");
                } else if (myPatientDetil.getSex() == 1) {
                    tvSex.setText("男");
                } else {
                    tvSex.setText("女");
                }
                modifLlSex.setVisibility(View.VISIBLE);
                break;
            case 8:
                etBronDate.setText(myPatientDetil.getBirthday().substring(0, 10));
                modifLlBorn.setVisibility(View.VISIBLE);
                break;
            case 9:
                if (myPatientDetil.getEducationDegree() == 1) {
                    tvErducation.setText("文盲");
                } else if (myPatientDetil.getEducationDegree() == 2) {
                    tvErducation.setText("小学");
                } else if (myPatientDetil.getEducationDegree() == 3) {
                    tvErducation.setText("初中");
                } else if (myPatientDetil.getEducationDegree() == 4) {
                    tvErducation.setText("高中");
                } else if (myPatientDetil.getEducationDegree() == 5) {
                    tvErducation.setText("大学");
                } else if (myPatientDetil.getEducationDegree() == 6) {
                    tvErducation.setText("研究生及以上");
                }
                educations.add("文盲");
                educations.add("小学");
                educations.add("初中");
                educations.add("高中");
                educations.add("大学");
                educations.add("研究生及以上");
                modifLlTvErducation.setVisibility(View.VISIBLE);
                break;
            case 10:
                if (myPatientDetil.getMaritalStatus() == 1) {
                    tvMarry.setText("已婚");
                } else if (myPatientDetil.getMaritalStatus() == 2) {
                    tvMarry.setText("未婚");
                } else {
                    tvMarry.setText("保密");
                }
                marrys.add("保密");
                marrys.add("已婚");
                marrys.add("未婚");
                modifLlMarry.setVisibility(View.VISIBLE);
                break;
            case 11:
                etAge.setText(myPatientDetil.getAge()+"");
                modifLlAge.setVisibility(View.VISIBLE);
                break;

        }

    }

    @OnClick({R.id.tv_disease, R.id.et_bron_date, R.id.tv_sex, R.id.tv_marry, R.id.tv_erducation})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_disease:
                if (titles.size() != 0) {
                    initPickerView(titles);
                } else {
                    requestDisease();
                }
                break;
            case R.id.et_bron_date:
                pickerTimer();
                break;
            case R.id.tv_sex:
                pickerSex();
                break;
            case R.id.tv_marry:
                pickerMarry();
                break;
            case R.id.tv_erducation:
                pickerEducation();
                break;

        }
    }

    private void requestDisease() {
        contentService.getFilterDisease(new BaseReq())
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<List<FilterDisease>>(this, o -> {
                    diseases = o;
                    for (int i = 0; i < diseases.size(); i++) {
                        titles.add(diseases.get(i).getDiseaseName());
                        diseaseIds.add(diseases.get(i).getDiseaseId());
                    }
                    initPickerView(titles);
                }));
    }

    private void pickerEducation() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String s = educations.get(options1);
                tvErducation.setText(s);
            }
        }).setTitleText("文化程度").build();
        pvOptions.setPicker(educations, null, null);
        pvOptions.show();
    }

    private void pickerMarry() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                tvMarry.setText(marrys.get(options1));
            }
        }).setTitleText("婚姻情况").build();
        pvOptions.setPicker(marrys, null, null);
        pvOptions.show();
    }

    private void pickerSex() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                tvSex.setText(sexs.get(options1));
            }
        }).setTitleText("选择性别").build();
        pvOptions.setPicker(sexs, null, null);
        pvOptions.show();
    }

    private void pickerTimer() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                etBronDate.setText(getTime(date));
            }
        }).setLabel("", "", "", "", "", "")
                .setType(TimePickerView.Type.YEAR_MONTH_DAY).setRangDate(null, Calendar.getInstance())
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    private void initPickerView(List<String> titles) {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                tvDisease.setText(titles.get(options1));
                diseaseId = diseaseIds.get(options1);
            }
        }).setTitleText("选择病症").build();
        pvOptions.setPicker(titles, null, null);
        pvOptions.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
