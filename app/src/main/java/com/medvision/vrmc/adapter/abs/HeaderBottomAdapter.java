package com.medvision.vrmc.adapter.abs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.common.utils.ToastUtil;
import com.medvision.vrmc.MainActivity;
import com.medvision.vrmc.R;
import com.medvision.vrmc.UrlPath.UrlHttp;
import com.medvision.vrmc.activity.AreaActivity;
import com.medvision.vrmc.activity.CetificationActivity0;
import com.medvision.vrmc.activity.MyPatientActivity;
import com.medvision.vrmc.activity.WebViewActivity;
import com.medvision.vrmc.bean.NewsInfo;
import com.medvision.vrmc.utils.Constant;
import com.medvision.vrmc.utils.SpUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by raytine on 2017/8/23.
 */

public class HeaderBottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    //模拟数据
    //模拟数据
    private List<NewsInfo> newsInfo = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private int mHeaderCount=1;//头部View个数
    private int mBottomCount=0;//底部View个数
    List images ;

    public HeaderBottomAdapter(Context context,List images) {
        mContext = context;
        this.images = images;
    }

    public void setImages(List images) {
        this.images = images;
        notifyDataSetChanged();
    }

    public HeaderBottomAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }



    //内容长度
    public int getContentItemCount(){
        return newsInfo.size();
    }
    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }
    //判断当前item是否是FooterView
    public boolean isBottomView(int position) {
        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }
    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (mHeaderCount != 0 && position < mHeaderCount) {
//头部View
            return ITEM_TYPE_HEADER;
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
//底部View
            return ITEM_TYPE_BOTTOM;
        } else {
//内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    public void setNewsInfo(List<NewsInfo> newsInfo) {
        this.newsInfo = newsInfo;
        notifyDataSetChanged();
    }

    //内容 ViewHolder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView title;
        TextView dsc;
        TextView date;

        public ContentViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.main_item_title);
//            dsc = (TextView) itemView.findViewById(R.id.main_item_dsc);
            date = (TextView) itemView.findViewById(R.id.main_item_date);
            iv = (ImageView) itemView.findViewById(R.id.main_item_img);

        }
    }
    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView addPatient;
        TextView vrContent;
        TextView vrHandbook;
        TextView more;

        public HeaderViewHolder(View itemView) {//声明控件
            super(itemView);
            addPatient = (TextView) itemView.findViewById(R.id.add_patient);
            vrContent = (TextView) itemView.findViewById(R.id.vr_content);
            vrHandbook = (TextView) itemView.findViewById(R.id.vr_handbook);
            more = (TextView) itemView.findViewById(R.id.news_more);

        }
    }
    //底部 ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder {
        public BottomViewHolder(View itemView) {
            super(itemView);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType ==ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.main_headview, parent, false));
        } else if (viewType == mHeaderCount) {
            return new ContentViewHolder(mLayoutInflater.inflate(R.layout.list_item_expert, parent, false));
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return new BottomViewHolder(null);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).addPatient.setOnClickListener(v->{
                if (SpUtils.getInstance().getUserStatus().getStatus()==4){
                mContext.startActivity(new Intent(mContext, MyPatientActivity.class));
                }else if(SpUtils.getInstance().getUserStatus().getStatus()==1){
                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("认证状态").setContentText("未认证")
                            .setCancelText("取消")
                            .setConfirmText("认证")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    Intent intent = new Intent(mContext, CetificationActivity0.class);
                                    mContext.startActivity(intent);
                                }
                            }).show();
                }else if (SpUtils.getInstance().getUserStatus().getStatus()==2){
                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("认证状态").setContentText("待审核")
                            .setConfirmText("确定")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                }else {
                    new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("认证状态").setContentText("认证失败")
                            .setCancelText("取消")
                            .setConfirmText("重新认证")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    Intent intent = new Intent(mContext, CetificationActivity0.class);
                                    mContext.startActivity(intent);
                                }
                            }).show();
                }
            });
            ((HeaderViewHolder) holder).vrContent.setOnClickListener(v->{
                mContext.startActivity(new Intent(mContext, AreaActivity.class));
            });
            ((HeaderViewHolder) holder).vrHandbook.setOnClickListener(v->{
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", UrlHttp.BASE_URL_EVERYTHING + "/h5/help");
                mContext.startActivity(intent);
            });
            ((HeaderViewHolder) holder).more.setOnClickListener(v->{
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", Constant.NWES_MORE);
                intent.putExtra("title","新闻资讯");
                mContext.startActivity(intent);
            });

        } else if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).title.setText(newsInfo.get(position - mHeaderCount).getName());
            ((ContentViewHolder) holder).date.setText(newsInfo.get(position - mHeaderCount).getReleasetime());
            Picasso.with(mContext).load(newsInfo.get(position-mHeaderCount).getCoverurl()).placeholder(R.mipmap.default_img).fit().into(((ContentViewHolder) holder).iv);
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra("url",newsInfo.get(position - mHeaderCount).getLinkurl());
            holder.itemView.setOnClickListener(v -> mContext.startActivity(intent));
        } else if (holder instanceof BottomViewHolder) {
        }
    }
    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }
}
