package com.simpotech.app.hlgg.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.entity.net.NetLoginInfo;
import com.simpotech.app.hlgg.ui.activity.LoginActivity;
import com.simpotech.app.hlgg.ui.activity.MainActivity;
import com.simpotech.app.hlgg.ui.activity.WelcomeActivity;
import com.simpotech.app.hlgg.util.GsonUtils;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by longuto on 2016/12/28.
 *
 * 登录客户端
 */

public class NetLoginParse {

    private static String TAG = "NetLoginParse";

    private static final String URL_LOGIN = Constant.HOST + Constant.LOGIN;
    private static SharedManager spManager = new SharedManager();

    public static void loginSys(final String username, final String password, final Context context) {
        //通过OkHttp异步请求网络
        OkHttpUtils
                .post()
                .url(URL_LOGIN)
                .addParams("username", username)
                .addParams("pwd", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i(TAG, "请求网络失败");
                        UiUtils.showToast("网络请求失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i(TAG, "请求网络成功");
                        //用Gson解析
                        NetLoginInfo loginInfo = (NetLoginInfo) GsonUtils.fromJson(response, new
                                TypeToken<NetLoginInfo>() {
                                }.getType());
                        //如果解析成功
                        if (loginInfo != null) {
                            if (loginInfo.code.equals("success")) {
                                //保存用户名至SharedPreferences
                                spManager.putStringToXml(SharedManager.USERNAME, username);
                                spManager.putStringToXml(SharedManager.PASSWORD, password);
                                if(!TextUtils.isEmpty(loginInfo.userId)) {
                                    spManager.putStringToXml(SharedManager.USERID, loginInfo.userId);
                                }else {
                                    UiUtils.showToast("未获取当前用户信息");
                                }

                                //携带result跳转至MainActivity
                                String result = loginInfo.result;
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra("RESULT", result);
                                context.startActivity(intent);
                                ((Activity)context).overridePendingTransition(R.anim.activity_top, R.anim.activity_top_exit);
                                //销毁此Activity
                                ((Activity)context).finish();
                            } else {
                                // 显示错误原因
                                UiUtils.showToast(loginInfo.msg);
                            }
                        }
                    }
                });

    }

    public static void loginForResult(final String username, final String password, final WelcomeActivity activity) {
        //通过OkHttp异步请求网络
        OkHttpUtils
                .post()
                .url(URL_LOGIN)
                .addParams("username", username)
                .addParams("pwd", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i(TAG, "请求网络失败");
                        UiUtils.showToast("网络请求失败");
                        enterLogin(activity);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i(TAG, "请求网络成功");
                        //用Gson解析
                        NetLoginInfo loginInfo = (NetLoginInfo) GsonUtils.fromJson(response, new
                                TypeToken<NetLoginInfo>() {
                                }.getType());
                        //如果解析成功
                        if (loginInfo != null) {
                            if (loginInfo.code.equals("success")) {
                                //保存用户名至SharedPreferences
                                spManager.putStringToXml(SharedManager.USERNAME, username);
                                spManager.putStringToXml(SharedManager.PASSWORD, password);
                                if(!TextUtils.isEmpty(loginInfo.userId)) {
                                    spManager.putStringToXml(SharedManager.USERID, loginInfo.userId);
                                }else {
                                    UiUtils.showToast("未获取当前用户信息");
                                }

                                //携带result跳转至MainActivity
                                String result = loginInfo.result;
                                Intent intent = new Intent(activity, MainActivity.class);
                                intent.putExtra("RESULT", result);
                                activity.startActivity(intent);
                                activity.overridePendingTransition(R.anim.activity_top, R.anim.activity_top_exit);
                                //销毁此Activity
                                activity.finish();
                            } else {
                                // 显示错误原因
                                UiUtils.showToast(loginInfo.msg);
                                enterLogin(activity);
                            }
                        }
                    }
                });
    }

    /**
     * 进入loginActivity
     * @param activity
     */
    private static void enterLogin(WelcomeActivity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.activity_top, R.anim.activity_top_exit);
        activity.finish();
    }
}
