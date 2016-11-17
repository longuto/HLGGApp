package com.simpotech.app.hlgg.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.entity.DbProLineInfo;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by longuto on 2016/11/17.
 * 简单的Spinner的适配器
 */

public class SpinnerAdapter extends BaseAdapter {
    List<DbProLineInfo> data;    //适配器数据

    public SpinnerAdapter(List<DbProLineInfo> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DbProLineInfo info = data.get(position);
        HolderView holderView = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout
                    .item_spinner, parent, false);
            holderView = new HolderView();
            holderView.prolineIdTv = (TextView) convertView.findViewById(R.id.tv_prolineId);
            holderView.prolineNameTv = (TextView) convertView.findViewById(R.id.tv_prolineName);
            convertView.setTag(holderView);
        }else {
            holderView = (HolderView) convertView.getTag();
        }
        holderView.prolineIdTv.setText(info.proLineId);
        holderView.prolineNameTv.setText(info.proLineName);
        return convertView;
    }

    class HolderView {
        TextView prolineIdTv;
        TextView prolineNameTv;
    }
}
