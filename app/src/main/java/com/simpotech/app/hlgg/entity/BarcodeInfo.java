package com.simpotech.app.hlgg.entity;

/**
 * Created by longuto on 2016/11/7.
 * 扫描红外返回的对象实体bean
 */

public class BarcodeInfo {
    /**
     * cml_code : 构件清单号
     * name : 加工厂
     * code : 构件号
     * spec : 规格
     * barcode : 条形码
     * qty : 数量
     * proj_name : 项目名称
     */
    public String cml_code;
    public String name;
    public String code;
    public String spec;
    public String barcode;
    public String qty;
    public String proj_name;

    public BarcodeInfo() {
    }

    public BarcodeInfo(String cml_code, String name, String code, String spec, String barcode,
                       String qty, String proj_name) {
        this.cml_code = cml_code;
        this.name = name;
        this.code = code;
        this.spec = spec;
        this.barcode = barcode;
        this.qty = qty;
        this.proj_name = proj_name;
    }
}
