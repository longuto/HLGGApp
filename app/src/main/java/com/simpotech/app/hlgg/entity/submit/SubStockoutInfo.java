package com.simpotech.app.hlgg.entity.submit;

import java.util.List;

/**
 * Created by longuto on 2016/11/14.
 * 发货单提交的信息,json转字符串(出库提交)
 */

public class SubStockoutInfo {

    /**
     * code : 发货单号
     * wo_code : 生产任务单号
     * cml_code : 构件清单编码
     * details : [{"invoice_code":"发货单号","contruction_code":"构件编码","qty":"出库件数","barCode":"条码"}]
     */
    public String code;
    public String wo_code;
    public String cml_code;
    public String flowId;   //出库流程id
    public String userId;   //用户信息
    public List<DetailsBean> details;

    /**
     * invoice_code : 发货单号
     * contruction_code : 构件编码
     * qty : 出库件数
     * barCode : 条码
     */
    public static class DetailsBean {
        public String invoice_code;
        public String contruction_code;
        public String qty;
        public String barCode;
    }
}
