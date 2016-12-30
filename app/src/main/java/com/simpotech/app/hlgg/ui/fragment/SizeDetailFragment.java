package com.simpotech.app.hlgg.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.api.Constant;
import com.simpotech.app.hlgg.business.DisplayImageOptionManager;
import com.simpotech.app.hlgg.entity.net.NetQualityInfo;
import com.simpotech.app.hlgg.ui.activity.PhotoActivity;
import com.simpotech.app.hlgg.util.GsonUtils;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by longuto on 2016/12/21.
 */

public class SizeDetailFragment extends QualityDetailFragment {

    private NetQualityInfo.DetailBean data;

    @OnClick({R.id.pic01, R.id.pic02, R.id.pic03, R.id.pic04})
    public void openPhotoDetail(View view) {
        String path = null;
        Intent intent = new Intent(getContext(), PhotoActivity.class);
        switch (view.getId()) {
            case R.id.pic01:
                path = Constant.HOST + data.pic1;
                intent.putExtra("PATH", path);
                break;
            case R.id.pic02:
                path = Constant.HOST + data.pic2;
                intent.putExtra("PATH", path);
                break;
            case R.id.pic03:
                path = Constant.HOST + data.pic3;
                intent.putExtra("PATH", path);
                break;
            case R.id.pic04:
                path = Constant.HOST + data.pic4;
                intent.putExtra("PATH", path);
                break;
            default:
                break;
        }
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }

    public static QualityDetailFragment newInstance(List<NetQualityInfo.DetailBean> data) {
        QualityDetailFragment fragment = new SizeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DATA", GsonUtils.toJson(data));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void refreshFragment(List<NetQualityInfo.DetailBean> beans) {
        for (NetQualityInfo.DetailBean bean : beans) {
            if("2".equals(bean.itemsFlag)) {
                data = bean;
                if("1".equals(bean.testResult)) {
                    resultTv.setText("合格");
                }else {
                    resultTv.setText("不合格");
                }
                explainTv.setText(bean.remark);

                ImageLoader.getInstance().displayImage(Constant.HOST + bean.pic1, pic01Iv,
                        DisplayImageOptionManager.getQualityOption());
                ImageLoader.getInstance().displayImage(Constant.HOST + bean.pic2, pic02Iv,
                        DisplayImageOptionManager.getQualityOption());
                ImageLoader.getInstance().displayImage(Constant.HOST + bean.pic3, pic03Iv,
                        DisplayImageOptionManager.getQualityOption());
                ImageLoader.getInstance().displayImage(Constant.HOST + bean.pic4, pic04Iv,
                        DisplayImageOptionManager.getQualityOption());
            }
        }
    }
}
