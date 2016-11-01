package com.simpotech.app.hlgg.entity;

import java.util.List;

/**
 * Created by longuto on 2016/10/31.
 */

public class ProLineInfo {

    public int id;  //主键
    public String departmentId; //部门id
    public String departmentName;   //部门名称
    public String proLineId;    //生产线id
    public String proLineName;  //生产线名称

    public ProLineInfo() {}

    public ProLineInfo(int id, String departmentId, String departmentName, String proLineId,
                       String proLineName) {
        this.id = id;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.proLineId = proLineId;
        this.proLineName = proLineName;
    }
}
