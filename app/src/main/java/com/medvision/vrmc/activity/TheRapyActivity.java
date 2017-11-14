package com.medvision.vrmc.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
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
import com.medvision.vrmc.bean.BusArea;
import com.medvision.vrmc.bean.Buser;
import com.medvision.vrmc.bean.HomeContent;
import com.medvision.vrmc.bean.requestbody.CollectContentReq;
import com.medvision.vrmc.bean.requestbody.HomeContentReq;
import com.medvision.vrmc.imp.EndlessRecyclerOnScrollListener;
import com.medvision.vrmc.imp.MyClick;
import com.medvision.vrmc.network.ContentService;
import com.medvision.vrmc.utils.ActivityManager;
import com.medvision.vrmc.utils.MyLog;
import com.medvision.vrmc.utils.SpUtils;
import com.medvision.vrmc.view.Navigation;
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
 * Created by raytine on 2017/7/18.
 */

public class TheRapyActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.base_header_back_iv)
    ImageView baseHeaderBackIv;
    @BindView(R.id.base_header_title_tv)
    TextView baseHeaderTitleTv;
    @BindView(R.id.base_header_right_bt)
    Button baseHeaderRightBt;
    @BindView(R.id.base_header_right_iv)
    ImageView baseHeaderRightIv;
    @BindView(R.id.base_header_rl)
    RelativeLayout baseHeaderRl;
    @BindView(R.id.therapy_tv_point)
    TextView therapyTvPoint;
    @BindView(R.id.therapy_tv_price)
    TextView therapyTvPrice;
    @BindView(R.id.therapy_tv_time)
    TextView therapyTvTime;
    @BindView(R.id.therapy_list)
    RecyclerView therapyList;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.nodata_layout)
    ViewStub nodataLayout;
    //    private ArrayList<String> mList = new ArrayList<>();
//    LinearLayoutManager layoutManager;
//    SelectAdapter mAdapter;
    private String diseaseId = null;
    private String therapyId = null;
    private String keyword = null;
    private int page = 1;
    private int priceClick = 1;
    private int timeClick = 1;
    private int firstClick = 1;
    private MyRecyclerAdapter adapter;
    private List<HomeContent> homes = new ArrayList<>();
    private ContentService contentService;
    private String sortName = null;
    private String sortOrder = null;
    private int types; //按钮是选择还是收藏
    private ArrayList<String> arrayList;
    private int Pos;//下标
    private HomeContent o1;
    private final int REQST_VIDEO = 100;
    private final int REQST_IMG = 101;
    private final int REQST_OK = 102;

    @Override
    protected void onResume() {
        super.onResume();
//        MyLog.e("-----TheRapy","onResume");
//        if (homes.size() !=0){
//            for (int i = 0; i <4 ; i++) {
//                MyLog.e("-----TheRapy",homes.get(i).getIsCollected()+"集");
//            }
//        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.therapy_all);
        setContentView(R.layout.activity_rherapy);
        ButterKnife.bind(this);
        RxBus.getDefault().register(this);
        MyLog.e("-----TheRapy", "onResume");
        contentService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        types = getIntent().getIntExtra("types", 0);//1为开处方选择，0为收藏
        if (types == 1) {
            arrayList = getIntent().getStringArrayListExtra("datalist");
        }
//        int type = getIntent().getIntExtra("type", 0);
        keyword = getIntent().getStringExtra("keyword");
        diseaseId = getIntent().getStringExtra("diseaseID");
        therapyId = getIntent().getStringExtra("therapyID");
        String name = getIntent().getStringExtra("name");
        MyLog.e("--------TheRapyActivity", diseaseId + "---" + therapyId);
        if (types == 0) {
            Navigation.getInstance(TheRapyActivity.this).setBack().setTitle(name);
        } else {
            Navigation.getInstance(TheRapyActivity.this).setBack().setTitle(name).setRight("完成", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (o1 != null)
                    ActivityManager.getInstance().finish(TheRapyActivity.class);
                    ActivityManager.getInstance().finish(AreaActivity.class);
//                    Intent intent = new Intent(TheRapyActivity.this, AddPrescriptionActivity.class);
//                    startActivity(intent);
//                    finish();
                }
            });
        }
        initView();
        initData();
