package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.QualityDb;
import com.simpotech.app.hlgg.entity.net.NetQualityInfo;
import com.simpotech.app.hlgg.ui.adapter.LocalQualityAdapter;
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

public class QualityQueryActivity extends BaseActivity {

    LocalQualityAdapter mAdapter;   //适配器

    @BindView(R.id.edt_search_quality)
    EditText mSearchQualityEdt;
    @BindView(R.id.recy_local_quality)
    RecyclerView mLocalQualityRecy;
    @BindView(R.id.ptr_local_quality)
    PtrClassicFrameLayout mLocalQualityPtr;

    /**
     * 查询质检单号
     */
    @OnClick(R.id.iv_search)
    public void search(View v) {
        String content = mSearchQualityEdt.getText().toString().trim();
        QualityDb db = new QualityDb();
        mAdapter.data = db.queryQualityByContent(content);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_quality_query);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showMiddleTv("质检查询");
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
                List<NetQualityInfo> netQualityInfos = mAdapter.data;
                QualityDb db = new QualityDb();
                for (NetQualityInfo info : netQualityInfos) {
                    if(info.isCheck) {
                        if(!db.delQuality(info.code)) {
                            UiUtils.showToast("质检单号为" + info.code + "的质检单删除失败");
                        }
                    }
                }
                mAdapter.data = db.getAllQualitys();
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
        mLocalQualityPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                LinearLayoutManager manager = new LinearLayoutManager(context,
                        LinearLayoutManager.VERTICAL, false);
                mLocalQualityRecy.setLayoutManager(manager);
                mAdapter = new LocalQualityAdapter(context);
                mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        NetQualityInfo itemData = mAdapter.getItemData(position);
                        Intent intent = new Intent(context, QualityQueryDetailActivity.class);
                        intent.putExtra("ITEMDATA", GsonUtils.toJson(itemData));
                        startActivity(intent);
                        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                    }
                });
                mAdapter.setmOnItemLongClickListener(new OnRecyclerViewItemLongClickListener() {
                    @Override
                    public void onItemLongClick(View view, int position) {
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.ckb_choose_quality);
                        NetQualityInfo itemData = mAdapter.getItemData(position);
                        if(itemData.isCheck) {
                            itemData.isCheck = false;
                            checkBox.setChecked(false);
                        }else {
                            itemData.isCheck = true;
                            checkBox.setChecked(true);
                        }

                    }
                });
                mLocalQualityRecy.setAdapter(mAdapter);

                mLocalQualityPtr.refreshComplete(); // 刷新完成
            }
        });
        mLocalQualityPtr.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLocalQualityPtr.autoRefresh();
            }
        }, 100);
    }
}
