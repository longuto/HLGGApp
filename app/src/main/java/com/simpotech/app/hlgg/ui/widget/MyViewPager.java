package com.simpotech.app.hlgg.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.simpotech.app.hlgg.util.LogUtils;

/**
 * Created by longuto on 2017/1/19.
 *
 * 取消ViewPager垂直滑动获取焦点问题
 */
public class MyViewPager extends ViewPager {
    private final static String TAG = "MyViewPager";

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    float startX;
    float startY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.i(TAG, "移动前startX:" + startX + ", startY:" + startY);
                float dx = ev.getX() - startX;
                float dy = ev.getY() - startY;
                if(Math.abs(dx) < Math.abs(dy)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                startX = ev.getX();
                startY = ev.getY();
                LogUtils.i(TAG, "移动后startX:" + startX + ", startY:" + startY);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
