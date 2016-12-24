package com.simpotech.app.hlgg.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.simpotech.app.hlgg.entity.net.NetQualityInfo;
import com.simpotech.app.hlgg.ui.fragment.FacadeDetailFragment;
import com.simpotech.app.hlgg.ui.fragment.QualityDetailFragment;
import com.simpotech.app.hlgg.ui.fragment.SizeDetailFragment;
import com.simpotech.app.hlgg.ui.fragment.WeldDetailFragment;

import java.util.List;

/**
 * Created by longuto on 2016/12/21.
 */

public class QualityDetailPagerAdapter extends FragmentPagerAdapter {

    String[] mTabNames;
    List<NetQualityInfo.DetailBean> data;

    public QualityDetailPagerAdapter(FragmentManager fm, List<NetQualityInfo.DetailBean> data) {
        super(fm);
        mTabNames = new String[]{"外观检测", "尺寸检测", "焊缝检测"};
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        QualityDetailFragment fragment = null;

        switch (position) {
            case 0:
                fragment = FacadeDetailFragment.newInstance(data);
                break;
            case 1:
                fragment = SizeDetailFragment.newInstance(data);
                break;
            case 2:
                fragment = WeldDetailFragment.newInstance(data);
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTabNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabNames[position];
    }
}
