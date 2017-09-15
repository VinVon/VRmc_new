package com.medvision.vrmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.entity.NoData;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.medvision.vrmc.R;
import com.medvision.vrmc.bean.BusArea;
import com.medvision.vrmc.bean.HomeContent;
import com.medvision.vrmc.bean.requestbody.CollectContentReq;
import com.medvision.vrmc.bean.requestbody.CollectionListReq;
import com.medvision.vrmc.network.ContentService;
import com.medvision.vrmc.network.UserService;
import com.medvision.vrmc.utils.Constant;
import com.medvision.vrmc.utils.MyLog;
import com.medvision.vrmc.utils.SpUtils;
import com.medvision.vrmc.view.Navigation;
import com.squareup.picasso.Picasso;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 、收藏界面
 * Created by raytine on 2017/7/20.
 */

public class CollectionActivity extends AppCompatActivity {
    @BindView(R.id.collection_rv)
    XRecyclerView mCollectionRv;
    @BindView(R.id.nodata_layout)
    ViewStub nodataLayout;
    private UserService mUserService;
    private ContentService contentService;
    private CollectionListReq mCollectionListReq;
    private int currentPage = 1;
    private MyAdapter mAdapter;
    private List<HomeContent> data = new ArrayList<>();
    private ArrayList<HomeContent> schemes = new ArrayList<>();
    private final int REQST_add = 101;
    private final int REQST_IMG = 103;
    private final int REQST_OK = 102;
    private int Pos; //item点击的下标
    private int types; //1为开处方选择，0为收藏
    private ArrayList<String> arrayList;
    private HomeContent o1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        RxBus.getDefault().register(this);
        types = getIntent().getIntExtra("types", 0);//1为开处方选择，0为收藏,2为 自制方案
        if (types == 1) {
            arrayList = getIntent().getStringArrayListExtra("datalist");
            Navigation.getInstance(this).setBack().setTitle("常用场景").setRight("完成", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (o1 != null)
                    Intent intent = new Intent(CollectionActivity.this, AddPrescriptionActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }else if (types == 2){
            schemes = (ArrayList<HomeContent>) getIntent().getSerializableExtra("list");
            Navigation.getInstance(this).setTitle("常用场景").setBack().setRight("完成", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("scheme",schemes);
                    setResult(98,intent);
                    finish();
                }
            });
        }
        else {
            Navigation.getInstance(this).setTitle("常用场景").setBack();
        }
        mUserService = HttpMethods.getInstance().getClassInstance(UserService.class);
        contentService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        mAdapter = new MyAdapter(data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCollectionRv.setLayoutManager(layoutManager);
        mCollectionRv.setAdapter(mAdapter);
        mCollectionListReq = new CollectionListReq();
        mCollectionListReq.setToken(SpUtils.getInstance().getToken());
        mCollectionListReq.setPaging(currentPage);
        requestCollectionList(mCollectionListReq, Constant.REQUEST_REFRESH);
        mCollectionRv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                mCollectionListReq.setPaging(currentPage);
                requestCollectionList(mCollectionListReq, Constant.REQUEST_REFRESH);

            }

