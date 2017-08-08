package com.medvision.vrmc.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.medvision.vrmc.R;
import com.medvision.vrmc.bean.HomeContent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



/**
 * Created by raytine on 2017/5/8.
 */

public class FavoriteStationListViewAdapter extends BaseSwipeAdapter {
    private Context mContext;
    private ArrayList<HomeContent> dataList;
    private SetClick setClick;
    private SwipeLayout mSwipeLayout;
    private SwipeLayout mSwipeLayout1;
    private TextView tv_zhouqi;
    public FavoriteStationListViewAdapter(Context mContext, ArrayList<HomeContent> dataList,SetClick setClick) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.setClick  = setClick;
    }



    public void setDataList(ArrayList<HomeContent> dataList) {
        double price = 0;
        this.dataList = dataList;
        for (int i = 0; i < dataList.size(); i++) {
            price+=(dataList.get(i).getPrice()*dataList.get(i).getFrequency()*dataList.get(i).getPeriod());
        }
        setClick.totalprice(price);
        notifyDataSetChanged();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_therapys, null);
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {

        mSwipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        ImageView title = (ImageView) convertView.findViewById(R.id.item_therapy_img);
        TextView tv_name = (TextView) convertView.findViewById(R.id.item_therapy_title);
        tv_zhouqi = (TextView) convertView.findViewById(R.id.item_therapy_zhouqi);
        TextView tv_price = (TextView) convertView.findViewById(R.id.item_therapy_price);
        ImageButton img = (ImageButton) convertView.findViewById(R.id.item_therapy_txt);
        tv_name.setText(dataList.get(position).getName());
        Picasso.with(mContext)
                .load(dataList.get(position).getCoverPic())
                .into(title);
        tv_price.setText("¥ "+dataList.get(position).getPrice()+"");
        tv_zhouqi.setText(dataList.get(position).getZhouqi());
        mSwipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
//                super.onOpen(layout);
                Log.e("TAG","open");

            }

            @Override
            public void onClose(SwipeLayout layout) {
//                super.onClose(layout);
                Log.e("TAG","onClose");
            }
        });
        convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClick.delete(dataList.get(position).getId());
                mItemManger.closeAllItems();

            }
        });
        convertView.findViewById(R.id.item_therapy_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("----adapter",dataList.get(position).getId());
                setClick.OnSet(dataList.get(position).getId());

            }
        });
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public  interface  SetClick{
        public void OnSet(String v);
        public void delete(String s);
        public  void totalprice(double m);
    }
}
