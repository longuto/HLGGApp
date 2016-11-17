package com.simpotech.app.hlgg.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.simpotech.app.hlgg.db.dao.InvoiceConStockoutDb;
import com.simpotech.app.hlgg.db.dao.InvoiceDb;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

/**
 * Created by longuto on 2016/11/14.
 * <p>
 * 发货单下的出库时构件信息数据库
 */

public class InvoiceConStockoutDbHelp extends SQLiteOpenHelper {
    private static final String DATABASENAME = "invoice_contruction_stockout.db"; //数据库名称
    private static final int VERSION = 1;    //数据库的版本号

    private String TAG = "InvoiceConStockoutDbHelp";

    public InvoiceConStockoutDbHelp() {
        super(UiUtils.getContext(), DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.i(TAG, "onCreate");
        String sql = "CREATE TABLE " + InvoiceConStockoutDb.TABLE_NAME + "(" +
                InvoiceConStockoutDb.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                InvoiceConStockoutDb.INVOICE_CODE + " TEXT NOT NULL, " +
                InvoiceConStockoutDb.STOCK_QTY + " TEXT, " + InvoiceConStockoutDb.CML_CODE + " TEXT, " +
                InvoiceConStockoutDb.NAME + " TEXT, " + InvoiceConStockoutDb.CODE + " TEXT, " +
                InvoiceConStockoutDb.SPEC + " TEXT, " + InvoiceConStockoutDb.BARCODE + " TEXT, " +
                InvoiceConStockoutDb.QTY + " TEXT, " + InvoiceConStockoutDb.SCANNER_PEOPLE + " TEXT, " +
                InvoiceConStockoutDb.SCANNER_TIME + " TEXT, FOREIGN KEY(" + InvoiceConStockoutDb
                .INVOICE_CODE + ") " + "REFERENCES " + InvoiceDb.TABLE_NAME + "(" + InvoiceDb
                .CODE + "))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.i(TAG, "onUpgrade");
    }
}
