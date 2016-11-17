package com.simpotech.app.hlgg.entity.net;

import java.util.List;

/**
 * Created by longuto on 2016/11/14.
 *
 * 出库单网络访问接口返回的数据库类型
 */

public class NetStockoutInfo {


    /**
     * code : 出库单号
     * invoice_code : 发货单号
     * proj_name : 项目名称
     * organ_name : 加工厂名
     * saleName : 营销员
     * addTime : 创建时间
     * addUserId : 提交人
     * addUserName : 提交人名称
     * details : [{"stockout_code":"出库单号","contruction_code":"构件编码","materialName":"材质",
     * "length":"长度","single":"单重","qty":"出库件数","tonnage":"出库重量","invoice_qty":"发货件数",
     * "invoice_tonnage":"发货重量","barCode":"条码"}]
     */
    public String code;
    public String invoice_code;
    public String proj_name;
    public String organ_name;
    public String saleName;
    public String addTime;
    public String addUserId;
    public String addUserName;
    public List<DetailsBean> details;

    public boolean isChecked; //当前条目是否选中

    /**
     * stockout_code : 出库单号
     * contruction_code : 构件编码
     * materialName : 材质
     * length : 长度
     * single : 单重
     * qty : 出库件数
     * tonnage : 出库重量
     * invoice_qty : 发货件数
     * invoice_tonnage : 发货重量
     * barCode : 条码
     */
    public static class DetailsBean {
        public String stockout_code;
        public String contruction_code;
        public String materialName;
        public String length;
        public String single;
        public String qty;
        public String tonnage;
        public String invoice_qty;
        public String invoice_tonnage;
        public String barCode;
    }
}
