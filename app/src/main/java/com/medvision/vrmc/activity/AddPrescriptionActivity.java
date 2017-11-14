package com.medvision.vrmc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.google.gson.Gson;
import com.medvision.vrmc.R;
import com.medvision.vrmc.adapter.FavoriteStationListViewAdapter;
import com.medvision.vrmc.bean.HomeContent;
import com.medvision.vrmc.bean.PrescriptionInfo;
import com.medvision.vrmc.bean.SingerSchemeInfo;
import com.medvision.vrmc.bean.requestbody.AddPrescriptionReq;
import com.medvision.vrmc.bean.requestbody.AddPrescriptionsReq;
import com.medvision.vrmc.network.ContentService;
import com.medvision.vrmc.utils.MyLog;
import com.medvision.vrmc.utils.NetworkStat;
import com.medvision.vrmc.view.ListViewForScrollView;
import com.medvision.vrmc.view.LoveLayout;
import com.medvision.vrmc.view.Navigation;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;
import com.wzgiceman.rxbuslibrary.rxbus.Subscribe;
import com.wzgiceman.rxbuslibrary.rxbus.SubscriberMethod;
import com.wzgiceman.rxbuslibrary.rxbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 开处方
 * Created by raytine on 2017/7/11.
 */

public class AddPrescriptionActivity extends BaseActivity implements FavoriteStationListViewAdapter.SetClick {
    @BindView(R.id.prescription_et1)
    EditText prescriptionEt1;
    @BindView(R.id.prescription_et2)
    EditText prescriptionEt2;
    @BindView(R.id.prescription_recycle)
    ListViewForScrollView prescriptionRecycle;
    @BindView(R.id.prescription_add)
    LinearLayout prescriptionAdd;
    private ArrayList<String> strings = new ArrayList<>();
    private FavoriteStationListViewAdapter adapter;

