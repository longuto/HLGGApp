package com.simpotech.app.hlgg.ui.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by longuto on 2016/11/2.
 * 自定义ChildRecyclerView,可以用做RecyclerView中嵌套的ChildRecyclerView,以便更好的展示子RecyclerView的布局
 */

public class ChildRecyclerView extends RecyclerView {

    public ChildRecyclerView(Context context) {
        super(context);
    }

    public ChildRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
