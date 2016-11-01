package com.simpotech.app.hlgg.entity.net;

import java.util.List;

/**
 * Created by longuto on 2016/11/1.
 */

public class NetProLineInfo {

    /**
     * id : 部门id
     * name : 部门名称
     * organList : [{"id":"生产线id","name":"生产线","pid":"部门id"}]
     */

    public String id;
    public String name;
    public List<OrganListBean> organList;

    /**
     * id : 生产线id
     * name : 生产线
     * pid : 部门id
     */
    public static class OrganListBean {
        public String id;
        public String name;
        public String pid;
    }
}
