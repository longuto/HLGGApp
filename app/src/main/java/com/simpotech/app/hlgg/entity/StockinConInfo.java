package com.simpotech.app.hlgg.entity;

/**
 * Created by longuto on 2016/11/16.
 *
 * 扫描入库时的构件信息
 */

public class StockinConInfo {
   /* {
        "stock_qty":"入库数量"

        "cml_code": "构件清单号",
        "name": "加工厂",
        "code": "构件号",
        "spec": "规格",
        "barcode": "条形码",
        "qty": "数量",
    }*/
    public int id;  //主键
    public String stock_qty;    //入库数量

    public String cml_code;
    public String name;
    public String code;
    public String spec;
    public String barcode;
    public String qty;

    public String prolineId; //生产线Id
    public String scannerPeople;    //扫描人
    public String scannerTime;  //扫描时间
    public int isError; //是否错误:0代表正确,1代表错误
    public String message;  //错误信息

    public boolean isChecked;   //列表中是否选中
}
