package com.innovellent.curight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.innovellent.curight.R;
import com.innovellent.curight.fragment.EditProfileFragment;
import com.innovellent.curight.fragment.EmergencyContactFragment;

import java.util.ArrayList;
import java.util.List;


public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TabLayout tabLayout;
    ImageView ivBack;
    ViewPager viewpager;
    EditProfileActivity.ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
        iniClick();
        setupViewPagerReminder();

    }


    public void init() {

        ivBack = (ImageView) findViewById(R.id.ivBack);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorPrimary));


    }

    public void iniClick() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ivBack:
                Intent i = new Intent(EditProfileActivity.this, HomeActivity.class);
                i.putExtra("flag", "profile");
                startActivity(i);
                break;


        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(EditProfileActivity.this, HomeActivity.class);
        i.putExtra("flag", "profile");
        startActivity(i);
        finish();
        super.onBackPressed();
    }
    private void setupViewPagerReminder() {
        adapter = new EditProfileActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.notifyDataSetChanged();
        adapter.addFragment(new EditProfileFragment(), "PROFILE");
        adapter.addFragment(new EmergencyContactFragment(), "EMERGENCY CONTACT");
        viewpager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
            super(manager);
            if (manager.getFragments() != null) {
                manager.getFragments().clear();
            }
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
