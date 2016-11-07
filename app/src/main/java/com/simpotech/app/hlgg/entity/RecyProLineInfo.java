package com.simpotech.app.hlgg.entity;

import com.simpotech.app.hlgg.entity.net.NetProLineInfo;

import java.util.List;

/**
 * Created by longuto on 2016/11/3.
 * 网络生产线配置的RecyclerView的数据
 */

public class RecyProLineInfo {

    public String id;
    public String name;
    public List<DbProLineInfo> prolines;

    public RecyProLineInfo(String id, String name, List<DbProLineInfo> prolines) {
        this.id = id;
        this.name = name;
        this.prolines = prolines;
    }
}
