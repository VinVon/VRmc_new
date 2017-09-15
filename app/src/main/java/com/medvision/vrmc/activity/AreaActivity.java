package com.medvision.vrmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.medvision.vrmc.R;
import com.medvision.vrmc.UrlPath.UrlHttp;
import com.medvision.vrmc.bean.FilterType;
import com.medvision.vrmc.bean.requestbody.BaseReq;
import com.medvision.vrmc.imp.MyClick;
import com.medvision.vrmc.utils.MyLog;
import com.medvision.vrmc.utils.SpUtils;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;
import com.wzgiceman.rxbuslibrary.rxbus.Subscribe;
import com.wzgiceman.rxbuslibrary.rxbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



import com.medvision.vrmc.bean.BusArea;
import com.medvision.vrmc.network.ContentService;

/**
 * Created by raytine on 2017/7/11.
 */

public class AreaActivity extends BaseActivity {
    @BindView(R.id.area_back)
    ImageView areaBack;
    @BindView(R.id.query)
    EditText query;
    @BindView(R.id.search_toolss)
    RelativeLayout searchToolss;
    @BindView(R.id.area_bar)
    RelativeLayout areaBar;
    @BindView(R.id.area_keshi)
    RadioGroup areaKeshi;
    @BindView(R.id.area_title)
    LinearLayout areaTitle;
    @BindView(R.id.area_list)
    RecyclerView areaList;
    private ContentService contentService;
    private HomeAdapter mAdapter;
    private HomeAdapters mAdapters;
    private List<FilterType> diseaseitems = new ArrayList<>();
    private List<String> strings = new ArrayList<>();
    private List<RadioButton> list = new ArrayList<>();
    private List<FilterType.TherapiesArrayBean> mDatas = new ArrayList<>();
    private EditText et_search;
    private View head;
    private int index;//症状下标
    private boolean isType = false;//radiogroup 是否切换到我的收藏，需要更换recycleView布局
    private int type ;//1 是选择，0是收藏
    private ArrayList<String> arrayList ;
    private String url = UrlHttp.BASE_URL_EVERYTHING+"h5/help";
    private String diseaseIds;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        ButterKnife.bind(this);
        RxBus.getDefault().register(this);
        type = getIntent().getIntExtra("type",0);
        if (type == 1){
            arrayList = getIntent().getStringArrayListExtra("datalist");
        }
        contentService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        head = LayoutInflater.from(this).inflate(R.layout.area_head, null);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String h5 ;
                if(diseaseIds.isEmpty()){
                   h5= url;
                }else {
                    h5= url+"/disease?id="+diseaseIds;
                }
                Intent intent = new Intent(AreaActivity.this,WebViewActivity.class);
                intent.putExtra("url",h5);
                startActivity(intent);
            }
        });
        initData();
        initView();
    }

    private void initView() {
        areaList.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapters = new HomeAdapters();
        mAdapters.setOnItemClickLitener(new MyClick() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(AreaActivity.this, CollectionActivity.class);
                        intent.putExtra("types",type);
                        if (type == 1){
                            intent.putStringArrayListExtra("datalist",arrayList);
                        }
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intents = new Intent(AreaActivity.this, SchemeActivity.class);
                        intents.putExtra("types",type);
                        startActivity(intents);
                        break;
                }
            }
        });
        mAdapter = new HomeAdapter();
        mAdapter.setOnItemClickLitener(new MyClick() {
            @Override
            public void onItemClick(View view, int position) {//根据选择的病种，进去疗法界面

                Intent intent = new Intent(AreaActivity.this, TheRapyActivity.class);
                intent.putExtra("types",type);
                if (type == 1){
                    intent.putStringArrayListExtra("datalist",arrayList);
                }
//                intent.putExtra("type", 1);//判断是否为搜索查询
                intent.putExtra("keyword","");
                intent.putExtra("name", mDatas.get(position).getTherapyName());
                intent.putExtra("diseaseID", diseaseitems.get(index-1).getDiseaseId());
                intent.putExtra("therapyID", mDatas.get(position).getTherapyId());
                startActivity(intent);

            }
        });

        areaList.setAdapter(mAdapter);
        mAdapter.setHeaderView(head);
