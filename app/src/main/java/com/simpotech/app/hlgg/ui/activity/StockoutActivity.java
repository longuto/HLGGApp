package com.simpotech.app.hlgg.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.InvoiceDb;
import com.simpotech.app.hlgg.entity.net.NetInvoiceInfo;
import com.simpotech.app.hlgg.ui.adapter.PopupAdapter;
import com.simpotech.app.hlgg.util.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.id.edit;

public class StockoutActivity extends BaseActivity {

    PopupWindow mPopupWindow;   //弹出框
    ListView mInvoiceLv; //弹出框的子控件ListView

    @BindView(R.id.edt_invoiceNo)
    EditText invoiceNoEdt;  //发货单输入框

    @OnClick(R.id.iv_invoiceNo_search)    //点击弹出所有的发货单
    public void showInvoiceNo(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(invoiceNoEdt.getWindowToken(), 0);  //隐藏软键盘

        showPopupWindow();  //显示popupWindow

    }

    /**
     * 显示pop
     */
    private void showPopupWindow() {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mInvoiceLv, invoiceNoEdt.getWidth(), 250, true);    //
            // 弹出框
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        }
        mPopupWindow.showAsDropDown(invoiceNoEdt);
    }

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
                if (netInvoiceInfo != null) {
                    Intent intent = new Intent(context, StockoutDetailActivity.class);
                    intent.putExtra("CODE", code);
                    startActivity(intent);
                } else {
                    UiUtils.showToast("本地没有此发货单,请先下载发货单");
                }
            }
        });
    }

    @Override
    protected void initData() {
        mInvoiceLv = new ListView(context);
        final PopupAdapter adapter = new PopupAdapter();
        mInvoiceLv.setAdapter(adapter);
        mInvoiceLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                invoiceNoEdt.setText(adapter.getItemCode(position));
                mPopupWindow.dismiss();
            }
        });
    }
}
