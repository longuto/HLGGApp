package com.simpotech.app.hlgg.business;

import com.simpotech.app.hlgg.entity.StockConInfo;
import com.simpotech.app.hlgg.entity.StockinConInfo;
import com.simpotech.app.hlgg.entity.StockoutConInfo;
import com.simpotech.app.hlgg.entity.submit.SubStockoutInfo;
import com.simpotech.app.hlgg.util.LogUtils;

/**
 * Created by longuto on 2016/11/14.
 * 解析扫描二维码得到的信息
 */

public class ParseScanner {

    private static String TAG = "ParseScanner";

    /**
     * 根据扫描的内容获取发货的构件信息
     *
     * @param content 扫描获取的内容
     * @return 解析成功返回构件清单信息, 否则返回null
     */
    public static StockoutConInfo scan2stockoutContru(String content) {
        StockConInfo stockConInfo = scan2stockContru(content);
        if(stockConInfo != null) {
            StockoutConInfo info = new StockoutConInfo();
            info.cml_code = stockConInfo.cml_code;
            info.name = stockConInfo.name;
            info.code = stockConInfo.code;
            info.spec = stockConInfo.spec;
            info.barcode = stockConInfo.barcode;
            info.qty = stockConInfo.qty;
            return info;
        }else {
            return null;
        }
    }

    /**
     * 根据扫描的内容获取入库的构件信息
     *
     * @param content 扫描获取的内容
     * @return 解析成功返回构件清单信息, 否则返回null
     */
    public static StockinConInfo scan2stockinContru(String content) {
        StockConInfo stockConInfo = scan2stockContru(content);
        if(stockConInfo != null) {
            StockinConInfo info = new StockinConInfo();
            info.cml_code = stockConInfo.cml_code;
            info.name = stockConInfo.name;
            info.code = stockConInfo.code;
            info.spec = stockConInfo.spec;
            info.barcode = stockConInfo.barcode;
            info.qty = stockConInfo.qty;
            return info;
        }else {
            return null;
        }
    }

    /**
     * 根据扫描的内容获取入库的构件信息
     *
     * @param content 扫描获取的内容
     * @return 解析成功返回构件清单信息, 否则返回null
     */
    public static StockConInfo scan2stockContru(String content) {
        content = content.replace("合同号：", "QUIT")
                .replace("加工厂：", "QUIT")
                .replace("构件号：", "QUIT")
                .replace("规格：", "QUIT")
                .replace("条码号：", "QUIT")
                .replace("数量：", "QUIT").trim();
        LogUtils.i(TAG, "替换后的内容:" + content);
        String[] strs = content.split("QUIT");
        LogUtils.i(TAG, "剪切以后的长度:" + strs.length);

        StockConInfo info;
        if (strs.length == 7) {
            info = new StockConInfo();
            info.cml_code = strs[1];
            info.name = strs[2];
            info.code = strs[3];
            info.spec = strs[4];
            info.barcode = strs[5];
            info.qty = strs[6];
        } else {
            return null;
        }
        return info;
    }

    /**
     * StockConInfo转成StockinConInfo
     * @param temp
     * @return
     */
    public static StockinConInfo stockCon2StockinInfo(StockConInfo temp) {
        StockinConInfo info = new StockinConInfo();
        info.cml_code = temp.cml_code;
        info.name = temp.name;
        info.code = temp.code;
        info.spec = temp.spec;
        info.barcode = temp.barcode;
        info.qty = temp.qty;
        return info;
    }

    /**
     * StockConInfo转成StockoutConInfo
     * @param temp
     * @return
     */
    public static StockoutConInfo stockCon2StockoutInfo(StockConInfo temp) {
        StockoutConInfo info = new StockoutConInfo();
        info.cml_code = temp.cml_code;
        info.name = temp.name;
        info.code = temp.code;
        info.spec = temp.spec;
        info.barcode = temp.barcode;
        info.qty = temp.qty;
        return info;
    }
}
