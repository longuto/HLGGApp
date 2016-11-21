package com.simpotech.app.hlgg.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.StockinContruDb;
import com.simpotech.app.hlgg.entity.net.NetStockinInfo;
import com.simpotech.app.hlgg.ui.adapter.interfaces.LocalStockinConAdapter;
import com.simpotech.app.hlgg.util.GsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockinQueryDetailActivity extends BaseActivity {

    NetStockinInfo mItemData;   //当前详情页的的数据

    @BindView(R.id.tv_stockin_code)
    TextView mStockinCodeTv;
    @BindView(R.id.tv_wo_code)
    TextView mWoCodeTv;
    @BindView(R.id.tv_cml_code)
    TextView mCmlCodeTv;
    @BindView(R.id.tv_proj_name)
    TextView mProjNameTv;
    @BindView(R.id.tv_organName)
    TextView mOrganNameTv;
    @BindView(R.id.tv_productLine)
    TextView mProductLineTv;
    @BindView(R.id.tv_addUserName)
    TextView mAddUserNameTv;
    @BindView(R.id.tv_addTime)
    TextView mAddTimeTv;
    @BindView(R.id.recy_stockin_detail)
    RecyclerView mStockinDetailRecy;

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_stockin_query_detail);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showMiddleTv("入库单详情");
        getLeftLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        String json = getIntent().getStringExtra("ITEMDATA");
        mItemData = (NetStockinInfo) GsonUtils.fromJson(json, new TypeToken<NetStockinInfo>() {
        }.getType());
        mStockinCodeTv.setText("入库单号 :" + mItemData.code);
        mWoCodeTv.setText(mItemData.wo_code);
        mCmlCodeTv.setText(mItemData.cml_code);
        mProjNameTv.setText(mItemData.proj_name);
        mOrganNameTv.setText(mItemData.organName);
        mProductLineTv.setText(mItemData.productLine);
        mAddUserNameTv.setText("提交人 :" + mItemData.addUserName);
        mAddTimeTv.setText("提交时间" + mItemData.addTime);

        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager
                .VERTICAL, false);
        mStockinDetailRecy.setLayoutManager(manager);
        List<NetStockinInfo.DetailsBean> details = new StockinContruDb()
                .getStockinConByStockinCode(mItemData.code);
        LocalStockinConAdapter adapter = new LocalStockinConAdapter(details);
        mStockinDetailRecy.setAdapter(adapter);
    }
}
