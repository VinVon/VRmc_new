package com.medvision.vrmc.adapter.abs;

/**
 * Created by 123 on 2017/2/4.
 */
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AbstractAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> listData;
    protected LayoutInflater inflater;

    // 资源文件ID
    private int resId;
    private int resIds;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    public AbstractAdapter(Context context, List<T> listData, int resId) {
        super();
        this.context = context;
        this.listData = listData;
        this.inflater = LayoutInflater.from(context);
        this.resId=resId;
    }
    public AbstractAdapter(Context context, List<T> listData, int resId,int resIds) {
        super();
        this.context = context;
        this.listData = listData;
        this.inflater = LayoutInflater.from(context);
        this.resId=resId;
        this.resIds = resIds;
    }

    @Override
    public int getCount() {
        return listData == null ? 0 : listData.size();
    }

    @Override
    public T getItem(int position) {
        return listData == null ? null : listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        int p = position;
        if (p == 0)
            return TYPE_1;
        else
            return TYPE_2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 初始化ViewHolder,使用通用的ViewHolder，一样代码就搞定ViewHolder的初始化咯
        ViewHolder holder=ViewHolder.get(context, convertView, parent, this.resId, position);
        convert(holder, listData.get(position));
        return holder.getmConvertView();
    }

    public abstract void convert(ViewHolder holder,T bean);

}