//        frombus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }

    /*接受事件*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(Buser o) {
        if (types == 1) {
//            if (o.is()){
//                homes.get(Pos).setIsselect(true);
//            }else{
//                homes.get(Pos).setIsselect(false);
//            }
//             o1 = homes.get(Pos);
//
        } else {
            if (o.is()) {
                MyLog.e("-----shoucang", "收藏" + Pos);
                homes.get(Pos).setIsCollected(1);
            } else {
                MyLog.e("-----shoucang", "取消收藏" + Pos);
                homes.get(Pos).setIsCollected(0);
            }
        }
        adapter.setmDatas(homes);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == REQST_OK) {
            boolean o = data.getBooleanExtra("is", false);
            switch (requestCode) {
                case REQST_VIDEO:
                    if (types == 1) {
                        if (o) {
                            homes.get(Pos).setIsselect(true);
                        } else {
                            homes.get(Pos).setIsselect(false);
                        }
                        o1 = homes.get(Pos);

                    } else {
                        if (o) {
                            homes.get(Pos).setIsCollected(1);
                        } else {
                            homes.get(Pos).setIsCollected(0);
                        }
                    }
                    adapter.setmDatas(homes);
                    break;
                case REQST_IMG:
                    if (types == 1) {
                        o1 = homes.get(Pos);
                        if (o) {
                            arrayList.add(o1.getId());
                            homes.get(Pos).setIsselect(true);
                        } else {
                            arrayList.remove(o1.getId());
                            homes.get(Pos).setIsselect(false);
                        }
                        RxBus.getDefault().post(o1);
                        RxBus.getDefault().post(new BusArea(o1.getId(), o1.isselect()));
                    } else {
                        if (o) {
                            homes.get(Pos).setIsCollected(1);
                        } else {
                            homes.get(Pos).setIsCollected(0);
                        }
                    }
                    adapter.setmDatas(homes);
                    break;
            }
        }
    }
//    private void frombus() {
//        RxBus.getInstance().toObserverable(Buser.class)
//                .subscribeOn(Schedulers.io())
//                //在主线程进行观察，可做UI更新操作 homeContentList.add(o);
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        o->{
//                    if (types == 1){
//                        if (o.is()){
//                            homes.get(Pos).setIsselect(true);
//                        }else{
//                            homes.get(Pos).setIsselect(false);
//                        }
//                        HomeContent o1 = homes.get(Pos);
//                        RxBus.getInstance().send(o1);
//
//                    }else{
//                        if (o.is()){
//                            homes.get(Pos).setIsCollected(1);
//                        }else{
//                            homes.get(Pos).setIsCollected(0);
//                        }
//                    }
//                    adapter.setmDatas(homes);
//                });
//    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        therapyList.setLayoutManager(layoutManager);
        adapter = new MyRecyclerAdapter(this, homes);
        therapyList.setAdapter(adapter);
//        therapyList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        swiperefreshlayout.setOnRefreshListener(this);
        therapyList.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page += 1;
                HomeContentReq h = new HomeContentReq();
                h.setDiseaseId(diseaseId);
                h.setTherapyId(therapyId);
                h.setPage(page);
                h.setSortName(sortName);
                h.setSortOrder(sortOrder);
                h.setKeyword(keyword);
                MyLog.e("----jiazai", "page=" +h.toString()+"token +"+ SpUtils.getInstance().getToken());
                simulateLoadMoreData(h);
            }
        });
    }

    private void initData() {
        HomeContentReq h = new HomeContentReq();
        h.setDiseaseId(diseaseId);
        h.setTherapyId(therapyId);
        h.setPage(page);
        h.setKeyword(keyword);
        contentService.getSearchContent(h)
                .map(new HttpResultFunc<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ProgressSubscriber<List<HomeContent>>(this, new SubscriberOnNextListener<List<HomeContent>>() {
                    @Override
                    public void onNext(List<HomeContent> homeContents) {
                       if (homeContents.size()==0){
                           nodataLayout.inflate();
                       }else{
                           if (types == 1) {
                               for (int i = 0; i < homeContents.size(); i++) {
                                   HomeContent homeContent = homeContents.get(i);
                                   for (int j = 0; j < arrayList.size(); j++) {
                                       if (homeContent.getId().equals(arrayList.get(j))) {//d收藏界面，请求的数据与，添加处方已选择疗法比较,刷新已选着处方的界面
                                           homeContent.setIsselect(true);
                                       }
                                   }
                               }
                           }
                           homes.addAll(homeContents);
                           adapter.notifyDataSetChanged();
                       }
                    }
                }));
    }

    @Override
    public void onRefresh() {
        page = 1;
        HomeContentReq h = new HomeContentReq();
        h.setDiseaseId(diseaseId);
        h.setTherapyId(therapyId);
        h.setPage(page);
        h.setSortName(sortName);
        h.setSortOrder(sortOrder);
        h.setKeyword(keyword);
        loadData(h);

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
            MyLog.e("----adaopter", mDatas.get(Pos).getIsCollected() + "");
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
            if (types == 1) {//types == 1 时，为开处方界面过来的
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
//                    myClick.onItemClick(holder.rl,position);
                    MyLog.e("------收藏relayout", mDatas.get(position).getId());
                    Pos = position;
//                    if (mDatas.get(position).getType() == 1) {
//                        Intent intent1 = new Intent(TheRapyActivity.this, VrVideoActivity.class);
//                        intent1.putExtra("contentId", mDatas.get(position).getId());
//                        intent1.putExtra("selecter", holder.tv2.isSelected());
//                        intent1.putExtra("types", types);//判断是iamgeButton收藏还是选择
//                        startActivityForResult(intent1, REQST_VIDEO);
//                    } else if (mDatas.get(position).getType() == 2) {
//                        MyLog.e("------收藏relayout", "2");
//                    } else if (mDatas.get(position).getType() == 3) {
                    if (types == 1) {
                        Intent intent1 = new Intent(TheRapyActivity.this, ImageViewActivity.class);
                        intent1.putExtra("contentId", mDatas.get(position).getId());
                        intent1.putExtra("selecter", holder.tv2.isSelected());
                        intent1.putExtra("types", types);//判断是iamgeButton收藏还是选择
                        startActivityForResult(intent1, REQST_IMG);
                    } else if (types == 0) {
                        Intent intent1 = new Intent(TheRapyActivity.this, ImageViewActivity.class);
                        intent1.putExtra("contentId", mDatas.get(position).getId());
                        intent1.putExtra("types", types);//判断是iamgeButton收藏还是选择
                        startActivity(intent1);
                    }
//                    }
                }
            });
            holder.tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyLog.e("---------sc", "选择收藏");
                    if (holder.tv2.isSelected()) {
                        holder.tv2.setSelected(false);
                        mDatas.get(position).setIsselect(false);
                        if (types == 1) {//开处方界面删除该疗法
                            RxBus.getDefault().post(mDatas.get(position));
                            RxBus.getDefault().post(new BusArea(mDatas.get(position).getId(), mDatas.get(position).isselect()));
                            arrayList.remove(mDatas.get(position).getId());
                        } else {
                            contentService.collectContentCancel(new CollectContentReq(mDatas.get(position).getId()))
                                    .map(new HttpResultFunc<>())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new ProgressSubscriber<NoData>(TheRapyActivity.this, o -> {
                                        ToastUtil.showMessage(TheRapyActivity.this, "取消收藏成功");
                                        mDatas.get(position).setIsCollected(0);
                                    }));
                        }
                    } else {
                        holder.tv2.setSelected(true);
                        mDatas.get(position).setIsselect(true);
                        if (types == 1) {//
                            MyLog.e("----场景", "场景界面的选着");
                            RxBus.getDefault().post(mDatas.get(position));
                            RxBus.getDefault().post(new BusArea(mDatas.get(position).getId(), mDatas.get(position).isselect()));
                            arrayList.add(mDatas.get(position).getId());
                        } else {
                            contentService.collectContent(new CollectContentReq(mDatas.get(position).getId()))
                                    .map(new HttpResultFunc<>())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new ProgressSubscriber<NoData>(TheRapyActivity.this, o -> {
                                        ToastUtil.showMessage(TheRapyActivity.this, "收藏成功");
                                        mDatas.get(position).setIsCollected(1);
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

    @OnClick({R.id.therapy_tv_price, R.id.therapy_tv_point, R.id.therapy_tv_time})
    public void OnClick(View v) {
        page = 1;
        swiperefreshlayout.setRefreshing(true);
        switch (v.getId()) {
            case R.id.therapy_tv_price://价格
                timeClick = 1;
                firstClick = 1;
                Drawable drawabless = getResources().getDrawable(R.mipmap.triangle_default);
                drawabless.setBounds(0, 0, drawabless.getMinimumWidth(), drawabless.getMinimumHeight());
                therapyTvTime.setCompoundDrawables(null, null, drawabless, null);
                sortName = "price";
                therapyTvPoint.setEnabled(true);
                therapyTvPrice.setTextColor(getResources().getColor(R.color.colorPrimary));
                therapyTvPoint.setTextColor(getResources().getColor(R.color.gray));
                therapyTvTime.setTextColor(getResources().getColor(R.color.gray));
                priceClick += 1;
                Drawable drawable = null;
                if (priceClick % 3 == 1) {
                    sortName = null;
                    sortOrder = null;
                    drawable = getResources().getDrawable(R.mipmap.triangle_default);

                } else if (priceClick % 3 == 2) {
                    drawable = getResources().getDrawable(R.mipmap.triangle_up);
                    sortOrder = "asc";
                } else {
                    drawable = getResources().getDrawable(R.mipmap.triangle_down);
                    sortOrder = "desc";
                }
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                therapyTvPrice.setCompoundDrawables(null, null, drawable, null);
                HomeContentReq h = new HomeContentReq();
                h.setDiseaseId(diseaseId);
                h.setTherapyId(therapyId);
                h.setPage(page);
                h.setKeyword(keyword);
                h.setSortName(sortName);
                h.setSortOrder(sortOrder);
                loadData(h);
                break;
            case R.id.therapy_tv_point://点击量
                sortName = "clicks";
                sortOrder = null;
                therapyTvPrice.setTextColor(getResources().getColor(R.color.gray));
                therapyTvTime.setTextColor(getResources().getColor(R.color.gray));
                timeClick = 1;
                priceClick = 1;
                firstClick += 1;
                if (firstClick % 2 == 0) {
                    therapyTvPoint.setTextColor(getResources().getColor(R.color.colorPrimary));

                } else {
                    sortName = null;
                    therapyTvPoint.setTextColor(getResources().getColor(R.color.gray));
                }
                Drawable drawables = getResources().getDrawable(R.mipmap.triangle_default);
                drawables.setBounds(0, 0, drawables.getMinimumWidth(), drawables.getMinimumHeight());
                therapyTvPrice.setCompoundDrawables(null, null, drawables, null);
                therapyTvTime.setCompoundDrawables(null, null, drawables, null);
                HomeContentReq h1 = new HomeContentReq();
                h1.setDiseaseId(diseaseId);
                h1.setTherapyId(therapyId);
                h1.setPage(page);
                h1.setKeyword(keyword);
                h1.setSortName(sortName);
                h1.setSortOrder(sortOrder);
                loadData(h1);
                break;
            case R.id.therapy_tv_time://时长
                sortName = "duration";
                priceClick = 1;
                firstClick = 1;
                Drawable drawablesss = getResources().getDrawable(R.mipmap.triangle_default);
                drawablesss.setBounds(0, 0, drawablesss.getMinimumWidth(), drawablesss.getMinimumHeight());
                therapyTvPrice.setCompoundDrawables(null, null, drawablesss, null);
                therapyTvPoint.setEnabled(true);
                therapyTvPrice.setTextColor(getResources().getColor(R.color.gray));
                therapyTvPoint.setTextColor(getResources().getColor(R.color.gray));
                therapyTvTime.setTextColor(getResources().getColor(R.color.colorPrimary));
                timeClick += 1;
                Drawable drawable1 = null;
                if (timeClick % 3 == 1) {
                    sortName = null;
                    sortOrder = null;
                    drawable1 = getResources().getDrawable(R.mipmap.triangle_default);
                } else if (timeClick % 3 == 2) {
                    drawable1 = getResources().getDrawable(R.mipmap.triangle_up);
                    sortOrder = "asc";
                } else {
                    drawable1 = getResources().getDrawable(R.mipmap.triangle_down);
                    sortOrder = "desc";
                }
                drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                therapyTvTime.setCompoundDrawables(null, null, drawable1, null);
                HomeContentReq h2 = new HomeContentReq();
                h2.setDiseaseId(diseaseId);
                h2.setTherapyId(therapyId);
                h2.setPage(page);
                h2.setKeyword(keyword);
                h2.setSortName(sortName);
                h2.setSortOrder(sortOrder);
                loadData(h2);
                break;
        }
    }

    private void loadData(HomeContentReq h) {
        contentService.getSearchContent(h)
                .map(new HttpResultFunc<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ProgressSubscriber<List<HomeContent>>(this, new SubscriberOnNextListener<List<HomeContent>>() {
                    @Override
                    public void onNext(List<HomeContent> homeContents) {
                        if (homes.size() != 0) {
                            homes.clear();
                        }
                        if (types == 1) {
                            for (int i = 0; i < homeContents.size(); i++) {
                                HomeContent homeContent = homeContents.get(i);
                                for (int j = 0; j < arrayList.size(); j++) {
                                    if (homeContent.getId().equals(arrayList.get(j))) {//d收藏界面，请求的数据与，添加出方已选择疗法比较
                                        homeContent.setIsselect(true);
                                    }
                                }
                            }
                        }
                        homes.addAll(homeContents);
                        adapter.notifyDataSetChanged();
                        swiperefreshlayout.setRefreshing(false);
                    }
                }));
    }

    private void simulateLoadMoreData(HomeContentReq h) {
        contentService.getSearchContent(h)
                .map(new HttpResultFunc<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ProgressSubscriber<List<HomeContent>>(this, new SubscriberOnNextListener<List<HomeContent>>() {
                    @Override
                    public void onNext(List<HomeContent> homeContents) {
                        if (types == 1){
                            for (int i = 0; i <homeContents.size() ; i++) {
                                HomeContent homeContent = homeContents.get(i);
                                for (int j = 0; j <arrayList.size() ; j++) {
                                    if (homeContent.getId().equals(arrayList.get(j))){//d收藏界面，请求的数据与，添加出方已选择疗法比较
                                        homeContent.setIsselect(true);
                                    }
                                }
                            }
                        }
                        if (homeContents.size() == 0) {
                            ToastUtil.showMessage(TheRapyActivity.this, "没有更多数据");
                            page -= 1;
                        }
//                        else if(homeContents.size()<8){
//                            homes.addAll(homeContents);
//                            adapter.notifyDataSetChanged();
//                        }
                        else {
                            homes.addAll(homeContents);
                            adapter.notifyDataSetChanged();
                        }

                    }
                }));
    }

    ;


}

