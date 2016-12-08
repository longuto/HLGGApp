package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.InvoiceContructionDb;
import com.simpotech.app.hlgg.entity.net.NetInvoiceInfo;
import com.simpotech.app.hlgg.ui.adapter.LocalInvoiceDetailAdapter;
import com.simpotech.app.hlgg.util.GsonUtils;

import java.util.List;

import butterknife.BindView;

public class InvoiceContructionDetailActivity extends BaseActivity {

    NetInvoiceInfo itemData;    //传递过来的数据

    @BindView(R.id.tv_code)
    TextView codeTv;
    @BindView(R.id.tv_proj_name)
    TextView projNameTv;
    @BindView(R.id.tv_addUserName)
    TextView addUserNameTv;
    @BindView(R.id.tv_cjdate)
    TextView cjdateTv;
    @BindView(R.id.recy_detail_invoiceCon)
    RecyclerView detailInvoiceConRecy;

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_invoice_contruction_detail);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showMiddleTv("发货单详情");
        showRightTv("出库");
        showRightIv(R.drawable.vector_invoice_stockout);
        getLeftLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getRightLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StockoutDetailActivity.class);
                intent.putExtra("CODE", itemData.code);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        String json = getIntent().getStringExtra("ITEMDATA");
        itemData = (NetInvoiceInfo) GsonUtils.fromJson(json, new
                TypeToken<NetInvoiceInfo>() {
                }.getType());
        codeTv.setText("发货单号 :" + itemData.code);
        projNameTv.setText(itemData.proj_name);
        addUserNameTv.setText(itemData.addUserName);
        cjdateTv.setText(itemData.cjdata);

        //设置详情页下的RecyclerView
        InvoiceContructionDb dbCon = new InvoiceContructionDb();
        List<NetInvoiceInfo.DetailsBean> invoiceContructions = dbCon.getInvoiceConByInvoiceCode
                (itemData.code);  //获取当前发货单号下的所有的构件信息集合
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager
                .VERTICAL, false);
        detailInvoiceConRecy.setLayoutManager(manager);
        LocalInvoiceDetailAdapter adapter = new LocalInvoiceDetailAdapter(invoiceContructions);
        detailInvoiceConRecy.setAdapter(adapter);
    }
}
