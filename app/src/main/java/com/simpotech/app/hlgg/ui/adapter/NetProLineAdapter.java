package com.simpotech.app.hlgg.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.NetProlineParse;
import com.simpotech.app.hlgg.entity.DbProLineInfo;
import com.simpotech.app.hlgg.entity.net.NetProLineInfo;
import com.simpotech.app.hlgg.ui.widget.ChildRecyclerView;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longuto on 2016/11/1.
 * 网络数据的生产线部门的适配器
 */

public class NetProLineAdapter extends RecyclerView.Adapter<NetProLineAdapter.NetProlineHolder> {

    List<NetProLineInfo> data;
    List<DbProLineInfo> prolineList;

    public NetProLineAdapter(List<NetProLineInfo> data) {
        this.data = data;
        prolineList = new ArrayList<>();
    }

    @Override
    public NetProlineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout
                .item_net_proline_recy, parent, false);
        return new NetProlineHolder(view);
    }

    @Override
    public void onBindViewHolder(NetProlineHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 自定义ViewHolder
     */
    class NetProlineHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_departmentId)
        TextView departmentIdTv;
        @BindView(R.id.tv_departmentName)
        TextView departmentNameTv;
        @BindView(R.id.crecy_prolines)
        ChildRecyclerView childRecyclerView;

        public NetProlineHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            NetProLineInfo temp = data.get(position);   //获取当前列数据
            departmentIdTv.setText(temp.id);
            departmentNameTv.setText("部门名称: " + temp.name);

            // 设置ChildRecyclerView
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                    LinearLayoutManager.VERTICAL);
            childRecyclerView.setLayoutManager(layoutManager);
            NetProlineChildAdapter childAdapter = new NetProlineChildAdapter(temp);
            childRecyclerView.setAdapter(childAdapter);
        }
    }

    /**
     * 部门下的生产线适配器
     */
    class NetProlineChildAdapter extends ChildRecyclerView.Adapter<NetProlineChildAdapter
            .NetProlineChildHolder> {

        NetProLineInfo netProLineInfo;
        List<DbProLineInfo> prolines;

        public NetProlineChildAdapter(NetProLineInfo netProLineInfo) {
            this.netProLineInfo = netProLineInfo;
            prolines = new ArrayList<>();
        }

        @Override
        public NetProlineChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout
                    .item_net_proline_child_recy, parent, false);
            return new NetProlineChildHolder(view);
        }

        @Override
        public void onBindViewHolder(NetProlineChildHolder holder, int position) {
            holder.setData(position);
        }

        @Override
        public int getItemCount() {
            return netProLineInfo.organList.size();
        }

        class NetProlineChildHolder extends ChildRecyclerView.ViewHolder {
            @BindView(R.id.ckb_childProline)
            CheckBox prolineChooseCkb;
            @BindView(R.id.tv_prolineName)
            TextView prolineNameTv;

            public NetProlineChildHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void setData(int position) {
                //将网络生产线的数据转换成数据库的生产线
                final DbProLineInfo temp = new DbProLineInfo(netProLineInfo.id, netProLineInfo.name,
                        netProLineInfo.organList.get(position).id, netProLineInfo.organList.get
                        (position).name);

                prolineChooseCkb.setChecked(false);
                prolineNameTv.setText(temp.proLineName);

                //为CheckBox设置点击事件
                prolineChooseCkb.setOnCheckedChangeListener(new CompoundButton
                        .OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            prolines.add(temp);
                        } else {
                            prolines.remove(temp);
                        }
                    }
                });
            }
        }
    }
}
