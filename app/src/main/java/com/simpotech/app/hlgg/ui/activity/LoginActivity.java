package com.simpotech.app.hlgg.ui.activity;

import android.view.View;

import com.simpotech.app.hlgg.R;

public class LoginActivity extends BaseActivity {

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initTitle() {
        showRightBtn("关闭");
        getRightBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }
}
