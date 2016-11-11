package com.simpotech.app.hlgg.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by longuto on 2016/11/10.
 * 出库的构件信息适配器
 */

public class LocalStockoutAdapter extends RecyclerView.Adapter<LocalStockoutAdapter.LocalStockoutHolder> {


    @Override
    public LocalStockoutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(LocalStockoutHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class LocalStockoutHolder extends RecyclerView.ViewHolder {

        public LocalStockoutHolder(View itemView) {
            super(itemView);
        }
    }
}