//        areaList.addItemDecoration(new DividerGridItemDecoration(this));
        areaBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_search = (EditText) this.findViewById(R.id.query);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_SEARCH://跳转
                        Intent intent = new Intent(AreaActivity.this, TheRapyActivity.class);
                        intent.putExtra("types",type);
                        if (type == 1){
                            intent.putStringArrayListExtra("datalist",arrayList);
                        }
//                        intent.putExtra("type", 1);//判断是否为搜索查询.决定是否有建议指导
                        intent.putExtra("keyword",v.getText()+"");
                        intent.putExtra("name", "搜索结果");
                        intent.putExtra("diseaseID", "");
                        intent.putExtra("therapyID","");
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    private void initData() {
        strings.add("常用场景");
        strings.add("我的方案");
        contentService.getFilterDiseases(new BaseReq())
                .map(new HttpResultFunc<List<FilterType>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<List<FilterType>>(this,filterDiseases->{
                            RadioGroup.LayoutParams l = new RadioGroup.LayoutParams(dip2px(120), dip2px(55));
                            RadioButton ra = new RadioButton(AreaActivity.this);
                            ra.setText("我的收藏");
                            ra.setBackground(getResources().getDrawable(R.drawable.bg_radiobuttons));
                            ra.setButtonDrawable(android.R.color.transparent);
                            ra.setSingleLine(false);
                            ra.setGravity(Gravity.CENTER);
                            ra.setMaxLines(2);
                            ra.setLayoutParams(l);
                            list.add(ra);
                            for (int i = 0; i < filterDiseases.size(); i++) {
                                diseaseitems.add(filterDiseases.get(i));
                                RadioButton ras = new RadioButton(AreaActivity.this);
                                if (filterDiseases.get(i).getDiseaseName().length()>5){
                                String substring = filterDiseases.get(i).getDiseaseName().substring(0, 5);
                                String name = filterDiseases.get(i).getDiseaseName().substring(5, filterDiseases.get(i).getDiseaseName().length());
                                    if (name.length()>5){
                                        name = name.substring(0,5)+"...";
                                    }
                                ras.setText(substring+"\n"+name);}
                                else {
                                    ras.setText(filterDiseases.get(i).getDiseaseName());
                                }
                                ras.setBackground(getResources().getDrawable(R.drawable.bg_radiobuttons));
                                ras.setButtonDrawable(android.R.color.transparent);
                                ra.setSingleLine(false);
                                ras.setGravity(Gravity.CENTER);
                                ras.setMaxLines(2);
                                ras.setLayoutParams(l);
                                list.add(ras);

                            }
                            for (int i = 0; i < list.size(); i++) {
                                areaKeshi.addView(list.get(i));

                            }
                            FilterType.TherapiesArrayBean r = new FilterType.TherapiesArrayBean();
                            r.setTherapyName("所有疗法");
                            r.setTherapyId("");
                            mDatas.add(r);
                            for (int i = 0; i <filterDiseases.get(0).getTherapiesArray().size() ; i++) {
                                mDatas.add(filterDiseases.get(0).getTherapiesArray().get(i));
                            }
                            mAdapter.addDatas(mDatas);
                            ((RadioButton) areaKeshi.getChildAt(0)).setChecked(true);
                })
                );
        areaKeshi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton viewById = (RadioButton) findViewById(checkedId);
                String s = viewById.getText().toString().trim();
                index = group.indexOfChild(viewById);
                if (index == 0){
                    isType = true;
                    areaList.setAdapter(mAdapters);
                    return ;
                }
                if (mDatas.size()!= 0){
                    mDatas.clear();
                }
                 diseaseIds = diseaseitems.get(index - 1).getDiseaseId();
                FilterType.TherapiesArrayBean r = new FilterType.TherapiesArrayBean();
                r.setTherapyName("所有疗法");
                r.setTherapyId("");
                mDatas.add(r);
                List<FilterType.TherapiesArrayBean> therapiesArray = diseaseitems.get(index-1).getTherapiesArray();
                for (int i = 0; i <therapiesArray.size() ; i++) {
                    mDatas.add(therapiesArray.get(i));
                }
                mAdapter.addDatas(mDatas);
                if (isType) {
                    isType  = false;
                    areaList.setAdapter(mAdapter);
//                    mAdapter.addDatas(mDatas);
//                    mAdapter.setHeaderView(head);
//                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    public int dip2px(int dip){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
    }
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
        public static final int TYPE_HEADER = 0;
        public static final int TYPE_NORMAL = 1;
        private View mHeaderView;
        private MyClick myClick;
        private List<FilterType.TherapiesArrayBean> mDatass = new ArrayList<>();
        public void setOnItemClickLitener(MyClick mOnItemClickLitener) {
            this.myClick = mOnItemClickLitener;
        }

        public void setHeaderView(View headerView) {
            mHeaderView = headerView;
            notifyItemInserted(0);
        }

        public View getHeaderView() {
            return mHeaderView;
        }
        public void addDatas(List<FilterType.TherapiesArrayBean> datas) {
            if (mDatass.size() != 0){
                mDatass.clear();
            }
            mDatass.addAll(datas);
            notifyDataSetChanged();
        }
        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return getItemViewType(position) == TYPE_HEADER
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (mHeaderView == null) return TYPE_NORMAL;
            if (position == 0) return TYPE_HEADER;
            return TYPE_NORMAL;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mHeaderView != null && viewType == TYPE_HEADER)
                return new MyViewHolder(mHeaderView);
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    AreaActivity.this).inflate(R.layout.item_home, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if (getItemViewType(position) == TYPE_HEADER) return;
            final int pos = getRealPosition(holder);

            holder.tv.setText(mDatass.get(pos).getTherapyName());
            // 如果设置了回调，则设置点击事件
            if (myClick != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myClick.onItemClick(holder.itemView, pos);
                    }
                });
            }
        }
        public int getRealPosition(RecyclerView.ViewHolder holder) {
            int position = holder.getLayoutPosition();
            return mHeaderView == null ? position : position - 1;
        }
        @Override
        public int getItemCount() {
            return mHeaderView == null ? mDatass.size() : mDatass.size() + 1;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                if(itemView == mHeaderView) return;
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }

    }

    class HomeAdapters extends RecyclerView.Adapter<HomeAdapters.MyViewHolder> {
        public static final int TYPE_HEADER = 0;
        public static final int TYPE_NORMAL = 1;
        private View mHeaderView;
        private MyClick myClick;

        public void setOnItemClickLitener(MyClick mOnItemClickLitener) {
            this.myClick = mOnItemClickLitener;
        }

        public void setHeaderView(View headerView) {
            mHeaderView = headerView;
            notifyItemInserted(0);
        }

        public View getHeaderView() {
            return mHeaderView;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return getItemViewType(position) == TYPE_HEADER
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (mHeaderView == null) return TYPE_NORMAL;
            if (position == 0) return TYPE_HEADER;
            return TYPE_NORMAL;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mHeaderView != null && viewType == TYPE_HEADER)
                return new MyViewHolder(mHeaderView);
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    AreaActivity.this).inflate(R.layout.item_home, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if (getItemViewType(position) == TYPE_HEADER) return;
            holder.tv.setText(strings.get(position));
            // 如果设置了回调，则设置点击事件
            if (myClick != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        myClick.onItemClick(holder.itemView, pos);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return strings.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }

    }
    private boolean aBoolean = true;
    /*接受事件*/
    @Subscribe(threadMode= ThreadMode.NEW_THREAD)
    public void event(BusArea o){
        MyLog.e("---来到场景界面","来到场景界面"+arrayList.size()+"个");
        ArrayList<String> Lists = new ArrayList<>();
        Lists.addAll(arrayList);
        if (arrayList.size() ==0){
            aBoolean = true;
        }
        for (int i = 0; i <Lists.size(); i++) {
            if (arrayList.get(i).equals(o.getStr()) && !o.isselect()){
                arrayList.remove(i);
                MyLog.e("---来到场景界面","移除了");
                aBoolean = false;
                break;
            }else{
                aBoolean = true ;
            }
        }
        if (aBoolean){
            arrayList.add(o.getStr());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }
}
