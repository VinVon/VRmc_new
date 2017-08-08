package com.medvision.vrmc.adapter;

import android.content.Context;
import android.widget.TextView;

import com.medvision.vrmc.R;
import com.medvision.vrmc.adapter.abs.AbstractAdapter;
import com.medvision.vrmc.adapter.abs.ViewHolder;
import com.medvision.vrmc.bean.Prescription;

import java.util.List;



/**
 * Created by 123 on 2017/2/4.
 */

public class MyAdapter extends AbstractAdapter<Prescription.DataBean> {

    public MyAdapter(Context context, List<Prescription.DataBean> listData, int resId) {
        super(context, listData, resId);
    }

    @Override
    public void convert(ViewHolder holder, Prescription.DataBean bean) {
        TextView tv_date = holder.getView(R.id.list_date);
        TextView tv_doctor = holder.getView(R.id.list_doctor);
        TextView tv_style = holder.getView(R.id.list_style);
        TextView tv_content = holder.getView(R.id.list_content);
        TextView tv_state = holder.getView(R.id.list_state);
        tv_date.setText(bean.getCreatedAt().substring(0,11));
        tv_doctor.setText(bean.getDoctorName());
        if (bean.getSource() == 1){
            tv_style.setText("线上");
        }else if (bean.getSource() == 2){
            tv_style.setText("线下");
        }
        if (bean.getStatus() == 1){
            tv_state.setText("未开始");
        }else if (bean.getStatus() == 2){
            tv_state.setText("进行中");
        }
        else if (bean.getStatus() == 3){
            tv_state.setText("已完成");
        }
        else{
            tv_state.setText("已中止");
        }
        tv_content.setText(bean.getDisease());
    }
}
