package com.medvision.vrmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.entity.NoData;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.medvision.vrmc.R;
import com.medvision.vrmc.activity.content.DemoBean;
import com.medvision.vrmc.activity.content.VRAreaActivity;
import com.medvision.vrmc.activity.content.VRPlanActivity;
import com.medvision.vrmc.adapter.NormalRecyclerViewAdapter;
import com.medvision.vrmc.bean.FilterDisease;
import com.medvision.vrmc.bean.HomeContent;
import com.medvision.vrmc.bean.PlanOfDisease;
import com.medvision.vrmc.bean.requestbody.BaseReq;
import com.medvision.vrmc.bean.requestbody.CollectPlanReq;
import com.medvision.vrmc.bean.requestbody.PushPlanReq;
import com.medvision.vrmc.network.ContentService;
import com.medvision.vrmc.utils.ActivityManager;
import com.medvision.vrmc.utils.DividerItemDecoration;
import com.medvision.vrmc.utils.MyLog;
import com.medvision.vrmc.view.Navigation;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by raytine on 2017/10/31.
 */

public class IdeaDetilActivity extends BaseActivity {

    @BindView(R.id.excel_recycle)
    RecyclerView excelRecycle;
    @BindView(R.id.plan_name)
    EditText planName;
    @BindView(R.id.plan_diease)
    TextView planDiease;
    @BindView(R.id.plan_dec)
    EditText planDec;
    @BindView(R.id.base_header_right_bt)
    TextView baseHeaderRightBt;

    private NormalRecyclerViewAdapter mAdapter;
    private PlanOfDisease mContent;
    /**
     * 1 为患者选着方案
     * 0 为浏览方案
     */
    private int type;
    private ContentService mContentService;
    private String diseaseId;
    private String patientId;
    private List<PlanOfDisease.ContentsBean> contents;
    private String content = "";
    private final int REQUEST_CODE = 0X0001;
    private final int RESULT_CODE = 0X0002;
    //更改的疗法下标
    private int changePositon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idea_detil_activity);
        ButterKnife.bind(this);
        mContentService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        Intent intent = getIntent();
        mContent = (PlanOfDisease) intent.getExtras().getSerializable("content");
        diseaseId = mContent.getDiseaseId();
        type = getIntent().getIntExtra("type", 0);
        patientId = getIntent().getStringExtra("patientId");
        if (type == 1) {
            Navigation.getInstance(this).setBack().setTitle("失眠症方案").setRight("发送", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (planName.getText().toString().isEmpty()) {
                        ToastUtil.showMessage(IdeaDetilActivity.this, "方案名称不能为空");
                        return;
                    }
                    if (planDiease.getText().toString().isEmpty()) {
                        ToastUtil.showMessage(IdeaDetilActivity.this, "病症不能为空");
                        return;
                    }
                    if (planDec.getText().toString().isEmpty()) {
                        ToastUtil.showMessage(IdeaDetilActivity.this, "说明不能为空");
                        return;
                    }
                    for (int i = 0; i < contents.size(); i++) {
                        if (i == (contents.size() - 1)) {
                            content += contents.get(i).getId();
                        } else {
                            content += contents.get(i).getId() + ",";
                        }
                    }
                    PushPlanReq pushPlanReq = new PushPlanReq();
                    pushPlanReq.setName(planName.getText().toString());
                    pushPlanReq.setTimes(mContent.getTimes());
                    pushPlanReq.setScenes(mContent.getScenes());
                    pushPlanReq.setInstruction(planDec.getText().toString());
                    pushPlanReq.setDiseaseId(diseaseId);
                    pushPlanReq.setPatientId(patientId);
                    pushPlanReq.setContents(content);
                    MyLog.e("TAG", "发送方案" + pushPlanReq.toString());
                    PushPlan(pushPlanReq);
                }
            });
        } else {
            if (mContent.getIsCollected() == 1) {
                Navigation.getInstance(this).setBack().setTitle("失眠症方案").setRight("已收藏", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mContent.getIsCollected() == 1) {
                            cancleCollectPlan(mContent.getId());
                        } else {
                            CollectPlan(mContent.getId());
                        }
                    }
                });
            } else {
                Navigation.getInstance(this).setBack().setTitle("失眠症方案").setRight("收藏", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mContent.getIsCollected() == 1) {
                            cancleCollectPlan(mContent.getId());
                        } else {
                            CollectPlan(mContent.getId());
                        }
                    }
                });
            }

        }
        initView();
    }

    /**
     * 发送方案
     *
     * @param pushPlanReq
     */
    private void PushPlan(PushPlanReq pushPlanReq) {
        mContentService.pushPlan(pushPlanReq)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<NoData>(IdeaDetilActivity.this, o -> {
                    ToastUtil.showMessage(IdeaDetilActivity.this, "方案发送成功");
                    ActivityManager.getInstance().finish(VRPlanActivity.class);
                    ActivityManager.getInstance().finish(IdeaDetilActivity.class);
                }));
    }

    protected void initView() {
        if (type == 0) {
            planName.setEnabled(false);
            planDiease.setEnabled(false);
            planDec.setEnabled(false);
        }
        planName.setText(mContent.getName());
        planDiease.setText(mContent.getDiseaseName());
        planDec.setText(mContent.getInstruction());
        List<String> strs = new ArrayList<>();
        contents = mContent.getContents();
        for (int i = 0; i < contents.size(); i++) {
            strs.add(contents.get(i).getName());
        }
        //相当于listview
        excelRecycle.setLayoutManager(
                new LinearLayoutManager(this));
        mAdapter = new NormalRecyclerViewAdapter(this);
        mAdapter.setTitles(new DemoBean(mContent.getTimes() + "", mContent.getScenes() + "", strs), type);
        mAdapter.setItem_plan(new NormalRecyclerViewAdapter.Item_plan() {
            @Override
            public void onclick(TextView v, int position) {
                changePositon = position;
                new SweetAlertDialog(IdeaDetilActivity.this).setTitleText("是否更换改疗法")
                        .setConfirmText("确定")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Intent intent = new Intent(IdeaDetilActivity.this, VRAreaActivity.class);
                                intent.putExtra("forresult", 1);
                                startActivityForResult(intent, REQUEST_CODE);
                                v.setSelected(false);
                                v.setTextColor(getResources().getColor(R.color.black));
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                v.setSelected(false);
                                v.setTextColor(getResources().getColor(R.color.black));
                            }
                        }).show();
            }
        });
        excelRecycle.setAdapter(mAdapter);
