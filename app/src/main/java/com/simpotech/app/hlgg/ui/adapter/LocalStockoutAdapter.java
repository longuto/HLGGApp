package com.simpotech.app.hlgg.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.StockoutDb;
import com.simpotech.app.hlgg.entity.net.NetStockoutInfo;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemLongClickListener;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longuto on 2016/11/10.
 * 出库单数据库信息的构件信息适配器
 */

public class LocalStockoutAdapter extends RecyclerView.Adapter<LocalStockoutAdapter
        .LocalStockoutHolder> implements View.OnClickListener, View.OnLongClickListener {

    //是否为Adapter设置条目点击事件
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerViewItemLongClickListener mOnItemLongClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null) {
            mOnItemLongClickListener.onItemLongClick(v, (Integer) v.getTag());
        }
        return true;    //消费掉,用于长按后取消单机时间
    }

    public List<NetStockoutInfo> data;

    public LocalStockoutAdapter() {
        StockoutDb db = new StockoutDb();
        data = db.getAllStockouts();
    }

    /**
     * 暴露一个公共方法,返回当前条目的数据
     */
    public NetStockoutInfo getItemData(int position) {
        return data.get(position);
    }

    @Override
    public LocalStockoutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout
                .item_local_stockout, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new LocalStockoutHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalStockoutHolder holder, int position) {
        holder.setData(position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LocalStockoutHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ckb_choose_stockout)
        CheckBox chooseStockoutCkb;
        @BindView(R.id.tv_stockout_code)
        TextView stockoutCodeTv;
        @BindView(R.id.tv_invoice_code)
        TextView invoiceCodeTv;
        @BindView(R.id.tv_proj_name)
        TextView projNameTv;
        @BindView(R.id.tv_addName)
        TextView addNameTv;
        @BindView(R.id.tv_addTime)
        TextView addTimeTv;

        public LocalStockoutHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            NetStockoutInfo info = data.get(position);
            stockoutCodeTv.setText("出库单号 :" + info.code);
            invoiceCodeTv.setText(info.invoice_code);
            projNameTv.setText(info.proj_name);
            addNameTv.setText(info.addUserName);
            addTimeTv.setText(info.addTime);
            if(info.isChecked) {
                chooseStockoutCkb.setChecked(true);
            }else {
                chooseStockoutCkb.setChecked(false);
            }
        }
    }
}
