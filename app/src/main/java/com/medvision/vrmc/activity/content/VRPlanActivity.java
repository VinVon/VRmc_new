package com.medvision.vrmc.activity.content;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.daimajia.swipe.SwipeLayout;
import com.medvision.vrmc.R;
import com.medvision.vrmc.activity.BaseActivity;
import com.medvision.vrmc.activity.IdeaDetilActivity;
import com.medvision.vrmc.bean.FilterDisease;
import com.medvision.vrmc.bean.PlanOfDisease;
import com.medvision.vrmc.bean.requestbody.BaseReq;
import com.medvision.vrmc.bean.requestbody.CollectPlanReq;
import com.medvision.vrmc.bean.requestbody.HomeContentReq;
import com.medvision.vrmc.bean.requestbody.PagingReq;
import com.medvision.vrmc.bean.requestbody.planofdiseaseReq;
import com.medvision.vrmc.imp.EndlessRecyclerOnScrollListener;
import com.medvision.vrmc.network.ContentService;
import com.medvision.vrmc.utils.MyLog;
import com.medvision.vrmc.utils.SpUtils;
import com.medvision.vrmc.view.Navigation;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;
import com.wzgiceman.rxbuslibrary.rxbus.Subscribe;
import com.wzgiceman.rxbuslibrary.rxbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by raytine on 2017/10/31.
 */

