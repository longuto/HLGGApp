package com.simpotech.app.hlgg.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.simpotech.app.hlgg.R;

/**
 * Created by longuto on 2016/10/26.
 */

public abstract class BaseActivity extends AppCompatActivity {
    Button mLeftBtn;    //左部按钮
    Button mMiddleBtn;  //中部按钮
    Button mRightBtn;   //右部按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toSetContentView();

        mLeftBtn = (Button) findViewById(R.id.btn_left);
        mMiddleBtn = (Button) findViewById(R.id.btn_middle);
        mRightBtn = (Button) findViewById(R.id.btn_right  );
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
    public void showLeftBtn(String content) {
        mLeftBtn.setText(content);
        mLeftBtn.setVisibility(View.VISIBLE);
    }

    /**显示中间按钮*/
    public void showMiddleBtn(String content) {
        mMiddleBtn.setText(content);
        mMiddleBtn.setVisibility(View.VISIBLE);
    }

    /**显示右边按钮*/
    public void showRightBtn(String content) {
        mRightBtn.setText(content);
        mRightBtn.setVisibility(View.VISIBLE);
    }

    /**获取左边按钮*/
    public Button getLeftBtn() {
        return mLeftBtn;
    }

    /**获取中间按钮*/
    public Button getMiddleBtn() {
        return mMiddleBtn;
    }

    /**获取右边按钮*/
    public Button getRightBtn() {
        return mRightBtn;
    }
}
