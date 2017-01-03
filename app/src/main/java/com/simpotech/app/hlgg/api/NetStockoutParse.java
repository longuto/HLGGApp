package com.simpotech.app.hlgg.api;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.db.dao.InvoiceConStockoutDb;
import com.simpotech.app.hlgg.db.dao.StockoutContruDb;
import com.simpotech.app.hlgg.db.dao.StockoutDb;
import com.simpotech.app.hlgg.entity.StockoutConInfo;
import com.simpotech.app.hlgg.entity.net.BaseJsonInfo;
import com.simpotech.app.hlgg.entity.net.ChooseInfo;
import com.simpotech.app.hlgg.entity.net.NetInvoiceInfo;
import com.simpotech.app.hlgg.entity.net.NetStockoutErrInfo;
import com.simpotech.app.hlgg.entity.net.NetStockoutInfo;
import com.simpotech.app.hlgg.entity.submit.SubStockoutInfo;
import com.simpotech.app.hlgg.ui.adapter.LocalInvoiceStockDetailAdapter;
import com.simpotech.app.hlgg.util.GsonUtils;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by longuto on 2016/11/14.
 * <p>
 * 加载网络,解析出库信息
 */
public class NetStockoutParse {

    private static final String TAG = "NetStockoutParse";

    public static final String URL_STOCKOUT = Constant.HOST + Constant.STOCK_OUT;   //出库访问地址

    public static List<StockoutConInfo> stockoutConInfos;

    //    public static final String URL_STOCKOUT1 = "http://10.110.1.98:8080/stockoutInfo1
    // .json";//测试地址
    //    public static final String URL_STOCKOUT2 = "http://10.110.1.98:8080/stockoutInfo2
    // .json";//测试地址
    //    public static final String URL_STOCKOUT3 = "http://10.110.1.98:8080/stockoutInfo3
    // .json";//测试地址
    //    public static final String URL_STOCKOUT4 = "http://10.110.1.98:8080/stockoutInfo4
    // .json";//测试地址
    //    public static List<String> URL_STOCKOUTS = new ArrayList<>();
    //
    //    static {
    //        URL_STOCKOUTS.add(URL_STOCKOUT1);
    //        URL_STOCKOUTS.add(URL_STOCKOUT2);
    //        URL_STOCKOUTS.add(URL_STOCKOUT3);
    //        URL_STOCKOUTS.add(URL_STOCKOUT4);
    //    }
    //
    //    public static String getUrlStockout() {
    //        Random r = new Random();
    //        int i = r.nextInt(4);
    //        return URL_STOCKOUTS.get(i);
    //    }


