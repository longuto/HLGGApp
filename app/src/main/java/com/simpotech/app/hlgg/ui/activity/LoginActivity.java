package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.entity.BaseInfo;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.util.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by longuto on 2016/10/27.
 */
public class LoginActivity extends BaseActivity {

//    private static final String URL_LOGIN = Constant.HOST + Constant.LOGIN;
    private static final String URL_LOGIN = "http://10.110.1.98:8080/login.json";
    private static final String TAG = "LoginActivity";

    @BindView(R.id.edt_username)
    EditText mUsernameEdt;
    @BindView(R.id.edt_password)
    EditText mPasswordEdt;
    @BindView(R.id.btn_login)
    Button mLoginBtn;

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
                        Gson gson = new Gson();
                        BaseInfo<String> loginInfo = gson.fromJson(response, new
                                TypeToken<BaseInfo<String>>() {
                                }.getType());
                        if (loginInfo.code.equals("success")) {
                            //保存用户名至SharedPreferences
                            spManager.putStringToXml(SharedManager.USERNAME, username);

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
                });

    }

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initTitle() {
        showMiddleIcon("登录");
        showRightIcon(R.drawable.vector_sys_back);
        getRightIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        String username = spManager.getStringFromXml(SharedManager.USERNAME);
        mUsernameEdt.setText(username);
    }


}
