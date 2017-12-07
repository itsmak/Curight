package com.innovellent.curight.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.innovellent.curight.fragment.ViewPagerFragment;

/**
 * Created by sagar on 10/12/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int[] mLuckyNumbers;

    public ViewPagerAdapter(FragmentManager fm, int[] luckyNumbers) {
        super(fm);
        this.mLuckyNumbers = luckyNumbers;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerFragment.LUCKY_NUMBER, position);
        ViewPagerFragment frag = new ViewPagerFragment();
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public int getCount() {
        return (mLuckyNumbers == null) ? 0: mLuckyNumbers.length;
    }
}