package com.simpotech.app.hlgg.business;

import com.simpotech.app.hlgg.entity.BarcodeInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.R.id.list;

/**
 * Created by longuto on 2016/11/7.
 */

public class BarcodeManager {

    /**
     * 调用红外扫描指定二维码生成构件类型数据
     *
     * @return
     */
    public static BarcodeInfo getContentByBarcode() {
        List<BarcodeInfo> list = new ArrayList<BarcodeInfo>();
        BarcodeInfo bar1 = new BarcodeInfo("SCRWD20160923011", "四十厂", "40C-GL-4",
                "HI350*180*6*6", "120161013000300002", "1", "人行天桥钢结构加工工程");
        BarcodeInfo bar2 = new BarcodeInfo("SCRWD20160923012", "五十厂", "40C-GL-5",
                "HI350*180*6*7", "120161013000300003", "2", "科大讯飞钢结构加工工程");
        BarcodeInfo bar3 = new BarcodeInfo("SCRWD20160923013", "六十厂", "40C-GL-6",
                "HI350*180*6*8", "120161013000300004", "3", "安徽辛普钢结构加工工程");
        BarcodeInfo bar4 = new BarcodeInfo("SCRWD20160923014", "七十厂", "40C-GL-7",
                "HI350*180*6*9", "120161013000300005", "4", "上海奔逸钢结构结构加工工程");
        BarcodeInfo bar5 = new BarcodeInfo("SCRWD20160923015", "八十厂", "40C-GL-8",
                "HI350*180*7*9", "120161013000300006", "5", "上海云宝钢结构结构加工工程");
        list.add(bar1);
        list.add(bar2);
        list.add(bar3);
        list.add(bar4);
        list.add(bar5);

        Random r = new Random();
        int i = r.nextInt(list.size());
        return list.get(i);
    }
}
