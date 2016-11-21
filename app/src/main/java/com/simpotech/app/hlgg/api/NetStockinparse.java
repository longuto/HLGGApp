package com.simpotech.app.hlgg.api;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.db.dao.StockinConSubDb;
import com.simpotech.app.hlgg.db.dao.StockinContruDb;
import com.simpotech.app.hlgg.db.dao.StockinDb;
import com.simpotech.app.hlgg.entity.StockinConInfo;
import com.simpotech.app.hlgg.entity.net.BaseJsonInfo;
import com.simpotech.app.hlgg.entity.net.NetStockinErrInfo;
import com.simpotech.app.hlgg.entity.net.NetStockinInfo;
import com.simpotech.app.hlgg.entity.submit.SubStockinInfo;
import com.simpotech.app.hlgg.ui.adapter.LocalStockinSubConAdapter;
import com.simpotech.app.hlgg.util.GsonUtils;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by longuto on 2016/11/17.
 * <p>
 * 加载网络,解析入库的网络连接
 */
public class NetStockinparse {

    public static final String URL_STOCKIN = Constant.HOST + Constant.STOCKIN;  //入库访问地址

    private static String TAG = "NetStockinparse";

    public static void getDataFromNet(final LocalStockinSubConAdapter mAdapter, final Context context) {
        String json = getSubStockinInfoJson();

        OkHttpUtils.post()
                .url(URL_STOCKIN)
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
                        BaseJsonInfo<List<NetStockinInfo>> tempS = (BaseJsonInfo<List
                                <NetStockinInfo>>) GsonUtils.fromJson(response, new
                                TypeToken<BaseJsonInfo<List<NetStockinInfo>>>() {
                                }.getType());
                        if (tempS != null) {
                            if (tempS.code.equals("success")) {
                                // 将正确的信息添加至发货单,并清空提交构件的数据库---------------------------------
                                List<NetStockinInfo> result = tempS.result;
                                StockinDb db = new StockinDb();
                                StockinContruDb dbCon = new StockinContruDb();
                                for (NetStockinInfo info : result) {
                                    if (db.addStockin(info)) {
                                        UiUtils.showToast("添加入库单至本地成功");
                                        for (NetStockinInfo.DetailsBean bean : info.details) {
                                            if (!dbCon.addStockinContruction(bean)) {
                                                UiUtils.showToast("构件编号为" + bean.contruction_code
                                                        + "的构件保存失败");
                                            }
                                        }
                                    } else {
                                        UiUtils.showToast("添加入库单至本地失败");
                                    }
                                }

                                new AlertDialog.Builder(context)
                                        .setTitle("提示")
                                        .setMessage("入库成功,是否清空本地数据库")
                                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                new StockinConSubDb().delAllData();
                                            }
                                        })
                                        .create()
                                        .show();

                            } else {
                                BaseJsonInfo<List<NetStockinErrInfo>> tempE = (BaseJsonInfo<List
                                        <NetStockinErrInfo>>) GsonUtils.fromJson(response, new
                                        TypeToken<BaseJsonInfo<List<NetStockinErrInfo>>>() {
                                        }.getType());
                                if (tempE != null) {
                                    UiUtils.showToast(tempE.msg);   //显示出错原因
                                    List<NetStockinErrInfo> errorContrus = tempE.result;
                                    StockinConSubDb db = new StockinConSubDb();
                                    for (NetStockinErrInfo info : errorContrus) {
                                        StockinConInfo infoChange = new StockinConInfo();
                                        infoChange.id = Integer.valueOf(info.id);
                                        if (TextUtils.isEmpty(info.errorMsg)) {
                                            infoChange.isError = 0; //0代表正确
                                        } else {
                                            infoChange.isError = 1; //1代表错误
                                        }
                                        infoChange.message = info.errorMsg;
                                        if (db.upDataByStockinMess(infoChange) <= 0) {
                                            //修改错误构件消息的消息提示
                                            UiUtils.showToast("修改构件编号为" + info.contruction_code +
                                                    "的构件失败");
                                        }

                                        mAdapter.data = db.getAllStockinCon();
                                        mAdapter.notifyDataSetChanged();    //通知适配器数据已改变
                                    }
                                } else {
                                    UiUtils.showToast("解析json数据失败");
                                }
                            }
                        } else {
                            UiUtils.showToast("解析json数据失败");
                        }
                    }
                });

    }

    /**
     * 将入库上传的实体类转成json格式
     *
     * @return json字符串
     */
    private static String getSubStockinInfoJson() {
        SharedManager sp = new SharedManager(SharedManager.PROCESS_CONFIG_NAME);
        String gjrk = sp.getStringFromXml("gjrk");  //构件入库的流程id
        List<StockinConInfo> stockinCons = new StockinConSubDb().getAllStockinCon();
        //获取所有的入库构件的集合
        //转换成提交的数据
        SubStockinInfo info = new SubStockinInfo();
        info.flowId = gjrk;
        info.stockins = new ArrayList<>();
        for (StockinConInfo temp : stockinCons) {
            SubStockinInfo.StockinsBean bean = new SubStockinInfo.StockinsBean();
            bean.cml_code = temp.cml_code;
            bean.contruction_code = temp.code;
            bean.barCode = temp.barcode;
            bean.qty = temp.stock_qty;
            bean.spec = temp.spec;
            bean.product_line = temp.prolineId;
            bean.id = temp.id + "";

            info.stockins.add(bean);
        }
        return GsonUtils.toJson(info);
    }
}
