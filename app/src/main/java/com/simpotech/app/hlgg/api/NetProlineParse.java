package com.simpotech.app.hlgg.api;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.entity.DbProLineInfo;
import com.simpotech.app.hlgg.entity.RecyProLineInfo;
import com.simpotech.app.hlgg.entity.net.BaseJsonInfo;
import com.simpotech.app.hlgg.entity.net.NetProLineInfo;
import com.simpotech.app.hlgg.ui.adapter.NetProLineAdapter;
import com.simpotech.app.hlgg.util.GsonUtils;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import okhttp3.Call;

/**
 * Created by longuto on 2016/11/2.
 * 获取网络生产线的数据
 */

public class NetProlineParse {

    private static final String TAG = "NetProlineParse";

    public static final String URL_PROLINE = Constant.HOST + Constant.PROLINE; //流水线地址
//  public static final String URL_PROLINE = "http://10.110.1.98:8080/proline.json"; //测试流水线地址


    /**
     * 调用接口,通过指定的部门名称查询生产线数据
     */
    public static void getDataByDepartmentName(String departmentName, final RecyclerView
            recyclerView, final PtrClassicFrameLayout refreshPtr, final Context context) {
        OkHttpUtils.post()
                .url(URL_PROLINE)
                .addParams("name", departmentName)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i(TAG, "访问网络失败");
                        UiUtils.showToast("网络加载失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i(TAG, "访问网络成功");
                        ParseResponse(response, recyclerView, refreshPtr, context);
                    }
                });
    }


    /**
     * 获取Proline网络所有生产线数据的集合
     */
    public static void getDataFromNet(final RecyclerView recyclerView, final
    PtrClassicFrameLayout refreshPtr, final Context context) {

        // 使用Okhttp访问网络
        OkHttpUtils.get()
                .url(URL_PROLINE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i(TAG, "网络访问失败");
                        UiUtils.showToast("网络加载失败!");
                        refreshPtr.refreshComplete();   //刷新完成
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i(TAG, "网络访问成功");
                        ParseResponse(response, recyclerView, refreshPtr, context);
                        refreshPtr.refreshComplete();   //刷新完成
                    }
                });
    }

    /**
     * 解析返回的json字符串,并适配到RecyclerView中
     *
     * @param response 传入的Json字符串
     */
    private static void ParseResponse(String response, RecyclerView recyclerView, final
    PtrClassicFrameLayout refreshPtr, Context context) {
        BaseJsonInfo<List<NetProLineInfo>> temp = (BaseJsonInfo<List<NetProLineInfo>>) GsonUtils
                .fromJson(response, new TypeToken<BaseJsonInfo<List<NetProLineInfo>>>() {
        }.getType());
        //如果解析成功
        if (temp != null) {
            if (temp.code.equals("success")) {
                List<NetProLineInfo> netProlineInfos = temp.result;

                //将网络获取的数据转化成RecyclerView需要的数据
                List<RecyProLineInfo> recyProLineInfos = NetData2RecyData(netProlineInfos);

                NetProLineAdapter adapter = new NetProLineAdapter(recyProLineInfos, context);
                recyclerView.setAdapter(adapter);
            } else {
                UiUtils.showToast(temp.msg);    //显示出错原因
            }
        } else {
            UiUtils.showToast("解析json数据失败");
        }
    }

    /**
     * 解析网络数据,转换成RecyclerView展示的数据
     */
    public static List<RecyProLineInfo> NetData2RecyData(List<NetProLineInfo> netProlineInfos) {
        List<RecyProLineInfo> recyProLineInfos = new ArrayList<RecyProLineInfo>();

        int size1 = netProlineInfos.size();
        for (int i = 0; i < size1; i++) {
            NetProLineInfo netTemp = netProlineInfos.get(i);

            List<DbProLineInfo> dbProLineInfos = new ArrayList<>();
            RecyProLineInfo recyTemp = new RecyProLineInfo(netTemp.id, netTemp.name,
                    dbProLineInfos);

            int size2 = netTemp.organList.size();
            if(size2 > 0) {
                for (int j = 0; j < size2; j++) {
                    DbProLineInfo temp = new DbProLineInfo(netTemp.id, netTemp.name, netTemp
                            .organList.get(j).id, netTemp.organList.get(j).name);
                    recyTemp.prolines.add(temp);
                }
            } else {
                DbProLineInfo temp = new DbProLineInfo(netTemp.id, netTemp.name, netTemp.id, netTemp.name);
                recyTemp.prolines.add(temp);
            }

            recyProLineInfos.add(recyTemp);
        }

        return recyProLineInfos;
    }
}
