package com.innovellent.curight.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


import com.innovellent.curight.R;
import com.innovellent.curight.adapter.DiagnosticTestAdapter;
import com.innovellent.curight.fragment.ArticleFragment;
import com.innovellent.curight.fragment.ForyouFragment;
import com.innovellent.curight.fragment.ReminderFragment;

import java.util.ArrayList;
import java.util.List;


public class HealthTodayActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recycler_view;
    DiagnosticTestAdapter mAdapter;
    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Button btnSubmit;

    ArrayList<String> arrayList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_today);
        init();
        getData();

    }

    public void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void getData(){
    //    mAdapter=new DiagnosticTestAdapter(HealthTodayActivity.this,arrayList);
    //    recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
   //     recycler_view.setAdapter(mAdapter);

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ArticleFragment(), "Categories");
        adapter.addFragment(new ForyouFragment(), "Articles");
        adapter.addFragment(new ReminderFragment(), "Wishlist");
        viewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {


        }
    }


}

