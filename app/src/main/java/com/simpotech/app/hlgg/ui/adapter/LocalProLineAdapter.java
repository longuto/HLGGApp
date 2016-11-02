package com.simpotech.app.hlgg.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.entity.DbProLineInfo;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longuto on 2016/11/1.
 * 本地生产线的适配器
 */
public class LocalProLineAdapter extends RecyclerView.Adapter<LocalProLineAdapter
        .LocalProLineHolder> {
    private final String TAG = "LocalProLineAdapter";

    public List<DbProLineInfo> data;  //本地生产线的集合

    public List<DbProLineInfo> delData;   //删除数据的集合

    public LocalProLineAdapter() {
        //从数据库中取出所有生产线的集合
//        data = new ProLineDb().getAllProLines();
//        delData = new ArrayList<>();

        // 模拟数据
        data = new ArrayList<DbProLineInfo>();
        data.add(new DbProLineInfo("b01", "部门1", "p01", "生产线1"));
        data.add(new DbProLineInfo("b01", "部门1", "p02", "生产线2"));
        data.add(new DbProLineInfo("b01", "部门1", "p03", "生产线3"));
        data.add(new DbProLineInfo("b02", "部门2", "p01", "生产线1"));
        data.add(new DbProLineInfo("b02", "部门2", "p02", "生产线2"));
        data.add(new DbProLineInfo("b02", "部门2", "p03", "生产线3"));
        data.add(new DbProLineInfo("b03", "部门3", "p01", "生产线1"));
        data.add(new DbProLineInfo("b03", "部门3", "p02", "生产线2"));
        data.add(new DbProLineInfo("b03", "部门3", "p03", "生产线3"));
        data.add(new DbProLineInfo("b04", "部门4", "p01", "生产线1"));
        data.add(new DbProLineInfo("b04", "部门4", "p02", "生产线2"));
        data.add(new DbProLineInfo("b04", "部门4", "p03", "生产线3"));
        delData = new ArrayList<DbProLineInfo>();
    }

    @Override
    public LocalProLineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout
                .item_local_proline_recy,
                parent, false);
        return new LocalProLineHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalProLineHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * HolderView
     */
    class LocalProLineHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_id)
        TextView idTv;
        @BindView(R.id.tv_department)
        TextView departmentTv;
        @BindView(R.id.tv_proline)
        TextView prolineTv;
        @BindView(R.id.ckb_choose)
        CheckBox chooseCkb;

        public LocalProLineHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(final int position) {
            final DbProLineInfo temp = data.get(position);
            idTv.setText(temp.id + "");
            departmentTv.setText(temp.departmentName);
            prolineTv.setText(temp.proLineName);
            //设置为未选中状态.因为有时候刷新数据时,CheckBox会有自动选中的情况
            chooseCkb.setChecked(false);
            //设置按钮选择事件
            chooseCkb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        LogUtils.i(TAG, "add : " + temp.departmentName + " + " + temp.proLineName);
                        delData.add(temp);
                    }else {
                        LogUtils.i(TAG, "remove : " + temp.departmentName + " + " + temp.proLineName);
                        delData.remove(temp);
                    }
                }
            });
        }
    }
}