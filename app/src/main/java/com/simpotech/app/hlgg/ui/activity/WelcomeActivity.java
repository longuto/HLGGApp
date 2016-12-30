package com.simpotech.app.hlgg.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.NetLoginParse;
import com.simpotech.app.hlgg.business.SharedManager;

public class WelcomeActivity extends AppCompatActivity {

    SharedManager spManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        context = this;
        spManager = new SharedManager();
        chooseStartAct();
    }

    /**
     * 登录选择启动界面
     */
    private void chooseStartAct() {
        final String username = spManager.getStringFromXml(SharedManager.USERNAME);
        final String password = spManager.getStringFromXml(SharedManager.PASSWORD);

        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                            Intent intent = new Intent(context, LoginActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.activity_top, R.anim.activity_top_exit);
                            finish();
                        }else {
                            NetLoginParse.loginForResult(username, password, WelcomeActivity.this);
                        }
                    }
                });
            }
        }.start();
    }
}
