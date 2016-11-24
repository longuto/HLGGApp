package com.simpotech.app.hlgg.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.simpotech.app.hlgg.db.ProLineDbHelp;
import com.simpotech.app.hlgg.entity.DbProLineInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by longuto on 2016/10/31.
 * 本地生产线
 */

public class ProLineDb {

    public final static String TABLE_NAME = "t_proline";    //生产线表名称
    public final static String ID = "_id";  //表的主键
    public final static String DEPARTMENT_ID = "p_department_id";   //部门id
    public final static String DEPARTMENT_NAME = "p_department_name"; //部门名称
    public final static String PROLINE_ID = "p_proline_id"; //生产线id
    public final static String PROLINE_NAME = "p_proline_name"; //生产线名称

    private ProLineDbHelp dbHelp;

    public ProLineDb() {
        dbHelp = new ProLineDbHelp();
    }

    /*
     * 查询本地生产线
     * 按照部门id和生产线id升序排列
     */
    public Cursor queryProLineTable() {
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, DEPARTMENT_ID, DEPARTMENT_NAME,
                PROLINE_ID, PROLINE_NAME}, null, null, null, null, DEPARTMENT_ID + " , " +
                PROLINE_ID + " ASC");
        return cursor;
    }

    /**
     * 获取本地所有的生产线集合,调用queryProLineTable()接口游标
     */
    public List<DbProLineInfo> getAllProLines() {
        List<DbProLineInfo> proLineInfos = new ArrayList<DbProLineInfo>();
        Cursor cursor = queryProLineTable();    //游标
        DbProLineInfo temp = null;
        while (cursor.moveToNext()) {
            temp = new DbProLineInfo();
            temp.id = cursor.getInt(cursor.getColumnIndex(ID));
            temp.departmentId = cursor.getString(cursor.getColumnIndex(DEPARTMENT_ID));
            temp.departmentName = cursor.getString(cursor.getColumnIndex(DEPARTMENT_NAME));
            temp.proLineId = cursor.getString(cursor.getColumnIndex(PROLINE_ID));
            temp.proLineName = cursor.getString(cursor.getColumnIndex(PROLINE_NAME));
            proLineInfos.add(temp);
        }
        cursor.close(); //关闭游标
        return proLineInfos;
    }


    /**
     * 添加生产线至数据库表中
     *
     * @param department_id   部门id
     * @param department_name 部门名称
     * @param proline_id      生产线id
     * @param proline_name    生产线名称
     */
    public void addProLine(String department_id, String department_name, String proline_id,
                           String proline_name) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DEPARTMENT_ID, department_id);
        values.put(DEPARTMENT_NAME, department_name);
        values.put(PROLINE_ID, proline_id);
        values.put(PROLINE_NAME, proline_name);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /**
     * 根据_id删除数据库的数据
     *
     * @param _id 数据库表中的主键
     */
    public void deleteProLineById(int _id) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=?", new String[]{_id + ""});
        db.close();
    }

    /**
     * 按照部门名称或者生产线名称进行查询,获取集合(如果查询条件为null,则调用getAllProLines()方法)
     *
     * @param name 部门或者生产线名称
     * @return 生产线信息集合
     */
    public List<DbProLineInfo> queryProlineByName(String name) {
        List<DbProLineInfo> proLineInfos = null;
        if (TextUtils.isEmpty(name)) {
            proLineInfos = getAllProLines();
        } else {
            proLineInfos = new ArrayList<DbProLineInfo>();
            SQLiteDatabase db = dbHelp.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME, new String[]{ID, DEPARTMENT_ID, DEPARTMENT_NAME,
                    PROLINE_ID, PROLINE_NAME}, DEPARTMENT_NAME + " like ? or " + PROLINE_NAME + " like ? ", new
                    String[]{"%" + name + "%", "%" + name + "%"}, null, null, DEPARTMENT_ID + " , " +
                    PROLINE_ID + " ASC");
            DbProLineInfo temp = null;
            while (cursor.moveToNext()) {
                temp = new DbProLineInfo();
                temp.id = cursor.getInt(cursor.getColumnIndex(ID));
                temp.departmentId = cursor.getString(cursor.getColumnIndex(DEPARTMENT_ID));
                temp.departmentName = cursor.getString(cursor.getColumnIndex(DEPARTMENT_NAME));
                temp.proLineId = cursor.getString(cursor.getColumnIndex(PROLINE_ID));
                temp.proLineName = cursor.getString(cursor.getColumnIndex(PROLINE_NAME));
                proLineInfos.add(temp);
            }
            cursor.close();
            db.close();
        }
        return proLineInfos;
    }

    /**
     * 判断数据库中是否存在指定的DbProLineInfo
     *
     * @param dbProLineInfo 传入的数据
     * @return 存在true
     */
    public boolean isExistProline(DbProLineInfo dbProLineInfo) {
        boolean flag = false;

        SQLiteDatabase db = dbHelp.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{DEPARTMENT_ID, PROLINE_ID},
                DEPARTMENT_ID + " = ? AND "
                + PROLINE_ID + " = ? ", new String[]{dbProLineInfo.departmentId, dbProLineInfo
                .proLineId}, null, null, null);
        if (cursor.moveToNext()) {
            flag = true;
        }
        cursor.close();
        db.close();
        return flag;
    }
}
