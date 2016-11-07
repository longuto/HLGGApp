package com.simpotech.app.hlgg.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.NetProcessParse;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 业务流程的Activity
 */
public class ProcessActivity extends BaseActivity {

    @BindView(R.id.recy_process)
    RecyclerView mProcessRecy;
    @BindView(R.id.ptr_process)
    PtrClassicFrameLayout mRefreshPtr;

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_process);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        getLeftLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showMiddleTv("流程设置");
    }

    @Override
    protected void initData() {
        mRefreshPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                LinearLayoutManager manager = new LinearLayoutManager(context,
                        LinearLayoutManager.VERTICAL, false);
                mProcessRecy.setLayoutManager(manager);
                NetProcessParse.getDataFromNet(mProcessRecy, mRefreshPtr, context);
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
