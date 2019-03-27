package com.example.schoolchatdemo.Frame;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mlist;
    private String[] mtitle;
    public MyFragmentAdapter(FragmentManager fm, List<Fragment> list, String[] title) {
        super(fm);
        mlist=list;
        mtitle=title;
    }

    @Override
    public Fragment getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mtitle[position];
    }
}
