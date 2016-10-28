package com.simpotech.app.hlgg.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.business.SharedManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longuto on 2016/10/26.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @BindView(R.id.iv_left)
    ImageView mLeftIv;    //左部按钮
    @BindView(R.id.tv_middle)
    TextView mMiddleTv;  //中部按钮
    @BindView(R.id.iv_right)
    ImageView mRightIv;   //右部按钮

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



    /**显示左边按钮*/
    public void showLeftIcon(int imageRes) {
        mLeftIv.setImageResource(imageRes);
        mLeftIv.setVisibility(View.VISIBLE);
    }

    /**显示中间按钮*/
    public void showMiddleIcon(String content) {
        mMiddleTv.setText(content);
        mMiddleTv.setVisibility(View.VISIBLE);
    }

    /**显示右边按钮*/
    public void showRightIcon(int imageRes) {
        mRightIv.setImageResource(imageRes);
        mRightIv.setVisibility(View.VISIBLE);
    }

    /**获取左边按钮*/
    public ImageView getLeftIcon() {
        return mLeftIv;
    }

    /**获取中间按钮*/
    public TextView getMiddleIcon() {
        return mMiddleTv;
    }

    /**获取右边按钮*/
    public ImageView getRightIcon() {
        return mRightIv;
    }
}
