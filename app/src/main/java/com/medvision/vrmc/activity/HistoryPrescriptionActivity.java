package com.medvision.vrmc.activity;

/**
 * 历史处方界面
 * Created by raytine on 2017/7/18.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.entity.NoData;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.medvision.vrmc.R;
import com.medvision.vrmc.activity.content.VRPlanActivity;
import com.medvision.vrmc.bean.HistoryPrescriptionInfo;
import com.medvision.vrmc.bean.PlanOfDisease;
import com.medvision.vrmc.bean.requestbody.GetSingerPrescriptionReq;
import com.medvision.vrmc.bean.requestbody.HistoryPrescriptionReq;
import com.medvision.vrmc.imp.EndlessRecyclerOnScrollListener;
import com.medvision.vrmc.network.ContentService;
import com.medvision.vrmc.utils.DividerGridItemDecoration;
import com.medvision.vrmc.utils.MyLog;
import com.medvision.vrmc.utils.PullToRefreshSwipeLayout;
import com.medvision.vrmc.utils.SpUtils;
import com.medvision.vrmc.utils.ToastCommom;
import com.medvision.vrmc.view.Navigation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HistoryPrescriptionActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.history_prescriptions)
    RecyclerView historyPrescriptions;
    @BindView(R.id.swipefresh)
    PullToRefreshSwipeLayout swipefresh;
    @BindView(R.id.nodata_layout)
    ViewStub nodataLayout;
    private String patientId;
    private ContentService consulationService;
    private List<PlanOfDisease> historyPrescriptionInfos = new ArrayList<>();
    private HistoryAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private int paging = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_prescription_activity);
        ButterKnife.bind(this);
        patientId = getIntent().getStringExtra("patientId");
        Navigation.getInstance(this).setBack().setTitle("历史方案");
        consulationService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        initView();
        initData();
    }

    private void initView() {
        adapter = new HistoryAdapter();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        historyPrescriptions.setLayoutManager(linearLayoutManager);
        historyPrescriptions.addItemDecoration(new DividerGridItemDecoration(this));
        historyPrescriptions.setAdapter(adapter);
        swipefresh.setOnRefreshListener(this);
        historyPrescriptions.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                paging += 1;
                loadMore();
            }
        });
    }

    private void initData() {
        MyLog.e("TAG", patientId + "=" + SpUtils.getInstance().getToken());
        consulationService.requestHistoryPlan(new HistoryPrescriptionReq(patientId, paging))
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<List<PlanOfDisease>>(HistoryPrescriptionActivity.this, o -> {
                    if (o.size() == 0) {
                        nodataLayout.inflate();
                    }
                    if (historyPrescriptionInfos.size() != 0) {
                        historyPrescriptionInfos.clear();
                    }
                    historyPrescriptionInfos.addAll(o);
                    adapter.notifyDataSetChanged();
                }));
    }

    @Override
    public void onRefresh() {
        paging = 1;
        fresh();
    }

    private void fresh() {
        consulationService.requestHistoryPlan(new HistoryPrescriptionReq(patientId, paging))
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<List<PlanOfDisease>>(HistoryPrescriptionActivity.this, o -> {
                    if (o.size() == 0) {
                        nodataLayout.inflate();
                    }
                    if (historyPrescriptionInfos.size() != 0) {
                        historyPrescriptionInfos.clear();
                    }
                    historyPrescriptionInfos.addAll(o);
                    adapter.notifyDataSetChanged();
                    swipefresh.setRefreshing(false);
                    ToastCommom.createInstance().ToastShow(HistoryPrescriptionActivity.this, "数据更新成功....");
                }));
    }


    private void loadMore() {

        consulationService.requestHistoryPlan(new HistoryPrescriptionReq(patientId, paging))
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<List<PlanOfDisease>>(HistoryPrescriptionActivity.this, o -> {

                    if (o.size() != 0) {
                        for (int i = 0; i < o.size(); i++) {
                            historyPrescriptionInfos.add(o.get(i));
                        }
                        adapter.notifyDataSetChanged();
                        ToastCommom.createInstance().ToastShow(HistoryPrescriptionActivity.this, "加载成功");

                    } else {
                        paging -= 1;
                        ToastCommom.createInstance().ToastShow(HistoryPrescriptionActivity.this, "无更多数据....");
                    }
                }));
    }

    private class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_history_prescriptions, null);
            v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            PlanOfDisease historyPrescriptionInfo = historyPrescriptionInfos.get(position);
            holder.tv_des.setText(historyPrescriptionInfo.getName());
            holder.tv_date.setText(historyPrescriptionInfo.getCreateAt());
            holder.tv_status.setText(historyPrescriptionInfo.getDiseaseName());
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HistoryPrescriptionActivity.this, IdeaDetilActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("content", historyPrescriptionInfo);
                    bundle.putSerializable("type", 0);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return historyPrescriptionInfos.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_date;
            TextView tv_status;
            TextView tv_des;
            TextView tv_contents;
            TextView tv_stop;
            RelativeLayout relativeLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_date = (TextView) itemView.findViewById(R.id.item_history_date);
                tv_status = (TextView) itemView.findViewById(R.id.item_history_status);
                tv_des = (TextView) itemView.findViewById(R.id.item_history_des);
                tv_contents = (TextView) itemView.findViewById(R.id.item_history_contents);
                tv_stop = (TextView) itemView.findViewById(R.id.item_history_stop);
                relativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_history);
            }
        }
    }
}