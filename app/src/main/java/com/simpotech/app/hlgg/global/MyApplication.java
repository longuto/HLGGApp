package com.simpotech.app.hlgg.global;

import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 配置全局变量
 * Created by longuto on 2016/10/27.
 */
public class MyApplication extends Application {

    private static Context context;  //上下文
    private static int mainThreadId; //主线程id

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        mainThreadId = Process.myTid();

        /**配置OkHttpClient*/
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //.addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(5000L, TimeUnit.MILLISECONDS)
                .readTimeout(5000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    /**返回上下文*/
    public static Context getContext() {
        return context;
    }

    /**获取主线程id*/
    public static int getUiTid() {
        return mainThreadId;
    }
}
