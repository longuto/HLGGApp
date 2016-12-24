package com.simpotech.app.hlgg.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.simpotech.app.hlgg.ui.fragment.FacadeFragment;
import com.simpotech.app.hlgg.ui.fragment.SizeFragment;
import com.simpotech.app.hlgg.ui.fragment.WeldFragment;

/**
 * Created by longuto on 2016/12/16.
 * <p>
 * 质检查询页的FragmentPagerAdapter
 */

public class QualityPagerAdapter extends FragmentPagerAdapter {
    public String[] mTabNames;

    public QualityPagerAdapter(FragmentManager fm) {
        super(fm);
        mTabNames = new String[]{"外观检测", "尺寸检测", "焊缝检测"};
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FacadeFragment();
                break;
            case 1:
                fragment = new SizeFragment();
                break;
            case 2:
                fragment = new WeldFragment();
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
