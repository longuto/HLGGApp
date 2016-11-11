package com.simpotech.app.hlgg.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.simpotech.app.hlgg.db.dao.InvoiceContructionDb;
import com.simpotech.app.hlgg.db.dao.InvoiceDb;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

import static android.R.attr.version;

/**
 * Created by longuto on 2016/11/8.
 * 发货单附带的构件的相关信息的数据库
 */

public class InvoiceContructionDbHelp extends SQLiteOpenHelper {
    private static final String DATABASENAME = "invoice_contruction.db"; //数据库名称
    private static final int VERSION = 1;    //数据库的版本号

    private String TAG = "InvoiceContructionDbHelp";

    public InvoiceContructionDbHelp() {
        super(UiUtils.getContext(), DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.i(TAG, "onCreate");
        String sql = "CREATE TABLE " + InvoiceContructionDb.TABLE_NAME + "(" +
                InvoiceContructionDb.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                InvoiceContructionDb.INVOICE_CODE + " TEXT NOT NULL, " +
                InvoiceContructionDb.CONTRUCTION_CODE + " , " + InvoiceContructionDb
                .SPEC + " , " + InvoiceContructionDb.QTY + " , " +
                InvoiceContructionDb.TONNAGE + " , " + InvoiceContructionDb.BARCODE
                + " , FOREIGN KEY(" + InvoiceContructionDb.INVOICE_CODE + ") " +
                "REFERENCES " + InvoiceDb.TABLE_NAME + "(" + InvoiceDb.CODE + "))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.i(TAG, "onUpgrade");
    }
}
