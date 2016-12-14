package com.simpotech.app.hlgg.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.simpotech.app.hlgg.db.dao.StockinDb;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

/**
 * Created by longuto on 2016/11/18.
 * <p>
 * 入库成功后返回的入库单数据库
 */

public class StockinDbHelp extends SQLiteOpenHelper {

    private static final String DATABASENAME = "stockin.db"; //数据库名称
    private static final int VERSION = 1;    //数据库的版本号

    private String TAG = "StockinDbHelp";

    public StockinDbHelp() {
        super(UiUtils.getContext(), DATABASENAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.i(TAG, "onCreate");

        //创建数表
        String sql = "CREATE TABLE " + StockinDb.TABLE_NAME + "(" + StockinDb.ID + " INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, " + StockinDb.CODE + " TEXT NOT NULL UNIQUE, " +
                StockinDb.WO_CODE + " TEXT, " + StockinDb.CML_CODE + " TEXT, " + StockinDb
                .PROJ_NAME + " TEXT, " + StockinDb.ORGANNAME + " TEXT, " + StockinDb.PRODUCTLINE
                + " TEXT, " + StockinDb.LINENAME + " TEXT, " + StockinDb.ADDTIME + " TEXT, " + StockinDb.ADDUSERID + " TEXT," +
                StockinDb.ADDUSERNAME + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.i(TAG, "onUpgrade");
    }
}
