package com.simpotech.app.hlgg.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.NetLoginFragParse;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.ui.fragment.LoginFragment;
import com.simpotech.app.hlgg.util.UiUtils;

public class MainFragActivity extends AppCompatActivity {

    private static String TAG = "MainFragActivity";

    FragmentManager manager;     //Fragment跳转工具
    SharedManager spManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frag);

        spManager = new SharedManager();
        manager = getSupportFragmentManager();
        initData();
    }

    private void initData() {
        String username = spManager.getStringFromXml(SharedManager.USERNAME, "");
        String password = spManager.getStringFromXml(SharedManager.PASSWORD, "");
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            LoginFragment fragment = new LoginFragment();
            manager.beginTransaction().replace(R.id.frag_content, fragment, "LoginFragment").commit();
        }else {
            NetLoginFragParse.loginForResult(username, password, this);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {

            final View v = View.inflate(this, R.layout.exit_dialog, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("是否退出").setView(v);
            final AlertDialog dialog = builder.create();

            final EditText editText = (EditText) v.findViewById(R.id.edt_exit_pwd);
            v.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String password = editText.getText().toString().trim();
                    String pwdXml = spManager.getStringFromXml(SharedManager.PASSWORD, "");
                    if (TextUtils.isEmpty(password)) {
                        UiUtils.showToast("密码不能为空");
                        return;
                    }
                    if (password.equals(pwdXml)) {
                        finish();
                        dialog.dismiss();
                    } else {
                        UiUtils.showToast("输入的密码不正确");
                        return;
                    }
                }
            });
            v.findViewById(R.id.btn_can).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