//        excelRecycle.setItemAnimator(new DefaultItemAnimator());
        excelRecycle.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));

        planDiease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDisease();
            }
        });
        /**
         * 加入header
         */
        setAdaperHeader();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == 0X0002) {
                HomeContent data1 = (HomeContent) data.getSerializableExtra("data");
                PlanOfDisease.ContentsBean contentsBean = contents.get(changePositon);
                contentsBean.setName(data1.getName());
                contentsBean.setId(data1.getId());
                List<String> strs = new ArrayList<>();
                for (int i = 0; i < contents.size(); i++) {
                    strs.add(contents.get(i).getName());
                }
                mAdapter.setTitles(new DemoBean(mContent.getTimes() + "", mContent.getScenes() + "", strs), type);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private void setAdaperHeader() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_first_header, null);
        mAdapter.setHeader(view);
    }

    private List<FilterDisease> diseases = new ArrayList<>();
    private List<String> titles = new ArrayList<>();//病症字段集合
    private List<String> diseaseIds = new ArrayList<>();//病症字段集合

    private void requestDisease() {
        mContentService.getFilterDisease(new BaseReq())
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
                planDiease.setText(titles.get(options1));
                diseaseId = diseaseIds.get(options1);
            }
        }).setTitleText("选择病症").build();
        pvOptions.setPicker(titles, null, null);
        pvOptions.show();
    }


    private void cancleCollectPlan(String planId) {
        mContentService.cancelCollectPlan(new CollectPlanReq(planId))
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<>(this, o -> {
                    RxBus.getDefault().post("plan");
                    baseHeaderRightBt.setText("收藏");
                    ToastUtil.showMessage(this, "取消收藏成功");
                    mContent.setIsCollected(0);
                }));
    }

    private void CollectPlan(String planId) {
        mContentService.collectPlan(new CollectPlanReq(planId))
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<>(this, o -> {
                    ToastUtil.showMessage(this, "收藏成功");
                    RxBus.getDefault().post("plan");
                    baseHeaderRightBt.setText("已收藏");
                    mContent.setIsCollected(1);
                }));
    }
}
