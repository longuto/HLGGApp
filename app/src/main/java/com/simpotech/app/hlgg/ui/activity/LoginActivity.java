package com.simpotech.app.hlgg.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.Constant;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.entity.net.NetLoginInfo;
import com.simpotech.app.hlgg.util.GsonUtils;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by longuto on 2016/10/27.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String URL_LOGIN = Constant.HOST + Constant.LOGIN;
    //    private static final String URL_LOGIN = "http://10.110.1.98:8080/login.json";
    private static final String TAG = "LoginActivity";

    private SharedManager spManager;
    Context context;

    @BindView(R.id.edt_username)
    EditText mUsernameEdt;
    @BindView(R.id.edt_password)
    EditText mPasswordEdt;

    @OnClick(R.id.iv_exit)
    public void exit(View v) {
        finish();
    }

    @OnClick(R.id.btn_login)
    public void login(View v) {
        final String username = mUsernameEdt.getText().toString();
        String password = mPasswordEdt.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            UiUtils.showToast("用户名或密码不能为空!");
            return;
        }

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
                                if(!TextUtils.isEmpty(loginInfo.userId)) {
                                    spManager.putStringToXml(SharedManager.USERID, loginInfo.userId);
                                }else {
                                    UiUtils.showToast("未获取当前用户信息");
                                }

                                //携带result跳转至MainActivity
                                String result = loginInfo.result;
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra("RESULT", result);
                                startActivity(intent);
                                //销毁此Activity
                                finish();
                            } else {
                                // 显示错误原因
                                UiUtils.showToast(loginInfo.msg);
                            }
                        }
                    }
                });

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        context = this;
        spManager = new SharedManager();
        String username = spManager.getStringFromXml(SharedManager.USERNAME);
        mUsernameEdt.setText(username);
    }
}
