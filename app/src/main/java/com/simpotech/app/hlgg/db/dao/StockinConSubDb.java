package com.simpotech.app.hlgg.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.simpotech.app.hlgg.db.StockinConSubDbHelp;
import com.simpotech.app.hlgg.entity.StockinConInfo;

import java.util.ArrayList;
import java.util.List;

import static com.simpotech.app.hlgg.api.Constant.PROLINE;

/**
 * Created by longuto on 2016/11/16.
 * <p>
 * 入库时单批次构件的数据库管理类
 */

public class StockinConSubDb {

    public static final String TABLE_NAME = "t_stockin_contru";    //表名称

    public static final String ID = "_id";  //主键
    public static final String CML_CODE = "s_cml_code"; //构件清单号
    public static final String NAME = "s_name";  //加工厂
    public static final String CODE = "s_code";  //构件编码
    public static final String SPEC = "s_spec";  //规格
    public static final String BARCODE = "s_barcode";  //条形码
    public static final String QTY = "s_qty";  //清单数量
    public static final String STOCK_QTY = "s_stock_qty";  //入库数量
    public static final String SCANNERPEOPLE = "s_scannerPeople";  //扫描人
    public static final String SCANNERTIME = "s_scannerTime";  //扫描时间
    public static final String ISERROR = "s_isError";  //是否报错
    public static final String MESSAGE = "s_message";  //报错信息
    public static final String PROLINEID = "s_prolineId";  //生产线id

    private StockinConSubDbHelp dbHelp;

    public StockinConSubDb() {
        dbHelp = new StockinConSubDbHelp();
    }

