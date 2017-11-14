package com.medvision.vrmc.activity.content;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.entity.NoData;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.cs.networklibrary.subscribers.SubscriberOnNextListener;
import com.medvision.vrmc.R;
import com.medvision.vrmc.UrlPath.UrlHttp;
import com.medvision.vrmc.activity.AreaActivity;
import com.medvision.vrmc.activity.BaseActivity;
import com.medvision.vrmc.activity.ImageViewActivity;
import com.medvision.vrmc.activity.WebViewActivity;
import com.medvision.vrmc.bean.Buser;
import com.medvision.vrmc.bean.FilterType;
import com.medvision.vrmc.bean.HomeContent;
import com.medvision.vrmc.bean.eventbus.Busers;
import com.medvision.vrmc.bean.requestbody.BaseReq;
import com.medvision.vrmc.bean.requestbody.CollectContentReq;
import com.medvision.vrmc.bean.requestbody.CollectionListReq;
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
 * Created by raytine on 2017/10/25.
 */

public class VRAreaActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.area_back)
    ImageView areaBack;
    @BindView(R.id.query)
    TextView query;
    @BindView(R.id.area_recycle)
    RecyclerView areaRecycle;
    @BindView(R.id.rp_therapy)
    RadioGroup rpTherapy;
    @BindView(R.id.rp_name)
    RadioGroup rpName;
    @BindView(R.id.therapy_line)
    LinearLayout therapyLine;
    @BindView(R.id.src_therapy)
    HorizontalScrollView srcTherapy;
    @BindView(R.id.nodata_rl)
    RelativeLayout nodataRl;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.fab)
    ImageView fab;
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
    private int isResult = 0; //是否为更换疗法进入该界面 默认为0不是
    private String url = UrlHttp.BASE_URL_EVERYTHING + "h5/help";

    @Override
    protected void onStart() {
        super.onStart();
                 /*註冊*/
        RxBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrarea);
        ButterKnife.bind(this);
        isResult = getIntent().getIntExtra("forresult", 0);
        initView();
        initData();
    }


    protected void initData() {
        contentService.getFilterDiseases(new BaseReq())
                .map(new HttpResultFunc<List<FilterType>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<List<FilterType>>(this, o -> {
                    filterDiseases = o;
                    if (mDatas.size() != 0) {
                        mDatas.clear();
                    }
                    RadioButton radioButtons = createrView("收藏");
                    rpName.addView(radioButtons);
                    for (int i = 0; i < o.size(); i++) {
                        mDatas.add(o.get(i).getDiseaseName());
                        RadioButton radioButton = createrView(o.get(i).getDiseaseName());
                        rpName.addView(radioButton);
                    }
                    ((RadioButton) rpName.getChildAt(1)).setChecked(true);
                    List<FilterType.TherapiesArrayBean> therapiesArray = filterDiseases.get(1).getTherapiesArray();
                    if (rpTherapy.getChildCount() != 0) {
                        rpTherapy.removeAllViews();
                    }
                    RadioButton radioButtonss = createrTherapyView("所有疗法");
                    rpTherapy.addView(radioButtonss);
                    for (int i = 0; i < therapiesArray.size(); i++) {
                        RadioButton radioButton = createrTherapyView(therapiesArray.get(i).getTherapyName());
                        rpTherapy.addView(radioButton);
                    }
                    ((RadioButton) rpTherapy.getChildAt(0)).setChecked(true);
                    rpName.setOnCheckedChangeListener(this);
                    rpTherapy.setOnCheckedChangeListener(this);
                }));
        requestTherapyContent();
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

    protected void initView() {
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
                MyLog.e("TAG", "kaishijizai");
                page += 1;
                LoadMoreData();
            }


        });
    }

    @OnClick({R.id.area_back, R.id.query, R.id.fab})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.area_back:
                finish();
                break;
            case R.id.query://跳转搜索页面
                if (isResult == 1) {
                    Intent intent = new Intent(this, SearchrapyActivity.class);
                    intent.putExtra("forresult", isResult);
                    startActivityForResult(intent, REQST_VIDEO);
                } else {
                    Intent intent = new Intent(this, SearchrapyActivity.class);
                    intent.putExtra("forresult", isResult);
                    startActivity(intent);
                }
                break;
            case R.id.fab:
                String h5;
                if (diseaseId == null) {
                    h5 = url;
                } else {
                    h5 = url + "/disease?id=" + diseaseId;
                }
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", h5);
                startActivity(intent);
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

    /*接受事件*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(Busers o) {
        for (int i = 0; i < homes.size(); i++) {
            if (homes.get(i).getId().equals(o.getHomeContent().getId())) {
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

    private RadioButton createrView(String text) {
        RadioButton ra = new RadioButton(this);
        ra.setText(text);
        ColorStateList colorStateList = getResources().getColorStateList(R.drawable.bg_radiobutton_textcolor);
        ra.setTextColor(colorStateList);
        ra.setBackground(getResources().getDrawable(R.drawable.bg_radiobutton1));
        ra.setButtonDrawable(android.R.color.transparent);
        ra.setHeight(160);
        ra.setGravity(Gravity.CENTER);
        ra.setPadding(20, 0, 20, 0);
        return ra;
    }

    private RadioButton createrTherapyView(String text) {
        RadioButton ra = new RadioButton(this);
        ra.setText(text);
        ColorStateList colorStateList = getResources().getColorStateList(R.drawable.bg_radiobutton_textcolor);
        ra.setTextColor(colorStateList);
        ra.setBackground(getResources().getDrawable(R.drawable.bg_radiobutton2));
        ra.setButtonDrawable(android.R.color.transparent);
        ra.setHeight(160);
        ra.setGravity(Gravity.CENTER);
        ra.setPadding(20, 0, 20, 0);
        RadioGroup.LayoutParams l = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        l.leftMargin = 15;
        l.topMargin = 20;
        l.rightMargin = 15;
        l.bottomMargin = 20;
        ra.setLayoutParams(l);
        return ra;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()) {
            case R.id.rp_name:
                page = 1;
                RadioButton viewById = (RadioButton) findViewById(checkedId);
                String s = viewById.getText().toString().trim();
                nameIndex = group.indexOfChild(viewById);
                Log.e("TAG", "病症group=" + nameIndex);
                if (nameIndex == 0) {
                    srcTherapy.setVisibility(View.GONE);
                    therapyLine.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                    //获取收藏列表
                    mUserService.requestCollectionList(new CollectionListReq(page))
                            .map(new HttpResultFunc<>())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ProgressSubscriber<List<HomeContent>>(this, o -> {
                                        if (o.size() == 0) {
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
                                        homes.addAll(o);
                                        adapter.notifyDataSetChanged();
                                    })
                            );
                } else {
                    if (srcTherapy.getVisibility() == View.GONE) {
                        srcTherapy.setVisibility(View.VISIBLE);
                        therapyLine.setVisibility(View.VISIBLE);
                        fab.setVisibility(View.VISIBLE);
                    }
                    List<FilterType.TherapiesArrayBean> therapiesArray = filterDiseases.get(nameIndex - 1).getTherapiesArray();
                    diseaseId = filterDiseases.get(nameIndex - 1).getDiseaseId();
                    if (rpTherapy.getChildCount() != 0) {
                        rpTherapy.removeAllViews();
                    }
                    RadioButton radioButtons = createrTherapyView("所有疗法");
                    rpTherapy.addView(radioButtons);
                    for (int i = 0; i < therapiesArray.size(); i++) {
                        RadioButton radioButton = createrTherapyView(therapiesArray.get(i).getTherapyName());
                        rpTherapy.addView(radioButton);
                    }
                    ((RadioButton) rpTherapy.getChildAt(0)).setChecked(true);
                }
//                requestTherapyContent();
                break;
            case R.id.rp_therapy:
                page = 1;
                RadioButton viewByIds = (RadioButton) findViewById(checkedId);
                String ss = viewByIds.getText().toString().trim();
                therapyindex = group.indexOfChild(viewByIds);
                Log.e("TAG", "疗法group=" + therapyindex);
                if (therapyindex != 0) {
                    List<FilterType.TherapiesArrayBean> therapiesArray = filterDiseases.get(nameIndex - 1).getTherapiesArray();
                    FilterType.TherapiesArrayBean therapiesArrayBean = therapiesArray.get(therapyindex - 1);
                    therapyId = therapiesArrayBean.getTherapyId();
                } else {
                    therapyId = null;
                }
                requestTherapyContent();
                break;
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
        if (nameIndex == 0) {
            //获取收藏列表
            mUserService.requestCollectionList(new CollectionListReq(page))
                    .map(new HttpResultFunc<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ProgressSubscriber<List<HomeContent>>(this, o -> {
                                if (o.size() == 0) {
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
                                homes.addAll(o);
                                adapter.notifyDataSetChanged();
                                swiperefreshlayout.setRefreshing(false);
                            })
                    );
        } else {
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
    }

    private void LoadMoreData() {
        if (nameIndex == 0) {
            //获取收藏列表
            mUserService.requestCollectionList(new CollectionListReq(page))
                    .map(new HttpResultFunc<>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ProgressSubscriber<List<HomeContent>>(this, o -> {
                                if (o.size() == 0) {
                                    page -- ;
                                    return;
                                }
                                homes.addAll(o);
                                adapter.notifyDataSetChanged();

                            })
                    );
        } else {
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
                                page--;
                                return;
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
            case REQST_VIDEO:
                if (resultCode == 0X0002) {
                    Intent intent = new Intent();
                    intent.putExtra("data", data.getSerializableExtra("data"));
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
                        Intent intent1 = new Intent(VRAreaActivity.this, ImageViewActivity.class);
                        intent1.putExtra("contentId", mDatas.get(position).getId());
                        intent1.putExtra("selecter", holder.tv2.isSelected());
                        intent1.putExtra("forresult", isResult);
                        startActivityForResult(intent1, REQST_IMG);
                    } else {
                        Intent intent1 = new Intent(VRAreaActivity.this, ImageViewActivity.class);
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
                                    .subscribe(new ProgressSubscriber<NoData>(VRAreaActivity.this, o -> {
                                        ToastUtil.showMessage(VRAreaActivity.this, "取消收藏成功");
                                        if (nameIndex == 0) {
                                            mDatas.remove(position);
                                            if (mDatas.size() == 0) {
                                                nodataRl.setVisibility(View.VISIBLE);
                                                areaRecycle.setVisibility(View.GONE);
                                            } else {
                                            }
                                            notifyDataSetChanged();
                                        } else {
                                            holder.tv2.setSelected(false);
                                            mDatas.get(position).setIsselect(false);
                                            mDatas.get(position).setIsCollected(0);
                                            homes.get(position).setIsselect(false);
                                            homes.get(position).setIsCollected(0);
                                        }
                                    }));

                        } else {
                            contentService.collectContent(new CollectContentReq(mDatas.get(position).getId()))
                                    .map(new HttpResultFunc<>())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new ProgressSubscriber<NoData>(VRAreaActivity.this, o -> {
                                        ToastUtil.showMessage(VRAreaActivity.this, "收藏成功");
                                        holder.tv2.setSelected(true);
                                        mDatas.get(position).setIsselect(true);
                                        homes.get(position).setIsselect(true);
                                        mDatas.get(position).setIsCollected(1);
                                        homes.get(position).setIsCollected(1);

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
