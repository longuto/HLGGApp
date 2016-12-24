package com.simpotech.app.hlgg.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.entity.net.NetInvoiceInfo;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longuto on 2016/11/9.
 * 发货单详情页的适配器
 */

public class LocalInvoiceDetailAdapter extends RecyclerView.Adapter<LocalInvoiceDetailAdapter
        .LocalInvoiceDetailHolder> {

    public List<NetInvoiceInfo.DetailsBean> data;

    public LocalInvoiceDetailAdapter(List<NetInvoiceInfo.DetailsBean> data) {
        this.data = data;
    }

    @Override
    public LocalInvoiceDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout
                .item_invoice_detail, parent, false);
        return new LocalInvoiceDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalInvoiceDetailHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LocalInvoiceDetailHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_qty)
        TextView qtyTv;
        @BindView(R.id.tv_contruction_code)
        TextView contructionCodeTv;
        @BindView(R.id.tv_spec)
        TextView specTv;

        public LocalInvoiceDetailHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            NetInvoiceInfo.DetailsBean bean = data.get(position);
            qtyTv.setText(/*"数量 :" + */bean.qty + "/" + bean.constructQty);
            contructionCodeTv.setText(/*"构件编号 :" + */bean.contruction_code);
            specTv.setText(/*"规格 :" + */bean.spec);
        }
    }
}
