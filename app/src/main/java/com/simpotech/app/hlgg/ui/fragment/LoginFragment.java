package com.simpotech.app.hlgg.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.NetLoginFragParse;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.ui.activity.MainFragActivity;
import com.simpotech.app.hlgg.util.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by longuto on 2016/12/28.
 * <p>
 * 登录界面的Fragment
 */

public class LoginFragment extends Fragment {

    private MainFragActivity activity;
    private SharedManager spManager;

    @BindView(R.id.edt_username)
    EditText mUsernameEdt;
    @BindView(R.id.edt_password)
    EditText mPasswordEdt;

    @OnClick(R.id.iv_exit)
    public void exitSys(View view) {

        final View v = View.inflate(activity, R.layout.exit_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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
                    activity.finish();
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

    @OnClick(R.id.btn_login)
    public void login(View view) {
        final String username = mUsernameEdt.getText().toString().trim();
        final String password = mPasswordEdt.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            UiUtils.showToast("用户名或密码不能为空!");
            return;
        }
        NetLoginFragParse.loginForResult(username, password, activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainFragActivity) getActivity();
        spManager = new SharedManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        ButterKnife.bind(this, view);

        String username = spManager.getStringFromXml(SharedManager.USERNAME, "");
        mUsernameEdt.setText(username);
        String password = spManager.getStringFromXml(SharedManager.PASSWORD, "");
        mPasswordEdt.setText(password);
        return view;
    }
}
