package com.simpotech.app.hlgg.api;

/**
 * 配置系统常量的类
 * Created by longuto on 2016/10/27.
 */

public class Constant {

    //客户那边地址
    public static final String HOST = "http://220.178.76.51:8081";

    //主机地址
    //    public static final String HOST = "http://10.110.1.232:8080/ ";

    //王波测试地址
    //    public static final String HOST = "http://10.110.1.16:8080";

    //登录系统,并返回权限
    public static final String LOGIN = "/ec/app/dologin.html";  //在LoginActivity中

    //生产线获取
    public static final String PROLINE = "/ec/app/proLine.html";    //在NetProlineParse

    //流程列表查询
    public static final String PROCESS = "/ec/app/processSearch.html";  //在NetProcessParse

    //构件明细
    public static final String CONTRUCTION = "/ec/app/contruction.html";

    //发货单下载地址
    public static final String INVOICE = "/ec/app/invoiceSearch.html";  //在NetInvoiceParse

    //查询已出库的发货单地址
    public static final String INVOICE_STOCK_OUT = "/ec/app/invoiceJudge.html";  //在NetInvoiceParse

    //出库单地址
    public static final String STOCK_OUT = "/ec/app/stockoutSave.html";     //在NetStockoutParse

    //入库单地址
    public static final String STOCKIN = "/ec/app/stockinSave.html";

}
