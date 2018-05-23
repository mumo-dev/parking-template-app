package com.example.mumo.parkingapp;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.example.mumo.parkingapp.adapters.PageAdapter;
import com.example.mumo.parkingapp.fragments.LoginFragment;
import com.example.mumo.parkingapp.fragments.RegisterFragment;

public class AccountActivity extends AppCompatActivity  implements RegisterFragment.IViewPagerSwitcher{

    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        TabLayout tabLayout = findViewById(R.id.ta_layout);
        TabItem loginTab = findViewById(R.id.login_tab);

        TabItem registerTab = findViewById(R.id.register_tab);
        mViewPager= findViewById(R.id.viewpager);
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mViewPager.setAdapter(pageAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void switchToPage(int newPagePosition) {
        mViewPager.setCurrentItem(newPagePosition);
    }
}
