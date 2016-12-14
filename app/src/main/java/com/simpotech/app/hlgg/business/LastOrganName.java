package com.simpotech.app.hlgg.business;

/**
 * Created by longuto on 2016/12/10.
 *
 * 按照"/"切割加工厂,取最后一段数据
 */

public class LastOrganName {

    public static String getLastOrganName(String organName) {
        String[] splits = organName.split("/");
        return splits[splits.length - 1];
    }
}
