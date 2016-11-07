package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.ProLineDb;
import com.simpotech.app.hlgg.entity.DbProLineInfo;
import com.simpotech.app.hlgg.ui.adapter.LocalProLineAdapter;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.simpotech.app.hlgg.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ProLineActivity extends BaseActivity {

    private final String TAG = "ProLineActivity";

    LocalProLineAdapter mAdapter;

    @BindView(R.id.edt_search_local)
    EditText mSearchEdt;
    @BindView(R.id.recy_local_proline)
    RecyclerView mLocalProlineRecy;

    @OnClick(R.id.btn_search)
    public void searchProLines() {
        String name = mSearchEdt.getText().toString().trim();
        List<DbProLineInfo> proLineInfos = new ProLineDb().queryProlineByName(name);
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
                List<DbProLineInfo> delProlineInfos = new ArrayList<DbProLineInfo>();   //删除数据的集合
                for (DbProLineInfo temp : mAdapter.data) {
                    if(temp.isChecked) {
                        delProlineInfos.add(temp);
                        new ProLineDb().deleteProLineById(temp.id); //删除数据库中的对象
                    }
                }
                mAdapter.data.removeAll(delProlineInfos);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        mLocalProlineRecy.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter = new LocalProLineAdapter();
        //设置自定义的item点击事件
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogUtils.i(TAG, "当前位置为: " + position);
                DbProLineInfo temp = mAdapter.data.get(position);
                CheckBox isCheckCkb = (CheckBox) view.findViewById(R.id.ckb_choose);
                if(temp.isChecked) {
                    temp.isChecked = false;
                    isCheckCkb.setChecked(false);
                }else {
                    temp.isChecked = true;
                    isCheckCkb.setChecked(true);
                }
            }
        });
        mLocalProlineRecy.setAdapter(mAdapter);
    }
}
