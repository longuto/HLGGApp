package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.business.PermissionManager;
import com.simpotech.app.hlgg.business.SharedManager;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private Map<String, String[]> permissionMap;    //权限管理的Map
    private String[] permissions;   //当前用户的权限,默认为null

    @BindView(R.id.tv_permission_info)
    TextView mPermissionInfoTv; //用户信息

    @BindView(R.id.btn_stockin)
    Button mStockInBtn; //入库
    @BindView(R.id.btn_stockin_manager)
    Button mStockInManBtn;  //入库管理
    @BindView(R.id.btn_stockout)
    Button mStockOutBtn;    //出库
    @BindView(R.id.btn_stockout_manager)
    Button mStockOutManBtn; //出库管理
    @BindView(R.id.btn_quality)
    Button mQualityBtn; //质检
    @BindView(R.id.btn_quality_query)
    Button mQualityQueryBtn;    //质检查询
    @BindView(R.id.btn_stockin_query)
    Button mStockInQueryBtn;    //入库查询
    @BindView(R.id.btn_stockout_query)
    Button mStockOutQueryBtn;   //出库查询
    @BindView(R.id.btn_Invoice)
    Button mInvoiceBtn; //下载发货单
    @BindView(R.id.btn_Invoice_manager)
    Button mInvoiceManBtn;  //发货单管理
    @BindView(R.id.btn_contruction_query)
    Button mContructionBtn; //构件查询
    @BindView(R.id.btn_process)
    Button mProcessBtn; //流程设置
    @BindView(R.id.btn_proLine)
    Button mProLineBtn; //生产线配置

    /**
     * 所有功能按钮的点击事件
     * @param button
     */
    @OnClick({R.id.btn_stockin, R.id.btn_stockin_manager, R.id.btn_stockout, R.id
            .btn_stockout_manager, R.id.btn_quality, R.id.btn_quality_query, R.id
            .btn_stockin_query, R.id.btn_stockout_query, R.id.btn_Invoice, R.id
            .btn_Invoice_manager, R.id.btn_contruction_query, R.id.btn_process, R.id.btn_proLine})
    public void functions(Button button) {
        switch (button.getId()) {
            case R.id.btn_stockin:  //入库

                break;
            case R.id.btn_stockin_manager:  //入库管理

                break;
            case R.id.btn_stockout: //出库

                break;
            case R.id.btn_stockout_manager: //出库管理

                break;
            case R.id.btn_quality:  //质检

                break;
            case R.id.btn_quality_query:    //质检查询

                break;
            case R.id.btn_stockin_query:    //入库查询

                break;
            case R.id.btn_stockout_query:   //出库查询

                break;
            case R.id.btn_Invoice:  //下载发货单

                break;
            case R.id.btn_Invoice_manager:  //发货单管理

                break;
            case R.id.btn_contruction_query:    //构件查询
                Intent inetntContruction = new Intent(context, ContructionActivity.class);
                startActivity(inetntContruction);
                break;
            case R.id.btn_process:  //流程设置
                Intent intentProcess = new Intent(context, ProcessActivity.class);
                startActivity(intentProcess);
                break;
            case R.id.btn_proLine:  //生产线配置
                Intent intentProLine = new Intent(context, ProLineActivity.class);
                startActivity(intentProLine);
                break;
            default:
                break;
        }
    }


    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initTitle() {
        showMiddleTv("首页");
        showRightIv(R.drawable.vector_main_back);
        getRightLly().setOnClickListener(new View.OnClickListener() {
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
        getPermissionToUnlock();
    }

    /**
     * 获取用户传过来的权限,并解锁对应的按钮
     */
    private void getPermissionToUnlock() {
        permissionMap = PermissionManager.getPermissionMap();
        String result = getIntent().getStringExtra("RESULT");   //获取登录界面传过来的参数
        String username = spManager.getStringFromXml(SharedManager.USERNAME);
        switch (result) {
            case "0":   //都没有权限
                permissions = permissionMap.get("0");
                mPermissionInfoTv.setText("游客(" + username + ")");
                break;
            case "1":   //入库权限
                permissions = permissionMap.get("1");
                mPermissionInfoTv.setText("入库员(" + username + ")");
                break;
            case "2":   //出库权限
                permissions = permissionMap.get("2");
                mPermissionInfoTv.setText("出库员(" + username + ")");
                break;
            case "3":   //质检权限
                permissions = permissionMap.get("3");
                mPermissionInfoTv.setText("质检员(" + username + ")");
                break;
            case "4":   //入库,出库权限
                permissions = permissionMap.get("4");
                mPermissionInfoTv.setText("入库出库员(" + username + ")");
                break;
            case "5":   //入库,质检权限
                permissions = permissionMap.get("5");
                mPermissionInfoTv.setText("入库质检员(" + username + ")");
                break;
            case "6":   //出库,质检权限
                permissions = permissionMap.get("6");
                mPermissionInfoTv.setText("出库质检员(" + username + ")");
                break;
            case "7":   //都有权限
                permissions = permissionMap.get("7");
                mPermissionInfoTv.setText("管理员(" + username + ")");
                break;
            default:
                break;
        }

        for (String str : permissions) {
            switch (str) {
                case "入库":
                    mStockInBtn.setClickable(true);
                    break;
                case "入库管理":
                    mStockInManBtn.setClickable(true);
                    break;
                case "出库":
                    mStockOutBtn.setClickable(true);
                    break;
                case "出库管理":
                    mStockOutManBtn.setClickable(true);
                    break;
                case "质检":
                    mQualityBtn.setClickable(true);
                    break;
                case "质检查询":
                    mQualityQueryBtn.setClickable(true);
                    break;
                case "入库查询":
                    mStockInQueryBtn.setClickable(true);
                    break;
                case "出库查询":
                    mStockOutQueryBtn.setClickable(true);
                    break;
                case "下载发货单":
                    mInvoiceBtn.setClickable(true);
                    break;
                case "发货单管理":
                    mInvoiceManBtn.setClickable(true);
                    break;
                case "构件查询":
                    mContructionBtn.setClickable(true);
                    break;
                case "流程设置":
                    mProcessBtn.setClickable(true);
                    break;
                case "生产线配置":
                    mProLineBtn.setClickable(true);
                    break;
                default:
                    break;
            }
        }
    }

}
