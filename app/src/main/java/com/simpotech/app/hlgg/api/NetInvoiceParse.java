package com.simpotech.app.hlgg.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.db.dao.InvoiceConStockoutDb;
import com.simpotech.app.hlgg.db.dao.InvoiceContructionDb;
import com.simpotech.app.hlgg.db.dao.InvoiceDb;
import com.simpotech.app.hlgg.entity.net.BaseJsonInfo;
import com.simpotech.app.hlgg.entity.net.NetInvoiceInfo;
import com.simpotech.app.hlgg.ui.adapter.LocalInvoiceAdapter;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by longuto on 2016/11/8.
 * 解析发货单数据
 */
public class NetInvoiceParse {
    private static final String TAG = "NetInvoiceParse";

    public static final String URL_INVOICE = Constant.HOST + Constant.INVOICE;  //发货单下载地址
    public static final String URL_STOCK_OUT = Constant.HOST + Constant.INVOICE_STOCK_OUT;    //已出库的发货单的地址

//    public static final String URL_INVOICE1 = "http://10.110.1.98:8080/invoice1.json";    //测试地址
//    public static final String URL_INVOICE2 = "http://10.110.1.98:8080/invoice2.json";    //测试地址
//    public static final String URL_INVOICE3 = "http://10.110.1.98:8080/invoice3.json";    //测试地址
//    public static final String URL_INVOICE4 = "http://10.110.1.98:8080/invoice4.json";    //测试地址
//    public static List<String> URL_INVOICES = new ArrayList<>();
//    static {
//        URL_INVOICES.add(URL_INVOICE1);
//        URL_INVOICES.add(URL_INVOICE2);
//        URL_INVOICES.add(URL_INVOICE3);
//        URL_INVOICES.add(URL_INVOICE4);
//    }
//    public static String getUrlInvoice() {
//        Random r = new Random();
//        int i = r.nextInt(4);
//        return URL_INVOICES.get(i);
//    }

//    public static final String URL_STOCK_OUT = "http://10.110.1.98:8080/invoice_stock_out.json";    //测试地址

    /** 根据发货单号,下载发货单信息至本地数据库*/
    public static void getDataByInvoice(String invoice) {
        OkHttpUtils.get()
                .url(URL_INVOICE)
                .addParams("code", invoice)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i(TAG, "网络加载失败!");
                        UiUtils.showToast("网络访问失败!");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i(TAG, "网络加载成功!");
                        Gson gson = new Gson();
                        BaseJsonInfo<NetInvoiceInfo> temp = gson.fromJson(response, new
                                TypeToken<BaseJsonInfo<NetInvoiceInfo>>() {
                                }.getType());
                        if (temp != null) {
                            if (temp.code.equals("success")) {
                                //将数据加载至数据库
                                NetInvoiceInfo result = temp.result;
                                InvoiceDb db = new InvoiceDb();
                                if (db.addInvoice(result)) {
                                    UiUtils.showToast("发货单保存至本地成功");
                                    List<NetInvoiceInfo.DetailsBean> details = result.details;
                                    InvoiceContructionDb dbCon = new InvoiceContructionDb();
                                    for (NetInvoiceInfo.DetailsBean bean : details) {
                                        if(!dbCon.addInvoiceContruction(bean)) {
                                            UiUtils.showToast("构件单号(" + bean.contruction_code + ")保存至本地失败.");
                                        }
                                    }
                                } else {
                                    UiUtils.showToast("发货单保存至本地失败,请不要输入重复的发货单号.");
                                }
                            } else {
                                UiUtils.showToast(temp.msg);    //显示出错原因
                            }
                        } else {
                            UiUtils.showToast("解析json数据失败,请输入正确的发货单号");
                        }
                    }
                });
    }

    /** 删除已出库的发货单 */
    public static void delStockOutInvoiceCode(String spliceCode, final LocalInvoiceAdapter mAdapter) {
        OkHttpUtils.post()
                .url(URL_STOCK_OUT)
                .addParams("code", spliceCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i(TAG, "网络加载失败!");
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i(TAG, "网络加载成功");
                        String[] codes = response.split("\\,");
                        int len = codes.length;
                        InvoiceDb db = new InvoiceDb();
                        InvoiceContructionDb dbCon = new InvoiceContructionDb();
                        InvoiceConStockoutDb dbStock = new InvoiceConStockoutDb();
                        for (int i = 0; i < len; i++) {
                            db.delInvoice(codes[i]);
                            dbCon.delInvoiceConByInvoiceCode(codes[i]);
                            dbStock.delInvoiceConByInvoiceCode(codes[i]);
                        }
                        mAdapter.data = db.getAllInvoices();
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }
}
