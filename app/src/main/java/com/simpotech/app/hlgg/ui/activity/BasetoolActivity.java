package com.simpotech.app.hlgg.ui.activity;

import com.simpotech.app.hlgg.ui.widget.ProgressDialog;

/**
 * Created by longuto on 2016/12/24.
 *
 * 增加Activity常用的工具类
 */

public abstract class BasetoolActivity extends BaseActivity {

    ProgressDialog waitDialog;  //加载等待对话框

    @Override
    protected void initTools() {
        waitDialog = new ProgressDialog(context);
    }

    /**
     * 显示指定内容的等待对话框
     * @param content
     */
    public void showWaitDialog(String content) {
        if(waitDialog == null) {
            waitDialog = new ProgressDialog(context);
        }
        waitDialog.setProgressText(content);
        waitDialog.show();
    }

    /** 隐藏对话 */
    public void hideWaitDialog() {
        if(waitDialog != null) {
            waitDialog.hide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(waitDialog != null) {
            waitDialog.dismiss();
        }
        onMyDestory();
    }

    /** 可自己决定是否需要重写此方法 */
    protected void onMyDestory() {};
}
