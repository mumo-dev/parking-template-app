package com.example.mumo.parkingapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mumo.parkingapp.fragments.LoginFragment;
import com.example.mumo.parkingapp.fragments.RegisterFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private int mNumOftabs;

    public PageAdapter(FragmentManager fm, int numOftabs) {
        super(fm);
        mNumOftabs = numOftabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LoginFragment();
            case 1:
                return new RegisterFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOftabs;
    }
}
