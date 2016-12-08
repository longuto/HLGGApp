package com.simpotech.app.hlgg.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.NetStockoutParse;
import com.simpotech.app.hlgg.business.ParseScanner;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.db.dao.InvoiceConStockoutDb;
import com.simpotech.app.hlgg.db.dao.InvoiceDb;
import com.simpotech.app.hlgg.entity.StockConInfo;
import com.simpotech.app.hlgg.entity.StockoutConInfo;
import com.simpotech.app.hlgg.entity.net.NetInvoiceInfo;
import com.simpotech.app.hlgg.scanner.CaptureActivity;
import com.simpotech.app.hlgg.ui.adapter.LocalInvoiceStockDetailAdapter;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemLongClickListener;
import com.simpotech.app.hlgg.util.GsonUtils;
import com.simpotech.app.hlgg.util.TimeUtils;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StockoutDetailActivity extends BaseActivity {

    private static final String TAG = "StockoutDetailActivity";

    NetInvoiceInfo netInvoiceInfo;  //出库单信息
    LocalInvoiceStockDetailAdapter mAdapter; //适配器

    boolean isAllChoose;    //是否全选

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

    @OnClick(R.id.btn_choose_all)
    public void chooseAll() {
        List<StockoutConInfo> infos = mAdapter.data;
        if (isAllChoose) {
            isAllChoose = false;
            for (StockoutConInfo info : infos) {
                info.isChecked = false;
            }
        } else {
            isAllChoose = true;
            for (StockoutConInfo info : infos) {
                info.isChecked = true;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_del_choose)
    public void delChoose() {
        List<StockoutConInfo> infos = mAdapter.data;
        InvoiceConStockoutDb db = new InvoiceConStockoutDb();
        for (StockoutConInfo info : infos) {
            if (info.isChecked) {
                int rows = db.delInvoiceConById(info.id);
                if (rows < 1) {
                    UiUtils.showToast("条形码为" + info.barcode + "的构件删除失败");
                }
            }
        }
        mAdapter.data = db.getInvoiceConByInvoiceCode(netInvoiceInfo.code);
        mAdapter.notifyDataSetChanged();
    }

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
                Intent intent = new Intent(context, CaptureActivity.class);
                startActivityForResult(intent, 6);
            }
        });
        getRightLly().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<StockoutConInfo> stockoutConInfos = new InvoiceConStockoutDb()
                        .getInvoiceConByInvoiceCode(netInvoiceInfo.code);
                NetStockoutParse.getDataFromNet(netInvoiceInfo, stockoutConInfos);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 6 && resultCode == 6) {
            String json = data.getStringExtra("SCAN");
            StockConInfo info = (StockConInfo) GsonUtils.fromJson(json, StockConInfo.class);
            if(info != null) {
                showDialogDetail(ParseScanner.stockCon2StockoutInfo(info), false);
            }
        }
    }

    @Override
    protected void initData() {
        String code = getIntent().getStringExtra("CODE");
        netInvoiceInfo = new InvoiceDb().queryInvoicesByCode(code);
        codeTv.setText("发货单号 :" + netInvoiceInfo.code);
        projNameTv.setText(netInvoiceInfo.proj_name);
        organNameTv.setText(netInvoiceInfo.organName);
        saleNameTv.setText(netInvoiceInfo.saleName);

        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager
                .VERTICAL, false);
        stockoutDetailRecy.setLayoutManager(manager);
        mAdapter = new LocalInvoiceStockDetailAdapter(netInvoiceInfo.code, context);
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showDialogDetail(mAdapter.data.get(position), true);
            }
        });
        mAdapter.setOnItemLongClickListener(new OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                StockoutConInfo temp = mAdapter.data.get(position);
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.ckb_choose);
                if (temp.isChecked) {
                    checkBox.setChecked(false);
                    temp.isChecked = false;
                } else {
                    checkBox.setChecked(true);
                    temp.isChecked = true;
                }
            }
        });
        stockoutDetailRecy.setAdapter(mAdapter);

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
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(RECE_DATA_ACTION)) {
                String data = intent.getStringExtra("se4500");

                StockoutConInfo info = ParseScanner.scan2stockoutContru(data);
                if (info != null) {
                    //显示对话框
                    showDialogDetail(info, false);
                } else {
                    UiUtils.showToast("扫描的不是构件信息");
                }
            }
        }
    };

    /**
     * 显示扫描后的弹框信息
     *
     * @param isEditContruction true表示之后的修改, false表示第一次的扫描
     * @param info              构件信息
     */
    private void showDialogDetail(final StockoutConInfo info, final boolean isEditContruction) {
        View view = View.inflate(context, R.layout.view_scanout_info, null);
        TextView barCodeTv = (TextView) view.findViewById(R.id.tv_barCode);
        final TextView cmlCodeTv = (TextView) view.findViewById(R.id.tv_cml_code);
        TextView nameTv = (TextView) view.findViewById(R.id.tv_name);
        TextView codeTv = (TextView) view.findViewById(R.id.tv_code);
        TextView specTv = (TextView) view.findViewById(R.id.tv_spec);
        TextView qtyTv = (TextView) view.findViewById(R.id.tv_qty);
        final EditText stockoutEdt = (EditText) view.findViewById(R.id.edt_stockout);

        barCodeTv.setText(info.barcode);
        cmlCodeTv.setText(info.cml_code);
        nameTv.setText(info.name);
        codeTv.setText(info.code);
        specTv.setText(info.spec);
        qtyTv.setText(info.qty);
        if (!isEditContruction) {
            stockoutEdt.setText(info.qty);
        } else {
            stockoutEdt.setText(info.stock_qty);
        }

        //扫描的构件信息是否是本清单的构件
        final boolean isThisCmlCode = info.cml_code.equals(netInvoiceInfo.cml_code);
        if (!isThisCmlCode) {
            cmlCodeTv.setBackgroundColor(Color.RED);
        }

        new AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //如果扫描的构件清单不等于当前的构件清单
                        if (!isThisCmlCode) {
                            UiUtils.showToast("采集的构件号不是本单的构件");
                            return;
                        }
                        String stockoutQty = stockoutEdt.getText().toString().trim();
                        if (Integer.valueOf(stockoutQty) > Integer.valueOf(info.qty)) {
                            UiUtils.showToast("出库件数不能大于清单数量");
                            return;
                        }
                        info.stock_qty = stockoutQty;     //发货件数
                        if (!isEditContruction) {
                            info.scannerPeople = spManager.getStringFromXml(SharedManager.USERNAME);
                            info.scannerTime = TimeUtils.DateToStr(new Date());
                            info.invoice_code = netInvoiceInfo.code;    //发货单号
                        }
                        InvoiceConStockoutDb db = new InvoiceConStockoutDb();
                        // 如果是扫描时显示,则将数据数据插入数据库
                        if (!isEditContruction) {
                            // 将信息存储至数据库
                            if (db.addInvoiceContruction(info)) {
                                UiUtils.showToast("插入数据成功");
                            } else {
                                UiUtils.showToast("插入数据失败");
                            }
                        // 如果是修改,则修改出库数量
                        } else {
                            if (db.upDataByStockoutQty(info) > 0) {
                                UiUtils.showToast("修改成功");
                            } else {
                                UiUtils.showToast("修改失败");
                            }
                        }
                        mAdapter.data = db.getInvoiceConByInvoiceCode(netInvoiceInfo.code);
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .create()
                .show();
    }

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
