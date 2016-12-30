package com.simpotech.app.hlgg.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.NetLoginParse;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.business.ShowDialogManager;
import com.simpotech.app.hlgg.util.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by longuto on 2016/10/27.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private SharedManager spManager;
    Context context;

    @BindView(R.id.edt_username)
    EditText mUsernameEdt;
    @BindView(R.id.edt_password)
    EditText mPasswordEdt;

    @OnClick(R.id.iv_exit)
    public void exit(View view) {

        ShowDialogManager.showpwdDialog(context);

//        ComponentName com = new ComponentName("com.android.launcher3", "com.android.launcher3.Launcher");
//        Intent intent = new Intent();
//        intent.setComponent(com);
//        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    public void login(View v) {
        final String username = mUsernameEdt.getText().toString();
        final String password = mPasswordEdt.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            UiUtils.showToast("用户名或密码不能为空!");
            return;
        }
        NetLoginParse.loginSys(username, password, context);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        context = this;
        spManager = new SharedManager();
        String username = spManager.getStringFromXml(SharedManager.USERNAME);
        mUsernameEdt.setText(username);
        String password = spManager.getStringFromXml(SharedManager.PASSWORD);
        mPasswordEdt.setText(password);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            ShowDialogManager.showpwdDialog(context);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
