package com.simpotech.app.hlgg.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.simpotech.app.hlgg.db.dao.QualityDb;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

/**
 * Created by longuto on 2016/12/19.
 * <p>
 * 创建质检的数据库
 */

public class QualityDbHelp extends SQLiteOpenHelper {

    private static final String DATABASENAME = "quality.db"; //数据库名称
    private static final int VERSION = 1;    //数据库的版本号

    private static final String TAG = "QualityDbHelp";

    public QualityDbHelp() {
        super(UiUtils.getContext(), DATABASENAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.i(TAG, "onCreate");
        String sql = "CREATE TABLE " + QualityDb.TABLE_NAME + "(" + QualityDb.ID + " INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, " + QualityDb.CODE + " TEXT NOT NULL UNIQUE, " +
                QualityDb.PROJECT_NAME + " TEXT, " + QualityDb.TESTUSER + " TEXT, " +
                QualityDb.CJDATA + " TEXT, " + QualityDb.USERID + " TEXT, " + QualityDb.CML_CODE
                + " TEXT, " + QualityDb.CONTRUCTION_CODE + " TEXT, " + QualityDb.ORGANNAME +
                " TEXT, " + QualityDb.BARCODE + " TEXT, " + QualityDb.QTY + " TEXT, " + QualityDb
                .SPEC + " TEXT, " + QualityDb.FACADE_RESULT + " TEXT, " + QualityDb.FACADE_REMARK
                + " TEXT, " + QualityDb.FACADE_PIC1 + " TEXT, " + QualityDb.FACADE_PIC2 + " TEXT," +
                QualityDb.FACADE_PIC3 + " TEXT, " + QualityDb.FACADE_PIC4 + " TEXT, " + QualityDb
                .SIZE_RESULT + " TEXT, " + QualityDb.SIZE_REMARK + " TEXT, " + QualityDb.SIZE_PIC1 +
                " TEXT, " + QualityDb.SIZE_PIC2 + " TEXT, " + QualityDb.SIZE_PIC3 + " TEXT, " +
                QualityDb.SIZE_PIC4 + " TEXT, " + QualityDb.WELD_RESULT + " TEXT, " +
                QualityDb.WELD_REMARK + " TEXT, " + QualityDb.WELD_PIC1 + " TEXT, " + QualityDb
                .WELD_PIC2 + " TEXT, " + QualityDb.WELD_PIC3 + " TEXT, " + QualityDb.WELD_PIC4 +
                " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.i(TAG, "onUpgrade");
    }
}
