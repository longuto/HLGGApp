package com.simpotech.app.hlgg.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.business.PermissionManager;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.ui.activity.InvoiceActivity;
import com.simpotech.app.hlgg.ui.activity.InvoiceManagerActivity;
import com.simpotech.app.hlgg.ui.activity.ProLineActivity;
import com.simpotech.app.hlgg.ui.activity.ProcessActivity;
import com.simpotech.app.hlgg.ui.activity.QualityActivity;
import com.simpotech.app.hlgg.ui.activity.QualityQueryActivity;
import com.simpotech.app.hlgg.ui.activity.StockinActivity;
import com.simpotech.app.hlgg.ui.activity.StockinQueryActivity;
import com.simpotech.app.hlgg.ui.activity.StockoutActivity;
import com.simpotech.app.hlgg.ui.activity.StockoutQueryActivity;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;


/**
 * Created by longuto on 2016/12/28.
 * <p>
 * 主界面的Fragment
 */

public class MainFragment extends Fragment implements View.OnClickListener {

    public static MainFragment getInstance(String result) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("RESULT", result);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String TAG = "MainFragment";
    private Map<String, String[]> permissionMap;    //权限管理的Map
    private String[] permissions;   //当前用户的权限,默认为null
    private String mResult;  //权限值
    private SharedManager spManager;
    private Context context;    //上下文

    //左部控件
    @BindView(R.id.lly_left)
    LinearLayout mleftLly;
    @BindView(R.id.iv_left)
    ImageView mLeftIv;
    @BindView(R.id.tv_left)
    TextView mLeftTv;
    //中间控件
    @BindView(R.id.lly_middle)
    LinearLayout mMiddleLly;
    @BindView(R.id.iv_middle)
    ImageView mMiddleIv;
    @BindView(R.id.tv_middle)
    TextView mMiddleTv;
    //右边控件
    @BindView(R.id.lly_right)
    LinearLayout mRightLly;
    @BindView(R.id.iv_right)
    ImageView mRightIv;
    @BindView(R.id.tv_right)
    TextView mRightTv;

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
        getActivity().overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        spManager = new SharedManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, view);

        mResult = getArguments().getString("RESULT");   //获取传递过来的权限
        initTitle();    //初始化标题
        getPermissionToUnlock();    //获取当前用户权限
        initRefreshPtr();   //初始化PtrHandler
        return view;
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        showMiddleTv("首页");
        showRightIv(R.drawable.vector_main_back);
        getRightLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment fragment = new LoginFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id
                        .frag_content, fragment, "LoginFragment").commit();
            }
        });
    }

    /**
     * 获取用户传过来的权限,并解锁对应的按钮
     */
    private void getPermissionToUnlock() {
        permissionMap = PermissionManager.getPermissionMap();
        //        String mResult = "7";
        String username = spManager.getStringFromXml(SharedManager.USERNAME);
        switch (mResult) {
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

    /**
     * 显示中间的TextView
     */
    public void showMiddleTv(String content) {
        mMiddleTv.setText(content);
        mMiddleTv.setVisibility(View.VISIBLE);
    }

    /**
     * 显示右边的ImageView
     */
    public void showRightIv(int imageRes) {
        mRightIv.setImageResource(imageRes);
        mRightIv.setVisibility(View.VISIBLE);
    }

    /**
     * 获取右边LinearLayout
     */
    public LinearLayout getRightLly() {
        return mRightLly;
    }
}
