package com.simpotech.app.hlgg.ui.activity;

import android.support.design.internal.ForegroundLinearLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.NetProlineParse;
import com.simpotech.app.hlgg.entity.DbProLineInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class ProlineNetActivity extends BaseActivity {

    private LinearLayoutManager manager;
    private List<DbProLineInfo> addData;

    @BindView(R.id.edt_search_net)
    EditText mSearchEdt;
    @BindView(R.id.ptr_net_proline)
    PtrClassicFrameLayout mRefreshPtr;
    @BindView(R.id.recy_net_proline)
    RecyclerView mNetProlineRecy;

    @OnClick(R.id.btn_search_net)
    public void searchProlineNet() {
        addData.clear();    //先清空需要增加数据的集合

    }

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_proline_net);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showRightTv("提交");
        showRightIv(R.drawable.vector_proline_get);
        getLeftLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getRightLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将选中生产线提交至本地生产线数据库
                int size = manager.getItemCount();
                for (int i = 0; i < size; i++) {
                    View view = manager.findViewByPosition(i);

                }
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        addData = new ArrayList<DbProLineInfo>();
        refreshRecyclerView();
    }

    /**
     * 刷新recyclerView的数据
     */
    private void refreshRecyclerView() {
        mRefreshPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                addData.clear();

                // 更新RecyclerView的数据
                manager = new LinearLayoutManager(context,
                        LinearLayoutManager.VERTICAL, false);
                mNetProlineRecy.setLayoutManager(manager);
                //将在网络数据并配置mNetProlineRecy
                NetProlineParse.getDataFromNet(mNetProlineRecy, mRefreshPtr);
            }
        });
        mRefreshPtr.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshPtr.autoRefresh();
            }
        }, 100);
    }
}
