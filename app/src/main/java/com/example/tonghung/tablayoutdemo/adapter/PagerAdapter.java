package com.example.tonghung.tablayoutdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tonghung.tablayoutdemo.FragmentOne;
import com.example.tonghung.tablayoutdemo.FragmentThree;
import com.example.tonghung.tablayoutdemo.FragmentTwo;

/**
 * Created by tonghung on 30/12/2016.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new FragmentOne();
        }else if(position == 1){
            return new FragmentTwo();
        }else if (position == 2){
            return new FragmentThree();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return "Tab 1";
        }else if(position == 1){
            return "Tab 2";
        }else if(position == 2){
            return "Tab 3";
        }
        return null;
    }
}
