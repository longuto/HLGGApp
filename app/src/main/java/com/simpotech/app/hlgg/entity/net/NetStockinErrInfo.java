package com.simpotech.app.hlgg.entity.net;

/**
 * Created by longuto on 2016/11/17.
 *
 * 当入库错误时,返回的json数据实体类
 */

public class NetStockinErrInfo {


    /**
     * cml_code : 构件清单
     * contruction_code : 构件编码
     * barCode : 条码
     * spec : 规格
     * qty : 数量
     * product_line : 生产线id
     * errorMsg : 每条的错误信息
     */

    public String cml_code;
    public String contruction_code;
    public String barCode;
    public String spec;
    public String qty;
    public String product_line;
    public String errorMsg;
    public String appId;   //传递时的构件的主键
}
