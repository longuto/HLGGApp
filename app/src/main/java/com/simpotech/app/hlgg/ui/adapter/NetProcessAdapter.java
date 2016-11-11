package com.simpotech.app.hlgg.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.entity.net.NetProcessInfo;
import com.simpotech.app.hlgg.ui.activity.ProcessActivity;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.simpotech.app.hlgg.ui.widget.ChildRecyclerView;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.string.no;
import static android.os.Build.VERSION_CODES.M;

/**
 * Created by longuto on 2016/11/4.
 * 显示网络流程模块数据信息
 */

public class NetProcessAdapter extends RecyclerView.Adapter<NetProcessAdapter.NetProcessHolder> {
    public SharedManager sp = new SharedManager(SharedManager.PROCESS_CONFIG_NAME);

    public List<NetProcessInfo> data;
    public Context context;

    public NetProcessAdapter(List<NetProcessInfo> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public NetProcessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout
                .item_process_business, parent, false);
        return new NetProcessHolder(view);
    }

    @Override
    public void onBindViewHolder(NetProcessHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NetProcessHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_process_business)
        TextView businessNameTv;
        @BindView(R.id.recy_child_process)
        ChildRecyclerView childProcessRecy;

        public NetProcessHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            final NetProcessInfo temp = data.get(position);
            businessNameTv.setText("业务模块:（" + temp.businessName + ")");

            //为每一个ChildRecyclerView设置数据
            final List<NetProcessInfo.FlowListBean> itemTemp = temp.flowList;
            LinearLayoutManager manager = new LinearLayoutManager(UiUtils.getContext(),
                    LinearLayoutManager.VERTICAL, false);
            childProcessRecy.setLayoutManager(manager);
            final NetProcessChildAdapter childAdapter = new NetProcessChildAdapter(itemTemp);
            childProcessRecy.setAdapter(childAdapter);
            childAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, final int position) {
                    final NetProcessInfo.FlowListBean bean = itemTemp.get(position);
                    if (!bean.isDef) {
                        new AlertDialog.Builder(context)
                                .setTitle("确定设置")
                                .setMessage("是否设置" + bean.flowName + "为默认设置")
                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sp.putStringToXml(bean.businessNo, bean.flowId);
                                        childAdapter.notifyDataSetChanged();
                                    }
                                })
                                .create()
                                .show();
//                        sp.putStringToXml(bean.businessNo, bean.flowId);
//                        childAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }


    //-------------------嵌套的子适配器---------------------


    /**
     * 为Adapter设置item点击事件
     */
    class NetProcessChildAdapter extends RecyclerView.Adapter<NetProcessChildAdapter
            .NetProcessChildHolder> implements View.OnClickListener {

        //item点击事件监听
        private OnRecyclerViewItemClickListener mOnItemClickListener = null;

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            this.mOnItemClickListener = listener;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
            }
        }

        public List<NetProcessInfo.FlowListBean> childData;    //适配器的数据

        public NetProcessChildAdapter(List<NetProcessInfo.FlowListBean> childData) {
            this.childData = childData;
        }

        @Override
        public NetProcessChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout
                    .item_process_child_business, parent, false);
            view.setOnClickListener(this);
            return new NetProcessChildHolder(view);
        }

        @Override
        public void onBindViewHolder(NetProcessChildHolder holder, int position) {
            holder.itemView.setTag(position);
            holder.setData(position);
        }

        @Override
        public int getItemCount() {
            return childData.size();
        }

        class NetProcessChildHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_process_follow)
            TextView followTv;
            @BindView(R.id.rdb_set_def)
            RadioButton defSetRdb;

            public NetProcessChildHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void setData(final int position) {
                NetProcessInfo.FlowListBean info = childData.get(position);
                followTv.setText(info.flowName);

                //获取本地Xml文件,看是否有默认值,没有的话,将第一个设置为默认值
                String followId = sp.getStringFromXml(info.businessNo, childData.get(0).flowId);
                //说明之前没有存储过数值,则将第一位设置为默认的
                if (followId.equals(info.flowId)) {
                    defSetRdb.setChecked(true);
                    info.isDef = true;
                } else {
                    defSetRdb.setChecked(false);
                    info.isDef = false;
                }
            }
        }
    }
}
