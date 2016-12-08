package com.simpotech.app.hlgg.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.StockinConSubDb;
import com.simpotech.app.hlgg.entity.StockinConInfo;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemLongClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longuto on 2016/11/16.
 * <p>
 * 本地入库的构件信息
 */

public class LocalStockinSubConAdapter extends RecyclerView.Adapter<LocalStockinSubConAdapter
        .LocalStockinConHolder> implements View.OnClickListener, View.OnLongClickListener {

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

    public List<StockinConInfo> data;   //适配器数据
    private Context context;

    public LocalStockinSubConAdapter(Context context) {
        this.context = context;
        StockinConSubDb db = new StockinConSubDb();
        data = db.getAllStockinCon();
    }

    /**暴露一个公共方法,获取传入position选项的数据*/
    public StockinConInfo getItemData(int position) {
        return data.get(position);
    }

    @Override
    public LocalStockinConHolder onCreateViewHolder(ViewGroup parent, int
            viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout
                .item_invoice_stockout_detail, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new LocalStockinConHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalStockinConHolder holder, int position) {
        holder.setData(position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LocalStockinConHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tv_barCode)
//        TextView barCodeTv;
        @BindView(R.id.tv_qty)
        TextView qtyTv;
        @BindView(R.id.tv_contruction_code)
        TextView contructionCodeTv;
        @BindView(R.id.tv_spec)
        TextView specTv;
//        @BindView(R.id.tv_sanner_people)
//        TextView sannerPeopleTv;
        @BindView(R.id.tv_sanner_time)
        TextView sannerTimeTv;
        @BindView(R.id.ckb_choose)
        CheckBox isCheckCkb;
        @BindView(R.id.tv_errorMsg)
        TextView errorMsgTv;

        public LocalStockinConHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            StockinConInfo info = data.get(position);
//            barCodeTv.setText("条码 :" + info.barcode);
            qtyTv.setText(/*"数量 :" +*/ info.stock_qty);
            contructionCodeTv.setText(/*"构件编号 :" + */info.code);
            specTv.setText(/*"规格 :" + */info.spec);
//            sannerPeopleTv.setText("扫描人 :" + info.scannerPeople);
            sannerTimeTv.setText(/*"扫描时间 :" + */info.scannerTime);
            if(info.isChecked) {
                isCheckCkb.setChecked(true);
            }else {
                isCheckCkb.setChecked(false);
            }
            // 错误
            if(info.isError == 1) {
                errorMsgTv.setVisibility(View.VISIBLE);
                errorMsgTv.setText(info.message);
            }else {
                errorMsgTv.setVisibility(View.GONE);
            }
        }
    }
}
