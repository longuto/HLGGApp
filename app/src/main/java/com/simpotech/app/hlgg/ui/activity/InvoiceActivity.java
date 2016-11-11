package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.NetInvoiceParse;
import com.simpotech.app.hlgg.db.dao.InvoiceDb;

import butterknife.BindView;

public class InvoiceActivity extends BaseActivity {
    @BindView(R.id.edt_invoice)
    EditText mInvoiceEdt;   //发货单编辑框

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_invoice);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showMiddleIv(R.drawable.vector_invoice_down);
        showRightIv(R.drawable.vector_invoice_downed);
        getLeftLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getMiddleLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载网络获取数据
                String invoice = mInvoiceEdt.getText().toString().trim();   //获取发货单
                NetInvoiceParse.getDataByInvoice(invoice);
            }
        });
        getRightLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInvoiceManager = new Intent(context, InvoiceManagerActivity.class);
                startActivity(intentInvoiceManager);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
