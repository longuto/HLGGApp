package com.simpotech.app.hlgg.entity.net;

/**
 * 网络请求数据的基类
 * Created by longuto on 2016/10/27.
 */

public class BaseJsonInfo<T> {

    public String code;
    public String msg;
    public T result;

    public BaseJsonInfo() {}

    public BaseJsonInfo(T result, String code, String msg) {
        this.result = result;
        this.code = code;
        this.msg = msg;
    }
}
