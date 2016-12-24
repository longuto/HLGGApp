package com.simpotech.app.hlgg.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.simpotech.app.hlgg.db.InvoiceContructionDbHelp;
import com.simpotech.app.hlgg.entity.net.NetInvoiceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longuto on 2016/11/8.
 * 发货单下的构件的数据库
 */

public class InvoiceContructionDb {

    public static final String TABLE_NAME = "t_invoice_contruction";    //表名称

    public static final String ID = "_id";  //主键
    public static final String INVOICE_CODE = "c_invoice_code";  //发货单号
    public static final String CONTRUCTION_CODE = "c_contruction_code";  //构件编码
    public static final String SPEC = "c_spec";  //规格
    public static final String QTY = "c_qty";  //发货件数
    public static final String TONNAGE = "c_tonnage";  //发货重量
    public static final String CONSTRUCTQTY = "c_constructQty";  //构件件数

    public InvoiceContructionDbHelp dbHelp;

    public InvoiceContructionDb() {
        dbHelp = new InvoiceContructionDbHelp();
    }

    /**
     * 根据指定的发货单的发货单号查询对应构件信息的游标
     *
     * @param code 指定的发货单号
     * @return 游标
     */
    public Cursor queryInvoiceConByInvoiceCode(String code) {
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, INVOICE_CODE, CONTRUCTION_CODE,
                SPEC, QTY, TONNAGE,
                CONSTRUCTQTY}, INVOICE_CODE + "=?", new String[]{code}, null, null, null);
        return cursor;
    }

    /**
     * 根据指定的发货单号返回对应所有构件信息的集合
     *
     * @param code 指定的发货单号
     * @return 构件信息集合
     */
    public List<NetInvoiceInfo.DetailsBean> getInvoiceConByInvoiceCode(String code) {
        List<NetInvoiceInfo.DetailsBean> invoiceContructions = new ArrayList<>();
        Cursor cursor = queryInvoiceConByInvoiceCode(code);
        NetInvoiceInfo.DetailsBean temp = null;
        while (cursor.moveToNext()) {
            temp = new NetInvoiceInfo.DetailsBean();
            temp.invoice_code = cursor.getString(cursor.getColumnIndex(INVOICE_CODE));
            temp.contruction_code = cursor.getString(cursor.getColumnIndex(CONTRUCTION_CODE));
            temp.spec = cursor.getString(cursor.getColumnIndex(SPEC));
            temp.qty = cursor.getString(cursor.getColumnIndex(QTY));
            temp.tonnage = cursor.getString(cursor.getColumnIndex(TONNAGE));
            temp.constructQty = cursor.getString(cursor.getColumnIndex(CONSTRUCTQTY));
            invoiceContructions.add(temp);
        }
        cursor.close();
        return invoiceContructions;
    }

    /**
     * 将发货单下的构件信息添加到对应的数据库中
     *
     * @param bean 相关构件信息的实体类
     * @return 插入成功返回true, 否则false
     */
    public boolean addInvoiceContruction(NetInvoiceInfo.DetailsBean bean) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INVOICE_CODE, bean.invoice_code);
        values.put(CONTRUCTION_CODE, bean.contruction_code);
        values.put(SPEC, bean.spec);
        values.put(QTY, bean.qty);
        values.put(TONNAGE, bean.tonnage);
        values.put(CONSTRUCTQTY, bean.constructQty);
        long rowNo = db.insert(TABLE_NAME, null, values);
        db.close();
        if (rowNo > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除指定发货单号的构件信息数据库表单项
     *
     * @param code 指定的发货单号
     * @return 返回删除的行数
     */
    public int delInvoiceConByInvoiceCode(String code) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, INVOICE_CODE + "=?", new String[]{code});
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