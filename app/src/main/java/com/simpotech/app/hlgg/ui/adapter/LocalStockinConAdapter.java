package com.simpotech.app.hlgg.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.entity.net.NetStockinInfo;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longuto on 2016/11/18.
 * <p>
 * 网络返回的入库单下的构件信息的适配器
 */

public class LocalStockinConAdapter extends RecyclerView.Adapter<LocalStockinConAdapter
        .LocalStockinConHolder> {

    public List<NetStockinInfo.DetailsBean> data;   // 适配器数据

    public LocalStockinConAdapter(List<NetStockinInfo.DetailsBean> data) {
        this.data = data;
    }

    @Override
    public LocalStockinConHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout
                .item_stockin_query_detail, parent, false);
        return new LocalStockinConHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalStockinConHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LocalStockinConHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_barCode)
        TextView barCodeTv;
        @BindView(R.id.tv_contruction_code)
        TextView contructionCodeTv;
        @BindView(R.id.tv_qty)
        TextView qtyTv;
        @BindView(R.id.tv_spec)
        TextView specTv;


        public LocalStockinConHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            NetStockinInfo.DetailsBean bean = data.get(position);
            barCodeTv.setText("条码 :" + bean.barCode);
            contructionCodeTv.setText("构件编号 :" + bean.contruction_code);
            qtyTv.setText("数量 :" + bean.qty);
            specTv.setText("规格 :" + bean.spec);
        }
    }
}
