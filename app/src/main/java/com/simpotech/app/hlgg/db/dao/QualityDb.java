package com.simpotech.app.hlgg.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.simpotech.app.hlgg.api.NetQualityParse;
import com.simpotech.app.hlgg.db.QualityDbHelp;
import com.simpotech.app.hlgg.entity.net.NetQualityInfo;
import com.simpotech.app.hlgg.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longuto on 2016/12/19.
 * <p>
 * 质检录入的数据库操作类
 */

public class QualityDb {
    public static final String TABLE_NAME = "t_quality";

    public static final String ID = "_id";  //主键
    public static final String CODE = "q_code"; //质检单号
    public static final String PROJECT_NAME = "q_project_name"; //项目名称
    public static final String TESTUSER = "q_testUser"; //质检人
    public static final String CJDATA = "q_cjdata"; //质检时间
    public static final String USERID = "q_userId"; //用户id

    public static final String CML_CODE = "q_cml_code"; //构件清单号
    public static final String CONTRUCTION_CODE = "q_contruction_code"; //构件编号
    public static final String ORGANNAME = "q_organname"; //构件加工厂
    public static final String BARCODE = "q_barcode"; //构件条码号
    public static final String QTY = "q_qty"; //构件数量
    public static final String SPEC = "q_spec"; //构件规格

    public static final String FACADE_RESULT = "q_facade_result";   //外观检测结果
    public static final String FACADE_REMARK = "q_facade_remark";  //外观检测说明
    public static final String FACADE_PIC1 = "q_facade_pic1";    //外观图片1
    public static final String FACADE_PIC2 = "q_facade_pic2";    //外观图片2
    public static final String FACADE_PIC3 = "q_facade_pic3";    //外观图片3
    public static final String FACADE_PIC4 = "q_facade_pic4";    //外观图片4
    public static final String SIZE_RESULT = "q_size_result";   //尺寸检测结果
    public static final String SIZE_REMARK = "q_size_remark";  //尺寸检测说明
    public static final String SIZE_PIC1 = "q_size_pic1";    //尺寸图片1
    public static final String SIZE_PIC2 = "q_size_pic2";    //尺寸图片2
    public static final String SIZE_PIC3 = "q_size_pic3";    //尺寸图片3
    public static final String SIZE_PIC4 = "q_size_pic4";    //尺寸图片4
    public static final String WELD_RESULT = "q_weld_result";   //焊缝检测结果
    public static final String WELD_REMARK = "q_weld_remark";  //焊缝检测说明
    public static final String WELD_PIC1 = "q_weld_pic1";    //焊缝图片1
    public static final String WELD_PIC2 = "q_weld_pic2";    //焊缝图片2
    public static final String WELD_PIC3 = "q_weld_pic3";    //焊缝图片3
    public static final String WELD_PIC4 = "q_weld_pic4";    //焊缝图片4

    public QualityDbHelp dbHelp;

    public QualityDb() {
        dbHelp = new QualityDbHelp();
    }

