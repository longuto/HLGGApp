package com.simpotech.app.hlgg.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.simpotech.app.hlgg.ui.activity.WelcomeActivity;

/**
 * Created by longuto on 2016/12/27.
 *
 * 开机自启广播接收器
 */

public class AutoStartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, WelcomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
