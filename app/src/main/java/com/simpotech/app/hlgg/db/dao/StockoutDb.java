package com.simpotech.app.hlgg.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.simpotech.app.hlgg.db.StockoutDbHelp;
import com.simpotech.app.hlgg.entity.net.NetStockoutInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longuto on 2016/11/15.
 * <p>
 * 出库单数据库
 */

public class StockoutDb {

    public static final String TABLE_NAME = "t_stockout";    //表名称

    public static final String ID = "_id";  //主键
    public static final String CODE = "s_code"; //出库单号
    public static final String INVOICE_CODE = "s_invoice_code"; //发货单号
    public static final String PROJ_NAME = "s_proj_name"; //项目名称
    public static final String ORGAN_NAME = "s_organ_name"; //加工厂名
    public static final String SALENAME = "s_saleName"; //营销员
    public static final String ADDTIME = "s_addTime"; //创建时间
    public static final String ADDUSERID = "s_addUserId"; //提交人
    public static final String ADDUSERNAME = "s_addUserName"; //提交人名称

    public StockoutDbHelp dbHelp;

    public StockoutDb() {
        dbHelp = new StockoutDbHelp();
    }

    /**
     * 查询出库单所有表记录,并返回游标
     * 按照添加时间降序排列
     *
     * @return 游标
     */
    public Cursor queryStockoutTable() {
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, CODE, INVOICE_CODE, PROJ_NAME,
                        ORGAN_NAME, SALENAME, ADDTIME, ADDUSERID, ADDUSERNAME}, null, null, null,
                null,
                ADDTIME + " DESC");
        return cursor;
    }

    /**
     * 获取数据库中所有出库单的集合
     *
     * @return 出库单集合
     */
    public List<NetStockoutInfo> getAllStockouts() {
        List<NetStockoutInfo> stockoutInfos = new ArrayList<NetStockoutInfo>();
        Cursor cursor = queryStockoutTable();
        NetStockoutInfo temp = null;
        while (cursor.moveToNext()) {
            temp = new NetStockoutInfo();
            temp.code = cursor.getString(cursor.getColumnIndex(CODE));
            temp.invoice_code = cursor.getString(cursor.getColumnIndex(INVOICE_CODE));
            temp.proj_name = cursor.getString(cursor.getColumnIndex(PROJ_NAME));
            temp.organ_name = cursor.getString(cursor.getColumnIndex(ORGAN_NAME));
            temp.saleName = cursor.getString(cursor.getColumnIndex(SALENAME));
            temp.addTime = cursor.getString(cursor.getColumnIndex(ADDTIME));
            temp.addUserId = cursor.getString(cursor.getColumnIndex(ADDUSERID));
            temp.addUserName = cursor.getString(cursor.getColumnIndex(ADDUSERNAME));

            stockoutInfos.add(temp);
        }
        cursor.close();
        return stockoutInfos;
    }

    /**
     * 添加出库单数据至数据库
     *
     * @param info 出库单实体类
     * @return 插入成功返回true, 失败返回false
     */
    public boolean addStockout(NetStockoutInfo info) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CODE, info.code);
        values.put(INVOICE_CODE, info.invoice_code);
        values.put(PROJ_NAME, info.proj_name);
        values.put(ORGAN_NAME, info.organ_name);
        values.put(SALENAME, info.saleName);
        values.put(ADDTIME, info.addTime);
        values.put(ADDUSERID, info.addUserId);
        values.put(ADDUSERNAME, info.addUserName);
        long rowNo = db.insert(TABLE_NAME, null, values);
        db.close();
        if (rowNo > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据指定出库单号删除数据库中的发货单
     *
     * @param code 指定的出库单
     * @return 删除的行数
     */
    public int delStockout(String code) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, CODE + "=?", new String[]{code});
        db.close();
        return rows;
    }

    /**
     * 清空所有数据
     *
     * @return 清空的行数
     */
    public int delAllData() {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, null, null);
        db.close();
        return rows;
    }

    /**
     * 按照出库单号或者项目名称查询相关条目(如果查询条件为null,则调用getAllStockouts()方法)
     *
     * @param content 指定的出库单号或者项目名称
     * @return 出库单信息集合
     */
    public List<NetStockoutInfo> queryStockoutsByInput(String content) {
        List<NetStockoutInfo> netStockoutInfos = null;
        if (TextUtils.isEmpty(content)) {
            netStockoutInfos = getAllStockouts();
        } else {
            netStockoutInfos = new ArrayList<NetStockoutInfo>();
            SQLiteDatabase db = dbHelp.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME, new String[]{CODE, INVOICE_CODE, PROJ_NAME,
                    ORGAN_NAME, SALENAME, ADDTIME, ADDUSERID, ADDUSERNAME},
                    CODE + " like ? or " + PROJ_NAME + " like ? ", new String[]{"%" + content + "%", "%" + content + "%"}, null,
                    null, null);
            NetStockoutInfo temp = null;
            while (cursor.moveToNext()) {
                temp = new NetStockoutInfo();
                temp.code = cursor.getString(cursor.getColumnIndex(CODE));
                temp.invoice_code = cursor.getString(cursor.getColumnIndex(INVOICE_CODE));
                temp.proj_name = cursor.getString(cursor.getColumnIndex(PROJ_NAME));
                temp.organ_name = cursor.getString(cursor.getColumnIndex(ORGAN_NAME));
                temp.saleName = cursor.getString(cursor.getColumnIndex(SALENAME));
                temp.addTime = cursor.getString(cursor.getColumnIndex(ADDTIME));
                temp.addUserId = cursor.getString(cursor.getColumnIndex(ADDUSERID));
                temp.addUserName = cursor.getString(cursor.getColumnIndex(ADDUSERNAME));

                netStockoutInfos.add(temp);
            }
            cursor.close();
            db.close();
        }
        return netStockoutInfos;
    }
}
