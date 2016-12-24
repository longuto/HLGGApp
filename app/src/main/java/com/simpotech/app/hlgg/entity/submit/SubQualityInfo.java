package com.simpotech.app.hlgg.entity.submit;

import java.util.List;

/**
 * Created by longuto on 2016/12/20.
 *
 * 质检录入时,提交的实体类
 */

public class SubQualityInfo {


    /**
     * userId : 用户id
     * cml_code : 构件清单编码
     * contruction_code : 构件编码
     * barCode : 条码
     * spec : 规格
     * qty : 数量
     * detail : [{"itemsFlag":"检测类型1:外观检测2:尺寸检测3:焊缝检测","testResult":"检测结果1:合格 2:不合格",
     * "remark":"检测说明","pics":[]}]
     */

    public String userId;
    public String cml_code;
    public String contruction_code;
    public String barCode;
    public String spec;
    public String qty;
    public List<DetailBean> detail;
    /**
     * itemsFlag : 检测类型1:外观检测2:尺寸检测3:焊缝检测
     * testResult : 检测结果1:合格 2:不合格
     * remark : 检测说明
     */
    public static class DetailBean {
        public String itemsFlag;
        public String testResult;
        public String remark;
    }
}
