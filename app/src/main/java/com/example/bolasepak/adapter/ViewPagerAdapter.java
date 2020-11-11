package com.example.bolasepak.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.bolasepak.R;
import com.example.bolasepak.fragment.FutureMatch;
import com.example.bolasepak.fragment.PastMatch;
import com.example.bolasepak.model.MatchEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> listFragment = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

         switch (position){
            case 0:
                return "Upcoming Match";
            case 1:
                return "Past Match";
        }
        return "0";
    }

    public void addFragment (Fragment fragment){
        listFragment.add(fragment);
        Log.e( Integer.toString(getCount()), "WEW");
    }
}
