package com.simpotech.app.hlgg.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.business.SharedManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longuto on 2016/10/26.
 */

public abstract class BaseActivity extends AppCompatActivity {
    //左部控件
    @BindView(R.id.lly_left)
    LinearLayout mleftLly;
    @BindView(R.id.iv_left)
    ImageView mLeftIv;
    @BindView(R.id.tv_left)
    TextView mLeftTv;
    //中间控件
    @BindView(R.id.lly_middle)
    LinearLayout mMiddleLly;
    @BindView(R.id.iv_middle)
    ImageView mMiddleIv;
    @BindView(R.id.tv_middle)
    TextView mMiddleTv;
    //右边控件
    @BindView(R.id.lly_right)
    LinearLayout mRightLly;
    @BindView(R.id.iv_right)
    ImageView mRightIv;
    @BindView(R.id.tv_right)
    TextView mRightTv;

    Context context;    // 上下文
    SharedManager spManager;    //SharedPreferences

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toSetContentView();

        ButterKnife.bind(this); //使用注解框架
        context = this;
        spManager = new SharedManager();

        //初始化标题
        initTitle();
        //初始化数据
        initData();
    }

    /**初始化数据*/
    protected abstract void initData();

    /**初始化主视图*/
    protected abstract void toSetContentView();

    /**初始化标题视图*/
    protected abstract void initTitle();


    /**显示左边ImageView*/
    public void showLeftIv(int imageRes) {
        mLeftIv.setImageResource(imageRes);
        mLeftIv.setVisibility(View.VISIBLE);
    }
    /**显示左边TextView*/
    public void showLeftTv(String content) {
        mLeftTv.setText(content);
        mLeftTv.setVisibility(View.VISIBLE);
    }

    /**显示中间ImageView*/
    public void showMiddleIv(int imageRes) {
        mMiddleIv.setImageResource(imageRes);
        mMiddleIv.setVisibility(View.VISIBLE);
    }
    /**显示中间的TextView*/
    public void showMiddleTv(String content) {
        mMiddleTv.setText(content);
        mMiddleTv.setVisibility(View.VISIBLE);
    }

    /**显示右边的ImageView*/
    public void showRightIv(int imageRes) {
        mRightIv.setImageResource(imageRes);
        mRightIv.setVisibility(View.VISIBLE);
    }
    /**显示右边的TextView*/
    public void showRightTv(String content) {
        mRightTv.setText(content);
        mRightTv.setVisibility(View.VISIBLE);
    }

    /**获取左边LinearLayout*/
    public LinearLayout getLeftLly() {
        return mleftLly;
    }

    /**获取中间LinearLayout*/
    public LinearLayout getMiddleLly() {
        return mMiddleLly;
    }

    /**获取右边LinearLayout*/
    public LinearLayout getRightLly() {
        return mRightLly;
    }
}