public class VRPlanActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,SwipeRefreshLayout.OnRefreshListener{
    private final String TAG = "VRPlanActivity";
    @BindView(R.id.idea_head_group)
    RadioGroup ideaHeadGroup;
    @BindView(R.id.idea_head_scr)
    HorizontalScrollView ideaHeadScr;
    @BindView(R.id.idea_recycle)
    RecyclerView ideaRecycle;
    @BindView(R.id.nodata_rl)
    RelativeLayout nodataRl;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    private List<FilterDisease> mDisease;
    private List<PlanOfDisease> datas = new ArrayList<>();
    private int mWindowWidth;
    private Object width;
    private IdeaAdapter adapter;
    private ContentService mContentService;
    private int mPaging = 1;
    private int mIndex = 0;
    /**
     * 1 为患者选着方案
     * 0 为浏览方案
     */
    private int type ;
    private String patientId;
    //recycleview item点击的下标
    private int  mPos ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vridea);
        ButterKnife.bind(this);
        RxBus.getDefault().register(this);
        Navigation.getInstance(this).setBack().setTitle("VR方案");
        mContentService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        type = getIntent().getIntExtra("type",0);
        patientId = getIntent().getStringExtra("patientId");
        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ideaRecycle.setLayoutManager(linearLayoutManager);
        adapter = new IdeaAdapter(datas, this);
        ideaRecycle.setAdapter(adapter);
        ideaRecycle.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ideaHeadGroup.setOnCheckedChangeListener(this);
        nodataRl.setVisibility(View.GONE);
        swiperefreshlayout.setOnRefreshListener(this);
        ideaRecycle.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                mPaging += 1;
                LoadMoreData();
            }


        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void postPlan(String st){
        if (st.equals("plan")){
            if (mIndex == 0) {
                datas.remove(mPos);
                if (datas.size() == 0) {
                    nodataRl.setVisibility(View.VISIBLE);
                    ideaRecycle.setVisibility(View.GONE);
                } else {

                }
                adapter.notifyDataSetChanged();
            }else {
                if (datas.get(mPos).getIsCollected() == 1) {
                    datas.get(mPos).setIsCollected(0);
                    adapter.notifyDataSetChanged();
                } else {
                    datas.get(mPos).setIsCollected(1);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }

    private void initData() {
        mWindowWidth = getWidth();
        mContentService.getFilterDisease(new PagingReq(mPaging))
                .map(new HttpResultFunc<List<FilterDisease>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<List<FilterDisease>>(this, p -> {
                    mDisease = p;
                    RadioButton radioButtons = createrView("收藏");
                    ideaHeadGroup.addView(radioButtons);
                    RadioButton radioButton2 = createrView("全部病症");
                    ideaHeadGroup.addView(radioButton2);
                    for (int i = 0; i < p.size(); i++) {
                        RadioButton radioButton = createrView(p.get(i).getDiseaseName());
                        ideaHeadGroup.addView(radioButton);
                    }
                    ((RadioButton) ideaHeadGroup.getChildAt(1)).setChecked(true);
                }));
        adapter.notifyDataSetChanged();
    }

    private RadioButton createrView(String text) {
        RadioButton ra = new RadioButton(this);
        ra.setText(text);
        ColorStateList colorStateList = getResources().getColorStateList(R.drawable.bg_radiobutton_textcolor);
        ra.setTextColor(colorStateList);
        ra.setBackground(getResources().getDrawable(R.drawable.bg_radiobutton_plan));
        ra.setButtonDrawable(android.R.color.transparent);
        ra.setHeight(160);
        ra.setGravity(Gravity.CENTER);
        ra.setPadding(20, 0, 20, 0);
        RadioGroup.LayoutParams l = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        ra.setLayoutParams(l);
        return ra;
    }

    public int getWidth() {
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        return width;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        RadioButton viewById = (RadioButton) group.findViewById(checkedId);
        mIndex = group.indexOfChild(viewById);
        mPaging = 1;
        MyLog.e(TAG, "RadioGroup的下标为" + mIndex);
        loadData(mIndex,false);
    }

    /**
     * 加载数据
     * @param mIndex
     */
    private void loadData(int mIndex ,boolean isFresh) {
        if (mIndex == 0) {
            mContentService.collectedPlansList(new PagingReq(mPaging))
                    .map(new HttpResultFunc<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ProgressSubscriber<List<PlanOfDisease>>(this, o -> {
                        if (o.size() == 0) {
                            nodataRl.setVisibility(View.VISIBLE);
                            ideaRecycle.setVisibility(View.GONE);
                            return;
                        }
                        if (nodataRl.getVisibility() == View.VISIBLE) {
                            nodataRl.setVisibility(View.GONE);
                        }
                        if (ideaRecycle.getVisibility() == View.GONE) {
                            ideaRecycle.setVisibility(View.VISIBLE);
                        }
                        if (datas.size() != 0) {
                            datas.clear();
                        }
                        datas = o;
                        adapter.setmDatas(datas);
                    }));
        } else if (mIndex == 1) {
            mContentService.requeatMyPlansOfDieasea(new planofdiseaseReq(mPaging))
                    .map(new HttpResultFunc<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ProgressSubscriber<List<PlanOfDisease>>(this, o -> {
                        if (o.size() == 0) {
                            nodataRl.setVisibility(View.VISIBLE);
                            ideaRecycle.setVisibility(View.GONE);
                            return;
                        }
                        if (nodataRl.getVisibility() == View.VISIBLE) {
                            nodataRl.setVisibility(View.GONE);
                        }
                        if (ideaRecycle.getVisibility() == View.GONE) {
                            ideaRecycle.setVisibility(View.VISIBLE);
                        }
                        if (datas.size() != 0) {
                            datas.clear();
                        }
                        datas = o;
                        adapter.setmDatas(datas);
                    }));
        } else {
            mContentService.requeatMyPlansOfDieasea(new planofdiseaseReq(mDisease.get(mIndex - 2).getDiseaseId(),mPaging))
                    .map(new HttpResultFunc<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ProgressSubscriber<List<PlanOfDisease>>(this, o -> {
                        if (o.size() == 0) {
                            nodataRl.setVisibility(View.VISIBLE);
                            ideaRecycle.setVisibility(View.GONE);
                            return;
                        }
                        if (nodataRl.getVisibility() == View.VISIBLE) {
                            nodataRl.setVisibility(View.GONE);
                        }
                        if (ideaRecycle.getVisibility() == View.GONE) {
                            ideaRecycle.setVisibility(View.VISIBLE);
                        }
                        if (datas.size() != 0) {
                            datas.clear();
                        }
                        datas = o;
                        adapter.setmDatas(datas);
                    }));
        }
        if (isFresh){
            swiperefreshlayout.setRefreshing(false);
        }
    }

    /**
     * 加载更多数据
     */
    private void LoadMoreData() {
        if (mIndex == 0) {
            mContentService.collectedPlansList(new PagingReq(mPaging))
                    .map(new HttpResultFunc<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ProgressSubscriber<List<PlanOfDisease>>(this, o -> {
                        if (o.size() == 0) {
                            mPaging--;
                            return;
                        }
                        datas.addAll(o);
                        adapter.setmDatas(datas);
                    }));
        } else if (mIndex == 1) {
            mContentService.requeatMyPlansOfDieasea(new planofdiseaseReq(mPaging))
                    .map(new HttpResultFunc<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ProgressSubscriber<List<PlanOfDisease>>(this, o -> {
                        if (o.size() == 0) {
                            mPaging--;
                            return;
                        }

                        datas.addAll(o);
                        adapter.setmDatas(datas);
                    }));
        } else {
            mContentService.requeatMyPlansOfDieasea(new planofdiseaseReq(mDisease.get(mIndex - 2).getDiseaseId(),mPaging))
                    .map(new HttpResultFunc<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ProgressSubscriber<List<PlanOfDisease>>(this, o -> {
                        if (o.size() == 0) {
                            mPaging--;
                            return;
                        }

                        datas.addAll(o);
                        adapter.setmDatas(datas);
                    }));
        }
    }
    @Override
    public void onRefresh() {
        mPaging = 1;
        loadData(mIndex,true);
    }

    private class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.ViewHolder> {
        private LayoutInflater inflater;
        private List<PlanOfDisease> mDatas;
        private Context mContext;

        public IdeaAdapter(List<PlanOfDisease> mDatas, Context mContext) {
            this.mDatas = mDatas;
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_idea, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        public void setmDatas(List<PlanOfDisease> mDatas) {
            this.mDatas = mDatas;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.item_text.setText(mDatas.get(position).getName());
            if (type == 1) {//types == 1 时，为开处方界面过来的
                holder.item_img.setBackground(getResources().getDrawable(R.drawable.item_therapy_bgs));
            } else {
                holder.item_img.setBackground(getResources().getDrawable(R.drawable.item_therapy_bg));
                if (mDatas.get(position).getIsCollected() == 0) {
                    holder.item_img.setSelected(false);
                } else {
                    holder.item_img.setSelected(true);
                }
                holder.item_img.setOnClickListener(o -> {
                    if (holder.item_img.isSelected()) {
                        //取消收藏
                        cancleCollectPlan((String) mDatas.get(position).getId(), holder.item_img, position);
                    } else {
                        //收藏
                        CollectPlan((String) mDatas.get(position).getId(), holder.item_img);
                    }
                });
            }
            holder.item_ll.setOnClickListener(o -> {
                mPos = position;
                Intent intent = new Intent(VRPlanActivity.this, IdeaDetilActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("content", mDatas.get(position));
                bundle.putSerializable("type", type);
                intent.putExtra("patientId", patientId);
                intent.putExtras(bundle);
                startActivity(intent);
            });

        }

        private void cancleCollectPlan(String planId, ImageButton button, int position) {
            mContentService.cancelCollectPlan(new CollectPlanReq(planId))
                    .map(new HttpResultFunc<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ProgressSubscriber<>(mContext, o -> {
                        if (mIndex == 0) {
                            mDatas.remove(position);
                            if (mDatas.size() == 0) {
                                nodataRl.setVisibility(View.VISIBLE);
                                ideaRecycle.setVisibility(View.GONE);
                            } else {

                            }
                            notifyDataSetChanged();
                        } else {
                            button.setSelected(false);
                        }
                        ToastUtil.showMessage(mContext, "取消收藏成功");
                    }));
        }

        private void CollectPlan(String planId, ImageButton button) {
            mContentService.collectPlan(new CollectPlanReq(planId))
                    .map(new HttpResultFunc<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ProgressSubscriber<>(mContext, o -> {
                        ToastUtil.showMessage(mContext, "收藏成功");
                        button.setSelected(true);
                    }));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private RelativeLayout item_rel;
            private LinearLayout item_ll;
            private TextView item_text;
            private ImageButton item_img;

            public ViewHolder(View itemView) {
                super(itemView);
                item_rel = (RelativeLayout) itemView.findViewById(R.id.item_idea_tl);
                item_ll = (LinearLayout) itemView.findViewById(R.id.item_idea_name_ll);
                item_text = (TextView) itemView.findViewById(R.id.item_idea_name);
                item_img = (ImageButton) itemView.findViewById(R.id.item_idea_txt);
            }
        }
    }
}
