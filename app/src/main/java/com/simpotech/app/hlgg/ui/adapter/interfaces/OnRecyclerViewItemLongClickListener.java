package com.simpotech.app.hlgg.ui.adapter.interfaces;

import android.view.View;

/**
 * Created by longuto on 2016/11/3.
 * RecyclerView的Item长按监听接口
 */

public interface OnRecyclerViewItemLongClickListener {
    void onItemLongClick(View view, int position);    //需要自定义的方法
}
