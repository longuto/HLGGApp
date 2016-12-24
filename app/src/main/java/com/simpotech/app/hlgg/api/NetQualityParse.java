package com.simpotech.app.hlgg.api;

import android.os.Environment;

import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.db.dao.QualityDb;
import com.simpotech.app.hlgg.entity.net.BaseJsonInfo;
import com.simpotech.app.hlgg.entity.net.NetImageInfo;
import com.simpotech.app.hlgg.entity.net.NetQualityInfo;
import com.simpotech.app.hlgg.entity.submit.SubQualityInfo;
import com.simpotech.app.hlgg.util.GsonUtils;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;

import okhttp3.Call;

/**
 * Created by longuto on 2016/12/20.
 * <p>
 * 质检写入流程网络请求的处理
 */

public class NetQualityParse {

    public static final String URL_QUALITY_SAVE = Constant.HOST + Constant.QUALITYSAVE; //质检录入地址

    public static final String URL_QUALITY_UPLOAD = Constant.HOST + Constant.QUALITYUPLOAD; //质检上传图片

    private static final String TAG = "NetQualityParse";    //标记

    public static final String FACADE1 = "facade1";
    public static final String FACADE2 = "facade2";
    public static final String FACADE3 = "facade3";
    public static final String FACADE4 = "facade4";
    public static final String SIZE1 = "size1";
    public static final String SIZE2 = "size2";
    public static final String SIZE3 = "size3";
    public static final String SIZE4 = "size4";
    public static final String WELD1 = "weld1";
    public static final String WELD2 = "weld2";
    public static final String WELD3 = "weld3";
    public static final String WELD4 = "weld4";

    /**
     * 质检录入构件质检信息
     *
     * @param info
     */
    public static void qualitySave(SubQualityInfo info) {

        String json = GsonUtils.toJson(info);
        LogUtils.i(TAG, json);

        OkHttpUtils.post()
                .url(URL_QUALITY_SAVE)
                .addParams("json", json)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UiUtils.showToast("网络加载失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //将对应的字符串解析成是实体类
                        BaseJsonInfo<NetQualityInfo> temp = (BaseJsonInfo<NetQualityInfo>)
                                GsonUtils.fromJson(response, new
                                        TypeToken<BaseJsonInfo<NetQualityInfo>>() {
                                        }.getType());
                        if ("success".equals(temp.code)) {
                            NetQualityInfo info = temp.result;
                            QualityDb db = new QualityDb();
                            if (db.addQuality(info)) {
                                UiUtils.showToast("质检单加载本地成功");

                                //添加成功后,上传质检图片,并返回对应的路径
                                HashMap<String, File> maps = getNeedUploadImg();
                                for (String str : maps.keySet()) {
                                    File file = maps.get(str);
                                    uploadImage(file, str, info.code);
                                }
                            } else {
                                UiUtils.showToast("质检单加载本地失败");
                            }
                        } else {
                            UiUtils.showToast(temp.msg);    //显示出错原因
                        }
                    }
                });


    }

    /**
     * 上传对应的图片文件至服务器,修改对应的数据库表路径
     *
     * @param file 图片文件
     */
    public static void uploadImage(File file, final String flag, final String code) {

        LogUtils.i(TAG, "fileName : " + file.getName());
        OkHttpUtils.post()
                .url(URL_QUALITY_UPLOAD)
                .addFile("file", file.getName(), file)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UiUtils.showToast("图片标志为" + flag + "的图片加载网络失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i(TAG, "网络加载成功" + response);
                        if(!"error".equals(response.trim())) {
                            NetImageInfo info = (NetImageInfo) GsonUtils.fromJson(response,
                                    NetImageInfo.class);
                            QualityDb db = new QualityDb();
                            if(!db.uploadImg(code, info.filePath, flag)) {
                                UiUtils.showToast("图片标志为" + flag + "的图片加载本地失败");
                            }
                        }else {
                            UiUtils.showToast("图片标志为" + flag + "的图片上传失败");
                        }
                    }
                });
    }


    /**
     * 根据质检的配置xml文件,返回需要上传图片的位置集合
     *
     * @return
     */
    public static HashMap<String, File> getNeedUploadImg() {
        SharedManager spQua = new SharedManager(SharedManager.QUALITY_CONFIG_NAME);
        HashMap<String, File> maps = new HashMap<>();

        if (spQua.getBooleanFromXml(SharedManager.FACADE_PIC1)) {
            File file = getFileByPos(1, 1);
            if (file.exists()) {
                maps.put(FACADE1, file);
            }
        }
        if (spQua.getBooleanFromXml(SharedManager.FACADE_PIC2)) {
            File file = getFileByPos(1, 2);
            if (file.exists()) {
                maps.put(FACADE2, file);
            }
        }
        if (spQua.getBooleanFromXml(SharedManager.FACADE_PIC3)) {
            File file = getFileByPos(1, 3);
            if (file.exists()) {
                maps.put(FACADE3, file);
            }
        }
        if (spQua.getBooleanFromXml(SharedManager.FACADE_PIC4)) {
            File file = getFileByPos(1, 4);
            if (file.exists()) {
                maps.put(FACADE4, file);
            }
        }

        if (spQua.getBooleanFromXml(SharedManager.SIZE_PIC1)) {
            File file = getFileByPos(2, 1);
            if (file.exists()) {
                maps.put(SIZE1, file);
            }
        }
        if (spQua.getBooleanFromXml(SharedManager.SIZE_PIC2)) {
            File file = getFileByPos(2, 2);
            if (file.exists()) {
                maps.put(SIZE2, file);
            }
        }
        if (spQua.getBooleanFromXml(SharedManager.SIZE_PIC3)) {
            File file = getFileByPos(2, 3);
            if (file.exists()) {
                maps.put(SIZE3, file);
            }
        }
        if (spQua.getBooleanFromXml(SharedManager.SIZE_PIC4)) {
            File file = getFileByPos(2, 4);
            if (file.exists()) {
                maps.put(SIZE4, file);
            }
        }

        if (spQua.getBooleanFromXml(SharedManager.WELD_PIC1)) {
            File file = getFileByPos(3, 1);
            if (file.exists()) {
                maps.put(WELD1, file);
            }
        }
        if (spQua.getBooleanFromXml(SharedManager.WELD_PIC2)) {
            File file = getFileByPos(3, 2);
            if (file.exists()) {
                maps.put(WELD2, file);
            }
        }
        if (spQua.getBooleanFromXml(SharedManager.WELD_PIC3)) {
            File file = getFileByPos(3, 3);
            if (file.exists()) {
                maps.put(WELD3, file);
            }
        }
        if (spQua.getBooleanFromXml(SharedManager.WELD_PIC4)) {
            File file = getFileByPos(3, 4);
            if (file.exists()) {
                maps.put(WELD4, file);
            }
        }

        return maps;
    }

    /**
     * 根据对应的位置返回File文件
     *
     * @param i (1.外观, 2.尺寸, 3.焊接)
     * @param j (图片位置: 1 2 3 4)
     * @return 图片文件
     */
    public static File getFileByPos(int i, int j) {
        File rootFile = new File(Environment.getExternalStoragePublicDirectory(Environment
                .DIRECTORY_PICTURES), "quality");
        switch (i) {
            case 1:
                rootFile = new File(rootFile, "facade");
                break;
            case 2:
                rootFile = new File(rootFile, "size");
                break;
            case 3:
                rootFile = new File(rootFile, "weld");
                break;
            default:
                break;
        }
        File file = new File(rootFile, "img" + j + ".jpg");
        return file;
    }
}
