package com.simpotech.app.hlgg.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.db.dao.InvoiceDb;
import com.simpotech.app.hlgg.entity.net.NetInvoiceInfo;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class StockoutDetailActivity extends BaseActivity {

    public static final int REQUESTCODE = 6;   //请求扫码Activity的请求码
    private static final String TAG = "StockoutDetailActivity";

    @BindView(R.id.tv_code)
    TextView codeTv;
    @BindView(R.id.tv_proj_name)
    TextView projNameTv;
    @BindView(R.id.tv_organName)
    TextView organNameTv;
    @BindView(R.id.tv_saleName)
    TextView saleNameTv;
    @BindView(R.id.recy_detail_stockout)
    RecyclerView stockoutDetailRecy;

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_stockout_detail);
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
            }
        });
        getMiddleLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScan(); //调用红外扫描
            }
        });
        getRightLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initData() {
        String code = getIntent().getStringExtra("CODE");
        NetInvoiceInfo netInvoiceInfo = new InvoiceDb().queryInvoicesByCode(code);
        codeTv.setText(netInvoiceInfo.code);
        projNameTv.setText(netInvoiceInfo.proj_name);
        organNameTv.setText(netInvoiceInfo.organName);
        saleNameTv.setText(netInvoiceInfo.saleName);


        // ------------------------------- by 红外 ---------------------------------

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
        public void onReceive(android.content.Context context,
                              android.content.Intent intent) {
            String action = intent.getAction();
            if (action.equals(RECE_DATA_ACTION)) {
                String data = intent.getStringExtra("se4500");
                data.replace("\n","ACTION");
                LogUtils.i(TAG, data);
                UiUtils.showToast(data);
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
        Intent intent = new Intent();
        intent.setAction(STOP_SCAN);
        sendBroadcast(intent);
        unregisterReceiver(receiver);
        super.onDestroy();
    }


}
