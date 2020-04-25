package com.example.prak_3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class Frag extends Fragment {
    List<Info> data;
    int start_pos;
    ViewPager vp;
    static FragAdapter pagerAdapter;
    public static Frag newInstance(List<Info> data, int start_position)
    {
        Frag frag = new Frag();
        frag.data = data;
        frag.start_pos = start_position;

        return frag;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static String tag() {
        return Frag.class.getSimpleName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager, container, false);
        vp = (ViewPager) view.findViewById(R.id.view_Pager);
        setAdapter();
        return view;
    }

    public void setAdapter() {
        pagerAdapter = new FragAdapter(getChildFragmentManager(), data);
        vp.setAdapter(pagerAdapter);
        vp.setCurrentItem(start_pos);
    }
}