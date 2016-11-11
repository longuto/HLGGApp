package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.InvoiceDb;
import com.simpotech.app.hlgg.entity.net.NetInvoiceInfo;
import com.simpotech.app.hlgg.util.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockoutActivity extends BaseActivity {

    @BindView(R.id.edt_invoiceNo)
    EditText invoiceNoEdt;  //发货单输入框

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_stockout);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showMiddleTv("出库");
        showRightIv(R.drawable.vector_stockout_next);
        getLeftLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //下一步
        getRightLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = invoiceNoEdt.getText().toString().trim(); //发货单号
                InvoiceDb db = new InvoiceDb();
                NetInvoiceInfo netInvoiceInfo = db.queryInvoicesByCode(code);
                if(netInvoiceInfo != null) {
                    Intent intent = new Intent(context, StockoutDetailActivity.class);
                    intent.putExtra("CODE", code);
                    startActivity(intent);
                }else {
                    UiUtils.showToast("本地没有此发货单,请先下载发货单");
                }
            }
        });
    }

    @Override
    protected void initData() {

    }
}
