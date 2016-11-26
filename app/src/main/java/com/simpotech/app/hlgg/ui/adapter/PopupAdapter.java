package com.simpotech.app.hlgg.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.InvoiceDb;
import com.simpotech.app.hlgg.entity.net.NetInvoiceInfo;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.codes;

/**
 * Created by longuto on 2016/11/26.
 *
 * 发货单数据库弹出框的适配器
 */

public class PopupAdapter extends BaseAdapter {

    public List<String> data;      //适配器数据

    public PopupAdapter() {
        InvoiceDb db = new InvoiceDb();
        List<NetInvoiceInfo> netInvoiceInfos = db.getAllInvoices();
        data = new ArrayList<String>(); //发货单集合
        for (NetInvoiceInfo info : netInvoiceInfos) {
            data.add(info.code);
        }
    }

    /**暴露一个公共方法,获取当前item的数据*/
    public String getItemCode(int position) {
        return data.get(position);
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
        String code = data.get(position);
        HolderView holderView = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(UiUtils.getContext()).inflate(R.layout.item_pop_lv,
                    parent, false);
            holderView = new HolderView();
            holderView.codeTv = (TextView) convertView.findViewById(R.id.tv_invoiceNo);
            convertView.setTag(holderView);
        }else {
            holderView = (HolderView) convertView.getTag();
        }
        holderView.codeTv.setText(code);
        return convertView;
    }

    class HolderView {
        TextView codeTv;
    }
}
