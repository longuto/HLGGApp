package com.simpotech.app.hlgg.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.NetQualityParse;
import com.simpotech.app.hlgg.business.ParseScanner;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.entity.StockConInfo;
import com.simpotech.app.hlgg.entity.submit.SubQualityInfo;
import com.simpotech.app.hlgg.scanner.CaptureActivity;
import com.simpotech.app.hlgg.ui.adapter.QualityPagerAdapter;
import com.simpotech.app.hlgg.util.GsonUtils;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class QualityActivity extends BaseActivity {
    SharedManager mQuaSp;      //质检默认配置文件
    QualityPagerAdapter mAdapter;   //ViewPager的适配器
    StockConInfo mStockConInfo; //扫描后的构件信息
    int mStockConQty;   //清单数量

    @BindView(R.id.tv_code)
    TextView codeTv;
    @BindView(R.id.tv_cml_code)
    TextView cmlCodeTv;
    @BindView(R.id.tv_name)
    TextView nameTv;
    @BindView(R.id.tv_spec)
    TextView specTv;
    @BindView(R.id.edt_qty)
    EditText qtyEdt;
    @BindView(R.id.tv_bar_code)
    TextView barCodeTv;
    @BindView(R.id.vp_quality)
    ViewPager qualityVp;
    @BindView(R.id.tabs_quality)
    TabLayout qualityTabs;
    @BindView(R.id.card_control)
    CardView controlCard;
    @BindView(R.id.pgb_net)
    ProgressBar progressBar;    //进度条

    @OnClick(R.id.btn_empty)
    public void clearData(View v) {
        clearQualityWidget();
        clearQualityXml();
    }

    //清除控件中的内容
    private void clearQualityWidget() {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View childView = qualityVp.getChildAt(i);
            if (childView != null) {
                Spinner spin = (Spinner) childView.findViewById(R.id.sp_result);
                EditText ed = (EditText) childView.findViewById(R.id.edt_explain);
                ImageView iv1 = (ImageView) childView.findViewById(R.id.pic01);
                ImageView iv2 = (ImageView) childView.findViewById(R.id.pic02);
                ImageView iv3 = (ImageView) childView.findViewById(R.id.pic03);
                ImageView iv4 = (ImageView) childView.findViewById(R.id.pic04);
                spin.setSelection(0);
                ed.setText("");
                iv1.setImageResource(R.drawable.vector_quality_camera);
                iv2.setImageResource(R.drawable.vector_quality_camera);
                iv3.setImageResource(R.drawable.vector_quality_camera);
                iv4.setImageResource(R.drawable.vector_quality_camera);
            }
        }
    }


    /**
     * 清除XMl中的内容
     */
    private void clearQualityXml() {
        mQuaSp.putStringToXml(SharedManager.FACADE_RESULT, "合格");
        mQuaSp.putStringToXml(SharedManager.FACADE_REMARK, "");
        mQuaSp.putBooleanToXml(SharedManager.FACADE_PIC1, false);
        mQuaSp.putBooleanToXml(SharedManager.FACADE_PIC2, false);
        mQuaSp.putBooleanToXml(SharedManager.FACADE_PIC3, false);
        mQuaSp.putBooleanToXml(SharedManager.FACADE_PIC4, false);
        mQuaSp.putStringToXml(SharedManager.SIZE_RESULT, "合格");
        mQuaSp.putStringToXml(SharedManager.SIZE_REMARK, "");
        mQuaSp.putBooleanToXml(SharedManager.SIZE_PIC1, false);
        mQuaSp.putBooleanToXml(SharedManager.SIZE_PIC2, false);
        mQuaSp.putBooleanToXml(SharedManager.SIZE_PIC3, false);
        mQuaSp.putBooleanToXml(SharedManager.SIZE_PIC4, false);
        mQuaSp.putStringToXml(SharedManager.WELD_RESULT, "合格");
        mQuaSp.putStringToXml(SharedManager.WELD_REMARK, "");
        mQuaSp.putBooleanToXml(SharedManager.WELD_PIC1, false);
        mQuaSp.putBooleanToXml(SharedManager.WELD_PIC2, false);
        mQuaSp.putBooleanToXml(SharedManager.WELD_PIC3, false);
        mQuaSp.putBooleanToXml(SharedManager.WELD_PIC4, false);
    }

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_quality);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showMiddleIv(R.drawable.vector_stockout_scan);
        showRightIv(R.drawable.vector_proline_get);
        showRightTv("提交");
        getLeftLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.activity_back_enter, R.anim.activity_back_exit);
            }
        });
        getMiddleLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CaptureActivity.class);
                startActivityForResult(intent, 6);
                overridePendingTransition(R.anim.activity_top, R.anim.activity_top_exit);
            }
        });
        getRightLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStockConInfo == null) {
                    UiUtils.showToast("请扫描获取构件信息");
                    return;
                }
                if(TextUtils.isEmpty(qtyEdt.getText().toString().trim())) {
                    UiUtils.showToast("质检数量不应为空");
                }
                if (Integer.valueOf(qtyEdt.getText().toString().trim()) > mStockConQty) {
                    UiUtils.showToast("质检数量不应大于清单数量");
                    return;
                }
                if (TextUtils.isEmpty(mQuaSp.getStringFromXml(SharedManager.FACADE_REMARK)) ||
                        TextUtils.isEmpty(mQuaSp.getStringFromXml(SharedManager.SIZE_REMARK)) ||
                        TextUtils.isEmpty(mQuaSp.getStringFromXml(SharedManager.WELD_REMARK))) {
                    UiUtils.showToast("请填写相关质检说明");
                    return;
                }

                SubQualityInfo info = new SubQualityInfo();
                info.userId = spManager.getStringFromXml(SharedManager.USERID);
                info.cml_code = mStockConInfo.cml_code;
                info.contruction_code = mStockConInfo.code;
                info.barCode = mStockConInfo.barcode;
                info.spec = mStockConInfo.spec;
                info.qty = qtyEdt.getText().toString().trim();

                info.detail = new ArrayList<SubQualityInfo.DetailBean>();
                SubQualityInfo.DetailBean facade = new SubQualityInfo.DetailBean();
                facade.itemsFlag = "1";
                switch (mQuaSp.getStringFromXml(SharedManager.FACADE_RESULT, "")) {
                    case "不合格":
                        facade.testResult = "2";
                        break;
                    case "合格":
                    default:
                        facade.testResult = "1";
                        break;
                }
                facade.remark = mQuaSp.getStringFromXml(SharedManager.FACADE_REMARK);
                info.detail.add(facade);

                SubQualityInfo.DetailBean size = new SubQualityInfo.DetailBean();
                size.itemsFlag = "2";
                switch (mQuaSp.getStringFromXml(SharedManager.SIZE_RESULT, "")) {
                    case "不合格":
                        size.testResult = "2";
                        break;
                    case "合格":
                    default:
                        size.testResult = "1";
                        break;
                }
                size.remark = mQuaSp.getStringFromXml(SharedManager.SIZE_REMARK);
                info.detail.add(size);

                SubQualityInfo.DetailBean weld = new SubQualityInfo.DetailBean();
                weld.itemsFlag = "3";
                switch (mQuaSp.getStringFromXml(SharedManager.WELD_RESULT, "")) {
                    case "不合格":
                        weld.testResult = "2";
                        break;
                    case "合格":
                    default:
                        weld.testResult = "1";
                        break;
                }
                weld.remark = mQuaSp.getStringFromXml(SharedManager.WELD_REMARK);
                info.detail.add(weld);

                NetQualityParse.qualitySave(info);  //调用质检录入
            }
        });
    }

    /**
     * 扫描二维码返回的构件信息处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6 && resultCode == 6) {
            String json = data.getStringExtra("SCAN");
            StockConInfo info = (StockConInfo) GsonUtils.fromJson(json, StockConInfo.class);

            showControlInfo(info);
        }
    }

    /**
     * 将构件信息显示在对应的控件中
     */
    private void showControlInfo(StockConInfo info) {
        mStockConInfo = info;
        mStockConQty = Integer.valueOf(info.qty);

        controlCard.setVisibility(View.VISIBLE);
        codeTv.setText(info.code);
        cmlCodeTv.setText(info.cml_code);
        nameTv.setText(info.name);
        qtyEdt.setText(info.qty);
        specTv.setText(info.spec);
        barCodeTv.setText(info.barcode);
    }

    @Override
    protected void initData() {
        mAdapter = new QualityPagerAdapter(getSupportFragmentManager());
        qualityVp.setAdapter(mAdapter);
        qualityTabs.setupWithViewPager(qualityVp);

        mQuaSp = new SharedManager(SharedManager.QUALITY_CONFIG_NAME);


        //-------------------------------------by 红外

        IntentFilter iFilter = new IntentFilter();
        //注册系统广播  接受扫描到的数据
        iFilter.addAction(RECE_DATA_ACTION);
        registerReceiver(receiver, iFilter);
    }

    //接受广播
    private String RECE_DATA_ACTION = "com.se4500.onDecodeComplete";
    //调用扫描广播
    private String START_SCAN_ACTION = "com.geomobile.se4500barcode";
    private String STOP_SCAN = "com.geomobile.se4500barcode.poweroff";

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(RECE_DATA_ACTION)) {
                String data = intent.getStringExtra("se4500");

                StockConInfo info = ParseScanner.scan2stockContru(data);    //解析红外扫描后的构件
                if (info != null) {
                    showControlInfo(info);
                } else {
                    UiUtils.showToast("扫描的不是构件信息");
                }
            }
        }
    };

    /**
     * 发送广播  调用系统扫描
     */
    private void startScan() {
        Intent intent = new Intent();
        intent.setAction(START_SCAN_ACTION);
        sendBroadcast(intent, null);
    }

    //    private Timer timer = new Timer();
    //    private void repeatScan() {
    //        if (timer == null) {
    //            timer = new Timer();
    //        }
    //        timer.scheduleAtFixedRate(new MyTask(), 100, 4 * 1000);
    //    }
    //
    //    private void cancelRepeat() {
    //        if (timer != null) {
    //            timer.cancel();
    //            timer = null;
    //        }
    //    };
    //
    //    public class MyTask extends TimerTask {
    //        @Override
    //        public void run() {
    //            startScan();
    //        }
    //    }

    @Override
    protected void onDestroy() {
        clearQualityXml();  //清除Xml中的内容

        Intent intent = new Intent();
        intent.setAction(STOP_SCAN);
        sendBroadcast(intent);
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
