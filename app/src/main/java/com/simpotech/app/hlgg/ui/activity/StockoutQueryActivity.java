package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.StockoutContruDb;
import com.simpotech.app.hlgg.db.dao.StockoutDb;
import com.simpotech.app.hlgg.entity.StockoutConInfo;
import com.simpotech.app.hlgg.entity.net.NetStockoutInfo;
import com.simpotech.app.hlgg.ui.adapter.LocalStockoutAdapter;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemLongClickListener;
import com.simpotech.app.hlgg.util.GsonUtils;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class StockoutQueryActivity extends BaseActivity {

    LocalStockoutAdapter mAdapter;  //适配器
    private static final String TAG = "StockoutQueryActivity";

    @BindView(R.id.edt_search_stockout)
    EditText mSearchStockoutEdt;
    @BindView(R.id.recy_local_stockout)
    RecyclerView mLocalStockoutRecy;
    @BindView(R.id.ptr_local_stockout)
    PtrClassicFrameLayout mLocalStockoutPtr;

    //查询出库单
    @OnClick(R.id.btn_search)
    public void searchStockout() {
        String edtStr = mSearchStockoutEdt.getText().toString().trim();
        StockoutDb db = new StockoutDb();
        mAdapter.data = db.queryStockoutsByInput(edtStr);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_stockout_query);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showMiddleTv("出库查询");
        showRightIv(R.drawable.vector_proline_del);
        getLeftLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //删除选中项
        getRightLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<NetStockoutInfo> netStockoutInfos = mAdapter.data;
                StockoutDb db = new StockoutDb();
                StockoutContruDb dbCon = new StockoutContruDb();
                for (NetStockoutInfo temp : netStockoutInfos) {
                    if(temp.isChecked) {
                        if(db.delStockout(temp.code) > 0) {
                            dbCon.delStockoutConByStockoutCode(temp.code);
                        }
                    }
                }
                mAdapter.data = db.getAllStockouts();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocalStockoutPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                LinearLayoutManager manager = new LinearLayoutManager(context,
                        LinearLayoutManager.VERTICAL, false);
                mLocalStockoutRecy.setLayoutManager(manager);
                mAdapter = new LocalStockoutAdapter(context);
                mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        NetStockoutInfo itemData = mAdapter.getItemData(position);
                        String json = GsonUtils.toJson(itemData);
                        Intent intent = new Intent(context, StockoutQueryDetailActivity.class);
                        intent.putExtra("ITEMDATA", json);
                        startActivity(intent);
                    }
                });
                mAdapter.setOnItemLongClickListener(new OnRecyclerViewItemLongClickListener() {
                    @Override
                    public void onItemLongClick(View view, int position) {
                        NetStockoutInfo itemData = mAdapter.getItemData(position);
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.ckb_choose_stockout);
                        if(itemData.isChecked) {
                            checkBox.setChecked(false);
                            itemData.isChecked = false;
                        }else {
                            checkBox.setChecked(true);
                            itemData.isChecked = true;
                        }
                    }
                });
                mLocalStockoutRecy.setAdapter(mAdapter);

                mLocalStockoutPtr.refreshComplete();    //刷新完成
            }
        });
        mLocalStockoutPtr.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLocalStockoutPtr.autoRefresh();    //自动刷新
            }
        }, 100);
    }
}
