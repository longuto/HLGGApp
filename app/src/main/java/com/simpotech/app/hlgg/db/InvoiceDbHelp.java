package com.simpotech.app.hlgg.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.simpotech.app.hlgg.db.dao.InvoiceDb;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

import static android.transition.Fade.IN;

/**
 * Created by longuto on 2016/11/8.
 * 发货单数据库
 */

public class InvoiceDbHelp extends SQLiteOpenHelper {

    private static final String DATABASENAME = "invoice.db"; //数据库名称
    private static final int VERSION = 1;    //数据库的版本号

    private String TAG = "InvoiceDbHelp";

    public InvoiceDbHelp() {
        super(UiUtils.getContext(), DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.i(TAG, "onCreate");
        //创建数表
        String sql = "CREATE TABLE " + InvoiceDb.TABLE_NAME + "(" + InvoiceDb.ID + " INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, " + InvoiceDb.CODE + " TEXT NOT NULL UNIQUE, " +
                InvoiceDb.WO_CODE + " TEXT, " + InvoiceDb.CML_CODE + " TEXT, " + InvoiceDb.PROJ_NAME +
                " TEXT, " + InvoiceDb.ORGAN_ID + " TEXT, " + InvoiceDb.STORAGE_CODE + " TEXT, " + InvoiceDb
                .ORGANNAME + " TEXT, " + InvoiceDb.SALENAME + " TEXT, " + InvoiceDb.CJDATE +
                " TEXT, " + InvoiceDb.ADDUSERID + " TEXT, " + InvoiceDb.ADDUSERNAME + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.i(TAG, "onUpgrade");
    }
}
