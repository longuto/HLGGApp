package com.simpotech.app.hlgg.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.Constant;
import com.simpotech.app.hlgg.api.NetLoginParse;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.entity.net.BaseJsonInfo;
import com.simpotech.app.hlgg.entity.net.NetUpdateInfo;
import com.simpotech.app.hlgg.util.GsonUtils;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;

public class WelcomeActivity extends AppCompatActivity {

    private final static String URL_UPDATE = Constant.HOST + Constant.UPDATEAPK;   //apk检测升级的地址
    private final static String APKNAME = "hlgg.apk";   //apk名称
    private static final String TAG = "WelcomeActivity";    //标记

    SharedManager spManager;
    Context context;

    ProgressBar mProgressBar;   //进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        context = this;
        spManager = new SharedManager();
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        checkApkUpdate();
//        new Thread() {
//            @Override
//            public void run() {
//                chooseStartAct();
//            }
//        }.start();
    }

    /**
     * 检测版本更新
     */
    private void checkApkUpdate() {

        OkHttpUtils
                .get()
                .addParams("version", getVersionName())
                .url(URL_UPDATE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i(TAG, "版本更新网络加载失败");
                        chooseStartAct(0);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i(TAG, "版本更新网络加载成功");
                        BaseJsonInfo<NetUpdateInfo> temp = (BaseJsonInfo<NetUpdateInfo>)
                                GsonUtils.fromJson(response, new
                                        TypeToken<BaseJsonInfo<NetUpdateInfo>>() {
                                        }.getType());

                        if (temp.code.equals("success")) {
                            NetUpdateInfo info = temp.result;
                            //如果版本名称不相等,则提示更新版本
                            if (!getVersionName().equals(info.version)) {
                                showUpdateDialog(info.filePath);
                            } else {
                                chooseStartAct();
                            }
                        } else {
                            chooseStartAct();
                        }
                    }
                });
    }

    /**
     * 显示版本更新对话框
     *
     * @param filePath apk下载地址
     */
    private void showUpdateDialog(final String filePath) {

        LogUtils.i(TAG, "版本下载地址为:" + Constant.HOST + filePath);
        new AlertDialog.Builder(context)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("版本更新")
                .setMessage("有新版本了,是否需要更新")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread() {
                            @Override
                            public void run() {
                                chooseStartAct(0);
                            }
                        }.start();
                    }
                })
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mProgressBar.setVisibility(View.VISIBLE);

                        OkHttpUtils.get()
//                                .url("http://10.110.1.98:8080/PhoneSafe.apk")
                                .url(Constant.HOST + filePath)
                                .build()
                                .execute(new FileCallBack(Environment
                                        .getExternalStoragePublicDirectory(Environment
                                                .DIRECTORY_DOWNLOADS).getAbsolutePath(), APKNAME) {

                                    @Override
                                    public void inProgress(float progress, long total, int id) {
                                        LogUtils.i(TAG, "total为:" + total + ",progress为:" + progress);
                                        mProgressBar.setProgress((int) (progress * 100));
                                    }

                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        LogUtils.i(TAG, "onError----");
                                        UiUtils.showToast("很遗憾,apk下载失败");
                                        chooseStartAct(0);
                                    }

                                    @Override
                                    public void onResponse(File response, int id) {
                                        LogUtils.i(TAG, "onResponse----");
                                        install();
                                    }
                                });
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    /**
     * 安装下载的apk
     */
    private void install() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), APKNAME)), "application/vnd.android.package-archive");
        startActivityForResult(intent, 6);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        new Thread() {
            @Override
            public void run() {
                chooseStartAct(0);
            }
        }.start();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取app的版本名称
     *
     * @return
     */
    protected String getVersionName() {
        String versionName = null;
        try {
            PackageManager pm = getPackageManager();    // 包管理器
            PackageInfo info = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            versionName = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 登录选择启动界面, 版本更新不成功该做的事
     * <p>
     * 该方法只在子线程调用,attention please
     */
    private void chooseStartAct(long million) {
        final String username = spManager.getStringFromXml(SharedManager.USERNAME);
        final String password = spManager.getStringFromXml(SharedManager.PASSWORD);

        SystemClock.sleep(million);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_top, R.anim
                            .activity_top_exit);
                    finish();
                } else {
                    NetLoginParse.loginForResult(username, password, WelcomeActivity.this);
                }
            }
        });
    }

    /**
     * 默认延时1500ms选择操作
     */
    private void chooseStartAct() {
        chooseStartAct(1500);
    }
}
