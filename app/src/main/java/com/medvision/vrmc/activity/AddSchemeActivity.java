package com.medvision.vrmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cs.networklibrary.entity.NoData;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.medvision.vrmc.R;
import com.medvision.vrmc.adapter.FavoriteStationListViewAdapterss;
import com.medvision.vrmc.bean.HomeContent;
import com.medvision.vrmc.bean.requestbody.schemeReq;
import com.medvision.vrmc.network.ContentService;
import com.medvision.vrmc.utils.MyLog;
import com.medvision.vrmc.utils.ToastCommom;
import com.medvision.vrmc.view.ListViewForScrollView;
import com.medvision.vrmc.view.Navigation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 新增方案界面
 * Created by raytine on 2017/8/25.
 */

public class AddSchemeActivity extends AppCompatActivity implements FavoriteStationListViewAdapterss.SetClick {
    @BindView(R.id.scheme_recycle)
    ListViewForScrollView schemeRecycle;
    @BindView(R.id.scheme_add)
    LinearLayout schemeAdd;
    @BindView(R.id.scheme_name)
    EditText schemeName;
    private FavoriteStationListViewAdapterss adapter;
    private ArrayList<HomeContent> homeContentList = new ArrayList<>();
    private ContentService contentService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addscheme_activity);
        ButterKnife.bind(this);
        Navigation.getInstance(this).setBack().setRight("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = schemeName.getText().toString();
                if (text.isEmpty()){
                    ToastCommom.createInstance().ToastShow(AddSchemeActivity.this,"名称不能为空");
                    return;
                }
                if (homeContentList.size()==0){
                    ToastCommom.createInstance().ToastShow(AddSchemeActivity.this,"内容不能为空");
                    return;
                }
                String ids = "";
                for (int i = 0; i < homeContentList.size(); i++) {
                    if (i == homeContentList.size()-1){
                        ids+=homeContentList.get(i).getId();
                    }else {
                        ids+=homeContentList.get(i).getId()+",";
                    }
                }
                MyLog.e("TAG",ids);
                schemeReq schemeReq = new schemeReq();
                schemeReq.setName(text);
                schemeReq.setContentIds(ids);
                requestScheme(schemeReq);
            }
        });
        contentService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        initView();
    }

    private void requestScheme(schemeReq schemeReq) {
        contentService.addScheme(schemeReq)
                .map(new HttpResultFunc<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ProgressSubscriber<NoData>(this,o->{
                    ToastCommom.createInstance().ToastShow(AddSchemeActivity.this,"添加方案成功");
                    setResult(201);
                    finish();
                }));
    }


    private void initView() {
        adapter = new FavoriteStationListViewAdapterss(this, homeContentList, this);
        schemeRecycle.setAdapter(adapter);
    }

    @OnClick({R.id.scheme_add})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.scheme_add:
                Intent intent = new Intent(AddSchemeActivity.this, CollectionActivity.class);
                intent.putExtra("types", 2);
                intent.putExtra("list", homeContentList);
                startActivityForResult(intent, 99);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 98) {
            ArrayList<HomeContent> scheme = (ArrayList<HomeContent>) data.getSerializableExtra("scheme");
            for (int i = 0; i < scheme.size(); i++) {
                boolean isSame = true;
                if (homeContentList.size() == 0) {
                    homeContentList.add(scheme.get(i));
                } else {
                    for (int j = 0; j < homeContentList.size(); j++) {
                        if (homeContentList.get(j).getId().equals(scheme.get(i).getId())) {
                            isSame = false;
                        }
                    }
                    if (isSame) {
                        homeContentList.add(scheme.get(i));
                    }
                }
            }
            adapter.setDataList(homeContentList);
        }
    }

    @Override
    public void OnSet(String v) {

    }

    @Override
    public void delete(String s) {
        for (int i = 0; i < homeContentList.size(); i++) {
            if (homeContentList.get(i).getId().equals(s)) {
                homeContentList.remove(i);
            }
        }
        adapter.setDataList(homeContentList);
    }

    @Override
    public void totalprice(double m) {

    }
}
