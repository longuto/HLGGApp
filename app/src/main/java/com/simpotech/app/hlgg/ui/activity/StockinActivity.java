package com.simpotech.app.hlgg.ui.activity;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.NetStockinparse;
import com.simpotech.app.hlgg.business.ParseScanner;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.db.dao.ProLineDb;
import com.simpotech.app.hlgg.db.dao.StockinConSubDb;
import com.simpotech.app.hlgg.entity.DbProLineInfo;
import com.simpotech.app.hlgg.entity.StockConInfo;
import com.simpotech.app.hlgg.entity.StockinConInfo;
import com.simpotech.app.hlgg.scanner.CaptureActivity;
import com.simpotech.app.hlgg.ui.adapter.LocalStockinSubConAdapter;
import com.simpotech.app.hlgg.ui.adapter.SpinnerAdapter;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.simpotech.app.hlgg.ui.adapter.interfaces.OnRecyclerViewItemLongClickListener;
import com.simpotech.app.hlgg.util.GsonUtils;
import com.simpotech.app.hlgg.util.TimeUtils;
import com.simpotech.app.hlgg.util.UiUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StockinActivity extends BaseActivity {

    boolean isAllChoose;    //反选
    LocalStockinSubConAdapter mAdapter;    //适配器

    @BindView(R.id.edt_search_stockin)
    EditText mSearchStockinEdt;
    @BindView(R.id.recy_local_stockin)
    RecyclerView mLocalStockinRecy;

    @OnClick({R.id.btn_choose_all, R.id.btn_del_choose, R.id.btn_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choose_all:   //反选
                List<StockinConInfo> infosChs = mAdapter.data;
                if (isAllChoose) {
                    isAllChoose = false;
                    for (StockinConInfo info : infosChs) {
                        info.isChecked = false;
                    }
                } else {
                    isAllChoose = true;
                    for (StockinConInfo info : infosChs) {
                        info.isChecked = true;
                    }
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_del_choose:   //删除选中项
                List<StockinConInfo> infosDel = mAdapter.data;
                StockinConSubDb db = new StockinConSubDb();
                for (StockinConInfo info : infosDel) {
                    if (info.isChecked) {
                        int rows = db.delStockinConById(info.id);
                        if (rows < 1) {
                            UiUtils.showToast("条形码为" + info.barcode + "的构件删除失败");
                        }
                    }
                }
                mAdapter.data = db.getAllStockinCon();
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_search:
                String content = mSearchStockinEdt.getText().toString().trim();
                StockinConSubDb dbSub = new StockinConSubDb();
                mAdapter.data = dbSub.queryStockinConByInput(content);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void toSetContentView() {
        setContentView(R.layout.activity_stockin);
    }

    @Override
    protected void initTitle() {
        showLeftIv(R.drawable.vector_proline_back);
        showMiddleIv(R.drawable.vector_stockout_scan);
        showRightIv(R.drawable.vector_proline_get);
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
                NetStockinparse.getDataFromNet(mAdapter, context);
            }
        });
    }

    /** 扫描二维码返回的构件信息处理*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 6 && resultCode == 6) {
            String json = data.getStringExtra("SCAN");
            StockConInfo info = (StockConInfo) GsonUtils.fromJson(json, StockConInfo.class);
            if(info != null) {
                showDialogDetail(ParseScanner.stockCon2StockinInfo(info), false);
            }
        }
    }

    @Override
    protected void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager
                .VERTICAL, false);
        mLocalStockinRecy.setLayoutManager(manager);
        mAdapter = new LocalStockinSubConAdapter();
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showDialogDetail(mAdapter.data.get(position), true);
            }
        });
        mAdapter.setOnItemLongClickListener(new OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                StockinConInfo temp = mAdapter.getItemData(position);
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
        mLocalStockinRecy.setAdapter(mAdapter);

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

                StockinConInfo info = ParseScanner.scan2stockinContru(data);
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
     * @param info              构件信息
     * @param isEditContruction true表示之后的修改, false表示第一次的扫描
     */
    private void showDialogDetail(final StockinConInfo info, final boolean isEditContruction) {
        View view = View.inflate(context, R.layout.view_scanin_info, null);
        TextView barCodeTv = (TextView) view.findViewById(R.id.tv_barCode);
        TextView cmlCodeTv = (TextView) view.findViewById(R.id.tv_cml_code);
        TextView nameTv = (TextView) view.findViewById(R.id.tv_name);
        TextView codeTv = (TextView) view.findViewById(R.id.tv_code);
        TextView specTv = (TextView) view.findViewById(R.id.tv_spec);
        TextView qtyTv = (TextView) view.findViewById(R.id.tv_qty);
        final EditText stockinEdt = (EditText) view.findViewById(R.id.edt_stockin);
        Spinner prolineSpin = (Spinner) view.findViewById(R.id.spin_proline);

        barCodeTv.setText(info.barcode);
        cmlCodeTv.setText(info.cml_code);
        nameTv.setText(info.name);
        codeTv.setText(info.code);
        specTv.setText(info.spec);
        qtyTv.setText(info.qty);
        if (!isEditContruction) {
            stockinEdt.setText(info.qty);
        } else {
            stockinEdt.setText(info.stock_qty);
        }

        final List<DbProLineInfo> proLines = new ProLineDb().getAllProLines();
        SpinnerAdapter adapter = new SpinnerAdapter(proLines);
        prolineSpin.setAdapter(adapter);
        prolineSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spManager.putIntegerToXml(SharedManager.PROLINE_POSITION, position);//将选择的位置存入xml
                info.prolineId = proLines.get(position).proLineId;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //获取xml中存取的默认的生产线的id
        int prolinePos = spManager.getIntegerFromXml(SharedManager.PROLINE_POSITION);
        //如果当前位置大于存储的位置
        if(prolinePos >= proLines.size()) {
            prolinePos = 0;
        }
        prolineSpin.setSelection(prolinePos);
        info.prolineId = proLines.get(prolinePos).proLineId;

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
                        String stockinQty = stockinEdt.getText().toString().trim();
                        if (Integer.valueOf(stockinQty) > Integer.valueOf(info.qty)) {
                            UiUtils.showToast("出库件数不能大于清单数量");
                            return;
                        }
                        info.stock_qty = stockinQty;     //发货件数
                        if (!isEditContruction) {
                            info.scannerPeople = spManager.getStringFromXml(SharedManager.USERNAME);
                            info.scannerTime = TimeUtils.DateToStr(new Date());
                        }
                        StockinConSubDb db = new StockinConSubDb();
                        if(!isEditContruction) {
                            if(db.addStockinCon(info)) {
                                UiUtils.showToast("插入数据成功");
                            }else {
                                UiUtils.showToast("插入数据失败");
                            }
                        }else {
                            if(db.upDataByStockinQty(info) > 0) {
                                UiUtils.showToast("修改数据成功");
                            }else {
                                UiUtils.showToast("修改数据失败");
                            }
                        }
                        mAdapter.data = db.getAllStockinCon();
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
