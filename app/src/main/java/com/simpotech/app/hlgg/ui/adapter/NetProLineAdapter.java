package com.simpotech.app.hlgg.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.simpotech.app.hlgg.entity.net.NetProLineInfo;

import java.util.List;

/**
 * Created by longuto on 2016/11/1.
 * 网络数据的生产线
 */

public class NetProLineAdapter extends RecyclerView.Adapter<NetProLineAdapter.NetProlineHolder> {

    List<NetProLineInfo> data;
    List<NetProLineInfo> addData;

    public NetProLineAdapter() {

    }

    @Override
    public NetProlineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(NetProlineHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**自定义ViewHolder*/
    class NetProlineHolder extends RecyclerView.ViewHolder {

        public NetProlineHolder(View itemView) {
            super(itemView);
        }
    }
}
