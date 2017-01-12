package com.simpotech.app.hlgg.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.entity.net.NetQualityInfo;
import com.simpotech.app.hlgg.ui.adapter.QualityDetailPagerAdapter;
import com.simpotech.app.hlgg.util.GsonUtils;

import butterknife.BindView;

public class QualityQueryDetailActivity extends BaseActivity {

    private static final String TAG = "QualityQueryDetailActivity";
    NetQualityInfo mItemData;   //详情页的数据

    @BindView(R.id.tv_code)
    TextView codeTv;
    @BindView(R.id.tv_projectName)
    TextView projectNameTv;
    @BindView(R.id.tv_name)
    TextView nameTv;
    @BindView(R.id.tv_control_code)
    TextView controlCodeTv;
    @BindView(R.id.tv_spec)
    TextView specTv;
    @BindView(R.id.tv_username)
    TextView usernameTv;
    @BindView(R.id.tv_cjdate)
    TextView cjdateTv;
    @BindView(R.id.tabs_quality)
    TabLayout qualityTabs;
    @BindView(R.id.vp_quality)
    ViewPager qualityVp;

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_quality_query_detail);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showMiddleTv("质检明细");
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
        String json = getIntent().getStringExtra("ITEMDATA");
        mItemData = (NetQualityInfo) GsonUtils.fromJson(json, new TypeToken<NetQualityInfo>() {
            }.getType());
        codeTv.setText(mItemData.code);
        projectNameTv.setText(mItemData.project_name);
        nameTv.setText(mItemData.organName);
        controlCodeTv.setText(mItemData.contruction_code);
        specTv.setText(mItemData.spec);
        usernameTv.setText(mItemData.testUser);
        cjdateTv.setText(mItemData.cjdata);

        QualityDetailPagerAdapter adapter = new QualityDetailPagerAdapter(getSupportFragmentManager(), mItemData.detail);
        qualityVp.setAdapter(adapter);
        qualityTabs.setupWithViewPager(qualityVp);
    }
}
