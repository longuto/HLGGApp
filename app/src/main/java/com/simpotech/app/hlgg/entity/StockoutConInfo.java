package com.simpotech.app.hlgg.entity;

/**
 * Created by longuto on 2016/11/14.
 *
 * 发货出库每个发货单号下的构件信息
 */

public class StockoutConInfo {
   /* {
        "invoice_code":"发货单号"
        "stock_qty":"出库数量"

        "cml_code": "构件清单号",
        "name": "加工厂",
        "code": "构件号",
        "spec": "规格",
        "barcode": "条形码",
        "qty": "数量",
    }*/
    public int id;  //主键

    public String invoice_code;
    public String stock_qty;

    public String cml_code;
    public String name;
    public String code;
    public String spec;
    public String barcode;
    public String qty;

    public String scannerPeople;    //扫描人
    public String scannerTime;  //扫描时间
    public boolean isChecked;   //列表中是否选中
}
