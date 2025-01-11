package org.wildstang.wildrank.androidv2.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.wildstang.wildrank.androidv2.fragments.PicklistSecondFragment;
import org.wildstang.wildrank.androidv2.fragments.PicklistFirstFragment;

public class PicklistFragmentPagerAdapter extends FragmentStatePagerAdapter {
    static final int NUM_FRAGMENTS = 2;

    private PicklistFirstFragment firstFragment;
    private PicklistSecondFragment secondFragment;

    public PicklistFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments() {
        firstFragment = new PicklistFirstFragment();
        secondFragment = new PicklistSecondFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return firstFragment;
            case 1:
                return secondFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return NUM_FRAGMENTS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "First Pick";
            case 1:
                return "Second Pick";
            default:
                return "ERROR INVALID POSITION";
        }
    }
}
