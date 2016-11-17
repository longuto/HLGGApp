package com.simpotech.app.hlgg.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.simpotech.app.hlgg.db.dao.StockinConSubDb;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

/**
 * Created by longuto on 2016/11/16.
 * <p>
 * 入库扫描时候单批次的构件的集合
 */

public class StockinConSubDbHelp extends SQLiteOpenHelper {

    private static final String DATABASENAME = "stockin_sub_contruction.db"; //数据库名称
    private static final int VERSION = 1;    //数据库的版本号

    private String TAG = "StockinConSubDbHelp";

    public StockinConSubDbHelp() {
        super(UiUtils.getContext(), DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.i(TAG, "onCreate");
        String sql = "CREATE TABLE " + StockinConSubDb.TABLE_NAME + "(" +
                StockinConSubDb.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                StockinConSubDb.CML_CODE + " TEXT, " + StockinConSubDb.NAME + " TEXT, " +
                StockinConSubDb.CODE + " TEXT, " + StockinConSubDb.SPEC + " TEXT, " +
                StockinConSubDb.BARCODE + " TEXT, " + StockinConSubDb.QTY + " TEXT, " +
                StockinConSubDb.STOCK_QTY + " TEXT, " + StockinConSubDb.SCANNERPEOPLE + " TEXT, " +
                StockinConSubDb.SCANNERTIME + " TEXT, " + StockinConSubDb.ISERROR + " INTEGER, "
                + StockinConSubDb.MESSAGE + " TEXT, " + StockinConSubDb.PROLINEID
                + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.i(TAG, "onUpgrade");
    }
}
