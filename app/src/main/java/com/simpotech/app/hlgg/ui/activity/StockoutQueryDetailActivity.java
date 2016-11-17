package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.StockoutContruDb;
import com.simpotech.app.hlgg.db.dao.StockoutDb;
import com.simpotech.app.hlgg.entity.net.NetStockoutInfo;
import com.simpotech.app.hlgg.ui.adapter.LocalStockQueryDetailAdapter;
import com.simpotech.app.hlgg.util.GsonUtils;

import java.util.List;

import butterknife.BindView;

public class StockoutQueryDetailActivity extends BaseActivity {

    NetStockoutInfo mItemData;  //当前详情页的数据

    private static String TAG = "StockoutQueryDetailActivity";

    @BindView(R.id.tv_stockout_code)
    TextView mStockoutCodeTv;
    @BindView(R.id.tv_invoice_code)
    TextView mInvoiceCodeTv;
    @BindView(R.id.tv_proj_name)
    TextView mProjNameTv;
    @BindView(R.id.tv_organ_name)
    TextView mOrganNameTv;
    @BindView(R.id.tv_saleName)
    TextView mSaleNameTv;
    @BindView(R.id.tv_addName)
    TextView mAddNameTv;
    @BindView(R.id.tv_addTime)
    TextView mAddTimeTv;
    @BindView(R.id.recy_stockout_detail)
    RecyclerView mStockoutDetailRecy;

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_stockout_query_detail);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showMiddleTv("出库详情");
        showRightIv(R.drawable.vector_proline_del);
        getLeftLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getRightLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockoutDb db = new StockoutDb();
                StockoutContruDb dbCon = new StockoutContruDb();
                db.delStockout(mItemData.code);
                dbCon.delStockoutConByStockoutCode(mItemData.code);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String json = intent.getStringExtra("ITEMDATA");
        mItemData = (NetStockoutInfo) GsonUtils.fromJson(json, new
                TypeToken<NetStockoutInfo>() {
                }.getType());
        mStockoutCodeTv.setText("出库单号 :" + mItemData.code);
        mInvoiceCodeTv.setText(mItemData.invoice_code);
        mProjNameTv.setText(mItemData.proj_name);
        mOrganNameTv.setText(mItemData.organ_name);
        mSaleNameTv.setText(mItemData.saleName);
        mAddNameTv.setText(mItemData.addUserName);
        mAddTimeTv.setText(mItemData.addTime);

        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager
                .VERTICAL, false);
        mStockoutDetailRecy.setLayoutManager(manager);
        StockoutContruDb dbCon = new StockoutContruDb();
        List<NetStockoutInfo.DetailsBean> details = dbCon
                .getStockoutConByStockoutCode(mItemData.code);
        LocalStockQueryDetailAdapter adapter = new LocalStockQueryDetailAdapter(details);
        mStockoutDetailRecy.setAdapter(adapter);
    }

}
