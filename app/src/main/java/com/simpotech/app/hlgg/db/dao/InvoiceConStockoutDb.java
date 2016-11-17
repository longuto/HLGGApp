package com.simpotech.app.hlgg.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.simpotech.app.hlgg.db.InvoiceConStockoutDbHelp;
import com.simpotech.app.hlgg.entity.StockoutConInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longuto on 2016/11/14.
 */
public class InvoiceConStockoutDb {

    public static final String TABLE_NAME = "t_invoice_contruction_stockout";

    public static final String ID = "_id";
    public static final String INVOICE_CODE = "s_invoice_code";  //发货单号
    public static final String STOCK_QTY = "s_stock_qty";  // 出库件数
    public static final String CML_CODE = "s_cml_code";   //构件清单号
    public static final String NAME = "s_name";   //加工厂
    public static final String CODE = "s_code";   //构件号
    public static final String SPEC = "s_spec";   //规格
    public static final String BARCODE = "s_barcode";   //条形码
    public static final String QTY = "s_qty";   //清单数量

    public static final String SCANNER_PEOPLE = "s_scanner_people"; //扫描人
    public static final String SCANNER_TIME = "s_scanner_time";   //扫描时间

    public InvoiceConStockoutDbHelp dbHelp;

    public InvoiceConStockoutDb() {
        dbHelp = new InvoiceConStockoutDbHelp();
    }

    /**
     * 根据指定的发货单的发货单号查询对应构件信息的游标
     *
     * @param code 指定的发货单号
     * @return 游标
     */
    public Cursor queryInvoiceConByInvoiceCode(String code) {
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, INVOICE_CODE, STOCK_QTY, CML_CODE,
                NAME, CODE, SPEC, BARCODE, QTY, SCANNER_PEOPLE, SCANNER_TIME}, INVOICE_CODE + "=?", new
                String[]{code}, null, null, null);
        return cursor;
    }

    /**
     * 根据指定的发货单号返回对应所有构件信息的集合
     *
     * @param code 指定的发货单号
     * @return 构件信息集合
     */
    public List<StockoutConInfo> getInvoiceConByInvoiceCode(String code) {
        List<StockoutConInfo> stockoutConInfos = new ArrayList<>();
        Cursor cursor = queryInvoiceConByInvoiceCode(code);

        StockoutConInfo temp = null;
        while (cursor.moveToNext()) {
            temp = new StockoutConInfo();
            temp.id = cursor.getInt(cursor.getColumnIndex(ID));
            temp.invoice_code = cursor.getString(cursor.getColumnIndex(INVOICE_CODE));
            temp.stock_qty = cursor.getString(cursor.getColumnIndex(STOCK_QTY));
            temp.cml_code = cursor.getString(cursor.getColumnIndex(CML_CODE));
            temp.name = cursor.getString(cursor.getColumnIndex(NAME));
            temp.code = cursor.getString(cursor.getColumnIndex(CODE));
            temp.spec = cursor.getString(cursor.getColumnIndex(SPEC));
            temp.barcode = cursor.getString(cursor.getColumnIndex(BARCODE));
            temp.qty = cursor.getString(cursor.getColumnIndex(QTY));
            temp.scannerPeople = cursor.getString(cursor.getColumnIndex(SCANNER_PEOPLE));
            temp.scannerTime = cursor.getString(cursor.getColumnIndex(SCANNER_TIME));
            stockoutConInfos.add(temp);
        }
        cursor.close();
        return stockoutConInfos;
    }

    /**
     * 将扫描到的构件信息添加到对应的数据库中
     *
     * @param bean 相关构件信息的实体类
     * @return 插入成功返回true, 否则false
     */
    public boolean addInvoiceContruction(StockoutConInfo bean) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INVOICE_CODE, bean.invoice_code);
        values.put(STOCK_QTY, bean.stock_qty);
        values.put(CML_CODE, bean.cml_code);
        values.put(NAME, bean.name);
        values.put(CODE, bean.code);
        values.put(SPEC, bean.spec);
        values.put(BARCODE, bean.barcode);
        values.put(QTY, bean.qty);
        values.put(SCANNER_PEOPLE, bean.scannerPeople);
        values.put(SCANNER_TIME, bean.scannerTime);
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
     *
     * @return 删除的行数
     */
    public int delAllData() {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, null, null);
        db.close();
        return rows;
    }


    /**
     * 根据指定编号id,修改对应的构件的出库数量
     * @param bean StockoutContructionInfo对象
     * @return 修改成功返回影响的行数
     */
    public int upDataByStockoutQty(StockoutConInfo bean) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STOCK_QTY, bean.stock_qty);
        int rows = db.update(TABLE_NAME, values, ID + "=?", new String[]{bean
                .id + ""});
        return rows;
    }

    /**
     * 根据指定的编号id删除构件信息
     * @param id 指定的条形码
     * @return 返回影响的行数
     */
    public int delInvoiceConById(int id) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, ID + "=?", new String[]{id + ""});
        db.close();
        return rows;
    }
}
