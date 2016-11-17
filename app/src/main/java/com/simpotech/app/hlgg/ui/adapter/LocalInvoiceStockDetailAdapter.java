package com.simpotech.app.hlgg.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.InvoiceConStockoutDb;
import com.simpotech.app.hlgg.entity.StockoutConInfo;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemLongClickListener;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longuto on 2016/11/14.
 *
 * 出库时,扫描构件的详情列表信息
 */

public class LocalInvoiceStockDetailAdapter extends RecyclerView
        .Adapter<LocalInvoiceStockDetailAdapter.LocalInvoiceStockDetailHolder> implements View
        .OnClickListener, View.OnLongClickListener {

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

    public List<StockoutConInfo> data;      //适配器的数据

    public LocalInvoiceStockDetailAdapter(String code) {
        InvoiceConStockoutDb db = new InvoiceConStockoutDb();
        data = db.getInvoiceConByInvoiceCode(code);
    }

    @Override
    public LocalInvoiceStockDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout
                .item_invoice_stockout_detail, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new LocalInvoiceStockDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalInvoiceStockDetailHolder holder, int position) {
        holder.setData(position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LocalInvoiceStockDetailHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_barCode)
        TextView barCodeTv;
        @BindView(R.id.tv_qty)
        TextView qtyTv;
        @BindView(R.id.tv_contruction_code)
        TextView contructionCodeTv;
        @BindView(R.id.tv_spec)
        TextView specTv;
        @BindView(R.id.tv_sanner_people)
        TextView sannerPeopleTv;
        @BindView(R.id.tv_sanner_time)
        TextView sannerTimeTv;
        @BindView(R.id.ckb_choose)
        CheckBox isCheckCkb;

        public LocalInvoiceStockDetailHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            StockoutConInfo bean = data.get(position);
            barCodeTv.setText("条码 :" + bean.barcode);
            qtyTv.setText("数量 :" + bean.stock_qty);
            contructionCodeTv.setText("构件编号 :" + bean.code);
            specTv.setText("规格 :" + bean.spec);
            sannerPeopleTv.setText("扫描人 :" + bean.scannerPeople);
            sannerTimeTv.setText("扫描时间 :" + bean.scannerTime);
            if(bean.isChecked) {
                isCheckCkb.setChecked(true);
            }else {
                isCheckCkb.setChecked(false);
            }
        }
    }
}
