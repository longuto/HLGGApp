package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.view.View;

import com.simpotech.app.hlgg.R;

import java.util.HashMap;

public class MainActivity extends BaseActivity {

    private HashMap<String, String[]> functionMap = new HashMap<String, String[]>();    //权限管理

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initTitle() {
        showMiddleBtn("首页");
        showRightBtn("退出");

        getRightBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        functionMap.put("0", new String[]{});   //都没有权限
        functionMap.put("1", new String[]{"入库", "入库管理", "入库查询"});   //入库权限
        functionMap.put("2", new String[]{"出库", "出库管理", "出库查询"});   //出库权限
        functionMap.put("3", new String[]{"质检", "质检查询"});   //质检权限
        functionMap.put("4", new String[]{"入库", "入库管理", "出库", "出库管理", "入库查询", "出库查询"}); //入库,出库权限
        functionMap.put("5", new String[]{"入库", "入库管理", "质检", "质检查询", "入库查询"}); //入库,质检权限
        functionMap.put("6", new String[]{"出库", "出库管理", "质检", "质检查询", "出库查询"}); //出库,质检权限
        functionMap.put("7", new String[]{"入库", "入库管理", "出库", "出库管理", "质检", "质检查询", "入库查询",
                "出库查询", "下载发货单", "发货单管理", "构件查询", "流程设置", "生产线配置"});    //都有权限
    }
}
