package com.medvision.vrmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import com.medvision.vrmc.R;
import com.medvision.vrmc.bean.FreshPatientList;
import com.medvision.vrmc.bean.HomeContent;
import com.medvision.vrmc.bean.MyPatients;
import com.medvision.vrmc.bean.requestbody.BaseReq;
import com.medvision.vrmc.network.ContentService;
import com.medvision.vrmc.utils.DividerGridItemDecoration;
import com.medvision.vrmc.view.BladeView;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;
import com.wzgiceman.rxbuslibrary.rxbus.Subscribe;
import com.wzgiceman.rxbuslibrary.rxbus.ThreadMode;


/**
 * 我的患者
 * Created by raytine on 2017/7/15.
 */

public class MyPatientActivity extends BaseActivity {
    @BindView(R.id.patient_recycle)
    RecyclerView patientRecycle;
    @BindView(R.id.patine_back)
    ImageView patineBack;
    @BindView(R.id.consultation_disease_bv)
    BladeView consultationDiseaseBv;
    @BindView(R.id.mypatinet_add)
    ImageView mypatinetAdd;
    @BindView(R.id.mypatinet_search)
    ImageView mypatinetSearch;
    private PatientAdapter patientAdapter;
    private List<MyPatients> myPatientsList = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private ContentService contentService;
    ArrayList<String> spellListForBladeView = new ArrayList<>();
    ArrayList<String> allSpellList = new ArrayList<>();
    String A = "";
    String B = "";

//    private void iniDatas() {
//        contentService.getMyPatient(new BaseReq())
//                .map(new HttpResultFunc<>())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ProgressSubscriber<List<MyPatients>>(this, o->{
//                    Log.e("----MyPatient","获取患者");
//                    if (myPatientsList.size() !=0){
//                        myPatientsList.clear();
//                    }
//                    myPatientsList.addAll(o);
//
//
//                }));
//    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypatient_activity);
        ButterKnife.bind(this);
        RxBus.getDefault().register(this);
        contentService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        iniData();
    }

    private void iniData() {
        contentService.getMyPatient(new BaseReq())
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<List<MyPatients>>(this, o->{
                    Log.e("----MyPatient","获取患者");
                    if (myPatientsList.size() !=0){
                        myPatientsList.clear();
                    }
                    myPatientsList.addAll(o);
                    initView();
                }));
    }

    private void initView() {
        patientAdapter = new PatientAdapter();
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        patientRecycle.setLayoutManager(layoutManager);
//        patientRecycle.addItemDecoration(new DividerGridItemDecoration(this));
        patientRecycle.setAdapter(patientAdapter);
        sort();
        consultationDiseaseBv.setOnItemClickListener(new BladeView.OnItemClickListener() {
            @Override
            public void click(String s) {
                layoutManager.scrollToPositionWithOffset(allSpellList.indexOf(s), 0);
            }
        });

    }
    private void  sort(){
        Collections.sort(myPatientsList, (o1, o2) -> o1.getRealnameFirstSpell().compareTo(o2.getRealnameFirstSpell()));
        for (int i = 0; i < myPatientsList.size(); i++) {
            B = myPatientsList.get(i).getRealnameFirstSpell();
            allSpellList.add(B);
            if (!A.equals(B)) {
                spellListForBladeView.add(B);
                A = B;
            }

        }
        consultationDiseaseBv.setList(spellListForBladeView.toArray(new String[spellListForBladeView.size()]));

    }
    /*接受事件*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(FreshPatientList o) {

        Log.e("----MyPatient","freshPatientList");
            A = "";
            B = "";
            allSpellList.clear();
            spellListForBladeView.clear();
            iniData();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }

    @OnClick({R.id.patine_back,R.id.mypatinet_search,R.id.mypatinet_add})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.patine_back:
                finish();
                break;
            case R.id.mypatinet_search:
                startActivity(new Intent(this, PaintActivity.class));
                break;
            case R.id.mypatinet_add:
                startActivity(new Intent(this, AddPatientActivity.class));
                break;
        }
    }

    public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_patients, null);
            v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            MyPatients d = myPatientsList.get(position);
            int d1 = position+1;
            if(d1<myPatientsList.size() ){
                MyPatients ds = myPatientsList.get(d1);
                if (d.getRealnameFirstSpell().equals(ds.getRealnameFirstSpell())){
                        d.setShowLine(true);

                }
            }
            Log.e("TAG",d.getName()+"=="+d.getRemark()+"=="+position);
            holder.line.setTag(position);
            if (d.isShowLine() && (int)holder.line.getTag() ==position){
                Log.e("TAGhide",d.getName()+"=="+d.getRemark()+"=="+position);
                holder.line.setVisibility(View.VISIBLE);
            }
            if (position == 0 || !d.getRealnameFirstSpell().equals(myPatientsList.get(position - 1).getRealnameFirstSpell())) {
                holder.spell.setVisibility(View.VISIBLE);

            } else {
                holder.spell.setVisibility(View.GONE);
            }
            holder.spell.setText(d.getRealnameFirstSpell());
            if (myPatientsList.get(position).getRemark() != null && !myPatientsList.get(position).getRemark().equals("")){
                holder.tv_name.setText(myPatientsList.get(position).getRemark());
            }else{
                holder.tv_name.setText(myPatientsList.get(position).getName());
            }
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //患者详情
                    Intent intent = new Intent(MyPatientActivity.this, PatientDetilActivity.class);
                    intent.putExtra("patientId",myPatientsList.get(position).getId());
                    startActivity(intent);
                }
            });

        }



        @Override
        public int getItemCount() {
            return myPatientsList.size();
        }



        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_name;
            ImageView tv_img;
            TextView spell;
            TextView line;
            LinearLayout linearLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_name = (TextView) itemView.findViewById(R.id.item_patient_name);
                spell = (TextView) itemView.findViewById(R.id.item_disease_spell_tv);
                line = (TextView) itemView.findViewById(R.id.line);
                tv_img = (ImageView) itemView.findViewById(R.id.item_patient_img);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.item_patient_ll);
            }
        }
    }
}
