package com.simpotech.app.hlgg.business;

import com.simpotech.app.hlgg.entity.StockoutConInfo;
import com.simpotech.app.hlgg.entity.net.NetInvoiceInfo;
import com.simpotech.app.hlgg.entity.submit.SubStockoutInfo;
import com.simpotech.app.hlgg.util.GsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longuto on 2016/11/14.
 * <p>
 * 将需要出库的发货单信息转换成json格式
 */

public class InvoiceStockoutManager {

    public static String InvoiceStockoutToJson(NetInvoiceInfo netInvoiceInfo,
                                               List<StockoutConInfo> stockoutInfos) {
        SubStockoutInfo info = new SubStockoutInfo();
        info.code = netInvoiceInfo.code;
        info.wo_code = netInvoiceInfo.wo_code;
        info.cml_code = netInvoiceInfo.cml_code;

        info.details = new ArrayList<>();
        for (StockoutConInfo temp : stockoutInfos) {
            SubStockoutInfo.DetailsBean bean = new SubStockoutInfo.DetailsBean();
            bean.invoice_code = temp.invoice_code;
            bean.contruction_code = temp.code;
            bean.qty = temp.stock_qty;
            bean.barCode = temp.barcode;

            info.details.add(bean);
        }

        return GsonUtils.toJson(info);
    }

}
