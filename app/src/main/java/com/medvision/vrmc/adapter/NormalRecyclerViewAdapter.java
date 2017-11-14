package com.medvision.vrmc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.medvision.vrmc.R;
import com.medvision.vrmc.activity.content.DemoBean;
import com.medvision.vrmc.activity.content.TextPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by raytine on 2017/10/28.
 */

public class NormalRecyclerViewAdapter extends
        RecyclerView.Adapter<NormalRecyclerViewAdapter.NormalViewholder> {

    private Context mContext;
    private DemoBean titles;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_FOOTER = 2;
    private View mHeaderView;
    private Map<Integer, List<TextPosition>> maps;
    /**
     * 1 为患者选着方案
     * 0 为浏览方案
     */
    private int type = 0;

    private Item_plan item_plan;

    public void setItem_plan(Item_plan item_plan) {
        this.item_plan = item_plan;
    }

    public NormalRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    public void setTitles(DemoBean titles, int type) {
        this.titles = titles;
        this.type = type;
    }

    public View getHeader() {
        return mHeaderView;
    }

    public void setHeader(View headerView) {

        this.mHeaderView = headerView;
        //插入新的item
        notifyItemInserted(0);
    }

    /**
     * 在方法getItemViewType()中, 根据position来设置不同的view，实现添加header和footer的功能
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }

        if (position == getItemCount() - 1) {
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }

        return TYPE_NORMAL;
    }

    @Override
    public NormalViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeaderView != null && viewType == TYPE_HEADER) {
            //需要传入parent，不然item不能居中
//            return new NormalViewholder(mHeaderView);
            return new NormalViewholder(LayoutInflater.from(mContext).inflate(R.layout.item_first_header, parent, false));
        }

        //需要传入parent，不然item不能居中
        View viewNormal = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_grid, parent, false);
        return new NormalViewholder(viewNormal);
    }

    @Override
    public void onBindViewHolder(NormalViewholder holder, int position) {

        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        if (holder.ll.getChildCount() > 0) {
            holder.ll.removeAllViews();
        }
        holder.cishu.setText(position + "");
        //算出总的场景数量
        int number = Integer.valueOf(titles.getTimes()) * Integer.valueOf(titles.getScenes());
        //scenes 是求余数，记录为map的key
        int keyNumber = Integer.valueOf(titles.getScenes());
        for (int i = Integer.valueOf(titles.getScenes()) * (position - 1); i < Integer.valueOf(titles.getScenes()) * position; i++) {
            TextView tv = new TextView(mContext);
            if (type == 0) {
                tv.setEnabled(false);
            }
            tv.setBackground(mContext.getResources().getDrawable(R.drawable.card_left_line));
            tv.setSelected(false);
            tv.setTextSize(sp2dip(10));
            tv.setTextColor(mContext.getResources().getColor(R.color.text_color));
            tv.setPadding(5, 0, 5, 0);
            tv.setText(titles.getLists().get(i));
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
            tv.setLayoutParams(l);
            tv.setGravity(Gravity.CENTER);
            int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<TextPosition> textViews = maps.get(finalI % keyNumber);
                    Log.e("TAG", "点击事件的位置" + finalI + "该余数的集合大小" + textViews.size());
                    for (int i1 = 0; i1 < textViews.size(); i1++) {
                        TextPosition ps = textViews.get(i1);
                        TextView textView = ps.getTv();
                        textView.setSelected(false);
                        textView.setTextColor(mContext.getResources().getColor(R.color.black));
                    }
                    tv.setSelected(true);
                    tv.setTextColor(mContext.getResources().getColor(R.color.white));
                    item_plan.onclick(tv, finalI);
                }
            });
            int AllNumber = 0;
            if (maps == null) {
                addMap(i, tv, keyNumber);
            } else {
                for (int i1 = 0; i1 < keyNumber; i1++) {
                    AllNumber += maps.get(i1).size();
                }
                if (AllNumber < number) {
                    addMap(i, tv, keyNumber);
                }
            }
            holder.ll.addView(tv);
        }
    }

    private int sp2dip(int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, mContext.getResources().getDisplayMetrics());
    }

    /**
     * 添加到map里面
     *
     * @param i
     * @param tv
     */
    private void addMap(int i, TextView tv, int keyNumber) {
        if (maps == null) {
            maps = new HashMap<>();
            for (int i1 = 0; i1 < keyNumber; i1++) {
                List<TextPosition> texts = new ArrayList<>();
                maps.put(i1, texts);
            }
            List<TextPosition> textViews = maps.get(i % keyNumber);
            textViews.add(new TextPosition(i, tv));
        } else {
            int i1 = i % keyNumber;
            List<TextPosition> textViews = maps.get(i1);
            textViews.add(new TextPosition(i, tv));
        }
    }

    @Override
    public int getItemCount() {
        return Integer.valueOf(titles.getTimes()) + 1;
    }

    public static class NormalViewholder extends RecyclerView.ViewHolder {
        LinearLayout ll;
        TextView cishu;

        public NormalViewholder(View view) {
            //需要设置super
            super(view);
//            ButterKnife.bind(this, view);
            cishu = (TextView) view.findViewById(R.id.cishu);
            ll = (LinearLayout) view.findViewById(R.id.item_sss);
        }
    }

    public interface Item_plan {
        void onclick(TextView v, int position);
    }
}
