package com.example.prak_3;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class FragAdapter extends FragmentPagerAdapter {
    List<Info> data;
    public FragAdapter(FragmentManager fm, List<Info> data){
        super(fm);
        this.data = data;
    }

    public int getCount(){ return data.size(); }

    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

    public Fragment getItem(int position){
        return FragItem.newInstance(data, position);
    }
}