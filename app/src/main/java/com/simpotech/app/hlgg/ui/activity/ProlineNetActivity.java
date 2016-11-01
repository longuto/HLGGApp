package com.simpotech.app.hlgg.ui.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.ui.adapter.NetProLineAdapter;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class ProlineNetActivity extends BaseActivity {

    @BindView(R.id.edt_search_net)
    EditText mSearchEdt;
    @BindView(R.id.ptr_net_proline)
    PtrClassicFrameLayout mRefreshPtr;
    @BindView(R.id.recy_net_proline)
    RecyclerView mNetProlineRecy;

    @OnClick(R.id.btn_search_net)
    public void searchProlineNet() {

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


                finish();
            }
        });
    }

    @Override
    protected void initData() {
        refreshRecyclerView();
    }

    /**
     * 刷新recyclerView的数据
     */
    private void refreshRecyclerView() {
        mRefreshPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 更新RecyclerView的数据
                LinearLayoutManager manager = new LinearLayoutManager(context,
                        LinearLayoutManager.VERTICAL, false);
                mNetProlineRecy.setLayoutManager(manager);
                NetProLineAdapter adapter = new NetProLineAdapter();
                mNetProlineRecy.setAdapter(adapter);

                mRefreshPtr.refreshComplete();  //完成刷新
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
