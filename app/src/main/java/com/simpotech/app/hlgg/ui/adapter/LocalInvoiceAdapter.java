package com.simpotech.app.hlgg.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.InvoiceDb;
import com.simpotech.app.hlgg.entity.net.NetInvoiceInfo;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemLongClickListener;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longuto on 2016/11/9.
 */

public class LocalInvoiceAdapter extends RecyclerView.Adapter<LocalInvoiceAdapter
        .LocalInvoiceHolder> implements View.OnClickListener, View.OnLongClickListener {
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
        if(mOnItemLongClickListener != null) {
            mOnItemLongClickListener.onItemLongClick(v, (Integer) v.getTag());
        }
        return true;    //消费掉,用于长按后取消单机时间
    }

    public List<NetInvoiceInfo> data;  //适配器的数据

    public LocalInvoiceAdapter() {
        InvoiceDb db = new InvoiceDb(); //构造时,从数据库中加载所有的数据
        data = db.getAllInvoices();
    }


     /** 暴露一个公共方法,返回当前position条目的数据 */
    public NetInvoiceInfo getItemData(int position) {
        return data.get(position);
    }

    @Override
    public LocalInvoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout.item_invoice,
                parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new LocalInvoiceHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalInvoiceHolder holder, int position) {
        holder.setData(position);
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class LocalInvoiceHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ckb_choose_invoice)
        CheckBox invoiceCkb;
        @BindView(R.id.tv_code)
        TextView codeTv;
        @BindView(R.id.tv_proj_name)
        TextView projNameTv;
        @BindView(R.id.tv_organName)
        TextView orgNameTv;
        @BindView(R.id.tv_saleName)
        TextView saleNameTv;
        @BindView(R.id.tv_addUserName)
        TextView addUserNameTv;
        @BindView(R.id.tv_cjdate)
        TextView cjdateTv;

        public LocalInvoiceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            NetInvoiceInfo info = data.get(position);

            codeTv.setText("发货单号:" + info.code);
            projNameTv.setText(info.proj_name);
            orgNameTv.setText(info.organName);
            saleNameTv.setText(info.saleName);
            addUserNameTv.setText(info.addUserName);
            cjdateTv.setText(info.cjdate);
            if(info.isChecked) {
                invoiceCkb.setChecked(true);
            }else {
                invoiceCkb.setChecked(false);
            }
        }
    }
}
