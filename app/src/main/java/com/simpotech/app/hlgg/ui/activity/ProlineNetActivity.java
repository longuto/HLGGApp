package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.internal.ForegroundLinearLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.NetProlineParse;
import com.simpotech.app.hlgg.db.dao.ProLineDb;
import com.simpotech.app.hlgg.entity.DbProLineInfo;
import com.simpotech.app.hlgg.entity.RecyProLineInfo;
import com.simpotech.app.hlgg.ui.adapter.NetProLineAdapter;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class ProlineNetActivity extends BaseActivity {

    private static final String TAG = "ProlineNetActivity";

    @BindView(R.id.edt_search_net)
    EditText mSearchEdt;
    @BindView(R.id.ptr_net_proline)
    PtrClassicFrameLayout mRefreshPtr;
    @BindView(R.id.recy_net_proline)
    RecyclerView mNetProlineRecy;

    @OnClick(R.id.btn_search_net)
    public void searchProlineNet() {
        String department = mSearchEdt.getText().toString().trim();
        if(!TextUtils.isEmpty(department)) {
            NetProlineParse.getDataByDepartmentName(department, mNetProlineRecy, mRefreshPtr);
        }else {
            UiUtils.showToast("查询内容不能为空");
        }
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
                ProLineDb proLineDb = new ProLineDb();

                NetProLineAdapter adapter = (NetProLineAdapter) mNetProlineRecy.getAdapter();
                List<RecyProLineInfo> adapterData = adapter.data;

                // 将选中的生产线对象加入addProLineInfos
                for (RecyProLineInfo info : adapterData) {
                    int size = info.prolines.size();
                    for (int i = 0; i < size; i++) {
                        DbProLineInfo temp = info.prolines.get(i);
                        if (temp.isChecked) {
                            //如果对象不存在则添加至数据库
                            if(!proLineDb.isExistProline(temp)) {
                                //添加至数据库
                                proLineDb.addProLine(temp.departmentId, temp.departmentName,
                                        temp.proLineId, temp.proLineName);
                            }
                        }
                    }
                }

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
