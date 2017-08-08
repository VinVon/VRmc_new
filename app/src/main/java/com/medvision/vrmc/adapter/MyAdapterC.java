package com.medvision.vrmc.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.medvision.vrmc.R;
import com.medvision.vrmc.adapter.abs.AbstractAdapter;
import com.medvision.vrmc.adapter.abs.ViewHolder;
import com.medvision.vrmc.bean.PrescriptionContent;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by 123 on 2017/2/4.
 */

public class MyAdapterC extends AbstractAdapter<PrescriptionContent.DataBean> {
    Callplat call;
    public MyAdapterC(Context context, List<PrescriptionContent.DataBean> listData, int resId, Callplat call) {
        super(context, listData, resId);
        this.call = call;
    }

    @Override
    public void convert(ViewHolder holder, final PrescriptionContent.DataBean bean) {
        TextView tv_name = holder.getView(R.id.mlist_name);
        TextView tv_time= holder.getView(R.id.mlist_time);
        TextView tv_num = holder.getView(R.id.mlist_num);
        ImageView tv_paly = holder.getView(R.id.mlist_paly);
        tv_name.setText(bean.getContent_name());
        if (bean.getPeriodUnit() == 1){
            tv_num.setText(bean.getFrequency()+"次/日-共"+bean.getPeriod()+"日-合计"+bean.getTimes()+"次  已使用"+bean.getUseTimes()+"次");
        }else if(bean.getPeriodUnit() == 2){
            tv_num.setText(bean.getFrequency()+"次/周-共"+bean.getPeriod()+"周-合计"+bean.getTimes()+"次  已使用"+bean.getUseTimes()+"次");
        }else{
            tv_num.setText(bean.getFrequency()+"次/月-共"+bean.getPeriod()+"月-合计"+bean.getTimes()+"次  已使用"+bean.getUseTimes()+"次");
        }
        Picasso.with(context)
                .load(bean.getContent_coverPic())
                .into(tv_paly);
        Log.e("--------url",bean.getContent_coverPic()+"");
        tv_paly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.onsuccess(bean);
            }
        });
        tv_time.setText("上次使用时间 ： "+bean.getLastUseAt());
    }
    public interface  Callplat{
        void onsuccess(PrescriptionContent.DataBean id);
    };

}
