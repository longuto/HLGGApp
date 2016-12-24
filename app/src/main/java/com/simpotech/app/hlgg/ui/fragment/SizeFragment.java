package com.simpotech.app.hlgg.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

import butterknife.OnClick;

/**
 * Created by longuto on 2016/12/17.
 *
 * 尺寸检测Fragment
 */

public class SizeFragment extends QualityFragment {

    private static String TAG = "SizeFragment";

    @Override
    public String getFragmentName() {
        return "size";
    }

    @Override
    protected void initFragmentView() {
        initData();
        initListener();
    }

    @OnClick({R.id.pic01, R.id.pic02, R.id.pic03, R.id.pic04})
    public void openPhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri fileUri = null;
        switch (view.getId()) {
            case R.id.pic01:
                if(quaSp.getBooleanFromXml(SharedManager.SIZE_PIC1)) {
                    fileUri = getOutputMediaFileUri(1);
                    LogUtils.i(TAG,fileUri.toString());
                    intent.setDataAndType(fileUri, "image/*");
                    startActivity(intent);
                }else {
                    UiUtils.showToast("长按先拍照");
                }
                break;
            case R.id.pic02:
                if(quaSp.getBooleanFromXml(SharedManager.SIZE_PIC2)) {
                    fileUri = getOutputMediaFileUri(2);
                    LogUtils.i(TAG,fileUri.toString());
                    intent.setDataAndType(fileUri, "image/*");
                    startActivity(intent);
                }else {
                    UiUtils.showToast("长按先拍照");
                }
                break;
            case R.id.pic03:
                if(quaSp.getBooleanFromXml(SharedManager.SIZE_PIC3)) {
                    fileUri = getOutputMediaFileUri(3);
                    LogUtils.i(TAG,fileUri.toString());
                    intent.setDataAndType(fileUri, "image/*");
                    startActivity(intent);
                }else {
                    UiUtils.showToast("长按先拍照");
                }
                break;
            case R.id.pic04:
                if(quaSp.getBooleanFromXml(SharedManager.SIZE_PIC4)) {
                    fileUri = getOutputMediaFileUri(4);
                    LogUtils.i(TAG,fileUri.toString());
                    intent.setDataAndType(fileUri, "image/*");
                    startActivity(intent);
                }else {
                    UiUtils.showToast("长按先拍照");
                }
                break;
            default:
                break;
        }
    }

    /** 初始化监听事件 */
    private void initListener() {
        resultSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    quaSp.putStringToXml(SharedManager.SIZE_RESULT, "合格");
                }else if(position == 1) {
                    quaSp.putStringToXml(SharedManager.SIZE_RESULT, "不合格");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //设置EditText文本监听事件
        explainEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String remark = s.toString().trim();
                quaSp.putStringToXml(SharedManager.SIZE_REMARK, remark);
            }
        });

    }

    /** 初始化显示事件 */
    private void initData() {
        //初始化检测结果
        String result = quaSp.getStringFromXml(SharedManager.SIZE_RESULT, "").trim();
        switch (result) {
            case "不合格":
                resultSp.setSelection(1);
                break;
            case "合格":
            default:
                resultSp.setSelection(0);
                break;
        }
        //初始化说明信息
        String remark = quaSp.getStringFromXml(SharedManager.SIZE_REMARK, "").trim();
        explainEdt.setText(remark);
        //初始化图片显示
        boolean pic1 = quaSp.getBooleanFromXml(SharedManager.SIZE_PIC1);
        if(pic1) {
            Bitmap bitmap1 = getBitFromLocal(QualityFragment.CAPTURE_REQUEST_CODE1);
            pic01Iv.setImageBitmap(bitmap1);
        }else {
            pic01Iv.setImageResource(R.drawable.vector_quality_camera);
        }
        boolean pic2 = quaSp.getBooleanFromXml(SharedManager.SIZE_PIC2);
        if(pic2) {
            Bitmap bitmap2 = getBitFromLocal(QualityFragment.CAPTURE_REQUEST_CODE2);
            pic02Iv.setImageBitmap(bitmap2);
        }else {
            pic02Iv.setImageResource(R.drawable.vector_quality_camera);
        }
        boolean pic3 = quaSp.getBooleanFromXml(SharedManager.SIZE_PIC3);
        if(pic3) {
            Bitmap bitmap3 = getBitFromLocal(QualityFragment.CAPTURE_REQUEST_CODE3);
            pic03Iv.setImageBitmap(bitmap3);
        }else {
            pic03Iv.setImageResource(R.drawable.vector_quality_camera);
        }
        boolean pic4 = quaSp.getBooleanFromXml(SharedManager.SIZE_PIC4);
        if(pic4) {
            Bitmap bitmap4 = getBitFromLocal(QualityFragment.CAPTURE_REQUEST_CODE4);
            pic04Iv.setImageBitmap(bitmap4);
        }else {
            pic04Iv.setImageResource(R.drawable.vector_quality_camera);
        }
    }

    @Override
    protected void putIvConfig2Xml(int position) {
        switch (position) {
            case QualityFragment.CAPTURE_REQUEST_CODE1:
                quaSp.putBooleanToXml(SharedManager.SIZE_PIC1, true);
                break;
            case QualityFragment.CAPTURE_REQUEST_CODE2:
                quaSp.putBooleanToXml(SharedManager.SIZE_PIC2, true);
                break;
            case QualityFragment.CAPTURE_REQUEST_CODE3:
                quaSp.putBooleanToXml(SharedManager.SIZE_PIC3, true);
                break;
            case QualityFragment.CAPTURE_REQUEST_CODE4:
                quaSp.putBooleanToXml(SharedManager.SIZE_PIC4, true);
                break;
            default:
                break;
        }
    }
}
