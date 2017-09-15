package com.medvision.vrmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.medvision.vrmc.R;
import com.medvision.vrmc.bean.FilterDisease;
import com.medvision.vrmc.bean.FreshPatientList;
import com.medvision.vrmc.bean.requestbody.AddPatientreq;
import com.medvision.vrmc.bean.requestbody.BaseReq;
import com.medvision.vrmc.network.ContentService;
import com.medvision.vrmc.utils.NetworkStat;
import com.medvision.vrmc.utils.ToastCommom;
import com.medvision.vrmc.view.Navigation;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;

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
 * Created by raytine on 2017/7/11.
 */

public class AddPatientActivity extends AppCompatActivity {
    //    @BindView(R.id.add_patient_back)
//    ImageView addPatientBack;
//    @BindView(R.id.add_patient_next)
//    TextView addPatientNext;
    @BindView(R.id.tv_disease)
    TextView tvDisease;
    @BindView(R.id.et_bron_date)
    TextView etBronDate;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_marry)
    TextView tvMarry;
    @BindView(R.id.tv_erducation)
    TextView tvErducation;
    @BindView(R.id.et_medical_number)
    EditText etMedicalNumber;
    @BindView(R.id.et_patient_name)
    EditText etPatientName;
    @BindView(R.id.et_phonenumber)
    EditText etPhonenumber;
    @BindView(R.id.et_medical_card_number)
    EditText etMedicalCardNumber;
    @BindView(R.id.et_patient_markname)
    EditText etPatientMarkname;
    @BindView(R.id.et_age)
    EditText etAge;
    private ContentService contentService;
    private List<FilterDisease> diseases = new ArrayList<>();
    private List<String> titles = new ArrayList<>();//病症字段集合
    private List<String> diseaseIds = new ArrayList<>();//病症字段集合
    private List<String> sexs = new ArrayList<>();
    private List<String> marrys = new ArrayList<>();
    private List<String> educations = new ArrayList<>();
    private String medical_nnumber; //病历号
    private String patient_name;//患者姓名
    private String patient_name_mark;//患者备注
    private String born_date; //出生日期
    private String disease; //病症
    private String diseaseId; //病症
    private int sex;
    private String phone_bumber;
    private int marry = 0;
    private int education = 0;
    private String medical_card;//医保卡号
    private  int age = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpatient_activity);
        ButterKnife.bind(this);
        RxBus.getDefault().register(this);
        Navigation.getInstance(this).setBack().setTitle("增加患者").setRight("下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medical_nnumber = etMedicalNumber.getText().toString();
                if (medical_nnumber.isEmpty()) {
                    ToastUtil.showMessage(AddPatientActivity.this, "请输入病历号");
                    return;
                }
                if (medical_nnumber.length() == 11) {
                    ToastUtil.showMessage(AddPatientActivity.this, "病历号长度不能为11位");
                    return;
                }
                patient_name = etPatientName.getText().toString();
                if (patient_name.isEmpty()) {
                    ToastUtil.showMessage(AddPatientActivity.this, "请输入姓名");
                    return;
                }
//                born_date = etBronDate.getText().toString();
//                if (born_date.isEmpty()) {
//                    ToastUtil.showMessage(AddPatientActivity.this, "出生日期不能为空");
//                    return;
//                }
                if (etAge.getText() == null){
                    age = 0;
                }else {age =Integer.valueOf(etAge.getText().toString());}


                disease = tvDisease.getText().toString();
                if (disease.isEmpty()) {
                    ToastUtil.showMessage(AddPatientActivity.this, "病症不能为空");
                    return;
                }
                String s = tvSex.getText().toString();
                if (s.isEmpty()) {
                    ToastUtil.showMessage(AddPatientActivity.this, "性别不能为空");
                    return;
                } else {
                    if (s.equals("保密")) {
                        sex = 0;
                    } else if (s.equals("男")) {
                        sex = 1;
                    } else {
                        sex = 2;
                    }
                }
                phone_bumber = etPhonenumber.getText().toString();
                if (!NetworkStat.isMobileNO(phone_bumber) && !phone_bumber.isEmpty()) {
                    ToastUtil.showMessage(AddPatientActivity.this, "手机格式不正确");
                    return;
                }

                String s1 = tvMarry.getText().toString();
                if (s1.equals("未婚")) {
                    marry = 2;
                } else if (s1.equals("已婚")) {
                    marry = 1;
                } else {
                    marry = 0;
                }
                String s2 = tvErducation.getText().toString();
                if (s2.equals("文盲")) {
                    education = 1;
                } else if (s2.equals("小学")) {
                    education = 2;
                } else if (s2.equals("初中")) {
                    education = 3;
                } else if (s2.equals("高中")) {
                    education = 4;
                } else if (s2.equals("大学")) {
                    education = 5;
                } else if (s2.equals("研究生及以上")) {
                    education = 6;
                }
                patient_name_mark = etPatientMarkname.getText().toString();
                medical_card = etMedicalCardNumber.getText().toString();
                AddPatientreq req = new AddPatientreq();
                req.setRoomId(patient_name_mark);
                req.setClinichistoryNo(medical_nnumber);
                req.setName(patient_name);
//                req.setBirthday(born_date + " 00:00:00");
                req.setDiseaseId(diseaseId);
                req.setSex(sex);
                req.setPhone(phone_bumber);
                req.setMaritalStatus(marry);
                req.setEducationDegree(education);
                req.setMedicalInsuranceCardNo(medical_card);
                req.setAge(age);
                requestAddPatient(req);
            }
        });
        contentService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        initData();
    }

    private void initData() {
        sexs.add("保密");
        sexs.add("男");
        sexs.add("女");
        marrys.add("保密");
        marrys.add("已婚");
        marrys.add("未婚");
        educations.add("文盲");
        educations.add("小学");
        educations.add("初中");
        educations.add("高中");
        educations.add("大学");
        educations.add("研究生及以上");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().register(this);
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

    /**
     * 添加患者
     */
    private void requestAddPatient(AddPatientreq req) {
        contentService.addPatient(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<>(this, o -> {
                    if (o.getCode().equals("0")) {
                        RxBus.getDefault().post(new FreshPatientList());
                        Intent intent = new Intent(AddPatientActivity.this, PatientDetilActivity.class);
                        intent.putExtra("patientId", o.getData().getId());
                        startActivity(intent);
                    } else {
                        ToastCommom.createInstance().ToastShow(AddPatientActivity.this, o.getMessage());
                    }
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
        }).setLabel("", "", "", "", "", "").setRangDate(null, Calendar.getInstance())
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .build();

        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
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
