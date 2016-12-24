package com.simpotech.app.hlgg.ui.activity;

import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.business.DisplayImageOptionManager;
import com.simpotech.app.hlgg.util.LogUtils;

import butterknife.BindView;

public class PhotoActivity extends BaseActivity {

    private String TAG = "PhotoActivity";

    @BindView(R.id.iv_detail)
    ImageView detailPhotoIv;

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_photo);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showMiddleTv("图片详情页");
        getLeftLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.activity_back_enter, R.anim.activity_back_exit);
            }
        });
    }

    @Override
    protected void initData() {
        String path = getIntent().getStringExtra("PATH");
        LogUtils.i(TAG, "路径是--------" + path);
        ImageLoader.getInstance().displayImage(path, detailPhotoIv, DisplayImageOptionManager
                .getQualityOption());
    }
}
