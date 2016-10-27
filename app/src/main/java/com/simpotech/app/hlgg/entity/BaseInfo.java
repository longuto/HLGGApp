package com.simpotech.app.hlgg.entity;

/**
 * 网络请求数据的基类
 * Created by longuto on 2016/10/27.
 */

public class BaseInfo<T> {

    public String code;
    public String msg;
    public T result;

    public BaseInfo(T result, String code, String msg) {
        this.result = result;
        this.code = code;
        this.msg = msg;
    }
}
