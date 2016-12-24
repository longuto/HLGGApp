package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.StockinContruDb;
import com.simpotech.app.hlgg.db.dao.StockinDb;
import com.simpotech.app.hlgg.entity.net.NetStockinInfo;
import com.simpotech.app.hlgg.ui.adapter.LocalStockinAdapter;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemLongClickListener;
import com.simpotech.app.hlgg.util.GsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class StockinQueryActivity extends BaseActivity {
    private static final String TAG = "StockinQueryActivity";

    LocalStockinAdapter mAdapter;   //适配器

    @BindView(R.id.edt_search_stockin)
    EditText mSearchStockinEdt;
    @BindView(R.id.recy_local_stockin)
    RecyclerView mLocalStockinRecy;
    @BindView(R.id.ptr_local_stockin)
    PtrClassicFrameLayout mLocalStockinPtr;

    @OnClick(R.id.btn_search)
    public void searchStockin() {
        String content = mSearchStockinEdt.getText().toString().trim();
        StockinDb db = new StockinDb();
        mAdapter.data = db.queryStockinsByInput(content);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_stockin_query);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showMiddleTv("入库查询");
        showRightIv(R.drawable.vector_proline_del);
        getLeftLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.activity_back_enter, R.anim.activity_back_exit);
            }
        });
        getRightLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<NetStockinInfo> netStockinInfos = mAdapter.data;
                StockinDb db = new StockinDb();
                StockinContruDb dbCon = new StockinContruDb();
                for (NetStockinInfo info : netStockinInfos) {
                    if(info.isCheck) {
                        if(db.delStockin(info.code) > 0) {
                            dbCon.delStockinConByStockinCode(info.code);
                        }
                    }
                }
                mAdapter.data = db.getAllStockins();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initData() {
        refreshPtr();
    }

    /**
     * 下拉刷新
     */
    private void refreshPtr() {
        mLocalStockinPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                LinearLayoutManager manager = new LinearLayoutManager(context,
                        LinearLayoutManager.VERTICAL, false);
                mLocalStockinRecy.setLayoutManager(manager);
                mAdapter = new LocalStockinAdapter(context);
                mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        NetStockinInfo itemData = mAdapter.getItemData(position);
                        Intent intent = new Intent(context, StockinQueryDetailActivity.class);
                        intent.putExtra("ITEMDATA", GsonUtils.toJson(itemData));
                        startActivity(intent);
                        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                    }
                });
                mAdapter.setOnItemLongClickListener(new OnRecyclerViewItemLongClickListener() {
                    @Override
                    public void onItemLongClick(View view, int position) {
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.ckb_choose_stockin);
                        NetStockinInfo itemData = mAdapter.getItemData(position);
                        if(itemData.isCheck) {
                            checkBox.setChecked(false);
                            itemData.isCheck = false;
                        }else {
                            checkBox.setChecked(true);
                            itemData.isCheck = true;
                        }
                    }
                });
                mLocalStockinRecy.setAdapter(mAdapter);

                mLocalStockinPtr.refreshComplete();
            }
        });
        mLocalStockinPtr.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLocalStockinPtr.autoRefresh();
            }
        }, 100);

    }
}
