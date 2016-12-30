package com.simpotech.app.hlgg.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.simpotech.app.hlgg.db.dao.StockoutContruDb;
import com.simpotech.app.hlgg.db.dao.StockoutDb;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

/**
 * Created by longuto on 2016/11/15.
 * <p>
 * 出库单下返回的构件信息数据库
 */

public class StockoutContruDbHelp extends SQLiteOpenHelper {
    private static final String DATABASENAME = "stockout_contruction.db"; //数据库名称
    private static final int VERSION = 1;    //数据库的版本号

    private String TAG = "StockoutContruDbHelp";

    public StockoutContruDbHelp() {
        super(UiUtils.getContext(), DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.i(TAG, "onCreate");
        String sql = "CREATE TABLE " + StockoutContruDb.TABLE_NAME + "(" + StockoutContruDb.ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + StockoutContruDb.STOCKOUT_CODE + " TEXT " +
                "NOT NULL, " + StockoutContruDb.CONTRUCTION_CODE + " TEXT, " + StockoutContruDb
                .MATERIALNAME + " TEXT, " + StockoutContruDb.LENGTH + " TEXT, " + StockoutContruDb.SINGLE
                + " TEXT, " + StockoutContruDb.QTY + " TEXT, " + StockoutContruDb.TONNAGE + " TEXT, " +
                StockoutContruDb.INVOICE_QTY + " TEXT, " + StockoutContruDb.INVOICE_TONNAGE +
                " TEXT, " + StockoutContruDb.SPEC + " TEXT, " + StockoutContruDb.BARCODE + " TEXT," +
                " FOREIGN KEY(" + StockoutContruDb.STOCKOUT_CODE + ") " + "REFERENCES " +
                StockoutDb.TABLE_NAME + "(" + StockoutDb.CODE + "))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.i(TAG, "onUpgrade");
    }
}
