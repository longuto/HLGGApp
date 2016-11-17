package com.simpotech.app.hlgg.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.simpotech.app.hlgg.db.StockoutContruDbHelp;
import com.simpotech.app.hlgg.entity.net.NetStockoutInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longuto on 2016/11/15.
 * <p>
 * 出库单下的构件信息的数据库
 */

public class StockoutContruDb {

    public static final String TABLE_NAME = "t_stockout_contruction";    //表名称

    public static final String ID = "_id";  //主键
    public static final String STOCKOUT_CODE = "sc_stockout_code";  //出库单号
    public static final String CONTRUCTION_CODE = "sc_contruction_code";  //构件编码
    public static final String MATERIALNAME = "sc_materialName";  //材质
    public static final String LENGTH = "sc_length";  //长度
    public static final String SINGLE = "sc_single";  //单重
    public static final String QTY = "sc_qty";  //出库件数
    public static final String TONNAGE = "sc_tonnage";  //出库重量
    public static final String INVOICE_QTY = "sc_invoice_qty";  //发货件数
    public static final String INVOICE_TONNAGE = "sc_invoice_tonnage";  //发货重量
    public static final String BARCODE = "sc_barCode";  //条码

    public StockoutContruDbHelp dbHelp;

    public StockoutContruDb() {
        dbHelp = new StockoutContruDbHelp();
    }

    /**
     * 根据指定的出库单号查询对应构件信息的游标
     *
     * @param code 指定的出库单号
     * @return 游标
     */
    public Cursor queryStockoutConByStockoutCode(String code) {
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, STOCKOUT_CODE, CONTRUCTION_CODE,
                MATERIALNAME, LENGTH, SINGLE, QTY, TONNAGE, INVOICE_QTY, INVOICE_TONNAGE,
                BARCODE}, STOCKOUT_CODE + "=?", new String[]{code}, null, null, null);
        return cursor;
    }

    /**
     * 根据指定的出库货单号返回对应所有构件信息的集合
     *
     * @param code 指定的出库单号
     * @return 构件信息集合
     */
    public List<NetStockoutInfo.DetailsBean> getStockoutConByStockoutCode(String code) {
        List<NetStockoutInfo.DetailsBean> stockoutContructions = new ArrayList<>();
        Cursor cursor = queryStockoutConByStockoutCode(code);
        NetStockoutInfo.DetailsBean temp = null;
        while (cursor.moveToNext()) {
            temp = new NetStockoutInfo.DetailsBean();
            temp.stockout_code = cursor.getString(cursor.getColumnIndex(STOCKOUT_CODE));
            temp.contruction_code = cursor.getString(cursor.getColumnIndex(CONTRUCTION_CODE));
            temp.materialName = cursor.getString(cursor.getColumnIndex(MATERIALNAME));
            temp.length = cursor.getString(cursor.getColumnIndex(LENGTH));
            temp.single = cursor.getString(cursor.getColumnIndex(SINGLE));
            temp.qty = cursor.getString(cursor.getColumnIndex(QTY));
            temp.tonnage = cursor.getString(cursor.getColumnIndex(TONNAGE));
            temp.invoice_qty = cursor.getString(cursor.getColumnIndex(INVOICE_QTY));
            temp.invoice_tonnage = cursor.getString(cursor.getColumnIndex(INVOICE_TONNAGE));
            temp.barCode = cursor.getString(cursor.getColumnIndex(BARCODE));

            stockoutContructions.add(temp);
        }
        cursor.close();
        return stockoutContructions;
    }

    /**
     * 将出库单下的构件信息添加到对应的数据库中
     *
     * @param bean 相关构件信息的实体类
     * @return 插入成功返回true, 否则false
     */
    public boolean addStockoutContruction(NetStockoutInfo.DetailsBean bean) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STOCKOUT_CODE, bean.stockout_code);
        values.put(CONTRUCTION_CODE, bean.contruction_code);
        values.put(MATERIALNAME, bean.materialName);
        values.put(LENGTH, bean.length);
        values.put(SINGLE, bean.single);
        values.put(QTY, bean.qty);
        values.put(TONNAGE, bean.tonnage);
        values.put(INVOICE_QTY, bean.invoice_qty);
        values.put(INVOICE_TONNAGE, bean.invoice_tonnage);
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
     * 删除指定出库单号的构件信息数据库表单项
     *
     * @param code 指定的出库单号
     * @return 返回删除的行数
     */
    public int delStockoutConByStockoutCode(String code) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, STOCKOUT_CODE + "=?", new String[]{code});
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