            @Override
            public void onLoadMore() {
                currentPage++;
                mCollectionListReq.setPaging(currentPage);
                requestCollectionList(mCollectionListReq, Constant.REQUEST_MORE);
            }
        });
    }

    private void requestCollectionList(CollectionListReq collectionListReq, int loadType) {

        mUserService.requestCollectionList(collectionListReq)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<List<HomeContent>>(this, o -> {

                            setViewData(o, loadType);

//							mAdapter.setData(o);
                        })
                );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent datas) {
        if (resultCode == REQST_OK) {
            switch (requestCode) {
                case REQST_IMG:
                    mAdapter.deleteData(Pos);
                    break;
                case REQST_add:
                    boolean o = datas.getBooleanExtra("is", false);
                    if (types == 1) {
                        o1 = data.get(Pos);
                        if (o) {
                            arrayList.add(o1.getId());
                            data.get(Pos).setIsselect(true);
                        } else {
                            arrayList.remove(o1.getId());
                            data.get(Pos).setIsselect(false);
                        }
                        RxBus.getDefault().post(o1);
                        RxBus.getDefault().post(new BusArea(o1.getId(), o1.isselect()));
                    } else {
                        if (o) {
                            data.get(Pos).setIsCollected(1);
                        } else {
                            data.get(Pos).setIsCollected(0);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
            }

        }
    }

    private void setViewData(List<HomeContent> collections, int loadType) {
        if (loadType == Constant.REQUEST_MORE) {
            if (collections.size() == 0) {
                currentPage--;
//                ToastUtil.showMessage(CollectionActivity.this,"没有更多数据.....");
            } else {
//                ToastUtil.showMessage(CollectionActivity.this,"数据加载成功.....");
                mAdapter.addData(collections);
            }

            mCollectionRv.loadMoreComplete();
        } else if (loadType == Constant.REQUEST_REFRESH) {
            if (types == 1) {
                for (int i = 0; i < collections.size(); i++) {
                    HomeContent homeContent = collections.get(i);
                    for (int j = 0; j < arrayList.size(); j++) {
                        if (homeContent.getId().equals(arrayList.get(j))) {//d收藏界面，请求的数据与，添加处方已选择疗法比较,刷新已选着处方的界面
                            homeContent.setIsselect(true);
                        }
                    }
                }
            }else  if (types == 2){
                for (int i = 0; i < collections.size(); i++) {
                    HomeContent homeContent = collections.get(i);
                    for (int j = 0; j < schemes.size(); j++) {
                        if (homeContent.getId().equals(schemes.get(j).getId())) {//d收藏界面，请求的数据与，添加处方已选择疗法比较,刷新已选着处方的界面
                            homeContent.setIsselect(true);
                        }
                    }
                }
            }
            mAdapter.setData(collections);
            mCollectionRv.refreshComplete();
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<HomeContent> datas;

        public MyAdapter(List<HomeContent> data) {
            datas = data;
        }

        public void setData(List<HomeContent> list) {
            datas.clear();
            datas.addAll(list);
            if (datas.size() == 0){
                nodataLayout.inflate();
            }
            mAdapter.notifyDataSetChanged();
        }

        public void addData(List<HomeContent> list) {
            datas.addAll(list);
            mAdapter.notifyDataSetChanged();
        }

        public void deleteData(int pos) {
            datas.remove(pos);
            if (datas.size() == 0){
                nodataLayout.inflate();
            }
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(CollectionActivity.this).inflate(R.layout.item_therapy, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            HomeContent collection = datas.get(position);
            Picasso.with(holder.itemView.getContext()).load(collection.getCoverPic()).into(holder.title);
            holder.tv1.setText(collection.getName());
            holder.tv3.setText("点击量 : " + collection.getClicks() + "   时长 : " + collection.getDuration() + "分钟");
            holder.tv4.setText("¥ " + collection.getPrice());
            if (types == 1) {//types == 1 时，为开处方界面过来的
                holder.tv2.setBackground(getResources().getDrawable(R.drawable.item_therapy_bgs));
                if (collection.isselect()) {
                    holder.tv2.setSelected(true);
                } else {
                    holder.tv2.setSelected(false);
                }
            } else if (types == 2) {//types == 2时，我的方案界面过来的
                holder.tv2.setBackground(getResources().getDrawable(R.drawable.item_therapy_bgs));
                if (collection.isselect()) {
                    holder.tv2.setSelected(true);
                } else {
                    holder.tv2.setSelected(false);
                }
            }
            else {
                holder.tv2.setBackground(getResources().getDrawable(R.drawable.item_therapy_bg));
                if (collection.getIsCollected() == 0) {//0 为没有收藏
                    holder.tv2.setSelected(false);
                } else {
                    holder.tv2.setSelected(true);
                }
            }
            holder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //跳转疗法详情
//                    myClick.onItemClick(holder.rl,position);
                    MyLog.e("------收藏relayout", collection.getId());
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
                        Intent intent1 = new Intent(CollectionActivity.this, ImageViewActivity.class);
                        intent1.putExtra("contentId", collection.getId());
                        intent1.putExtra("selecter", holder.tv2.isSelected());
                        intent1.putExtra("types", types);//判断是iamgeButton收藏还是选择
                        startActivityForResult(intent1, REQST_add);
                    } else if (types == 0) {
                        Intent intent2 = new Intent(CollectionActivity.this, ImageViewActivity.class);
                        intent2.putExtra("contentId", collection.getId());
                        intent2.putExtra("types", 2);//判断是iamgeButton收藏还是选择
                        startActivityForResult(intent2, REQST_IMG);
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
                        collection.setIsselect(false);
                        if (types == 1) {//开处方界面删除该疗法
                            RxBus.getDefault().post(collection);
                            RxBus.getDefault().post(new BusArea(collection.getId(), collection.isselect()));
                            arrayList.remove(collection.getId());
                        } else if (types == 0){
                            contentService.collectContentCancel(new CollectContentReq(collection.getId()))
                                    .map(new HttpResultFunc<>())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new ProgressSubscriber<NoData>(CollectionActivity.this, o -> {
                                        deleteData(position);
                                    }));
                        }else if (types == 2){//取消方案中的内容
                            for (int i = 0; i < schemes.size(); i++) {
                                if (schemes.get(i).getId().equals(collection.getId())){
                                    schemes.remove(i);
                                }
                            }
                        }
                    } else {
                        holder.tv2.setSelected(true);
                        collection.setIsselect(true);
                        if (types == 1) {//
                            MyLog.e("----收藏界面", "收藏界面的选着");
                            RxBus.getDefault().post(collection);
                            RxBus.getDefault().post(new BusArea(collection.getId(), collection.isselect()));
                            arrayList.add(collection.getId());
                        } else if (types == 0){
                            contentService.collectContent(new CollectContentReq(collection.getId()))
                                    .map(new HttpResultFunc<>())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new ProgressSubscriber<NoData>(CollectionActivity.this, o -> {
                                        ToastUtil.showMessage(CollectionActivity.this, "收藏成功");
                                        collection.setIsCollected(1);
                                    }));
                        }else if (types == 2){
                            schemes.add(collection);
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            RelativeLayout rl;
            ImageView title;
            TextView tv1;
            ImageButton tv2;
            TextView tv3;
            TextView tv4;

            public ViewHolder(View view) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }
}
