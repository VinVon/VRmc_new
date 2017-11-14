package com.medvision.vrmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.entity.NoData;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.medvision.vrmc.MainActivity;
import com.medvision.vrmc.R;
import com.medvision.vrmc.bean.HomeContent;
import com.medvision.vrmc.bean.SingerPrescriptionInfo;
import com.medvision.vrmc.bean.SingerSchemeInfo;
import com.medvision.vrmc.bean.requestbody.BaseReq;
import com.medvision.vrmc.bean.requestbody.schemedeleteReq;
import com.medvision.vrmc.network.ContentService;
import com.medvision.vrmc.utils.ActivityManager;
import com.medvision.vrmc.utils.RecycleViewDivider;
import com.medvision.vrmc.utils.ToastCommom;
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
 * 我的方案界面
 * Created by raytine on 2017/8/23.
 */

public class SchemeActivity extends BaseActivity {
    @BindView(R.id.recycle_scheme)
    RecyclerView recycleScheme;
    private ContentService contentService;
    private SchemeAdapter adapter;
    private List<SingerSchemeInfo> singerSchemeInfos = new ArrayList<>();
    private int type = 0; //1为开处方选择
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheme_activity);
        ButterKnife.bind(this);
        RxBus.getDefault().register(this);
        type = getIntent().getIntExtra("types",0);
      if (type ==0){
          Navigation.getInstance(this).setBack().setTitle("我的方案").setRight("新增方案", new View.OnClickListener() {
              @Override
              public void onClick(View v) {//新增方案
                  startActivityForResult(new Intent(SchemeActivity.this,AddSchemeActivity.class),200);
              }
          });
      }else if (type == 1){
          Navigation.getInstance(this).setBack().setTitle("我的方案");
      }
        contentService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        initView();
        initData();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        adapter = new SchemeAdapter(singerSchemeInfos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleScheme.setLayoutManager(linearLayoutManager);
        recycleScheme.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.VERTICAL));
        recycleScheme.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 201){
            initData();
        }
    }

    /**
     * 获取方案数据
     */
    private void initData() {
        contentService.getScheme(new BaseReq())
                .map(new HttpResultFunc<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ProgressSubscriber<>(this,o->{
                    if (singerSchemeInfos.size() !=0){
                        singerSchemeInfos.clear();
                    }
                    singerSchemeInfos.addAll(o);
                    adapter.notifyDataSetChanged();
                }));
    }
    public class  SchemeAdapter extends RecyclerView.Adapter<SchemeAdapter.ViewHolder>{
        private List<SingerSchemeInfo> datas;

        public SchemeAdapter(List<SingerSchemeInfo> data) {
            datas = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(SchemeActivity.this).inflate(R.layout.scheme_item,parent,false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv_name.setText(datas.get(position).getName());
            if (type == 1){
            holder.item_rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<SingerSchemeInfo.ContentsBean> contents = datas.get(position).getContents();
                    for (SingerSchemeInfo.ContentsBean content : contents) {
                        RxBus.getDefault().post(content);

                    }
                    ActivityManager.getInstance().finish(SchemeActivity.class);
                    ActivityManager.getInstance().finish(AreaActivity.class);
                }
            });
            }else{//编辑我的方案
                holder.item_rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SchemeActivity.this, SchemeModifyActivity.class);
                        intent.putExtra("datas",datas.get(position));
                        startActivityForResult(intent,202);
                    }
                });

            }
            holder.item_rl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new SweetAlertDialog(SchemeActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("是否删除方案")
                            .setCancelText("取消")
                            .setConfirmText("确实")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    schemedeleteReq schemedeleteReq = new schemedeleteReq();
                                    schemedeleteReq.setPlanId(singerSchemeInfos.get(position).getId());
                                    contentService.deleteScheme(schemedeleteReq)
                                            .map(new HttpResultFunc<>())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribeOn(Schedulers.io())
                                            .subscribe(new ProgressSubscriber<NoData>(SchemeActivity.this,o->{
                                                ToastUtil.showMessage(SchemeActivity.this,"删除方案成功");
                                                sweetAlertDialog.dismiss();
                                                singerSchemeInfos.remove(position);
                                                notifyDataSetChanged();
                                            }));
                                }
                            }).show();

                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
                TextView tv_name;
                RelativeLayout item_rl;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_name = (TextView) itemView.findViewById(R.id.scheme_item_name );
                item_rl = (RelativeLayout) itemView.findViewById(R.id.scheme_item_rl );

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }
}
