package com.simpotech.app.hlgg.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.simpotech.app.hlgg.R;
import com.simpotech.app.hlgg.entity.net.NetQualityInfo;
import com.simpotech.app.hlgg.util.GsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longuto on 2016/12/21.
 */

public abstract class QualityDetailFragment extends Fragment {

    @BindView(R.id.tv_result)
    TextView resultTv;
    @BindView(R.id.tv_explain)
    TextView explainTv;
    @BindView(R.id.pic01)
    ImageView pic01Iv;
    @BindView(R.id.pic02)
    ImageView pic02Iv;
    @BindView(R.id.pic03)
    ImageView pic03Iv;
    @BindView(R.id.pic04)
    ImageView pic04Iv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_quality_query, container, false);
        ButterKnife.bind(this, view);

        String json = getArguments().getString("DATA");
        List<NetQualityInfo.DetailBean> beans = (List<NetQualityInfo.DetailBean>) GsonUtils
                .fromJson(json, new TypeToken<List<NetQualityInfo.DetailBean>>() {
        }.getType());
        refreshFragment(beans);
        return view;
    }

    /**
     * 动态刷新界面数据
     */
    public abstract void refreshFragment(List<NetQualityInfo.DetailBean> beans);
}
