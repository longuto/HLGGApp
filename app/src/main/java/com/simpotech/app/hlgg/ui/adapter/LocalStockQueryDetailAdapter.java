package com.simpotech.app.hlgg.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.entity.net.NetStockoutInfo;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longuto on 2016/11/16.
 * <p>
 * 出库查询详情页
 */

public class LocalStockQueryDetailAdapter extends RecyclerView
        .Adapter<LocalStockQueryDetailAdapter.LocalStockQueryDetailHolder> {

    public List<NetStockoutInfo.DetailsBean> data;  //适配器数据

    public LocalStockQueryDetailAdapter(List<NetStockoutInfo.DetailsBean> data) {
        this.data = data;
    }

    @Override
    public LocalStockQueryDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout
                .item_stockout_query_detail, parent, false);
        return new LocalStockQueryDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalStockQueryDetailHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LocalStockQueryDetailHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_contruction_code)
        TextView contructionCodeTv;
        @BindView(R.id.tv_spec)
        TextView specTv;
        @BindView(R.id.tv_materialName)
        TextView materialNameTv;
        @BindView(R.id.tv_length)
        TextView lengthTv;
        @BindView(R.id.tv_qty)
        TextView qtyTv;
        @BindView(R.id.tv_tonnage)
        TextView tonnageTv;

        public LocalStockQueryDetailHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            NetStockoutInfo.DetailsBean bean = data.get(position);
            contructionCodeTv.setText("构件编号 :" + bean.contruction_code);
            materialNameTv.setText("材质 :" + bean.materialName);
            specTv.setText("规格 :" + bean.spec);
            lengthTv.setText("长度 :" + bean.length);
            qtyTv.setText("出库数量 :" + bean.qty + "/" + bean.invoice_qty);
            tonnageTv.setText("出库重量 :" + bean.tonnage);
        }
    }
}
