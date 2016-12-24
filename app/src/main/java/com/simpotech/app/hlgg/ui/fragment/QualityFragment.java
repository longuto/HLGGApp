package com.simpotech.app.hlgg.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.business.SharedManager;
import com.simpotech.app.hlgg.util.LogUtils;
import com.simpotech.app.hlgg.util.UiUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by longuto on 2016/12/16.
 * <p>
 * 质检查询页的每个条目的Fragment
 */

public abstract class QualityFragment extends Fragment {

    public static final int CAPTURE_REQUEST_CODE1 = 1;   //第一张图片的请求码
    public static final int CAPTURE_REQUEST_CODE2 = 2;   //第二张图片的请求码
    public static final int CAPTURE_REQUEST_CODE3 = 3;   //第三张图片的请求码
    public static final int CAPTURE_REQUEST_CODE4 = 4;   //第四张图片的请求码

    private static final String TAG = "QualityFragment";
    public SharedManager quaSp;

    @BindView(R.id.sp_result)
    Spinner resultSp;
    @BindView(R.id.edt_explain)
    EditText explainEdt;
    @BindView(R.id.pic01)
    ImageView pic01Iv;
    @BindView(R.id.pic02)
    ImageView pic02Iv;
    @BindView(R.id.pic03)
    ImageView pic03Iv;
    @BindView(R.id.pic04)
    ImageView pic04Iv;

    @OnLongClick({R.id.pic01, R.id.pic02, R.id.pic03, R.id.pic04})
    public boolean openCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri fileUri = null; //指定拍照成功后存储的路径
        switch (view.getId()) {
            case R.id.pic01:
                fileUri = getOutputMediaFileUri(1);
                LogUtils.i(TAG,fileUri.toString());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAPTURE_REQUEST_CODE1);
                break;
            case R.id.pic02:
                fileUri = getOutputMediaFileUri(2);
                LogUtils.i(TAG,fileUri.toString());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAPTURE_REQUEST_CODE2);
                break;
            case R.id.pic03:
                fileUri = getOutputMediaFileUri(3);
                LogUtils.i(TAG,fileUri.toString());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAPTURE_REQUEST_CODE3);
                break;
            case R.id.pic04:
                fileUri = getOutputMediaFileUri(4);
                LogUtils.i(TAG,fileUri.toString());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAPTURE_REQUEST_CODE4);
                break;
            default:
                break;
        }
        return true;    //消费事件
    }

    /**
     * 获取当前
     * @param i 第几张图片(1, 2, 3, 4)
     * @return 返回图片的Uri
     */
    protected Uri getOutputMediaFileUri(int i) {
        File rootFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "quality" + File.separator +
                getFragmentName());
        if (!rootFile.exists()) {
            LogUtils.i(TAG, getFragmentName() + "文件夹创建");
            if (!rootFile.mkdirs()) {
                LogUtils.i(TAG, "quality" + File.separator + getFragmentName() + "文件夹未创建成功");
            }
        }
        File file = getOutputMediaFile(i, rootFile);
        return Uri.fromFile(file);
    }

    /**
     * 创建每个图片的路径
     */
    protected File getOutputMediaFile(int i, File rootFile) {
        return new File(rootFile, "img" + i + ".jpg");
    }


    /** 指定存储图片的子文件的名称 */
    protected abstract String getFragmentName();

    /** 初始化当前的质检模块 */
    protected abstract void initFragmentView();

    /** 当拍照成功后,更改对应的Xml配置 */
    protected abstract void putIvConfig2Xml(int position);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_quality, container, false);
        ButterKnife.bind(this, view);

        quaSp = new SharedManager(SharedManager.QUALITY_CONFIG_NAME);
        initFragmentView();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(TAG, "onActivityResult");
        if (RESULT_OK == resultCode) {
            if(data != null) {
                LogUtils.i(TAG, "存储本地失败");
                UiUtils.showToast("存储本地失败,请重新拍照");
//                if (data.hasExtra("data")) {
//                    Bitmap thumbnail = data.getParcelableExtra("data");
//                    switch (requestCode) {
//                        case CAPTURE_REQUEST_CODE1:
//                            pic01Iv.setImageBitmap(thumbnail);
//                            break;
//                        case CAPTURE_REQUEST_CODE2:
//                            pic02Iv.setImageBitmap(thumbnail);
//                            break;
//                        case CAPTURE_REQUEST_CODE3:
//                            pic03Iv.setImageBitmap(thumbnail);
//                            break;
//                        case CAPTURE_REQUEST_CODE4:
//                            pic04Iv.setImageBitmap(thumbnail);
//                            break;
//                        default:
//                            break;
//                    }
//                }
            }else {
                LogUtils.i(TAG, "存储本地成功");
                Bitmap bitmap = null;
                switch (requestCode) {
                    case CAPTURE_REQUEST_CODE1:
                        bitmap = getBitFromLocal(CAPTURE_REQUEST_CODE1);
                        pic01Iv.setImageBitmap(bitmap);
                        putIvConfig2Xml(CAPTURE_REQUEST_CODE1);
                        break;
                    case CAPTURE_REQUEST_CODE2:
                        bitmap = getBitFromLocal(CAPTURE_REQUEST_CODE2);
                        pic02Iv.setImageBitmap(bitmap);
                        putIvConfig2Xml(CAPTURE_REQUEST_CODE2);
                        break;
                    case CAPTURE_REQUEST_CODE3:
                        bitmap = getBitFromLocal(CAPTURE_REQUEST_CODE3);
                        pic03Iv.setImageBitmap(bitmap);
                        putIvConfig2Xml(CAPTURE_REQUEST_CODE3);
                        break;
                    case CAPTURE_REQUEST_CODE4:
                        bitmap = getBitFromLocal(CAPTURE_REQUEST_CODE4);
                        pic04Iv.setImageBitmap(bitmap);
                        putIvConfig2Xml(CAPTURE_REQUEST_CODE4);
                        break;
                    default:
                        break;
                }
            }
        } else {
            UiUtils.showToast("请重新拍照");
        }
    }

    /**
     * 当拍照成功后,获取ImageView配置
     */
    protected Bitmap getBitFromLocal(int position) {
        int width = UiUtils.getScreenWidth() * 2 / 11;
        int height = width;

        BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
        factoryOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(getOutputMediaFileUri(position).getPath(), factoryOptions);

        int imageWidth = factoryOptions.outWidth;
        int imageHeight = factoryOptions.outHeight;
        // Determine how much to scale down the image
        int scaleFactor = Math.min(imageWidth / width, imageHeight / height);
        // Decode the image file into a Bitmap sized to fill the View
        factoryOptions.inJustDecodeBounds = false;
        factoryOptions.inSampleSize = scaleFactor;
        factoryOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(getOutputMediaFileUri(position).getPath(), factoryOptions);
    }
}
