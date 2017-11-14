package com.medvision.vrmc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.medvision.vrmc.R;
import com.medvision.vrmc.activity.PrescriptionContentActivity;
import com.medvision.vrmc.adapter.MyAdapter;
import com.medvision.vrmc.bean.NophonePrescriptionContent;
import com.medvision.vrmc.bean.Prescription;
import com.medvision.vrmc.bean.PrescriptionContent;
import com.medvision.vrmc.imp.QueryPrescription;
import com.medvision.vrmc.presenter.QueryPrescriptionPresenter;
import com.medvision.vrmc.utils.SpUtils;
import com.medvision.vrmc.utils.ToastCommom;



/**
 * Created by raytine on 2017/7/4.
 */

public class PrescriptionFragment extends BaseFragment implements QueryPrescription {
    private static PrescriptionFragment fragment;
    @BindView(R.id.listview)
    ListView listview;
    Unbinder unbinder;
    private MyAdapter myAdapter;
    private String userId;
    private List patient = new ArrayList();
    private String token ;
    private QueryPrescriptionPresenter queryPrescriptionPresenter;
    public static PrescriptionFragment newInstance() {
        if (fragment == null) {
            return new PrescriptionFragment();
        }
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prescription, container, false);
        unbinder = ButterKnife.bind(this, view);
        queryPrescriptionPresenter = new QueryPrescriptionPresenter(this);
        Bundle arguments = getArguments();
        userId = arguments.getString("id");
        token= SpUtils.getInstance().getToken();
        initView();
        if (userId != null){
            getPrescription();
        }
        return view;
    }

    private void initView() {
        myAdapter = new MyAdapter(getActivity(), patient, R.layout.layout_chufang);
        listview.setAdapter(myAdapter);
        listview.setDividerHeight(25);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prescription.DataBean cf = (Prescription.DataBean) patient.get(position);
                    Intent ss = new Intent(getActivity(), PrescriptionContentActivity.class);
                    ss.putExtra("id", cf.getId());
                    startActivity(ss);
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getPrescription() {
        Map<String, Object> priArgs = new HashMap<>();
        priArgs.put("patientId", userId);
        priArgs.put("token", token);
        queryPrescriptionPresenter.setMap(priArgs);
        queryPrescriptionPresenter.getPrescription();
    }

    @Override
    public void tokenchange() {

    }

    @Override
    public void updateView(Prescription user) {
        if (patient.size()!=0){
            patient.clear();
        }
        if (user.getData() != null) {
            for (int i = 0; i < user.getData().size(); i++) {
                patient.add(user.getData().get(i));
            }
            myAdapter.notifyDataSetChanged();
        } else {
            ToastCommom.createInstance().ToastShow(getActivity(), "没有数据");
        }
    }

    @Override
    public void update(PrescriptionContent p) {

    }

    @Override
    public void update(NophonePrescriptionContent p, int s) {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String msg) {

    }
}
