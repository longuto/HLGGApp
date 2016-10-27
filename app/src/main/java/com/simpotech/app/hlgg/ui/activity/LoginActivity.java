package com.simpotech.app.hlgg.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.Constant;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class LoginActivity extends BaseActivity {

    private static final String URL_LOGIN = Constant.HOST + Constant.LOGIN;
    private static final String TAG = "LoginActivity";

    @BindView(R.id.edt_username)
    EditText mUsernameEdt;
    @BindView(R.id.edt_password)
    EditText mPasswordEdt;
    @BindView(R.id.btn_login)
    Button mLoginBtn;

    @OnClick(R.id.btn_login)
    public void login(View v) {
        String username = mUsernameEdt.getText().toString();
        String password = mPasswordEdt.getText().toString();
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
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

                    }
                });

    }

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initTitle() {
        showRightBtn("关闭");
        getRightBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }


}
