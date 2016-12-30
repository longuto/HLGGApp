package com.simpotech.app.hlgg.api;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.entity.net.NetLoginInfo;
import com.simpotech.app.hlgg.ui.activity.MainFragActivity;
import com.simpotech.app.hlgg.ui.fragment.LoginFragment;
import com.simpotech.app.hlgg.ui.fragment.MainFragment;
import com.simpotech.app.hlgg.util.GsonUtils;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by longuto on 2016/12/28.
 * <p>
 * 登录Fragment
 */

public class NetLoginFragParse {

    private static String TAG = "NetLoginFragParse";

    private static final String URL_LOGIN = Constant.HOST + Constant.LOGIN;
    private static SharedManager spManager = new SharedManager();

    public static void loginForResult(final String username, final String password, final
    MainFragActivity activity) {
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
                        UiUtils.showToast("加载网络失败");
                        LoginFragment fragment = new LoginFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R
                                .id.frag_content, fragment, "LoginFragment").commit();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i(TAG, "请求网络成功");
                        //用Gson解析
                        NetLoginInfo loginInfo = (NetLoginInfo) GsonUtils.fromJson(response, new
                                TypeToken<NetLoginInfo>() {}.getType());
                        //如果解析成功
                        if (loginInfo != null) {
                            if (loginInfo.code.equals("success")) {
                                //保存用户名至SharedPreferences
                                spManager.putStringToXml(SharedManager.USERNAME, username);
                                spManager.putStringToXml(SharedManager.PASSWORD, password);
                                if (!TextUtils.isEmpty(loginInfo.userId)) {
                                    spManager.putStringToXml(SharedManager.USERID, loginInfo
                                            .userId);
                                } else {
                                    UiUtils.showToast("未获取当前用户信息");
                                }

                                //携带result跳转至MainFragment
                                String result = loginInfo.result;
                                MainFragment fragment = MainFragment.getInstance(result);
                                activity.getSupportFragmentManager().beginTransaction().replace(R
                                        .id.frag_content, fragment, "MainFragment").commit();
                            } else {
                                // 显示错误原因
                                UiUtils.showToast(loginInfo.msg);
                                LoginFragment fragment = new LoginFragment();
                                activity.getSupportFragmentManager().beginTransaction().replace(R
                                        .id.frag_content, fragment, "LoginFragment").commit();
                            }
                        }
                    }
                });
    }
}
