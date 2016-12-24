package com.simpotech.app.hlgg.entity.net;

import java.util.List;

/**
 * Created by longuto on 2016/12/20.
 *
 * 网络请求成功以后,返回的实体类
 */

public class NetQualityInfo {


    /**
     * barCode : 条码
     * cjdata : 质检时间
     * cml_code : 构件清单编码
     * code : 质检编号
     * contruction_code : 构件编码
     * detail : [{"itemsFlag":"检测类型","quality_code":"质检编号","remark":"检测说明","testResult":"检测结果"}]
     * organName : 加工厂
     * project_name : 项目名称
     * qty : 构件数量
     * spec : 构件规格
     * testUser : 质检人
     * userId : 用户id
     */
    public String barCode;
    public String cml_code;
    public String contruction_code;
    public String organName;
    public String qty;
    public String spec;

    public String code;
    public String project_name;
    public String testUser;
    public String cjdata;
    public String userId;

    public boolean isCheck; //是否选中
    public List<DetailBean> detail;

    /**
     * itemsFlag : 检测类型
     * remark : 检测说明
     * testResult : 检测结果
     */
    public static class DetailBean {
        public String itemsFlag;
        public String remark;
        public String testResult;
        public String pic1; //图片1的地址
        public String pic2; //图片2的地址
        public String pic3; //图片3的地址
        public String pic4; //图片4的地址
    }
}
