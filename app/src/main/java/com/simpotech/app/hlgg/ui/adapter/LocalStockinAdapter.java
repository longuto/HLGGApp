package com.simpotech.app.hlgg.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.StockinDb;
import com.simpotech.app.hlgg.entity.net.NetStockinInfo;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemLongClickListener;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longuto on 2016/11/18.
 * <p>
 * 加载网络成功,本地入库单的适配器
 */

public class LocalStockinAdapter extends RecyclerView.Adapter<LocalStockinAdapter
        .LocalStockinHolder> implements View.OnClickListener, View.OnLongClickListener {

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

    public List<NetStockinInfo> data;   //适配器的数据
    private Context context;

    public LocalStockinAdapter(Context context) {
        this.context = context;
        StockinDb db = new StockinDb();
        data = db.getAllStockins();
    }

    /** 暴露一个公共方法，返回当前item的数据 */
    public NetStockinInfo getItemData(int position) {
        return data.get(position);
    }

    @Override
    public LocalStockinHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout
                .item_local_stockin, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new LocalStockinHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalStockinHolder holder, int position) {
        holder.setData(position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LocalStockinHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ckb_choose_stockin)
        CheckBox chooseStockinCkb;
        @BindView(R.id.tv_stockin_code)
        TextView stockinCodeTv;
        @BindView(R.id.tv_wo_code)
        TextView woCodeTv;
        @BindView(R.id.tv_cml_code)
        TextView cmlCodeTv;
        @BindView(R.id.tv_proj_name)
        TextView projNameTv;
        @BindView(R.id.tv_organName)
        TextView organNameTv;
        @BindView(R.id.tv_productLine)
        TextView productLineTv;
        @BindView(R.id.tv_addUserName)
        TextView addUserNameTv;
        @BindView(R.id.tv_addTime)
        TextView addTimeTv;

        public LocalStockinHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            NetStockinInfo info = data.get(position);
            stockinCodeTv.setText("入库单号 :" + info.code);
            woCodeTv.setText(info.wo_code);
            cmlCodeTv.setText(info.cml_code);
            projNameTv.setText(info.proj_name);
            organNameTv.setText(info.organName);
            productLineTv.setText(info.productLine);
            addUserNameTv.setText("提交人 :" + info.addUserName);
            addTimeTv.setText(" 提交时间 :" + info.addTime);
            if(info.isCheck) {
                chooseStockinCkb.setChecked(true);
            }else {
                chooseStockinCkb.setChecked(false);
            }
        }
    }
}
