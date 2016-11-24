package com.simpotech.app.hlgg.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.simpotech.app.hlgg.db.StockinDbHelp;
import com.simpotech.app.hlgg.entity.net.NetStockinInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longuto on 2016/11/18.
 * <p>
 * 入库成功后,返回的入库数据表操作类
 */

public class StockinDb {

    public static final String TABLE_NAME = "t_stockin";    //表名称

    public static final String ID = "_id";  //主键
    public static final String CODE = "s_code"; //入库单号
    public static final String WO_CODE = "s_wo_code"; //生产任务单号
    public static final String CML_CODE = "s_cml_code"; //构件清单编码
    public static final String PROJ_NAME = "s_proj_name"; //项目名称
    public static final String ORGANNAME = "s_organName"; //入库工厂
    public static final String PRODUCTLINE = "s_productLine"; //入库生产线
    public static final String ADDTIME = "s_addTime"; //创建时间
    public static final String ADDUSERID = "s_addUserId"; //提交人id
    public static final String ADDUSERNAME = "s_addUserName"; //提交人

    public StockinDbHelp dbHelp;

    public StockinDb() {
        dbHelp = new StockinDbHelp();
    }

    /**
     * 查询入库单所有表记录,并返回游标
     * 按照添加时间降序排列
     *
     * @return 游标
     */
    public Cursor queryStockinTable() {
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, CODE, WO_CODE, CML_CODE, PROJ_NAME,
                        ORGANNAME, PRODUCTLINE, ADDTIME, ADDUSERID, ADDUSERNAME}, null, null,
                null, null,
                ADDTIME + " DESC");
        return cursor;
    }

    /**
     * 获取数据库中所有出库单的集合
     *
     * @return 入库单集合
     */
    public List<NetStockinInfo> getAllStockins() {
        List<NetStockinInfo> stockinInfos = new ArrayList<NetStockinInfo>();
        Cursor cursor = queryStockinTable();
        NetStockinInfo temp = null;
        while (cursor.moveToNext()) {
            temp = new NetStockinInfo();
            temp.code = cursor.getString(cursor.getColumnIndex(CODE));
            temp.wo_code = cursor.getString(cursor.getColumnIndex(WO_CODE));
            temp.cml_code = cursor.getString(cursor.getColumnIndex(CML_CODE));
            temp.proj_name = cursor.getString(cursor.getColumnIndex(PROJ_NAME));
            temp.organName = cursor.getString(cursor.getColumnIndex(ORGANNAME));
            temp.productLine = cursor.getString(cursor.getColumnIndex(PRODUCTLINE));
            temp.addTime = cursor.getString(cursor.getColumnIndex(ADDTIME));
            temp.addUserId = cursor.getString(cursor.getColumnIndex(ADDUSERID));
            temp.addUserName = cursor.getString(cursor.getColumnIndex(ADDUSERNAME));

            stockinInfos.add(temp);
        }
        cursor.close();
        return stockinInfos;
    }

    /**
     * 添加入库单数据至数据库
     *
     * @param info 入库单实体类
     * @return 插入成功返回true, 失败返回false
     */
    public boolean addStockin(NetStockinInfo info) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CODE, info.code);
        values.put(WO_CODE, info.wo_code);
        values.put(CML_CODE, info.cml_code);
        values.put(PROJ_NAME, info.proj_name);
        values.put(ORGANNAME, info.organName);
        values.put(PRODUCTLINE, info.productLine);
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
     * 根据指定入库单号删除数据库中的入库单
     *
     * @param code 指定的入库单
     * @return 删除的行数
     */
    public int delStockin(String code) {
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
     * 按照入库单号或者项目名称查询相关条目(如果查询条件为null,则调用getAllStockins()方法)
     *
     * @param content 指定的入库单号或者项目名称
     * @return 入库单信息集合
     */
    public List<NetStockinInfo> queryStockinsByInput(String content) {
        List<NetStockinInfo> netStockinInfos = null;
        if (TextUtils.isEmpty(content)) {
            netStockinInfos = getAllStockins();
        } else {
            netStockinInfos = new ArrayList<NetStockinInfo>();
            SQLiteDatabase db = dbHelp.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME, new String[]{CODE, WO_CODE, CML_CODE, PROJ_NAME,
                    ORGANNAME, PRODUCTLINE, ADDTIME, ADDUSERID, ADDUSERNAME}, CODE + " like ? " +
                    "or " + PROJ_NAME + " like ? ", new String[]{"%" + content + "%", "%" +
                    content + "%"}, null, null, null);
            NetStockinInfo temp = null;
            while (cursor.moveToNext()) {
                temp = new NetStockinInfo();
                temp.code = cursor.getString(cursor.getColumnIndex(CODE));
                temp.wo_code = cursor.getString(cursor.getColumnIndex(WO_CODE));
                temp.cml_code = cursor.getString(cursor.getColumnIndex(CML_CODE));
                temp.proj_name = cursor.getString(cursor.getColumnIndex(PROJ_NAME));
                temp.organName = cursor.getString(cursor.getColumnIndex(ORGANNAME));
                temp.productLine = cursor.getString(cursor.getColumnIndex(PRODUCTLINE));
                temp.addTime = cursor.getString(cursor.getColumnIndex(ADDTIME));
                temp.addUserId = cursor.getString(cursor.getColumnIndex(ADDUSERID));
                temp.addUserName = cursor.getString(cursor.getColumnIndex(ADDUSERNAME));

                netStockinInfos.add(temp);
            }
            cursor.close();
            db.close();
        }
        return netStockinInfos;
    }
}
