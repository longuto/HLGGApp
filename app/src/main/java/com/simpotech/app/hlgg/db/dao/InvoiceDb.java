package com.simpotech.app.hlgg.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.simpotech.app.hlgg.db.InvoiceDbHelp;
import com.simpotech.app.hlgg.entity.net.NetInvoiceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longuto on 2016/11/8.
 * 发货单数据库管理类
 */

public class InvoiceDb {

    public static final String TABLE_NAME = "t_invoice";    //表名称

    public static final String ID = "_id";  //主键
    public static final String CODE = "i_code"; //发货单号
    public static final String WO_CODE = "i_wo_code";  //生产任务单号
    public static final String CML_CODE = "i_cml_code";  //构件清单编码
    public static final String PROJ_NAME = "i_proj_name";  //项目名称
    public static final String ORGAN_ID = "i_organ_id";  //发货部门
    public static final String STORAGE_CODE = "i_storage_code";  //发货仓库
    public static final String ORGANNAME = "i_organName";  //加工厂
    public static final String SALENAME = "i_saleName";  //营销员
    public static final String CJDATE = "i_cjdate";  //创建时间
    public static final String ADDUSERID = "i_addUserId";  //下载人
    public static final String ADDUSERNAME = "i_addUserName";  //下载人名称

    private InvoiceDbHelp dbHelp;

    public InvoiceDb() {
        dbHelp = new InvoiceDbHelp();
    }

