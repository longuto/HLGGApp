package com.simpotech.app.hlgg.business;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理首页权限的类
 * Created by longuto on 2016/10/27.
 */

public class PermissionManager {

    private static Map<String, String[]> permissionMap = new HashMap<String, String[]>();

    public static Map<String, String[]> getPermissionMap() {
        return permissionMap;
    }

    static {
        permissionMap.put("0", new String[]{});   //都没有权限
        permissionMap.put("1", new String[]{"入库", "入库管理", "入库查询"});   //入库权限
        permissionMap.put("2", new String[]{"出库", "出库管理", "出库查询"});   //出库权限
        permissionMap.put("3", new String[]{"质检", "质检查询"});   //质检权限
        permissionMap.put("4", new String[]{"入库", "入库管理", "出库", "出库管理", "入库查询", "出库查询"}); //入库,出库权限
        permissionMap.put("5", new String[]{"入库", "入库管理", "质检", "质检查询", "入库查询"}); //入库,质检权限
        permissionMap.put("6", new String[]{"出库", "出库管理", "质检", "质检查询", "出库查询"}); //出库,质检权限
        permissionMap.put("7", new String[]{"入库", "入库管理", "出库", "出库管理", "质检", "质检查询", "入库查询",
                "出库查询", "下载发货单", "发货单管理", "构件查询", "流程设置", "生产线配置"});    //都有权限
    }
}
