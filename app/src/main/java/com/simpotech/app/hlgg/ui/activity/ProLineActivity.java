package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.support.design.internal.ForegroundLinearLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.ProLineDb;
import com.simpotech.app.hlgg.entity.ProLineInfo;
import com.simpotech.app.hlgg.ui.adapter.LocalProLineAdapter;
import com.simpotech.app.hlgg.ui.widget.RecycleViewDivider;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.string.no;

public class ProLineActivity extends BaseActivity {

    private final String TAG = "ProLineActivity";

    LinearLayoutManager mLinearLayoutManager;
    LocalProLineAdapter mAdapter;

    @BindView(R.id.edt_search_local)
    EditText mSearchEdt;
    @BindView(R.id.recy_local_proline)
    RecyclerView mLocalProlineRecy;

    @OnClick(R.id.btn_search)
    public void searchProLines() {
        mAdapter.delData.clear();   //清空delData

        String name = mSearchEdt.getText().toString().trim();
        List<ProLineInfo> proLineInfos = new ProLineDb().queryProlineByName(name);
        mAdapter.data = proLineInfos;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_pro_line);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showMiddleIv(R.drawable.vector_proline_add);
        showRightIv(R.drawable.vector_proline_del);
        //返回点击事件
        getLeftLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //添加服务器中的生产线
        getMiddleLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProlineNetActivity.class);
                startActivity(intent);
            }
        });
        //删除点击事件
        getRightLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.data.removeAll(mAdapter.delData);  //删除已经选中的数据
                mAdapter.notifyDataSetChanged();    //通知数据改变
                //删除数据库中对应的数据
                for(ProLineInfo info : mAdapter.delData) {
                    new ProLineDb().deleteProLineById(info.id);
                }

                for (ProLineInfo info : mAdapter.delData) {
                    LogUtils.i(TAG, "delData-----------------" + info.departmentName + " = " + info.proLineName);
                }
                for (ProLineInfo info : mAdapter.data) {
                    LogUtils.i(TAG, "data-----------------" + info.departmentName + " = " + info.proLineName);
                }

                mAdapter.delData.clear();       //清空delData数据
            }
        });
    }

    @Override
    protected void initData() {
        mLinearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager
                .VERTICAL, false);
        mLocalProlineRecy.setLayoutManager(mLinearLayoutManager);
    }

    /**Activity变为可见时*/
    @Override
    protected void onStart() {
        super.onStart();
        mAdapter = new LocalProLineAdapter();
        mLocalProlineRecy.setAdapter(mAdapter);
    }
}
