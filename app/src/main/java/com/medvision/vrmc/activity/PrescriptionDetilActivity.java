package com.medvision.vrmc.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import com.medvision.vrmc.R;
import com.medvision.vrmc.bean.SingerPrescriptionInfo;
import com.medvision.vrmc.bean.requestbody.GetSingerPrescriptionReq;
import com.medvision.vrmc.network.ContentService;
import com.medvision.vrmc.view.ListViewForScrollView;
import com.medvision.vrmc.view.Navigation;



import com.medvision.vrmc.adapter.FavoriteStationListViewAdapters;

import android.support.annotation.Nullable;

import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;

/**
 * Created by raytine on 2017/7/18.
 */

public class PrescriptionDetilActivity extends AppCompatActivity {
    @BindView(R.id.detil_billid)
    TextView detilBillid;
    @BindView(R.id.detil_status)
    TextView detilStatus;
    @BindView(R.id.detil_status_tv1)
    TextView detilStatusTv1;
    @BindView(R.id.detil_status_tv2)
    TextView detilStatusTv2;
    @BindView(R.id.detil_recycle)
    ListViewForScrollView detilRecycle;
    @BindView(R.id.detil_price)
    TextView detilPrice;
    private String prescriptionId;
    private ContentService contentService;
    private FavoriteStationListViewAdapters adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescriptiondetil);
        ButterKnife.bind(this);
        Navigation.getInstance(this).setBack().setTitle("处方详情");
        contentService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        prescriptionId = getIntent().getStringExtra("prescriptionId");
        initData();
    }

    private void initData() {
        contentService.getPrescription(new GetSingerPrescriptionReq(prescriptionId))
                .map(new HttpResultFunc<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ProgressSubscriber<SingerPrescriptionInfo>(this, o ->{
                    detilBillid.setText("订单号 : "+o.getBillno());
                    if (o.getStatus() == 1){
                        detilStatus.setText("未付款");
                    }else{
                        detilStatus.setText("已付款");
                    }
                    detilStatusTv1.setText(o.getDisease());
                    detilStatusTv2.setText(o.getSuggestion());
                    detilPrice.setText(o.getTotal()+"");
                    adapter = new FavoriteStationListViewAdapters(this,o.getContents(),R.layout.item_therapyss);
                    detilRecycle.setAdapter(adapter);
                }));
    }
}
