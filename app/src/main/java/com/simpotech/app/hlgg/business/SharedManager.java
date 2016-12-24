package com.simpotech.app.hlgg.business;

import android.content.Context;
import android.content.SharedPreferences;

import com.simpotech.app.hlgg.util.UiUtils;

/**
 * 配置个人偏爱设置
 * Created by longuto on 2016/10/27.
 */

public class SharedManager {

    private final String CONFIG_NAME = "normal_config"; //默认配置文件的名称
    public static final String USERNAME = "username";   //登录的用户名
    public static final String USERID = "userId"; //用户的id
    public static final String PROLINE_POSITION = "proline_position";    //默认选择的生产线位置

    public static final String PROCESS_CONFIG_NAME = "process_config_name"; //流程模块的配置文件名称

    public static final String QUALITY_CONFIG_NAME = "quality_config_name"; //质检模块的配置文件名称
    public static final String FACADE_RESULT = "facadeResult";  //外观检测结果
    public static final String FACADE_REMARK = "facadeRemark";  //外观检测结果
    public static final String FACADE_PIC1 = "facadePic1";  //外观图片1
    public static final String FACADE_PIC2 = "facadePic2";  //外观图片2
    public static final String FACADE_PIC3 = "facadePic3";  //外观图片3
    public static final String FACADE_PIC4 = "facadePic4";  //外观图片4
    public static final String SIZE_RESULT = "sizeResult";  //尺寸检测结果
    public static final String SIZE_REMARK = "sizeRemark";  //尺寸检测结果
    public static final String SIZE_PIC1 = "sizePic1";  //尺寸图片1
    public static final String SIZE_PIC2 = "sizePic2";  //尺寸图片2
    public static final String SIZE_PIC3 = "sizePic3";  //尺寸图片3
    public static final String SIZE_PIC4 = "sizePic4";  //尺寸图片4
    public static final String WELD_RESULT = "weldResult";  //焊接检测结果
    public static final String WELD_REMARK = "weldRemark";  //焊接检测结果
    public static final String WELD_PIC1 = "weldPic1";  //焊接图片1
    public static final String WELD_PIC2 = "weldPic2";  //焊接图片2
    public static final String WELD_PIC3 = "weldPic3";  //焊接图片3
    public static final String WELD_PIC4 = "weldPic4";  //焊接图片4


    SharedPreferences sp;

    /**系统默认配置文件*/
    public SharedManager() {
        sp = UiUtils.getContext().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
    }

    /**自定义配置文件*/
    public SharedManager(String name) {
        sp = UiUtils.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**获取对象*/
    public SharedPreferences getSharedPreferences() {
        return sp;
    }


    /**将字符串储存至XML*/
    public void putStringToXml(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    /**从XML中获取指定名称的字符串值,没有返回默认值*/
    public String getStringFromXml(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    /**从XML中获取指定名称的字符串值,没有返回null*/
    public String getStringFromXml(String key) {
        return getStringFromXml(key, null);
    }

    /**将布尔值储存至XML*/
    public void putBooleanToXml(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    /**从XML中获取指定名称的布尔值,没有返回默认值*/
    public boolean getBooleanFromXml(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    /**从XML中获取指定名称的布尔值值,没有返回false*/
    public boolean getBooleanFromXml(String key) {
        return getBooleanFromXml(key, false);
    }

    /**将整型储存至XML*/
    public void putIntegerToXml(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    /**从XML中获取指定名称的整型,没有返回默认值*/
    public int getIntegerFromXml(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    /**从XML中获取指定名称的整型,没有返回false*/
    public int getIntegerFromXml(String key) {
        return getIntegerFromXml(key, 0);
    }
}
