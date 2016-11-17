package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.NetInvoiceParse;
import com.simpotech.app.hlgg.db.dao.InvoiceConStockoutDb;
import com.simpotech.app.hlgg.db.dao.InvoiceContructionDb;
import com.simpotech.app.hlgg.db.dao.InvoiceDb;
import com.simpotech.app.hlgg.entity.net.NetInvoiceInfo;
import com.simpotech.app.hlgg.ui.adapter.LocalInvoiceAdapter;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemLongClickListener;
import com.simpotech.app.hlgg.util.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class InvoiceManagerActivity extends BaseActivity {

    private LocalInvoiceAdapter mAdapter;   //适配器
    private String TAG = "InvoiceManagerActivity";  //数据库标记

    @BindView(R.id.edt_search_invoice)
    EditText mInvoiceEdt;   //发货单EditText
    @BindView(R.id.btn_search)
    Button mSearchBtn;  //发货单查询Button
    @BindView(R.id.ptr_local_invoice)
    PtrClassicFrameLayout mInvoicePtr;  //下拉刷新控件
    @BindView(R.id.recy_local_invoice)
    RecyclerView mInvoiceRecy;  //列表展示控件

    @OnClick(R.id.btn_search)
    public void searchInvoices() {
        String edtStr = mInvoiceEdt.getText().toString().trim();
        mAdapter.data = new InvoiceDb().queryInvoicesByInput(edtStr);
        mAdapter.notifyDataSetChanged();
    }

    //清空数据库
    @OnClick(R.id.btn_del_all)
    public void delAllInvoice() {
        mAdapter.data = new ArrayList<>();  //清空展板数据
        mAdapter.notifyDataSetChanged();    //通知适配器数据已改变
        new InvoiceDb().delAllData();   //清空数据库信息
        new InvoiceContructionDb().delAllData();    //清空附属表的数据
        new InvoiceConStockoutDb().delAllData();    //清空附属出库的构件表
    }

    //删除已出库
    @OnClick(R.id.btn_del_stockout)
    public void delStockOutInvoice() {
        StringBuffer sb = new StringBuffer();
        for (NetInvoiceInfo info : mAdapter.data) {
            sb.append(info.code);
            sb.append(",");
        }
        String codeStr = sb.toString();
        codeStr.substring(0, codeStr.length() - 1);
        NetInvoiceParse.delStockOutInvoiceCode(codeStr, mAdapter);
    }

    //删除选中的
    @OnClick(R.id.btn_del_choose)
    public void delChooseInvoice() {
        List<String> codes = new ArrayList<>();
        for (NetInvoiceInfo info : mAdapter.data) {
            //如果当前项选中
            if(info.isChecked) {
                codes.add(info.code);   //获取所有选中项的发货单的集合
            }
        }
        InvoiceDb db = new InvoiceDb();
        InvoiceContructionDb dbCon = new InvoiceContructionDb();
        InvoiceConStockoutDb dbStock = new InvoiceConStockoutDb();
        for (String str : codes) {
            db.delInvoice(str); //删除选中项的表记录
            dbCon.delInvoiceConByInvoiceCode(str);  //删除附属表的表记录
            dbStock.getInvoiceConByInvoiceCode(str);    //删除附属出库的构件表
        }
        mAdapter.data = db.getAllInvoices();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_invoice_manager);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showMiddleTv("发货单管理");
        getLeftLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        refreshRecyclerView();
    }

    /**
     * 下拉刷新RecyclerView
     */
    private void refreshRecyclerView() {
        mInvoicePtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 更新RecyclerView的数据
                LinearLayoutManager manager = new LinearLayoutManager(context,
                        LinearLayoutManager.VERTICAL, false);
                mInvoiceRecy.setLayoutManager(manager);
                mAdapter = new LocalInvoiceAdapter();
                mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //将itemData通过意图传递给下一个Activity
                        NetInvoiceInfo itemData = mAdapter.getItemData(position);
                        String json = GsonUtils.toJson(itemData);
                        Intent intent = new Intent(context, InvoiceContructionDetailActivity.class);
                        intent.putExtra("ITEMDATA", json);
                        startActivity(intent);
                    }
                });
                mAdapter.setOnItemLongClickListener(new OnRecyclerViewItemLongClickListener() {
                    @Override
                    public void onItemLongClick(View view, int position) {
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.ckb_choose_invoice);
                        NetInvoiceInfo itemData = mAdapter.getItemData(position);
                        if(itemData.isChecked) {
                            checkBox.setChecked(false);
                            itemData.isChecked = false;
                        }else {
                            checkBox.setChecked(true);
                            itemData.isChecked = true;
                        }
                    }
                });
                mInvoiceRecy.setAdapter(mAdapter);

                mInvoicePtr.refreshComplete();  //刷新完成
            }
        });
        //设置自动刷新
        mInvoicePtr.postDelayed(new Runnable() {
            @Override
            public void run() {
                mInvoicePtr.autoRefresh();
            }
        }, 100);
    }
}
