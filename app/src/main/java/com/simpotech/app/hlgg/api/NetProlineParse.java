package com.simpotech.app.hlgg.api;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.entity.net.BaseJsonInfo;
import com.simpotech.app.hlgg.entity.net.NetProLineInfo;
import com.simpotech.app.hlgg.ui.adapter.NetProLineAdapter;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import okhttp3.Call;

/**
 * Created by longuto on 2016/11/2.
 * 获取网络生产线的数据
 */

public class NetProlineParse {
    private static final String TAG = "NetProlineParse";

    //public static final String URL_PROLINE = Constant.HOST + Constant.PROLINE; //流水线地址
    public static final String URL_PROLINE = "http://10.110.1.98:8080/proline.json"; //测试流水线地址

    /**
     * 获取Proline网络数据的集合
     */
    public static void getDataFromNet(final RecyclerView recyclerView, final PtrClassicFrameLayout refreshPtr) {

        // 使用Okhttp访问网络
        OkHttpUtils.get()
                .url(URL_PROLINE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i(TAG, "网络访问失败");
                        UiUtils.showToast("网络加载失败!");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i(TAG, "网络访问成功");
                        Gson gson = new Gson();
                        BaseJsonInfo<List<NetProLineInfo>> temp = gson.fromJson(response, new
                                TypeToken<BaseJsonInfo<List<NetProLineInfo>>>() {
                                }.getType());
                        //如果解析成功
                        if (temp != null) {
                            if (temp.code.equals("success")) {
                                List<NetProLineInfo> netProlineInfos = temp.result;
                                NetProLineAdapter adapter = new NetProLineAdapter(netProlineInfos);
                                recyclerView.setAdapter(adapter);
                            } else {
                                UiUtils.showToast(temp.msg);    //显示出错原因
                            }
                            refreshPtr.refreshComplete();   //刷新完成
                        } else {
                            UiUtils.showToast("解析json数据失败");
                        }
                    }
                });
    }

}
