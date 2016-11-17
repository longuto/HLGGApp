package com.simpotech.app.hlgg.entity.submit;

import java.util.List;

/**
 * Created by longuto on 2016/11/17.
 *
 * 入库提交时的数据实体类
 */

public class SubStockinInfo {

    /**
     * flowId : 流程id
     * stockins : [{"cml_code":"构件清单","contruction_code":"构件编码","barCode":"条码","spec":"规格",
     * "qty":"数量","product_line":"生产线id"}]
     */

    public String flowId;
    public List<StockinsBean> stockins;

    /**
     * cml_code : 构件清单
     * contruction_code : 构件编码
     * barCode : 条码
     * spec : 规格
     * qty : 数量
     * product_line : 生产线id
     */
    public static class StockinsBean {
        public String id;       //构件的主键Id
        public String cml_code;
        public String contruction_code;
        public String barCode;
        public String spec;
        public String qty;
        public String product_line;
    }
}