    /**
     * 查询质检表所有表记录,并返回游标
     * 按照添加时间降序排列
     *
     * @return 游标
     */
    public Cursor queryQualityTable() {
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, CODE, PROJECT_NAME, TESTUSER,
                        CJDATA, USERID, CML_CODE, CONTRUCTION_CODE, ORGANNAME, BARCODE, QTY,
                        SPEC, FACADE_RESULT, FACADE_REMARK, FACADE_PIC1, FACADE_PIC2, FACADE_PIC3,
                        FACADE_PIC4, SIZE_RESULT, SIZE_REMARK, SIZE_PIC1, SIZE_PIC2, SIZE_PIC3,
                        SIZE_PIC4, WELD_RESULT, WELD_REMARK, WELD_PIC1, WELD_PIC2, WELD_PIC3,
                        WELD_PIC4},
                null, null, null, null, CJDATA + " DESC");
        return cursor;
    }

    /**
     * 返回所有质检的实体类集合
     *
     * @return
     */
    public List<NetQualityInfo> getAllQualitys() {
        List<NetQualityInfo> qualityInfos = new ArrayList<>();
        Cursor cursor = queryQualityTable();
        NetQualityInfo temp = null;
        while (cursor.moveToNext()) {
            temp = new NetQualityInfo();
            temp.code = cursor.getString(cursor.getColumnIndex(CODE));
            temp.project_name = cursor.getString(cursor.getColumnIndex(PROJECT_NAME));
            temp.testUser = cursor.getString(cursor.getColumnIndex(TESTUSER));
            temp.cjdata = cursor.getString(cursor.getColumnIndex(CJDATA));
            temp.userId = cursor.getString(cursor.getColumnIndex(USERID));
            temp.cml_code = cursor.getString(cursor.getColumnIndex(CML_CODE));
            temp.contruction_code = cursor.getString(cursor.getColumnIndex(CONTRUCTION_CODE));
            temp.organName = cursor.getString(cursor.getColumnIndex(ORGANNAME));
            temp.barCode = cursor.getString(cursor.getColumnIndex(BARCODE));
            temp.qty = cursor.getString(cursor.getColumnIndex(QTY));
            temp.spec = cursor.getString(cursor.getColumnIndex(SPEC));

            temp.detail = new ArrayList<>();
            NetQualityInfo.DetailBean facade = new NetQualityInfo.DetailBean();
            facade.itemsFlag = "1";
            facade.testResult = cursor.getString(cursor.getColumnIndex(FACADE_RESULT));
            facade.remark = cursor.getString(cursor.getColumnIndex(FACADE_REMARK));
            facade.pic1 = cursor.getString(cursor.getColumnIndex(FACADE_PIC1));
            facade.pic2 = cursor.getString(cursor.getColumnIndex(FACADE_PIC2));
            facade.pic3 = cursor.getString(cursor.getColumnIndex(FACADE_PIC3));
            facade.pic4 = cursor.getString(cursor.getColumnIndex(FACADE_PIC4));
            temp.detail.add(facade);

            NetQualityInfo.DetailBean size = new NetQualityInfo.DetailBean();
            size.itemsFlag = "2";
            size.testResult = cursor.getString(cursor.getColumnIndex(SIZE_RESULT));
            size.remark = cursor.getString(cursor.getColumnIndex(SIZE_REMARK));
            size.pic1 = cursor.getString(cursor.getColumnIndex(SIZE_PIC1));
            size.pic2 = cursor.getString(cursor.getColumnIndex(SIZE_PIC2));
            size.pic3 = cursor.getString(cursor.getColumnIndex(SIZE_PIC3));
            size.pic4 = cursor.getString(cursor.getColumnIndex(SIZE_PIC4));
            temp.detail.add(size);

            NetQualityInfo.DetailBean weld = new NetQualityInfo.DetailBean();
            weld.itemsFlag = "3";
            weld.testResult = cursor.getString(cursor.getColumnIndex(WELD_RESULT));
            weld.remark = cursor.getString(cursor.getColumnIndex(WELD_REMARK));
            weld.pic1 = cursor.getString(cursor.getColumnIndex(WELD_PIC1));
            weld.pic2 = cursor.getString(cursor.getColumnIndex(WELD_PIC2));
            weld.pic3 = cursor.getString(cursor.getColumnIndex(WELD_PIC3));
            weld.pic4 = cursor.getString(cursor.getColumnIndex(WELD_PIC4));
            temp.detail.add(weld);

            qualityInfos.add(temp);
        }
        cursor.close();
        return qualityInfos;
    }

    /**
     * 添加质检信息至数据库表
     *
     * @param info 质检实体类
     * @return 添加成功返回true, 失败返回false
     */
    public boolean addQuality(NetQualityInfo info) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CODE, info.code);
        values.put(PROJECT_NAME, info.project_name);
        values.put(TESTUSER, info.testUser);
        values.put(CJDATA, info.cjdata);
        values.put(USERID, info.userId);
        values.put(CML_CODE, info.cml_code);
        values.put(CONTRUCTION_CODE, info.contruction_code);
        values.put(ORGANNAME, info.organName);
        values.put(BARCODE, info.barCode);
        values.put(QTY, info.qty);
        values.put(SPEC, info.spec);
        for (NetQualityInfo.DetailBean bean : info.detail) {
            switch (bean.itemsFlag) {
                case "1":   //外观检测
                    values.put(FACADE_RESULT, bean.testResult);
                    values.put(FACADE_REMARK, bean.remark);
                    break;
                case "2":   //尺寸检测
                    values.put(SIZE_RESULT, bean.testResult);
                    values.put(SIZE_REMARK, bean.remark);
                    break;
                case "3":   //焊接检测
                    values.put(WELD_RESULT, bean.testResult);
                    values.put(WELD_REMARK, bean.remark);
                    break;
                default:
                    break;
            }
        }
        long rowNo = db.insert(TABLE_NAME, null, values);
        db.close();
        if (rowNo > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改指定质检号对应位置的图片路径
     *
     * @param code 质检号
     * @param path 图片路径
     * @param flag 修改的对应标志
     * @return 成功返回true, 否则返回false
     */
    public boolean uploadImg(String code, String path, String flag) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();

        switch (flag) {
            case NetQualityParse.FACADE1:
                values.put(FACADE_PIC1, path);
                break;
            case NetQualityParse.FACADE2:
                values.put(FACADE_PIC2, path);
                break;
            case NetQualityParse.FACADE3:
                values.put(FACADE_PIC3, path);
                break;
            case NetQualityParse.FACADE4:
                values.put(FACADE_PIC4, path);
                break;
            case NetQualityParse.SIZE1:
                values.put(SIZE_PIC1, path);
                break;
            case NetQualityParse.SIZE2:
                values.put(SIZE_PIC2, path);
                break;
            case NetQualityParse.SIZE3:
                values.put(SIZE_PIC3, path);
                break;
            case NetQualityParse.SIZE4:
                values.put(SIZE_PIC4, path);
                break;
            case NetQualityParse.WELD1:
                values.put(WELD_PIC1, path);
                break;
            case NetQualityParse.WELD2:
                values.put(WELD_PIC2, path);
                break;
            case NetQualityParse.WELD3:
                values.put(WELD_PIC3, path);
                break;
            case NetQualityParse.WELD4:
                values.put(WELD_PIC4, path);
                break;
            default:
                break;
        }

        int rows = db.update(TABLE_NAME, values, CODE + "=?", new String[]{code});
        db.close();
        if (rows > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 通过指定的质检单号或者项目名称查询对应的质检条目
     *
     * @param content 输入的内容
     * @return
     */
    public List<NetQualityInfo> queryQualityByContent(String content) {
        List<NetQualityInfo> qualityInfos = null;
        if (TextUtils.isEmpty(content)) {
            qualityInfos = getAllQualitys();
        } else {
            qualityInfos = new ArrayList<>();
            SQLiteDatabase db = dbHelp.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME, new String[]{ID, CODE, PROJECT_NAME, TESTUSER,
                    CJDATA, USERID, CML_CODE, CONTRUCTION_CODE, ORGANNAME, BARCODE, QTY,
                    SPEC, FACADE_RESULT, FACADE_REMARK, FACADE_PIC1, FACADE_PIC2, FACADE_PIC3,
                    FACADE_PIC4, SIZE_RESULT, SIZE_REMARK, SIZE_PIC1, SIZE_PIC2, SIZE_PIC3,
                    SIZE_PIC4, WELD_RESULT, WELD_REMARK, WELD_PIC1, WELD_PIC2, WELD_PIC3,
                    WELD_PIC4}, CODE + " like ? or " + PROJECT_NAME + " like ? ", new
                    String[]{"%" + content + "%", "%" + content + "%"}, null, null, CJDATA + " DESC");
            NetQualityInfo temp = null;
            while(cursor.moveToNext()) {
                temp = new NetQualityInfo();
                temp.code = cursor.getString(cursor.getColumnIndex(CODE));
                temp.project_name = cursor.getString(cursor.getColumnIndex(PROJECT_NAME));
                temp.testUser = cursor.getString(cursor.getColumnIndex(TESTUSER));
                temp.cjdata = cursor.getString(cursor.getColumnIndex(CJDATA));
                temp.userId = cursor.getString(cursor.getColumnIndex(USERID));
                temp.cml_code = cursor.getString(cursor.getColumnIndex(CML_CODE));
                temp.contruction_code = cursor.getString(cursor.getColumnIndex(CONTRUCTION_CODE));
                temp.organName = cursor.getString(cursor.getColumnIndex(ORGANNAME));
                temp.barCode = cursor.getString(cursor.getColumnIndex(BARCODE));
                temp.qty = cursor.getString(cursor.getColumnIndex(QTY));
                temp.spec = cursor.getString(cursor.getColumnIndex(SPEC));

                temp.detail = new ArrayList<>();
                NetQualityInfo.DetailBean facade = new NetQualityInfo.DetailBean();
                facade.itemsFlag = "1";
                facade.testResult = cursor.getString(cursor.getColumnIndex(FACADE_RESULT));
                facade.remark = cursor.getString(cursor.getColumnIndex(FACADE_REMARK));
                facade.pic1 = cursor.getString(cursor.getColumnIndex(FACADE_PIC1));
                facade.pic2 = cursor.getString(cursor.getColumnIndex(FACADE_PIC2));
                facade.pic3 = cursor.getString(cursor.getColumnIndex(FACADE_PIC3));
                facade.pic4 = cursor.getString(cursor.getColumnIndex(FACADE_PIC4));
                temp.detail.add(facade);

                NetQualityInfo.DetailBean size = new NetQualityInfo.DetailBean();
                size.itemsFlag = "2";
                size.testResult = cursor.getString(cursor.getColumnIndex(SIZE_RESULT));
                size.remark = cursor.getString(cursor.getColumnIndex(SIZE_REMARK));
                size.pic1 = cursor.getString(cursor.getColumnIndex(SIZE_PIC1));
                size.pic2 = cursor.getString(cursor.getColumnIndex(SIZE_PIC2));
                size.pic3 = cursor.getString(cursor.getColumnIndex(SIZE_PIC3));
                size.pic4 = cursor.getString(cursor.getColumnIndex(SIZE_PIC4));
                temp.detail.add(size);

                NetQualityInfo.DetailBean weld = new NetQualityInfo.DetailBean();
                weld.itemsFlag = "3";
                weld.testResult = cursor.getString(cursor.getColumnIndex(WELD_RESULT));
                weld.remark = cursor.getString(cursor.getColumnIndex(WELD_REMARK));
                weld.pic1 = cursor.getString(cursor.getColumnIndex(WELD_PIC1));
                weld.pic2 = cursor.getString(cursor.getColumnIndex(WELD_PIC2));
                weld.pic3 = cursor.getString(cursor.getColumnIndex(WELD_PIC3));
                weld.pic4 = cursor.getString(cursor.getColumnIndex(WELD_PIC4));
                temp.detail.add(weld);

                qualityInfos.add(temp);
            }
            cursor.close();
            db.close();
        }
        return qualityInfos;
    }

    /**
     * 根据指定的质检单号删除对应的质检单
     * @param code 质检单号
     * @return 删除成功返回true, 否则返回false
     */
    public boolean delQuality(String code) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, CODE + "=?", new String[]{code});
        db.close();
        if(rows > 0) {
            return true;
        }else {
            return false;
        }
    }
}
