package com.simpotech.app.hlgg.entity.net;

import java.util.List;

/**
 * Created by longuto on 2016/11/17.
 *
 * 入库加载网络,成功返回的数据类型
 */

public class NetStockinInfo {


    /**
     * code : 入库单号
     * wo_code : 生产任务单号
     * cml_code : 构件清单编码
     * proj_name : 项目名称
     * organName : 入库工厂
     * productLine : 入库生产线
     * addTime : 创建时间
     * addUserId : 提交人No
     * addUserName : 提交人名称
     * details : [{"stockin_code":"入库单号","contruction_code":"构件编码","cml_code":"构件清单编码",
     * "spec":"规格","qty":"入库数量","barCode":"条码"}]
     */

    public String code;
    public String wo_code;
    public String cml_code;
    public String proj_name;
    public String organName;
    public String productLine;
    public String addTime;
    public String addUserId;
    public String addUserName;
    public List<DetailsBean> details;


    /**
     * stockin_code : 入库单号
     * contruction_code : 构件编码
     * cml_code : 构件清单编码
     * spec : 规格
     * qty : 入库数量
     * barCode : 条码
     */
    public static class DetailsBean {
        public String stockin_code;
        public String contruction_code;
        public String cml_code;
        public String spec;
        public String qty;
        public String barCode;
    }
}