    /**
     * 查询发货单所有表记录,并返回游标
     * 按照创建时间降序排列
     *
     * @return 游标
     */
    public Cursor queryInvoiceTable() {
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, CODE, WO_CODE, CML_CODE, PROJ_NAME,
                        ORGAN_ID, STORAGE_CODE, ORGANNAME, SALENAME, CJDATE, ADDUSERID,
                        ADDUSERNAME},
                null, null, null, null, CJDATE + " DESC");
        return cursor;
    }

    /**
     * 获取所有数据库中的发货单的集合
     *
     * @return 发货单集合
     */
    public List<NetInvoiceInfo> getAllInvoices() {
        List<NetInvoiceInfo> invoiceInfos = new ArrayList<NetInvoiceInfo>();
        Cursor cursor = queryInvoiceTable();
        NetInvoiceInfo temp = null;
        while (cursor.moveToNext()) {
            temp = new NetInvoiceInfo();
            temp.code = cursor.getString(cursor.getColumnIndex(CODE));
            temp.wo_code = cursor.getString(cursor.getColumnIndex(WO_CODE));
            temp.cml_code = cursor.getString(cursor.getColumnIndex(PROJ_NAME));
            temp.proj_name = cursor.getString(cursor.getColumnIndex(PROJ_NAME));
            temp.organ_id = cursor.getString(cursor.getColumnIndex(ORGAN_ID));
            temp.storage_code = cursor.getString(cursor.getColumnIndex(STORAGE_CODE));
            temp.organName = cursor.getString(cursor.getColumnIndex(ORGANNAME));
            temp.saleName = cursor.getString(cursor.getColumnIndex(SALENAME));
            temp.cjdate = cursor.getString(cursor.getColumnIndex(CJDATE));
            temp.addUserId = cursor.getString(cursor.getColumnIndex(ADDUSERID));
            temp.addUserName = cursor.getString(cursor.getColumnIndex(ADDUSERNAME));
            invoiceInfos.add(temp);
        }
        cursor.close();
        return invoiceInfos;
    }

    /**
     * 添加发货单数据至数据库
     *
     * @param info 发货单实体类
     * @return 插入成功返回true, 失败返回false
     */
    public boolean addInvoice(NetInvoiceInfo info) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CODE, info.code);
        values.put(WO_CODE, info.wo_code);
        values.put(CML_CODE, info.cml_code);
        values.put(PROJ_NAME, info.proj_name);
        values.put(ORGAN_ID, info.organ_id);
        values.put(STORAGE_CODE, info.storage_code);
        values.put(ORGANNAME, info.organName);
        values.put(SALENAME, info.saleName);
        values.put(CJDATE, info.cjdate);
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
     * 根据指定发货单号删除数据库中的发货单
     *
     * @param code 指定的发货单
     * @return 删除的行数
     */
    public int delInvoice(String code) {
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
     * 按照发货单号或者项目名称查询相关条目(如果查询条件为null,则调用getAllInvoices()方法)
     *
     * @param content 指定的发货单号或者项目名称
     * @return 发货单信息集合
     */
    public List<NetInvoiceInfo> queryInvoicesByInput(String content) {
        List<NetInvoiceInfo> netInvoiceInfos = null;
        if (TextUtils.isEmpty(content)) {
            netInvoiceInfos = getAllInvoices();
        } else {
            netInvoiceInfos = new ArrayList<NetInvoiceInfo>();
            SQLiteDatabase db = dbHelp.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME, new String[]{CODE, WO_CODE, CML_CODE, PROJ_NAME,
                    ORGAN_ID, STORAGE_CODE, ORGANNAME, SALENAME, CJDATE, ADDUSERID, ADDUSERNAME},
                    CODE + " like ? or " + PROJ_NAME + " like ? ", new String[]{"%" + content + "%", "%" + content + "%"}, null,
                    null, null);
            NetInvoiceInfo temp = null;
            while (cursor.moveToNext()) {
                temp = new NetInvoiceInfo();
                temp.code = cursor.getString(cursor.getColumnIndex(CODE));
                temp.wo_code = cursor.getString(cursor.getColumnIndex(WO_CODE));
                temp.cml_code = cursor.getString(cursor.getColumnIndex(CML_CODE));
                temp.proj_name = cursor.getString(cursor.getColumnIndex(PROJ_NAME));
                temp.organ_id = cursor.getString(cursor.getColumnIndex(ORGAN_ID));
                temp.storage_code = cursor.getString(cursor.getColumnIndex(STORAGE_CODE));
                temp.organName = cursor.getString(cursor.getColumnIndex(ORGANNAME));
                temp.saleName = cursor.getString(cursor.getColumnIndex(SALENAME));
                temp.cjdate = cursor.getString(cursor.getColumnIndex(CJDATE));
                temp.addUserId = cursor.getString(cursor.getColumnIndex(ADDUSERID));
                temp.addUserName = cursor.getString(cursor.getColumnIndex(ADDUSERNAME));
                netInvoiceInfos.add(temp);
            }
            cursor.close();
            db.close();
        }
        return netInvoiceInfos;
    }

    /**
     * 按照发货单号查询对应的发货单信息
     *
     * @param code 指定的发货单号
     * @return 发货单信息
     */
    public NetInvoiceInfo queryInvoicesByCode(String code) {
        NetInvoiceInfo temp = null;
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{CODE, WO_CODE, CML_CODE, PROJ_NAME,
                ORGAN_ID, STORAGE_CODE, ORGANNAME, SALENAME, CJDATE, ADDUSERID,
                ADDUSERNAME}, CODE + " = ? ", new String[]{code}, null, null, null);
        if (cursor.moveToNext()) {
            temp = new NetInvoiceInfo();
            temp.code = cursor.getString(cursor.getColumnIndex(CODE));
            temp.wo_code = cursor.getString(cursor.getColumnIndex(WO_CODE));
            temp.cml_code = cursor.getString(cursor.getColumnIndex(CML_CODE));
            temp.proj_name = cursor.getString(cursor.getColumnIndex(PROJ_NAME));
            temp.organ_id = cursor.getString(cursor.getColumnIndex(ORGAN_ID));
            temp.storage_code = cursor.getString(cursor.getColumnIndex(STORAGE_CODE));
            temp.organName = cursor.getString(cursor.getColumnIndex(ORGANNAME));
            temp.saleName = cursor.getString(cursor.getColumnIndex(SALENAME));
            temp.cjdate = cursor.getString(cursor.getColumnIndex(CJDATE));
            temp.addUserId = cursor.getString(cursor.getColumnIndex(ADDUSERID));
            temp.addUserName = cursor.getString(cursor.getColumnIndex(ADDUSERNAME));
        }
        cursor.close();
        db.close();
        return temp;
    }
}




