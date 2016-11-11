package com.simpotech.app.hlgg.entity.net;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by longuto on 2016/11/8.
 * 发货单的网络实体类
 */
public class NetInvoiceInfo {
    /**
     * code : 发货单号
     * wo_code : 生产任务单号
     * cml_code : 构件清单编码
     * proj_name : 项目名称
     * organ_id : 发货部门
     * storage_code : 发货仓库
     * organName : 加工厂
     * saleName : 营销员
     * cjdate : 创建时间
     * addUserId : 下载人
     * addUserName : 下载人名称
     * details : [{"invoice_code":"发货单号","contruction_code":"构件编码","spec":"规格","qty":"发货件数","tonnage":"发货重量","barCode":"条码"}]
     */
    public String code;
    public String wo_code;
    public String cml_code;
    public String proj_name;
    public String organ_id;
    public String storage_code;
    public String organName;
    public String saleName;
    public String cjdate;
    public String addUserId;
    public String addUserName;
    public boolean isChecked;   //是否选中,默认未选中

    public List<DetailsBean> details;

    /**
     * invoice_code : 发货单号
     * contruction_code : 构件编码
     * spec : 规格
     * qty : 发货件数
     * tonnage : 发货重量
     * barCode : 条码
     * 实现序列化,用于意图传递
     */
    public static class DetailsBean implements Serializable {
        public String invoice_code;
        public String contruction_code;
        public String spec;
        public String qty;
        public String tonnage;
        public String barCode;
    }
}
