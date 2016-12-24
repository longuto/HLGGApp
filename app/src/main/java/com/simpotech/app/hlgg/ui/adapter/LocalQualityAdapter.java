package com.simpotech.app.hlgg.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.QualityDb;
import com.simpotech.app.hlgg.entity.net.NetQualityInfo;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemLongClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longuto on 2016/12/21.
 * <p>
 * 本地质检单号适配器
 */

public class LocalQualityAdapter extends RecyclerView.Adapter<LocalQualityAdapter
        .LocalQualityHolder> implements View.OnClickListener,View.OnLongClickListener {

    //是否为Adapter设置条目点击事件
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerViewItemLongClickListener mOnItemLongClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public void setmOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
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
        return true;    //消费事件,防止长按以后,单机事件的响应
    }

    public List<NetQualityInfo> data; //适配器数据
    private Context context;

    public LocalQualityAdapter(Context context) {
        this.context = context;
        QualityDb db = new QualityDb();
        data = db.getAllQualitys();
    }

    /**
     * 暴露一个公共方法,返回指定item数据
     */
    public NetQualityInfo getItemData(int position) {
        return data.get(position);
    }

    @Override
    public LocalQualityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_local_quality, parent,
                false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new LocalQualityHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalQualityHolder holder, int position) {
        holder.setData(position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class LocalQualityHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ckb_choose_quality)
        CheckBox chooseQualityCkb;
        @BindView(R.id.tv_quality_code)
        TextView qualityCodeTv;
        @BindView(R.id.tv_projectName)
        TextView projectNameTv;
        @BindView(R.id.tv_organName)
        TextView organNameTv;
        @BindView(R.id.tv_username)
        TextView usernameTv;
        @BindView(R.id.tv_cjdate)
        TextView cjdateTv;

        public LocalQualityHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            NetQualityInfo info = data.get(position);
            qualityCodeTv.setText("质检单号 :" + info.code);
            projectNameTv.setText(info.project_name);
            organNameTv.setText(info.organName);
            usernameTv.setText("质检人 :" + info.testUser);
            cjdateTv.setText("质检时间 :" + info.cjdata);
            if(info.isCheck) {
                chooseQualityCkb.setChecked(true);
            }else {
                chooseQualityCkb.setChecked(false);
            }
        }
    }
}
