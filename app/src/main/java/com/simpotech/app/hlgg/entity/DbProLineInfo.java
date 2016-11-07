package com.simpotech.app.hlgg.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by longuto on 2016/10/31.
 * 数据库中主键的名称
 */

public class DbProLineInfo {

    public int id;  //主键
    public String departmentId; //部门id
    public String departmentName;   //部门名称
    public String proLineId;    //生产线id
    public String proLineName;  //生产线名称

    public boolean isChecked;   //是否选中(使用与RecyclerView展示的判断)

    public DbProLineInfo() {}

    public DbProLineInfo(String departmentId, String departmentName, String proLineId,
                         String proLineName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.proLineId = proLineId;
        this.proLineName = proLineName;
    }
}
