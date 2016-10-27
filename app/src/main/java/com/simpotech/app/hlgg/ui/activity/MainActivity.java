package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.view.View;

import com.simpotech.app.hlgg.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initTitle() {
        showMiddleBtn("首页");
        showRightBtn("退出");

        getRightBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }
}
