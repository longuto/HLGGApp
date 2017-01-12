package com.simpotech.app.hlgg.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.NetProcessParse;
import com.simpotech.app.hlgg.business.PermissionManager;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Map<String, String[]> permissionMap;    //权限管理的Map
    private String[] permissions;   //当前用户的权限,默认为null

    @BindView(R.id.tv_permission_info)
    TextView mPermissionInfoTv; //用户信息
    @BindView(R.id.btn_stockin)
    Button mStockInBtn; //入库
    @BindView(R.id.btn_stockout)
    Button mStockOutBtn;    //出库
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
    @BindView(R.id.ptr_main)
    PtrFrameLayout mRefreshPtr;

    /**
     * 所有功能按钮的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_stockin:  //入库
                Intent intentStockin = new Intent(context, StockinActivity.class);
                startActivity(intentStockin);
                break;
            case R.id.btn_stockin_query:    //入库查询
                Intent intentStockinQuery = new Intent(context, StockinQueryActivity.class);
                startActivity(intentStockinQuery);
                break;
            case R.id.btn_stockout: //出库
                Intent intentStockout = new Intent(context, StockoutActivity.class);
                startActivity(intentStockout);
                break;
            case R.id.btn_stockout_query:   //出库查询
                Intent intentStockoutQuery = new Intent(context, StockoutQueryActivity.class);
                startActivity(intentStockoutQuery);
                break;
            case R.id.btn_quality:  //质检
                Intent intentQuality = new Intent(context, QualityActivity.class);
                startActivity(intentQuality);
                break;
            case R.id.btn_quality_query:    //质检查询
                Intent intentQualityQuery = new Intent(context, QualityQueryActivity.class);
                startActivity(intentQualityQuery);
                break;
            case R.id.btn_Invoice:  //下载发货单
                Intent intentInvoice = new Intent(context, InvoiceActivity.class);
                startActivity(intentInvoice);
                break;
            case R.id.btn_Invoice_manager:  //发货单管理
                Intent intentInvoiceManager = new Intent(context, InvoiceManagerActivity.class);
                startActivity(intentInvoiceManager);
                break;
            case R.id.btn_contruction_query:    //构件查询
                // Intent inetntContruction = new Intent(context, ContructionActivity.class);
                // startActivity(inetntContruction);
//                showWaitDialog("哈哈哈..");
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
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
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
                overridePendingTransition(R.anim.activity_top, R.anim.activity_top_exit);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        SharedManager spP = new SharedManager(SharedManager.PROCESS_CONFIG_NAME);
        if(TextUtils.isEmpty(spP.getStringFromXml(SharedManager.GJRK)) || TextUtils.isEmpty(spP.getStringFromXml(SharedManager.GJCK))) {
            NetProcessParse.firstSetData();
        }
        getPermissionToUnlock();

        //初始化PtrHandler
        initRefreshPtr();
    }

    /**
     * 获取用户传过来的权限,并解锁对应的按钮
     */
    private void getPermissionToUnlock() {
        permissionMap = PermissionManager.getPermissionMap();
        String result = getIntent().getStringExtra("RESULT");   //获取登录界面传过来的参数
//        String result = "7";
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
                    mStockInBtn.setOnClickListener(this);
                    mStockInBtn.setClickable(true);
                    mStockInBtn.setBackgroundResource(R.drawable.selector_main_btn);
                    break;
                case "出库":
                    mStockOutBtn.setOnClickListener(this);
                    mStockOutBtn.setClickable(true);
                    mStockOutBtn.setBackgroundResource(R.drawable.selector_main_btn);
                    break;
                case "质检":
                    mQualityBtn.setOnClickListener(this);
                    mQualityBtn.setClickable(true);
                    mQualityBtn.setBackgroundResource(R.drawable.selector_main_btn);
                    break;
                case "质检查询":
                    mQualityQueryBtn.setOnClickListener(this);
                    mQualityQueryBtn.setClickable(true);
                    mQualityQueryBtn.setBackgroundResource(R.drawable.selector_main_btn);
                    break;
                case "入库查询":
                    mStockInQueryBtn.setOnClickListener(this);
                    mStockInQueryBtn.setClickable(true);
                    mStockInQueryBtn.setBackgroundResource(R.drawable.selector_main_btn);
                    break;
                case "出库查询":
                    mStockOutQueryBtn.setOnClickListener(this);
                    mStockOutQueryBtn.setClickable(true);
                    mStockOutQueryBtn.setBackgroundResource(R.drawable.selector_main_btn);
                    break;
                case "下载发货单":
                    mInvoiceBtn.setOnClickListener(this);
                    mInvoiceBtn.setClickable(true);
                    mInvoiceBtn.setBackgroundResource(R.drawable.selector_main_btn);
                    break;
                case "发货单管理":
                    mInvoiceManBtn.setOnClickListener(this);
                    mInvoiceManBtn.setClickable(true);
                    mInvoiceManBtn.setBackgroundResource(R.drawable.selector_main_btn);
                    break;
                case "构件查询":
                    mContructionBtn.setOnClickListener(this);
                    mContructionBtn.setClickable(true);
                    mContructionBtn.setBackgroundResource(R.drawable.selector_main_btn);
                    break;
                case "流程设置":
                    mProcessBtn.setOnClickListener(this);
                    mProcessBtn.setClickable(true);
                    mProcessBtn.setBackgroundResource(R.drawable.selector_main_btn);
                    break;
                case "生产线配置":
                    mProLineBtn.setOnClickListener(this);
                    mProLineBtn.setClickable(true);
                    mProLineBtn.setBackgroundResource(R.drawable.selector_main_btn);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 设置StoreHouse风格的下拉刷新
     */
    private void initRefreshPtr() {
        StoreHouseHeader header = new StoreHouseHeader(context);
        header.initWithString("HONG LU", 28);
        header.setTextColor(UiUtils.getColor(R.color.navigation_red));
        header.setPadding(0, UiUtils.dip2px(15), 0, UiUtils.dip2px(15));
        mRefreshPtr.setHeaderView(header);
        mRefreshPtr.addPtrUIHandler(header);

        mRefreshPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mRefreshPtr.refreshComplete();  //刷新完成
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
