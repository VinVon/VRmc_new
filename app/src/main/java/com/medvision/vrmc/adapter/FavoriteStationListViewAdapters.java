package com.medvision.vrmc.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.medvision.vrmc.R;
import com.medvision.vrmc.adapter.abs.AbstractAdapter;
import com.medvision.vrmc.adapter.abs.ViewHolder;
import com.medvision.vrmc.bean.SingerPrescriptionInfo;
import com.squareup.picasso.Picasso;

import java.util.List;



/**
 * Created by raytine on 2017/5/15.
 */

public class FavoriteStationListViewAdapters extends AbstractAdapter<SingerPrescriptionInfo.ContentsBean> {

    public FavoriteStationListViewAdapters(Context context, List<SingerPrescriptionInfo.ContentsBean> listData, int resId) {
        super(context, listData, resId);
    }

    @Override
    public void convert(ViewHolder holder, SingerPrescriptionInfo.ContentsBean bean) {
        TextView tv_title = holder.getView(R.id.item_detil_title);
        TextView tv_count = holder.getView(R.id.item_detil_count);
        TextView tv_price = holder.getView(R.id.item_detil_price);
        ImageView img = holder.getView(R.id.item_detil_img);
        tv_title.setText(bean.getName());
        tv_price.setText(bean.getPrice()+"");
        Picasso.with(context).load(bean.getCoverPic()).into(img);
    }
}
