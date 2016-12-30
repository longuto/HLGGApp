package com.simpotech.app.hlgg.business;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.util.UiUtils;

/**
 * Created by longuto on 2016/12/30.
 *
 * 显示密码对话框
 */

public class ShowDialogManager {

    public static void showpwdDialog(final Context context) {

        final View v = View.inflate(context, R.layout.exit_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("是否退出").setView(v);
        final AlertDialog dialog = builder.create();

        final EditText editText = (EditText) v.findViewById(R.id.edt_exit_pwd);
        v.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = editText.getText().toString().trim();
                String pwdXml = new SharedManager().getStringFromXml(SharedManager.PASSWORD, "");
                if (TextUtils.isEmpty(password)) {
                    UiUtils.showToast("密码不能为空");
                    return;
                }
                if (password.equals(pwdXml)) {
                    ((Activity)context).finish();
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
    }
}
