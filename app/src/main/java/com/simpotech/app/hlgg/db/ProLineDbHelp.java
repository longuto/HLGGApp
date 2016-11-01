package com.simpotech.app.hlgg.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.simpotech.app.hlgg.db.dao.ProLineDb;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

/**
 * Created by longuto on 2016/10/31.
 */

public class ProLineDbHelp extends SQLiteOpenHelper {
    public static final String DATABASENAME = "proline.db"; //数据库名称
    public static final int VERSION = 1;    //数据库的版本号
    private String TAG = "ProLineDbHelp";

    public ProLineDbHelp() {
        super(UiUtils.getContext(), DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.i(TAG, "onCreate");
        //创建数据库表
        String sql = "CREATE TABLE " + ProLineDb.TABLE_NAME + "(" + ProLineDb.ID + " INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, " + ProLineDb.DEPARTMENT_ID + " TEXT NOT NULL, " +
                ProLineDb.DEPARTMENT_NAME + " TEXT NOT NULL, " + ProLineDb.PROLINE_ID + " TEXT " +
                "NOT NULL, " + ProLineDb.PROLINE_NAME + " TEXT NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.i(TAG, "onUpgrade");
    }
}
