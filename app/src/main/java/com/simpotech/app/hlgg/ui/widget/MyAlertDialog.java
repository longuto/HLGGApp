package com.simpotech.app.hlgg.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

import com.simpotech.app.hlgg.util.LogUtils;

/**
 * Created by longuto on 2017/1/11.
 */

public class MyAlertDialog extends AlertDialog {

    protected MyAlertDialog(@NonNull Context context) {
        super(context);
    }

    protected MyAlertDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected MyAlertDialog(@NonNull Context context, boolean cancelable, @Nullable
            OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER) {
            LogUtils.i("MyAlertDialog", "KeyEvent.KEYCODE_ENTER");
        }
        return super.onKeyDown(keyCode, event);
    }
}