    public static void getDataFromNet(final NetInvoiceInfo netInvoiceInfo, final
    LocalInvoiceStockDetailAdapter adapter, final Context context) {
        //将传入的实体类转化成json
        String json = invoiceStockoutToJson(netInvoiceInfo);
        LogUtils.i(TAG, json);

        OkHttpUtils.post()
                .url(URL_STOCKOUT)
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
                        ChooseInfo temp = (ChooseInfo) GsonUtils.fromJson(response, ChooseInfo
                                .class);

                        if (temp.code.equals("success")) {
                            BaseJsonInfo<NetStockoutInfo> tempS = (BaseJsonInfo<NetStockoutInfo>)
                                    GsonUtils.fromJson(response, new
                                            TypeToken<BaseJsonInfo<NetStockoutInfo>>() {
                                            }.getType());
                            NetStockoutInfo info = tempS.result;   //出库单数据
                            StockoutDb db = new StockoutDb();
                            final StockoutContruDb dbCon = new StockoutContruDb();
                            if (db.addStockout(info)) {
                                UiUtils.showToast("出库单提交成功");
                                for (NetStockoutInfo.DetailsBean bean : info.stockoutDetail) {
                                    if (!dbCon.addStockoutContruction(bean)) {
                                        UiUtils.showToast(bean.contruction_code + "的构件添加失败");
                                    }
                                }

                                new AlertDialog.Builder(context)
                                        .setTitle("提示")
                                        .setMessage("出库成功,是否清空本地数据库")
                                        .setCancelable(false)
                                        .setPositiveButton("取消", new DialogInterface
                                                .OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //取消错误消息的显示
                                                for (StockoutConInfo bean : adapter.data) {
                                                    bean.isError = 0;
                                                    bean.message = "";
                                                }
                                                adapter.notifyDataSetChanged();
                                            }
                                        })
                                        .setNegativeButton("确定", new DialogInterface
                                                .OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                InvoiceConStockoutDb dbConS = new
                                                        InvoiceConStockoutDb();
                                                dbConS.delInvoiceConByInvoiceCode(netInvoiceInfo
                                                        .code);
                                                adapter.data = dbConS.getInvoiceConByInvoiceCode
                                                        (netInvoiceInfo.code);
                                                adapter.notifyDataSetChanged();
                                            }
                                        })
                                        .create()
                                        .show();
                            } else {
                                UiUtils.showToast("出库单提交失败,系统不该返回重复出库单");
                            }

                        } else {
                            BaseJsonInfo<List<NetStockoutErrInfo>> tempE =
                                    (BaseJsonInfo<List<NetStockoutErrInfo>>) GsonUtils.fromJson
                                            (response, new
                                                    TypeToken<BaseJsonInfo<List<NetStockoutErrInfo>>>() {
                                                    }.getType());
                            UiUtils.showToast(temp.msg);    //显示出错原因

                            List<NetStockoutErrInfo> errorContrus = tempE.result;
                            InvoiceConStockoutDb db = new InvoiceConStockoutDb();
                            for (NetStockoutErrInfo info : errorContrus) {
                                StockoutConInfo infoChange = new StockoutConInfo();
                                infoChange.id = Integer.valueOf(info.appId);
                                if (TextUtils.isEmpty(info.errorMsg)) {
                                    infoChange.isError = 0; //代表正确
                                } else {
                                    infoChange.isError = 1; //代表错误
                                }
                                infoChange.message = info.errorMsg;
                                if (db.upDataByStockoutMess(infoChange) <= 0) {
                                    UiUtils.showToast("修改构件编号为" + info.contruction_code +
                                            "的构件失败");
                                }
                                adapter.data = db.getInvoiceConByInvoiceCode(netInvoiceInfo.code);
                                adapter.notifyDataSetChanged(); //通知适配器数据已经改变
                            }
                        }
                    }
                });
    }

    /**
     * Created by longuto on 2016/11/14.
     * <p>
     * 将需要出库的发货单信息转换成json格式
     */
    public static String invoiceStockoutToJson(NetInvoiceInfo netInvoiceInfo) {
        stockoutConInfos = new InvoiceConStockoutDb()
                .getInvoiceConByInvoiceCode(netInvoiceInfo.code);
        SharedManager sp = new SharedManager(SharedManager.PROCESS_CONFIG_NAME); //xml存储的配置
        SharedManager spP = new SharedManager();
        SubStockoutInfo info = new SubStockoutInfo();
        info.invoice_code = netInvoiceInfo.code;
        info.wo_code = netInvoiceInfo.wo_code;
        info.cml_code = netInvoiceInfo.cml_code;
        info.flowId = sp.getStringFromXml(SharedManager.GJCK);
        info.userId = spP.getStringFromXml(SharedManager.USERID);

        info.stockoutDetail = new ArrayList<>();
        for (StockoutConInfo temp : stockoutConInfos) {
            SubStockoutInfo.DetailsBean bean = new SubStockoutInfo.DetailsBean();

            bean.appId = temp.id + "";
            bean.invoice_code = temp.invoice_code;
            bean.contruction_code = temp.code;
            bean.qty = temp.stock_qty;
            bean.barCode = temp.barcode;

            info.stockoutDetail.add(bean);
        }

        return GsonUtils.toJson(info);
    }
}
