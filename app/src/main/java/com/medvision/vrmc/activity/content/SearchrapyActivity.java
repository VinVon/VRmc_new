package com.medvision.vrmc.activity.content;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.entity.NoData;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.cs.networklibrary.subscribers.SubscriberOnNextListener;
import com.medvision.vrmc.R;
import com.medvision.vrmc.activity.BaseActivity;
import com.medvision.vrmc.activity.ImageViewActivity;
import com.medvision.vrmc.bean.Buser;
import com.medvision.vrmc.bean.FilterType;
import com.medvision.vrmc.bean.HomeContent;
import com.medvision.vrmc.bean.eventbus.Busers;
import com.medvision.vrmc.bean.requestbody.CollectContentReq;
import com.medvision.vrmc.bean.requestbody.HomeContentReq;
import com.medvision.vrmc.imp.EndlessRecyclerOnScrollListener;
import com.medvision.vrmc.imp.MyClick;
import com.medvision.vrmc.network.ContentService;
import com.medvision.vrmc.network.UserService;
import com.medvision.vrmc.utils.MyLog;
import com.squareup.picasso.Picasso;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;
import com.wzgiceman.rxbuslibrary.rxbus.Subscribe;
import com.wzgiceman.rxbuslibrary.rxbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by raytine on 2017/11/3.
 */

