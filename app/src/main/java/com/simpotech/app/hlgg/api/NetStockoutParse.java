package com.simpotech.app.hlgg.api;

import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.business.InvoiceStockoutManager;
import com.simpotech.app.hlgg.db.dao.StockoutContruDb;
import com.simpotech.app.hlgg.db.dao.StockoutDb;
import com.simpotech.app.hlgg.entity.StockoutConInfo;
import com.simpotech.app.hlgg.entity.net.BaseJsonInfo;
import com.simpotech.app.hlgg.entity.net.NetInvoiceInfo;
import com.simpotech.app.hlgg.entity.net.NetStockoutInfo;
import com.simpotech.app.hlgg.util.GsonUtils;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;

import static com.simpotech.app.hlgg.util.GsonUtils.fromJson;

/**
 * Created by longuto on 2016/11/14.
 * <p>
 * 加载网络,解析出库信息
 */
public class NetStockoutParse {

    private static final String TAG = "NetStockoutParse";

//    public static final String URL_STOCKOUT = Constant.HOST + Constant.STOCK_OUT;
    public static final String URL_STOCKOUT1 = "http://10.110.1.98:8080/stockoutInfo1.json";   //测试地址
    public static final String URL_STOCKOUT2 = "http://10.110.1.98:8080/stockoutInfo2.json";   //测试地址
    public static final String URL_STOCKOUT3 = "http://10.110.1.98:8080/stockoutInfo3.json";   //测试地址
    public static final String URL_STOCKOUT4 = "http://10.110.1.98:8080/stockoutInfo4.json";   //测试地址
    public static List<String> URL_STOCKOUTS = new ArrayList<>();
    static{
        URL_STOCKOUTS.add(URL_STOCKOUT1);
        URL_STOCKOUTS.add(URL_STOCKOUT2);
        URL_STOCKOUTS.add(URL_STOCKOUT3);
        URL_STOCKOUTS.add(URL_STOCKOUT4);
    }
    public static String getUrlStockout() {
        Random r = new Random();
        int i = r.nextInt(4);
        return URL_STOCKOUTS.get(i);
    }


    public static void getDataFromNet(NetInvoiceInfo netInvoiceInfo, List<StockoutConInfo>
            stockoutConInfos) {
        //将传入的实体类转化成json
        String json = InvoiceStockoutManager.InvoiceStockoutToJson(netInvoiceInfo,
                stockoutConInfos);
        LogUtils.i(TAG, json);

        OkHttpUtils.post()
                .url(getUrlStockout())
                .addParams("json", json)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i(TAG, "网络加载失败");
                        UiUtils.showToast("网络加载失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i(TAG, "网络加载成功");
                        BaseJsonInfo<NetStockoutInfo> temp = (BaseJsonInfo<NetStockoutInfo>)
                                GsonUtils.fromJson(response, new
                                        TypeToken<BaseJsonInfo<NetStockoutInfo>>() {
                                        }.getType());
                        if (temp != null) {
                            if (temp.code.equals("success")) {
                                NetStockoutInfo info = temp.result;   //出库单数据

                                StockoutDb db = new StockoutDb();
                                StockoutContruDb dbCon = new StockoutContruDb();
                                if (db.addStockout(info)) {
                                    for (NetStockoutInfo.DetailsBean bean : info.details) {
                                        UiUtils.showToast("出库单下载至本地成功!");
                                        if (!dbCon.addStockoutContruction(bean)) {
                                            UiUtils.showToast(bean.contruction_code + "的构件添加失败");
                                        }
                                    }
                                } else {
                                    UiUtils.showToast("出库单保存至本地失败,请不要重复下载相同的出库单");
                                }
                            } else {
                                UiUtils.showToast(temp.msg);
                            }
                        } else {
                            UiUtils.showToast("解析Json数据失败");
                        }

                    }
                });
    }
}
