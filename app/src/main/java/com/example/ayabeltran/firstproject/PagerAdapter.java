package com.example.ayabeltran.firstproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Lorenzo11 on 15/02/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;


    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                FragList fragList = new FragList();
                return fragList;
            case 1:
                FragGrid fragGrid = new FragGrid();
                return fragGrid;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}