    /**
     * 根据指定的发货单的发货单号查询对应构件信息的游标
     *
     * @return 游标
     */
    public Cursor queryAllStockinCon() {
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, CML_CODE, NAME, CODE, SPEC,
                BARCODE, QTY, STOCK_QTY, SCANNERPEOPLE, SCANNERTIME, ISERROR, MESSAGE,
                PROLINEID}, null, null, null, null, null);
        return cursor;
    }

    /**
     * 根据指定的发货单号返回对应所有构件信息的集合
     *
     * @return 构件信息集合
     */
    public List<StockinConInfo> getAllStockinCon() {
        List<StockinConInfo> stockinConInfos = new ArrayList<>();
        Cursor cursor = queryAllStockinCon();
        StockinConInfo temp = null;
        while (cursor.moveToNext()) {
            temp = new StockinConInfo();
            temp.id = cursor.getInt(cursor.getColumnIndex(ID));
            temp.cml_code = cursor.getString(cursor.getColumnIndex(CML_CODE));
            temp.name = cursor.getString(cursor.getColumnIndex(NAME));
            temp.code = cursor.getString(cursor.getColumnIndex(CODE));
            temp.spec = cursor.getString(cursor.getColumnIndex(SPEC));
            temp.barcode = cursor.getString(cursor.getColumnIndex(BARCODE));
            temp.qty = cursor.getString(cursor.getColumnIndex(QTY));
            temp.stock_qty = cursor.getString(cursor.getColumnIndex(STOCK_QTY));
            temp.scannerPeople = cursor.getString(cursor.getColumnIndex(SCANNERPEOPLE));
            temp.scannerTime = cursor.getString(cursor.getColumnIndex(SCANNERTIME));
            temp.isError = cursor.getInt(cursor.getColumnIndex(ISERROR));
            temp.message = cursor.getString(cursor.getColumnIndex(MESSAGE));
            temp.prolineId = cursor.getString(cursor.getColumnIndex(PROLINEID));

            stockinConInfos.add(temp);
        }
        cursor.close();
        return stockinConInfos;
    }

    /**
     * 将扫描到的构件信息添加到对应的数据库中
     *
     * @param bean 相关构件信息的实体类
     * @return 插入成功返回true, 否则false
     */
    public boolean addStockinCon(StockinConInfo bean) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CML_CODE, bean.cml_code);
        values.put(NAME, bean.name);
        values.put(CODE, bean.code);
        values.put(SPEC, bean.spec);
        values.put(BARCODE, bean.barcode);
        values.put(QTY, bean.qty);
        values.put(STOCK_QTY, bean.stock_qty);
        values.put(SCANNERPEOPLE, bean.scannerPeople);
        values.put(SCANNERTIME, bean.scannerTime);
        values.put(ISERROR, bean.isError);
        values.put(MESSAGE, bean.message);
        values.put(PROLINEID, bean.prolineId);
        long rowNo = db.insert(TABLE_NAME, null, values);
        db.close();
        if (rowNo > 0) {
            return true;
        } else {
            return false;
        }
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
     * 根据指定id,修改对应的构件的入库数量和生产线名称
     *
     * @param bean StockinConInfo对象
     * @return 修改成功返回影响的行数
     */
    public int upDataByStockinQty(StockinConInfo bean) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STOCK_QTY, bean.stock_qty);
        values.put(PROLINEID, bean.prolineId);
        int rows = db.update(TABLE_NAME, values, ID + " = ? ", new String[]{bean.id + ""});
        return rows;
    }

    /**
     * 根据指定id,修改对应的构件的出错信息和是否出错
     *
     * @param bean StockinConInfo对象
     * @return 修改成功返回影响的行数
     */
    public int upDataByStockinMess(StockinConInfo bean) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ISERROR, bean.isError);
        values.put(MESSAGE, bean.message);
        int rows = db.update(TABLE_NAME, values, ID + "=?", new String[]{bean.id + ""});
        return rows;
    }

    /**
     * 删除指定id的构件信息数据库表单项
     *
     * @param id 指定的构件的id
     * @return 返回删除的行数
     */
    public int delStockinConById(int id) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, ID + "=?", new String[]{id + ""});
        db.close();
        return rows;
    }

    /**
     * 按照构件清单或者构件编号查询相关条目(如果查询条件为null,getAllStockinCon()方法)
     *
     * @param content 指定的构件清单或者构件编号
     * @return 构件信息集合
     */
    public List<StockinConInfo> queryStockinConByInput(String content) {
        List<StockinConInfo> stockinConInfos = null;
        if (TextUtils.isEmpty(content)) {
            stockinConInfos = getAllStockinCon();
        } else {
            stockinConInfos = new ArrayList<StockinConInfo>();
            SQLiteDatabase db = dbHelp.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME, new String[]{ID, CML_CODE, NAME, CODE, SPEC,
                            BARCODE, QTY, STOCK_QTY, SCANNERPEOPLE, SCANNERTIME, ISERROR,
                            MESSAGE, PROLINEID}, CML_CODE + " like ? or " + CODE + " like ? ", new String[]{"%" + content + "%",
                            "%" + content + "%"}, null,
                    null, null);
            StockinConInfo temp = null;
            while (cursor.moveToNext()) {
                temp = new StockinConInfo();
                temp.id = cursor.getInt(cursor.getColumnIndex(CODE));
                temp.cml_code = cursor.getString(cursor.getColumnIndex(CML_CODE));
                temp.name = cursor.getString(cursor.getColumnIndex(NAME));
                temp.code = cursor.getString(cursor.getColumnIndex(CODE));
                temp.spec = cursor.getString(cursor.getColumnIndex(SPEC));
                temp.barcode = cursor.getString(cursor.getColumnIndex(BARCODE));
                temp.qty = cursor.getString(cursor.getColumnIndex(QTY));
                temp.stock_qty = cursor.getString(cursor.getColumnIndex(STOCK_QTY));
                temp.scannerPeople = cursor.getString(cursor.getColumnIndex(SCANNERPEOPLE));
                temp.scannerTime = cursor.getString(cursor.getColumnIndex(SCANNERTIME));
                temp.isError = cursor.getInt(cursor.getColumnIndex(ISERROR));
                temp.message = cursor.getString(cursor.getColumnIndex(MESSAGE));
                temp.prolineId = cursor.getString(cursor.getColumnIndex(PROLINEID));

                stockinConInfos.add(temp);
            }
            cursor.close();
            db.close();
        }
        return stockinConInfos;
    }
}
