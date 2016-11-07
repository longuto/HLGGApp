package com.simpotech.app.hlgg.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.simpotech.app.hlgg.R;

public class ContructionActivity extends BaseActivity {

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_contruction);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showMiddleTv("构件查询");
        getLeftLly().setOnClickListener(new View.OnClickListener() {
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