public class SearchrapyActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tab_bar_keyword_et)
    EditText tabBarKeywordEt;
    @BindView(R.id.clear_keyword_iv)
    ImageView clearKeywordIv;
    @BindView(R.id.tab_bar_cancel_tv)
    TextView tabBarCancelTv;
    @BindView(R.id.global_search_action_bar_rl)
    RelativeLayout globalSearchActionBarRl;
    @BindView(R.id.nodata_rl)
    RelativeLayout nodataRl;
    @BindView(R.id.search_recycle)
    RecyclerView areaRecycle;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    private int isResult; //判断是更换疗法过来的，还是浏览过来的
    private int nameIndex = 1;
    private int therapyindex;
    private List<String> mDatas = new ArrayList<>();
    private List<FilterType> filterDiseases = new ArrayList<>();
    private ContentService contentService;
    private UserService mUserService;
    private String diseaseId = null;
    private String therapyId = null;
    private String keyword = null;
    private MyRecyclerAdapter adapter;
    private List<HomeContent> homes = new ArrayList<>();
    private int Pos;//下标
    //    private int types = 0; //按钮是选择还是收藏1为开处方选择，0为收藏
    private final int REQST_VIDEO = 100;
    private final int REQST_IMG = 101;
    private final int REQST_OK = 102;
    private int page = 1;
    private ArrayList<String> arrayList;

    @Override
    protected void onStart() {
        super.onStart();
                 /*註冊*/
        RxBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_rapy_activity);
        ButterKnife.bind(this);
        isResult = getIntent().getIntExtra("forresult", 0);
        initView();
    }



    /**
     * 请求疗法详情
     */
    private void requestTherapyContent() {
        HomeContentReq h = new HomeContentReq();
        h.setDiseaseId(diseaseId);
        h.setTherapyId(therapyId);
        h.setPage(page);
        h.setKeyword(keyword);
        Log.e("TAGdd", diseaseId + "==" + therapyId);
        contentService.getSearchContent(h)
                .map(new HttpResultFunc<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ProgressSubscriber<List<HomeContent>>(this, new SubscriberOnNextListener<List<HomeContent>>() {
                    @Override
                    public void onNext(List<HomeContent> homeContents) {
                        if (homeContents.size() == 0) {
                            nodataRl.setVisibility(View.VISIBLE);
                            areaRecycle.setVisibility(View.GONE);
                            return;
                        }
                        if (nodataRl.getVisibility() == View.VISIBLE) {
                            nodataRl.setVisibility(View.GONE);
                        }
                        if (areaRecycle.getVisibility() == View.GONE) {
                            areaRecycle.setVisibility(View.VISIBLE);
                        }
                        if (homes.size() != 0) {
                            homes.clear();
                        }
                        for (int i = 0; i < homeContents.size(); i++) {
                            if (homeContents.get(i).getIsCollected() == 1) {
                                homeContents.get(i).setIsselect(true);
                            }
                        }
                        homes.addAll(homeContents);
                        adapter.notifyDataSetChanged();
                    }
                }));
    }

    private void initView() {
        contentService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        mUserService = HttpMethods.getInstance().getClassInstance(UserService.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        areaRecycle.setLayoutManager(layoutManager);
        adapter = new MyRecyclerAdapter(this, homes);
        areaRecycle.setAdapter(adapter);
        areaRecycle.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        nodataRl.setVisibility(View.GONE);
        swiperefreshlayout.setOnRefreshListener(this);
        areaRecycle.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page += 1;
                LoadMoreData();
            }
        });
        tabBarKeywordEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keyword  = tabBarKeywordEt.getText().toString().trim();
                    requestTherapyContent();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.img_back,R.id.tab_bar_cancel_tv})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tab_bar_cancel_tv:
                keyword  = tabBarKeywordEt.getText().toString().trim();
                requestTherapyContent();
                break;
        }
    }

    /*接受事件*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(Buser o) {
        for (int i = 0; i < homes.size(); i++) {
            if (homes.get(i).getId().equals(o.getContenteId())) {
                if (o.is()) {
                    homes.get(i).setIsCollected(1);
                    homes.get(i).setIsselect(true);
                } else {
                    homes.get(i).setIsCollected(0);
                    homes.get(i).setIsselect(false);
                }
                adapter.setmDatas(homes);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
         /*註銷*/
        RxBus.getDefault().unRegister(this);
    }

    @Override
    public void onRefresh() {
        page = 1;
        HomeContentReq h = new HomeContentReq();
        h.setDiseaseId(diseaseId);
        h.setTherapyId(therapyId);
        h.setPage(page);
        h.setKeyword(keyword);
        Log.e("TAGdd", diseaseId + "==" + therapyId);
        contentService.getSearchContent(h)
                .map(new HttpResultFunc<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ProgressSubscriber<List<HomeContent>>(this, new SubscriberOnNextListener<List<HomeContent>>() {
                    @Override
                    public void onNext(List<HomeContent> homeContents) {
                        if (homeContents.size() == 0) {
                            nodataRl.setVisibility(View.VISIBLE);
                            areaRecycle.setVisibility(View.GONE);
                            return;
                        }
                        if (nodataRl.getVisibility() == View.VISIBLE) {
                            nodataRl.setVisibility(View.GONE);
                        }
                        if (areaRecycle.getVisibility() == View.GONE) {
                            areaRecycle.setVisibility(View.VISIBLE);
                        }
                        if (homes.size() != 0) {
                            homes.clear();
                        }
                        for (int i = 0; i < homeContents.size(); i++) {
                            if (homeContents.get(i).getIsCollected() == 1) {
                                homeContents.get(i).setIsselect(true);
                            }
                        }
                        homes.addAll(homeContents);
                        adapter.notifyDataSetChanged();
                        swiperefreshlayout.setRefreshing(false);
                    }
                }));
    }

    private void LoadMoreData() {
        HomeContentReq h = new HomeContentReq();
        h.setDiseaseId(diseaseId);
        h.setTherapyId(therapyId);
        h.setPage(page);
        h.setKeyword(keyword);
        Log.e("TAGdd", diseaseId + "==" + therapyId);
        contentService.getSearchContent(h)
                .map(new HttpResultFunc<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ProgressSubscriber<List<HomeContent>>(this, new SubscriberOnNextListener<List<HomeContent>>() {
                    @Override
                    public void onNext(List<HomeContent> homeContents) {
                        if (homeContents.size() == 0) {
                            nodataRl.setVisibility(View.VISIBLE);
                            areaRecycle.setVisibility(View.GONE);
                            return;
                        }
                        if (nodataRl.getVisibility() == View.VISIBLE) {
                            nodataRl.setVisibility(View.GONE);
                        }
                        if (areaRecycle.getVisibility() == View.GONE) {
                            areaRecycle.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < homeContents.size(); i++) {
                            if (homeContents.get(i).getIsCollected() == 1) {
                                homeContents.get(i).setIsselect(true);
                            }
                        }
                        homes.addAll(homeContents);
                        adapter.notifyDataSetChanged();
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQST_IMG:
                if (resultCode == 1000) {
                    Intent intent = new Intent();
                    intent.putExtra("data", homes.get(Pos));
                    setResult(0X0002, intent);
                    finish();
                }
                break;
        }
    }

    class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

        private List<HomeContent> mDatas;
        private Context mContext;
        private LayoutInflater inflater;
        private MyClick myClick;

        public void setOnItemClickLitener(MyClick mOnItemClickLitener) {
            this.myClick = mOnItemClickLitener;
        }

        public MyRecyclerAdapter(Context context, List<HomeContent> datas) {
            this.mContext = context;
            this.mDatas = datas;
            inflater = LayoutInflater.from(mContext);
        }

        public void setmDatas(List<HomeContent> mDatas) {
            this.mDatas = mDatas;
            this.notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        //填充onCreateViewHolder方法返回的holder中的控件
        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            Picasso.with(mContext).load(mDatas.get(position).getCoverPic()).into(holder.title);
            holder.tv1.setText(mDatas.get(position).getName());
            holder.tv3.setText("点击量 : " + mDatas.get(position).getClicks() + "   时长 : " + mDatas.get(position).getDuration() + "分钟");
            holder.tv4.setText("¥ " + mDatas.get(position).getPrice());
            if (isResult == 1) {//types == 1 时，为开处方界面过来的
                holder.tv2.setBackground(getResources().getDrawable(R.drawable.item_therapy_bgs));
                if (mDatas.get(position).isselect()) {
                    holder.tv2.setSelected(true);
                } else {
                    holder.tv2.setSelected(false);
                }
            } else {
                holder.tv2.setBackground(getResources().getDrawable(R.drawable.item_therapy_bg));
                if (mDatas.get(position).getIsCollected() == 0) {//0 为没有收藏
                    holder.tv2.setSelected(false);
                } else {
                    holder.tv2.setSelected(true);
                }
            }
            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //跳转疗法详情
                    Pos = position;
                    if (isResult == 1) {
                        Intent intent1 = new Intent(SearchrapyActivity.this, ImageViewActivity.class);
                        intent1.putExtra("contentId", mDatas.get(position).getId());
                        intent1.putExtra("selecter", holder.tv2.isSelected());
                        intent1.putExtra("forresult", isResult);
                        startActivityForResult(intent1, REQST_IMG);
                    } else {
                        Intent intent1 = new Intent(SearchrapyActivity.this, ImageViewActivity.class);
                        intent1.putExtra("contentId", mDatas.get(position).getId());
                        intent1.putExtra("selecter", holder.tv2.isSelected());
                        startActivity(intent1);
                    }

                }
            });
            holder.tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isResult == 1) {
                        Intent intent = new Intent();
                        intent.putExtra("data", mDatas.get(position));
                        setResult(0X0002, intent);
                        finish();
                    } else {
                        if (mDatas.get(position).getIsCollected() == 1) {
                            contentService.collectContentCancel(new CollectContentReq(mDatas.get(position).getId()))
                                    .map(new HttpResultFunc<>())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new ProgressSubscriber<NoData>(SearchrapyActivity.this, o -> {
                                        ToastUtil.showMessage(SearchrapyActivity.this, "取消收藏成功");
                                        holder.tv2.setSelected(false);
                                        mDatas.get(position).setIsselect(false);
                                        mDatas.get(position).setIsCollected(0);
                                        homes.get(position).setIsselect(false);
                                        homes.get(position).setIsCollected(0);
                                        RxBus.getDefault().post(new Busers(mDatas.get(position),false));

                                    }));

                        } else {
                            contentService.collectContent(new CollectContentReq(mDatas.get(position).getId()))
                                    .map(new HttpResultFunc<>())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new ProgressSubscriber<NoData>(SearchrapyActivity.this, o -> {
                                        ToastUtil.showMessage(SearchrapyActivity.this, "收藏成功");
                                        holder.tv2.setSelected(true);
                                        mDatas.get(position).setIsselect(true);
                                        homes.get(position).setIsselect(true);
                                        mDatas.get(position).setIsCollected(1);
                                        homes.get(position).setIsCollected(1);
                                        RxBus.getDefault().post(new Busers(mDatas.get(position),true));

                                    }));

                        }
                    }
                }
            });

        }

        //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_therapy, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            RelativeLayout rl;
            ImageView title;
            TextView tv1;
            ImageButton tv2;
            TextView tv3;
            TextView tv4;

            public MyViewHolder(View view) {
                super(view);
                rl = (RelativeLayout) view.findViewById(R.id.item_therapy_tl);
                title = (ImageView) view.findViewById(R.id.item_therapy_img);
                tv1 = (TextView) view.findViewById(R.id.item_therapy_title);
                tv2 = (ImageButton) view.findViewById(R.id.item_therapy_txt);
                tv3 = (TextView) view.findViewById(R.id.item_therapy_count);
                tv4 = (TextView) view.findViewById(R.id.item_therapy_price);
            }

        }
    }
}
