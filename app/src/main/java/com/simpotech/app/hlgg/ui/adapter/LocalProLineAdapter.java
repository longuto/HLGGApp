package com.simpotech.app.hlgg.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.ProLineDb;
import com.simpotech.app.hlgg.entity.DbProLineInfo;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longuto on 2016/11/1.
 * 本地生产线的适配器
 * 通过观察者模式提供自定义ItemView的点击事件,暴露setOnItemClickListener
 */
public class LocalProLineAdapter extends RecyclerView.Adapter<LocalProLineAdapter
        .LocalProLineHolder> implements View.OnClickListener{

    //是否为Adapter设置条目点击事件
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }


    private final String TAG = "LocalProLineAdapter";

    public List<DbProLineInfo> data;  //本地生产线的集合
    private Context context;

    public LocalProLineAdapter(Context context) {
        this.context = context;
        //从数据库中取出所有生产线的集合
        data = new ProLineDb().getAllProLines();

        /*// 模拟数据
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
        data.add(new DbProLineInfo("b05", "部门5", "p01", "生产线1"));
        data.add(new DbProLineInfo("b05", "部门5", "p02", "生产线2"));
        data.add(new DbProLineInfo("b05", "部门5", "p03", "生产线3"));
        data.add(new DbProLineInfo("b06", "部门6", "p01", "生产线1"));
        data.add(new DbProLineInfo("b06", "部门6", "p02", "生产线2"));
        data.add(new DbProLineInfo("b06", "部门6", "p03", "生产线3"));*/
    }

    @Override
    public LocalProLineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout
                        .item_local_proline_recy,
                parent, false);
        view.setOnClickListener(this);  //添加每个条目的点击事件------item点击事件
        return new LocalProLineHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalProLineHolder holder, int position) {
        holder.setData(position);
        holder.itemView.setTag(position);       //将当前位置保存至itemView---------item点击事件
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    /**HolderView*/
    class LocalProLineHolder extends RecyclerView.ViewHolder {
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

            departmentTv.setText(temp.departmentName);
            prolineTv.setText(temp.proLineName);
            //设置初始状态是否选中
            if(temp.isChecked) {
                chooseCkb.setChecked(true);
            }else {
                chooseCkb.setChecked(false);
            }
        }
    }
}