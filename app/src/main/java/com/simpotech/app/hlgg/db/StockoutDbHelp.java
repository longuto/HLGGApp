package com.simpotech.app.hlgg.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.simpotech.app.hlgg.db.dao.StockoutDb;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

/**
 * Created by longuto on 2016/11/15.
 * <p>
 * 出库单数据库帮助类
 */

public class StockoutDbHelp extends SQLiteOpenHelper {

    private static final String DATABASENAME = "stockout.db"; //数据库名称
    private static final int VERSION = 1;    //数据库的版本号

    private String TAG = "StockoutDbHelp";

    public StockoutDbHelp() {
        super(UiUtils.getContext(), DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.i(TAG, "onCreate");
        //创建数表
        String sql = "CREATE TABLE " + StockoutDb.TABLE_NAME + "(" + StockoutDb.ID + " INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, " + StockoutDb.CODE + " TEXT NOT NULL UNIQUE, " +
                StockoutDb.INVOICE_CODE + " TEXT NOT NULL UNIQUE, " + StockoutDb.PROJ_NAME + " TEXT, " + StockoutDb
                .ORGAN_NAME + " TEXT, " + StockoutDb.SALENAME + " TEXT, " + StockoutDb.ADDTIME + " TEXT, " +
                StockoutDb.ADDUSERID + " TEXT, " + StockoutDb.ADDUSERNAME + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.i(TAG, "onUpgrade");
    }
}
