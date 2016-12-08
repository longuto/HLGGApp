package com.simpotech.app.hlgg.api;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.entity.net.BaseJsonInfo;
import com.simpotech.app.hlgg.entity.net.NetProcessInfo;
import com.simpotech.app.hlgg.ui.adapter.NetProcessAdapter;
import com.simpotech.app.hlgg.util.GsonUtils;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import okhttp3.Call;

/**
 * Created by longuto on 2016/11/4.
 * 解析流程模块的网络数据
 */

public class NetProcessParse {

    private static final String TAG = "NetProcessParse";

    public static final String URL_PROCESS = Constant.HOST + Constant.PROCESS;    //流程获取地址
//  public static final String URL_PROCESS = "http://10.110.1.98:8080/process.json"; //测试流程获取地址


    /**
     * 解析网络数据加载,并设置RecyclerView控件
     *
     * @param recyclerView
     * @param refreshPtr
     */
    public static void getDataFromNet(final RecyclerView recyclerView, final
    PtrClassicFrameLayout refreshPtr, final Context context) {
        OkHttpUtils.get()
                .url(URL_PROCESS)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i(TAG, "网络加载失败");
                        UiUtils.showToast("网络加载失败");
                        refreshPtr.refreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i(TAG, "网络加载成功");
                        BaseJsonInfo<List<NetProcessInfo>> temp = (BaseJsonInfo<List
                                <NetProcessInfo>>) GsonUtils.fromJson(response, new
                                TypeToken<BaseJsonInfo<List<NetProcessInfo>>>() {
                                }.getType());
                        if (temp != null) {
                            if (temp.code.equals("success")) {
                                NetProcessAdapter adapter = new NetProcessAdapter(temp.result,
                                        context);
                                recyclerView.setAdapter(adapter);
                            } else {
                                UiUtils.showToast(temp.msg);    //显示出错原因
                            }
                        } else {
                            UiUtils.showToast("解析json数据失败");
                        }
                        refreshPtr.refreshComplete();
                    }
                });
    }

    /**默认设置gjrk的第一项,gjck的第一项*/
    public static void firstSetData() {

        OkHttpUtils.get()
                .url(URL_PROCESS)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i(TAG, "网络加载失败");
                        UiUtils.showToast("加载流程网络失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i(TAG, "网络加载成功");
                        BaseJsonInfo<List<NetProcessInfo>> temp = (BaseJsonInfo<List
                                <NetProcessInfo>>) GsonUtils.fromJson(response, new
                                TypeToken<BaseJsonInfo<List<NetProcessInfo>>>() {
                                }.getType());
                        if(temp != null) {
                            if(temp.code.equals("success")) {
                                SharedManager sp = new SharedManager(SharedManager.PROCESS_CONFIG_NAME);
                                List<NetProcessInfo> data = temp.result;
                                for (NetProcessInfo info : data) {
                                    sp.putStringToXml(info.businessNo, info.flowList.get(0).flowId);
                                }
                            }else {
                                UiUtils.showToast(temp.msg);
                            }
                        }else {
                            UiUtils.showToast("解析流程json数据失败");
                        }
                    }

                });

    }

}
