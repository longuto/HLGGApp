package com.simpotech.app.hlgg.entity.net;

import java.util.List;

/**
 * Created by longuto on 2016/11/4.
 * 网络流程模块的实体类
 */

public class NetProcessInfo {


    /**
     * businessName : 流程模块
     * businessNo : 业务编号
     * flowList : [{"businessName":"流程模块","businessNo":"业务编号","flowId":"流程id","flowName":"流程名称",
     * "id":"id"}]
     */
    public String businessName;
    public String businessNo;
    public List<FlowListBean> flowList;

    /**
     * businessName : 流程模块
     * businessNo : 业务编号
     * flowId : 流程id
     * flowName : 流程名称
     * id : id
     * isDef : 是否是默认的选项
     */
    public static class FlowListBean {
        public String businessName;
        public String businessNo;
        public String flowId;
        public String flowName;
        public String id;

        public boolean isDef;  //是否是默认的选项
    }
}
