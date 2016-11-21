package com.simpotech.app.hlgg.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.simpotech.app.hlgg.db.dao.StockinContruDb;
import com.simpotech.app.hlgg.db.dao.StockinDb;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

/**
 * Created by longuto on 2016/11/18.
 *
 * 加载网络成功后,返回的入库单下的构件清单的信息的数据库帮助类
 */

public class StockinContruDbHelp extends SQLiteOpenHelper {

    private static final String DATABASENAME = "stockin_contruction.db"; //数据库名称
    private static final int VERSION = 1;    //数据库的版本号

    private String TAG = "StockinContruDbHelp";

    public StockinContruDbHelp() {
        super(UiUtils.getContext(), DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.i(TAG, "onCreate");
        String sql = "CREATE TABLE " + StockinContruDb.TABLE_NAME + "(" + StockinContruDb.ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + StockinContruDb.STOCKIN_CODE + " TEXT " +
                "NOT NULL, " + StockinContruDb.CONTRUCTION_CODE + " TEXT, " + StockinContruDb
                .CML_CODE + " TEXT, " + StockinContruDb.SPEC + " TEXT, " + StockinContruDb.QTY
                + " TEXT, " + StockinContruDb.BARCODE + " TEXT, FOREIGN KEY(" + StockinContruDb.STOCKIN_CODE +
                ") " + "REFERENCES " + StockinDb.TABLE_NAME + "(" + StockinDb.CODE + "))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.i(TAG, "onUpgrade");
    }
}
