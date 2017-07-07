package vr.promulgator.com.newvrmc.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vr.promulgator.com.newvrmc.R;


/**
 * Created by raytine on 2017/4/25.
 */

public class MyArrayAdapter extends BaseAdapter{
    private List<String> list;
    private LayoutInflater layoutInflater;

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public MyArrayAdapter(Context context, List<String> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size()==0?0:list.size()<=10?list.size():10;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position) == null ? null :list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder =new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_search_history,null);
            LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,120);
            convertView.setLayoutParams(layoutParams);
            viewHolder.title = (TextView) convertView.findViewById(R.id.contentTextView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder  = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(list.get(position));
        return convertView;
    }
    class ViewHolder{
        public TextView title;
    }
}
