package com.simpotech.app.hlgg.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.simpotech.app.hlgg.db.StockinContruDbHelp;
import com.simpotech.app.hlgg.entity.net.NetStockinInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longuto on 2016/11/18.
 * <p>
 * 入库加载网络成功后,入库单下的构件信息的数据库表管理类
 */

public class StockinContruDb {

    public static final String TABLE_NAME = "t_stockin_contruction";    //表名称

    public static final String ID = "_id";  //主键
    public static final String STOCKIN_CODE = "sc_stockin_code";  //入库单号
    public static final String CONTRUCTION_CODE = "sc_contruction_code";  //构件编码
    public static final String CML_CODE = "sc_cml_code";  //构件清单编码
    public static final String SPEC = "sc_spec";  //规格
    public static final String QTY = "sc_qty";  //入库数量
    public static final String BARCODE = "sc_barCode";  //条码

    public StockinContruDbHelp dbHelp;

    public StockinContruDb() {
        dbHelp = new StockinContruDbHelp();
    }

    /**
     * 根据指定的入库单号查询对应构件信息的游标
     *
     * @param code 指定的入库单号
     * @return 游标
     */
    public Cursor queryStockinConByStockinCode(String code) {
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, STOCKIN_CODE, CONTRUCTION_CODE,
                CML_CODE, SPEC, QTY, BARCODE}, STOCKIN_CODE + "=?", new String[]{code}, null,
                null, null);
        return cursor;
    }

    /**
     * 根据指定的入库货单号返回对应所有构件信息的集合
     *
     * @param code 指定的入库单号
     * @return 构件信息集合
     */
    public List<NetStockinInfo.DetailsBean> getStockinConByStockinCode(String code) {
        List<NetStockinInfo.DetailsBean> stockinContructions = new ArrayList<>();
        Cursor cursor = queryStockinConByStockinCode(code);
        NetStockinInfo.DetailsBean temp = null;
        while (cursor.moveToNext()) {
            temp = new NetStockinInfo.DetailsBean();
            temp.stockin_code = cursor.getString(cursor.getColumnIndex(STOCKIN_CODE));
            temp.contruction_code = cursor.getString(cursor.getColumnIndex(CONTRUCTION_CODE));
            temp.cml_code = cursor.getString(cursor.getColumnIndex(CML_CODE));
            temp.spec = cursor.getString(cursor.getColumnIndex(SPEC));
            temp.qty = cursor.getString(cursor.getColumnIndex(QTY));
            temp.barCode = cursor.getString(cursor.getColumnIndex(BARCODE));

            stockinContructions.add(temp);
        }
        cursor.close();
        return stockinContructions;
    }

    /**
     * 将入库单下的构件信息添加到对应的数据库中
     *
     * @param bean 相关构件信息的实体类
     * @return 插入成功返回true, 否则false
     */
    public boolean addStockinContruction(NetStockinInfo.DetailsBean bean) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STOCKIN_CODE, bean.stockin_code);
        values.put(CONTRUCTION_CODE, bean.contruction_code);
        values.put(CML_CODE, bean.cml_code);
        values.put(SPEC, bean.spec);
        values.put(QTY, bean.qty);
        values.put(BARCODE, bean.barCode);
        long rowNo = db.insert(TABLE_NAME, null, values);
        db.close();
        if (rowNo > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除指定入库单号的构件信息数据库表单项
     *
     * @param code 指定的入库单号
     * @return 返回删除的行数
     */
    public int delStockinConByStockinCode(String code) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, STOCKIN_CODE + "=?", new String[]{code});
        db.close();
        return rows;
    }

    /**
     * 删除表中所有数据
     * @return 删除的行数
     */
    public int delAllData() {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, null, null);
        db.close();
        return rows;
    }
}