    private boolean aBoolean = true;
    private String disease;
    private String hospotalContent;
    private ContentService contentService;
    private ArrayList<HomeContent> homeContentList = new ArrayList<>();
    private ArrayList<AddPrescriptionsReq> addsList = new ArrayList<>();
    private String userId = null;//患者id
    private double totalPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        ButterKnife.bind(this);
        RxBus.getDefault().register(this);
        contentService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        userId = getIntent().getStringExtra("patientId");
        Navigation.getInstance(this).setBack().setTitle("医嘱内容").setRight("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disease = prescriptionEt1.getText().toString().trim();
                if (disease.isEmpty()) {
                    ToastUtil.showMessage(AddPrescriptionActivity.this, "请输入病症");
                    return;
                }
                if (NetworkStat.containsEmoji(disease)) {
                    ToastUtil.showMessage(AddPrescriptionActivity.this, "不能为非法字符");
                    return;
                }
                hospotalContent = prescriptionEt2.getText().toString().trim();
                if (hospotalContent.isEmpty()) {
                    ToastUtil.showMessage(AddPrescriptionActivity.this, "请输入医嘱疗法");
                    return;
                }
                if (NetworkStat.containsEmoji(hospotalContent)) {
                    ToastUtil.showMessage(AddPrescriptionActivity.this, "不能为非法字符");
                    return;
                }
                if (homeContentList.size() == 0) {
                    ToastUtil.showMessage(AddPrescriptionActivity.this, "请添加疗法内容");
                    return;
                }
                if (userId == null) {
                    ToastUtil.showMessage(AddPrescriptionActivity.this, "患者信息丢失，请重试");
                    return;
                }
                for (int i = 0; i < homeContentList.size(); i++) {
                    HomeContent homeContent = homeContentList.get(i);
                    if (homeContent.getFrequency() == 0 || homeContent.getPeriod() == 0) {
                        ToastUtil.showLongMessage(AddPrescriptionActivity.this, homeContent.getName() + "治疗周期未设置");
                        return;
                    }
                }
                if (addsList.size() != 0) {
                    addsList.clear();
                }
                AddPrescriptionReq addp = new AddPrescriptionReq();
                addp.setUserId(userId);
                addp.setDisease(disease);
                addp.setSuggestion(hospotalContent);
                addp.setPrice(totalPrice);
                for (int i = 0; i < homeContentList.size(); i++) {
                    HomeContent homeContent = homeContentList.get(i);
                    AddPrescriptionsReq adds = new AddPrescriptionsReq();
                    adds.setContentId(homeContent.getId());
                    adds.setPeriodUnit(homeContent.getPeriodUnit());
                    adds.setPeriod(homeContent.getPeriod());
                    adds.setFrequency(homeContent.getFrequency());
                    adds.setTimes(homeContent.getFrequency() * homeContent.getPeriod());
                    addsList.add(adds);
                }
                addp.setPrescriptionContentList(addsList);
                Gson gson = new Gson();
                String st = gson.toJson(addp);
                MyLog.e("TAG", st);
                toAddprescription(addp);
            }
        });
        initView();
    }

    private void initView() {
        adapter = new FavoriteStationListViewAdapter(this, homeContentList, this);
        prescriptionRecycle.setAdapter(adapter);
    }

    @OnClick({R.id.prescription_add})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.prescription_add:
                Intent intent = new Intent(AddPrescriptionActivity.this, AreaActivity.class);
                intent.putExtra("type", 1);
                intent.putStringArrayListExtra("datalist", strings);
                startActivity(intent);
                break;
        }
    }

    //开处方
    private void toAddprescription(AddPrescriptionReq aq) {
        contentService.addPrescription(aq)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<PrescriptionInfo>(this, o -> {
                    setResult(13,null);
                    finish();
                }));
    }

    /*接受事件*/
    @Subscribe(threadMode = ThreadMode.MAIN )
    public void event(HomeContent o) {
        MyLog.e("---开处方", "来到开处方处方界面" + homeContentList.size() + "个");
        ArrayList<HomeContent> homeContentLists = new ArrayList<>();
        homeContentLists.addAll(homeContentList);
        if (homeContentList.size() == 0) {
            aBoolean = true;
        }
        for (int i = 0; i < homeContentLists.size(); i++) {
            if (homeContentList.get(i).getId().equals(o.getId()) && !o.isselect()) {
                homeContentList.remove(i);
                strings.remove(i);
                MyLog.e("---开处方", "移除了");
                adapter.setDataList(homeContentList);
                aBoolean = false;
                break;
            } else {
                aBoolean = true;
            }
        }
        if (aBoolean) {
            homeContentList.add(o);
            MyLog.e("---开处方", o.getName() + "--" + o.getPrice());
            strings.add(o.getId());
            adapter.setDataList(homeContentList);
        }
    }
    /*接受事件*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(SingerSchemeInfo.ContentsBean o) {
        ArrayList<HomeContent> homeContentLists = new ArrayList<>();
        homeContentLists.addAll(homeContentList);
        if (homeContentList.size() == 0) {
            aBoolean = true;
        }
        for (int i = 0; i < homeContentLists.size(); i++) {
            if (homeContentList.get(i).getId().equals(o.getId())) {
                aBoolean = false;
                break;
            } else {
                aBoolean = true;
            }
        }
        if (aBoolean) {
            HomeContent homeContent = new HomeContent();
            homeContent.setId(o.getId());
            homeContent.setType(o.getType());
            homeContent.setTypeName(o.getTypeName());
            homeContent.setPrice(o.getPrice());
            homeContent.setName(o.getName());
            homeContent.setCoverPic(o.getCoverPic());
            homeContent.setClicks((Integer) o.getClicks());
            homeContent.setDuration(o.getDuration());
            homeContent.setIsCollected(o.getIsCollected());
            homeContentList.add(homeContent);
            strings.add(o.getId());
            adapter.setDataList(homeContentList);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
         /*註銷*/

        RxBus.getDefault().unRegister(this);
    }

    private String times;
    private String day;
    private String dat_type;
    private int dat_typr_in = 1;

    /**
     * 设置周期
     *
     * @param vs
     */
    @Override
    public void OnSet(String vs) {
        prescriptionEt1.clearFocus();
        prescriptionEt2.clearFocus();
        View inflate = LayoutInflater.from(AddPrescriptionActivity.this).inflate(R.layout.popop_layout_zhouqi, null);
        PopupWindow mPopupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAtLocation(prescriptionEt2, Gravity.CENTER, 0, 50);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.ll_all);
        LinearLayout linearLayout1 = (LinearLayout) inflate.findViewById(R.id.ll_one);
        ImageView img_close = (ImageView) inflate.findViewById(R.id.popo_close);
        EditText et_time = (EditText) inflate.findViewById(R.id.popo_times);
        EditText et_day = (EditText) inflate.findViewById(R.id.popo_day);
        TextView tv_total = (TextView) inflate.findViewById(R.id.popo_total);
        TextView tv_yes = (TextView) inflate.findViewById(R.id.popo_yes);
        TextView tv_type = (TextView) inflate.findViewById(R.id.tv_date_type);
        Spinner spinner = (Spinner) inflate.findViewById(R.id.popo_sp);
        MyLog.e("------zhouqie", dat_typr_in + "");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] languages = getResources().getStringArray(R.array.datetimes);
                dat_type = languages[position];
                dat_typr_in = position + 1;
                MyLog.e("------zhouqitype", dat_typr_in + "");
                tv_type.setText(dat_type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        et_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0 && !et_day.getText().toString().trim().equals("")) {
                    times = s.toString().trim();
                    day = et_day.getText().toString().trim();
                    tv_total.setText(Integer.valueOf(times) * Integer.valueOf(day) + "");

                }
            }
        });
        et_day.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0 && !et_time.getText().toString().trim().equals("")) {
                    times = et_time.getText().toString().trim();
                    day = s.toString().trim();
                    tv_total.setText(Integer.valueOf(times) * Integer.valueOf(day) + "");

                }
            }
        });
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                times = et_time.getText().toString().trim();
                day = et_day.getText().toString().trim();
                if (times.isEmpty()) {
                    ToastUtil.showMessage(AddPrescriptionActivity.this, "次数不能为空");
                    return;
                }
                if (day.isEmpty()) {
                    ToastUtil.showMessage(AddPrescriptionActivity.this, "日期不能为空");
                    return;
                }

                mPopupWindow.dismiss();
                KeyBoardCancle();
                for (int i = 0; i < homeContentList.size(); i++) {
                    if (homeContentList.get(i).getId().equals(vs)) {
                        HomeContent homeContent = homeContentList.get(i);
                        homeContent.setFrequency(Integer.valueOf(times));
                        homeContent.setPeriod(Integer.valueOf(day));
                        homeContent.setPeriodUnit(dat_typr_in);
                        homeContent.setZhouqi(times + "次/" + dat_type + "-共" + day + dat_type + "-共" + Integer.valueOf(times) * Integer.valueOf(day) + "次");
                        homeContentList.set(i, homeContent);
                    }
                }
                //vs.setText();
                adapter.setDataList(homeContentList);
            }

        });
    }

    @Override
    public void delete(String s) {
        for (int i = 0; i < homeContentList.size(); i++) {
            if (homeContentList.get(i).getId().equals(s)) {
                homeContentList.remove(i);
                strings.remove(i);
            }
        }

        adapter.setDataList(homeContentList);
    }

    @Override
    public void totalprice(double m) {
        totalPrice = m;
    }

    public void KeyBoardCancle() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
