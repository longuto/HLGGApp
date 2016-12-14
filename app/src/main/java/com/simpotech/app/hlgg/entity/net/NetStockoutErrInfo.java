package com.simpotech.app.hlgg.entity.net;

/**
 * Created by longuto on 2016/12/10.
 *
 * 出库错误,返回对应的构建信息
 */

public class NetStockoutErrInfo {


    /**
     * appId : 构件清单id
     * cml_code : 构件清单
     * contruction_code : 构件编码
     * barCode : 条码
     * qty : 数量
     * errorMsg : 每条的错误信息
     */

    public String appId;
    public String cml_code;
    public String contruction_code;
    public String barCode;
    public String qty;
    public String errorMsg;
}
