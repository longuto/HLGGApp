package com.simpotech.app.hlgg.entity;

/**
 * Created by longuto on 2016/10/27.
 */

public class LoginInfo {

    /**
     * code : success/fail
     * msg : 失败原因
     * result : 1:入库权限  2：出库权限 3：质检权限 4：入库 出库权限 5：入库 质检权限 6：出库 质检权限 7：都有权限 0：都没有权限
     */

    private String code;
    private String msg;
    private String result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
