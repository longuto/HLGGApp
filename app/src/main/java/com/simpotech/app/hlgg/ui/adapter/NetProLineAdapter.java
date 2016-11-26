package com.simpotech.app.hlgg.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.entity.DbProLineInfo;
import com.simpotech.app.hlgg.entity.RecyProLineInfo;
import com.simpotech.app.hlgg.entity.net.NetProLineInfo;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemClickListener;
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

    public List<RecyProLineInfo> data;
    private Context context;

    public NetProLineAdapter(List<RecyProLineInfo> data, Context context) {
        this.context = context;
        this.data = data;
    }

    @Override
    public NetProlineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout
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

        @BindView(R.id.tv_departmentName)
        TextView departmentNameTv;
        @BindView(R.id.crecy_prolines)
        ChildRecyclerView childRecyclerView;

        public NetProlineHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            final RecyProLineInfo temp = data.get(position);   //获取当前列数据
            departmentNameTv.setText("部门名称: " + temp.name);

            // 设置ChildRecyclerView
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                    LinearLayoutManager.VERTICAL);
            childRecyclerView.setLayoutManager(layoutManager);
            NetProlineChildAdapter childAdapter = new NetProlineChildAdapter(temp);
            //设置每一个子Adapter的item点击事件
            childAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    DbProLineInfo info = temp.prolines.get(position);
                    CheckBox checkBox = (CheckBox) view.findViewById(R.id.ckb_childProline);
                    if(info.isChecked) {
                        checkBox.setChecked(false);
                        info.isChecked = false;
                    }else {
                        checkBox.setChecked(true);
                        info.isChecked = true;
                    }
                }
            });
            childRecyclerView.setAdapter(childAdapter);
        }
    }


    //----------------------------------嵌套的Adapter-------------------------------


    /**
     * 部门下的生产线适配器
     */
    class NetProlineChildAdapter extends ChildRecyclerView.Adapter<NetProlineChildAdapter
            .NetProlineChildHolder> implements View.OnClickListener {
        //是否为Adapter设置条目点击事件
        private OnRecyclerViewItemClickListener mOnItemClickListener = null;

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            this.mOnItemClickListener = listener;
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
            }
        }


        RecyProLineInfo recyProLineInfo;  //适配器的数据

        public NetProlineChildAdapter(RecyProLineInfo recyProLineInfo) {
            this.recyProLineInfo = recyProLineInfo;
        }

        @Override
        public NetProlineChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout
                    .item_net_proline_child_recy, parent, false);
            view.setOnClickListener(this);
            return new NetProlineChildHolder(view);
        }

        @Override
        public void onBindViewHolder(NetProlineChildHolder holder, int position) {
            holder.setData(position);
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return recyProLineInfo.prolines.size();
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
                final DbProLineInfo temp = recyProLineInfo.prolines.get(position);

                if(temp.isChecked) {
                    prolineChooseCkb.setChecked(true);
                }else {
                    prolineChooseCkb.setChecked(false);
                }
                prolineNameTv.setText(temp.proLineName);
            }
        }
    }
}
