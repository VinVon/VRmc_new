package com.medvision.vrmc.imp;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * recycleView 加载更多接口
 * Created by raytine on 2017/5/3.
 */

public abstract class EndlessRecyclerOnScrollListener extends
        RecyclerView.OnScrollListener{
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();
    private LinearLayoutManager manager;
    //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
    boolean isSlidingToLast = false;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.manager = linearLayoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        // 当不滚动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //获取最后一个完全显示的ItemPosition
            int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
            int totalItemCount = manager.getItemCount();

            // 判断是否滚动到底部，并且是向右滚动
            if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                //加载更多功能的代码
                onLoadMore();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
        if (dx > 5 || dy > 5) {
            //大于0表示，正在向右滚动
            isSlidingToLast = true;
        } else {
            //小于等于0 表示停止或向左滚动
            isSlidingToLast = false;
        }

    }


    public abstract void onLoadMore();
}