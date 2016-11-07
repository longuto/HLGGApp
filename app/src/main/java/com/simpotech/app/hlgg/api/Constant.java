package com.simpotech.app.hlgg.api;

/**
 * 配置系统常量的类
 * Created by longuto on 2016/10/27.
 */

public class Constant {

    //主机地址
    public static final String HOST = "http://127.0.0.1:8080/HLGG";

    //登录系统,并返回权限
    public static final String LOGIN = "/ec/app/doLogin.html";  //在LoginActivity中

    //生产线获取
    public static final String PROLINE = "/ec/app/proLine.html";    //在NetProlineParse

    //流程列表查询
    public static final String PROCESS = "/ec/app/processSearch.html";  //在NetProcessParse

    //构件明细
    public static final String CONTRUCTION = "/ec/app/contruction.html";
}
