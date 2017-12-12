package com.innovellent.curight.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.innovellent.curight.LoginActivity;
import com.innovellent.curight.R;
import com.innovellent.curight.adapter.CustomSpinnerAdapter2;
import com.innovellent.curight.adapter.DiagnosticTestAdapter;
import com.innovellent.curight.fragment.ArticleFragment;
import com.innovellent.curight.fragment.BMIFragment;
import com.innovellent.curight.fragment.BPFragment;
import com.innovellent.curight.fragment.BloodCountListFragment;
import com.innovellent.curight.fragment.BloodSugarFragment;
import com.innovellent.curight.fragment.CholesterolFragment;
import com.innovellent.curight.fragment.ForyouFragment;
import com.innovellent.curight.fragment.HomeFragment;
import com.innovellent.curight.fragment.ListBloodSugarFragment;
import com.innovellent.curight.fragment.MedicineReminderFragment;
import com.innovellent.curight.fragment.ProfileFragment;
import com.innovellent.curight.fragment.ReminderFragment;
import com.innovellent.curight.fragment.FemaleCycleFragment;
import com.innovellent.curight.fragment.WHRFragment;
import com.innovellent.curight.model.AddBMIRecordsDialog;
import com.innovellent.curight.model.AddBPRecordsDialog;
import com.innovellent.curight.model.AddBloodSugarDialog;
import com.innovellent.curight.model.ExpandableListAdapter;
import com.innovellent.curight.model.ExpandedMenuModel;
import com.innovellent.curight.model.ReminderDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TrackActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    ExpandableListAdapter mMenuAdapter;
    ExpandableListView expandableList;
    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;
    Fragment currentFragment;
    DrawerLayout drawer;
    RecyclerView recycler_view;
    DiagnosticTestAdapter mAdapter;
    Toolbar toolbar;
    ViewPager viewpager;
    ViewPagerAdapter adapter;
    Spinner spUser;
    TextView tvTrends, tvList;
    TextView tvHome, tvTitle, tvReminder, tvArticle, tvTrack, tvProfile;
    ImageView ivHome, ivreminder, ivArticle, ivTrack, ivProfile, ivAdd;
    Button btnSubmit;
    AddBPRecordsDialog addRecordsDialog;
    ReminderDialog reminderDialog;
    AddBMIRecordsDialog addBMIRecordsDialog;
    AddBloodSugarDialog addBloodSugarDialog;
    FrameLayout frameontainer;
    LinearLayout button_panel, llList, button_track, button_profile, button_article, button_reminder, button_home, button_report, button_bmi, button_bloodsugar, button_excercise;
    String[] spinner1 = {"John", "Jobby", "Suresh", "Mahesh"};
    ArrayList<String> arrayList = new ArrayList<String>();
    FrameLayout frameLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String add = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);
        init();
        getData();
        onclick();
        setColor();
        prepareListData();
        tvTitle.setText("Home");
        mMenuAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList);
        // setting list adapter
        currentFragment = getSupportFragmentManager().findFragmentById(R.id.rlMainFragment);
        expandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Log.d("onGroupExpand", "" + listDataHeader.get(groupPosition).getIconName());
                if (mMenuAdapter.getChildrenCount(groupPosition) == 0) {
                    //  fragmentChange(listDataHeader.get(groupPosition).getIconName());
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });


        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.d("onChildClick", "" + groupPosition);
                return false;
            }
        });

        expandableList.setAdapter(mMenuAdapter);

    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPagerHome(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        spUser = (Spinner) findViewById(R.id.spUser);
        button_article = (LinearLayout) findViewById(R.id.button_article);
        button_track = (LinearLayout) findViewById(R.id.button_track);
        button_profile = (LinearLayout) findViewById(R.id.button_profile);
        button_reminder = (LinearLayout) findViewById(R.id.button_reminder);
        button_home = (LinearLayout) findViewById(R.id.button_home);
        ivHome = (ImageView) findViewById(R.id.ivHome);
        tvHome = (TextView) findViewById(R.id.tvHome);
        ivreminder = (ImageView) findViewById(R.id.ivreminder);
        tvReminder = (TextView) findViewById(R.id.tvReminder);
        ivArticle = (ImageView) findViewById(R.id.ivArticle);
        tvArticle = (TextView) findViewById(R.id.tvArticle);
        tvTrack = (TextView) findViewById(R.id.tvTrack);
        ivTrack = (ImageView) findViewById(R.id.ivTrack);
        tvTitle = (TextView) findViewById(R.id.title);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        tvProfile = (TextView) findViewById(R.id.tvProfile);
        frameLayout = (FrameLayout) findViewById(R.id.rlMainFragment);
        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.darkgray));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ivAdd=(ImageView)findViewById(R.id.ivAdd);

    }

    public void onclick() {
        button_home.setOnClickListener(this);
        button_article.setOnClickListener(this);
        button_track.setOnClickListener(this);
        button_reminder.setOnClickListener(this);
        button_profile.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        spUser.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);

    }

    public void getData() {

        CustomSpinnerAdapter2 customSpinnerAdapter3 = new CustomSpinnerAdapter2(TrackActivity.this, spinner1);
        spUser.setAdapter(customSpinnerAdapter3);
        spUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setupViewPagerArticle(ViewPager viewPager) {
        adapter = new TrackActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ArticleFragment(), "Categories");
        adapter.addFragment(new ForyouFragment(), "Articles");
        adapter.addFragment(new FemaleCycleFragment(), "Wishlist");
        viewPager.setAdapter(adapter);
    }

    private void setupViewPagerHome(ViewPager viewPager) {

        adapter = new TrackActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "");
        viewPager.setAdapter(adapter);
    }

    private void setupViewPagerProfile(ViewPager viewPager) {

        adapter = new TrackActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProfileFragment(), "");
        viewPager.setAdapter(adapter);
    }

    private void setupViewPagerListBP(ViewPager viewPager) {
        adapter = new TrackActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ListBloodSugarFragment(), "");
        viewPager.setAdapter(adapter);
    }

    public void setupViewPagerReminder() {
        viewPager.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
        spUser.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        setColor();
        ivreminder.setImageResource(R.drawable.ic_reminder);
        tvReminder.setTextColor(Color.parseColor("#0075b2"));
        adapter = new TrackActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MedicineReminderFragment(), "");
        viewPager.setAdapter(adapter);
    }

    public void setupViewPagerReminderset(ViewPager viewPager) {
        tvTitle.setText("Medicine Reminder");
        adapter = new TrackActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.notifyDataSetChanged();
        adapter.addFragment(new ReminderFragment(), "");
        viewPager.setAdapter(adapter);
    }

    public void setColor() {


        ivHome.setImageResource(R.drawable.ic_home);
        tvHome.setTextColor(Color.parseColor("#9DA1A0"));
        ivreminder.setImageResource(R.drawable.ic_reminder);
        tvReminder.setTextColor(Color.parseColor("#9DA1A0"));
        ivArticle.setImageResource(R.drawable.ic_article);
        tvArticle.setTextColor(Color.parseColor("#9DA1A0"));
        tvTrack.setTextColor(Color.parseColor("#9DA1A0"));
        ivTrack.setImageResource(R.drawable.ic_track);
        ivProfile.setImageResource(R.drawable.ic_profilegray);
        tvProfile.setTextColor(Color.parseColor("#9DA1A0"));

    }

    private void setupViewPagerTrack(ViewPager viewPager) {

        adapter = new TrackActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BPFragment(), "BP");
        adapter.addFragment(new CholesterolFragment(), "Cholestrol");
        adapter.addFragment(new BMIFragment(), "BMI");
        adapter.addFragment(new WHRFragment(), "WHR");
        adapter.addFragment(new BloodSugarFragment(), "Blood Sugar");
        adapter.addFragment(new FemaleCycleFragment(), "Female Cycle");
        adapter.addFragment(new BloodCountListFragment(), "Blood Count");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_home:
                ivAdd.setVisibility(View.INVISIBLE);
                setColor();
                tvTitle.setText("Home");
                viewPager.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                //  llList.setVisibility(View.GONE);
                spUser.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                setupViewPagerHome(viewPager);
                tabLayout.setupWithViewPager(viewPager);
                ivHome.setImageResource(R.drawable.ic_homeblue);
                tvHome.setTextColor(Color.parseColor("#0075b2"));
                break;
            case R.id.button_reminder:
                tvTitle.setText("Reminder");
                ivAdd.setVisibility(View.INVISIBLE);
                Fragment fragment = new ReminderFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rlMainFragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                viewPager.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                tabLayout.setupWithViewPager(viewPager);
                //setupViewPagerReminderset(viewPager);
                spUser.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
                setColor();
                ivreminder.setImageResource(R.drawable.ic_reminderblue);
                tvReminder.setTextColor(Color.parseColor("#0075b2"));
                break;
            case R.id.button_article:
                tvTitle.setText("Article");
                ivAdd.setVisibility(View.INVISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                tabLayout.setVisibility(View.VISIBLE);
                spUser.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                setupViewPagerArticle(viewPager);
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setTabMode(TabLayout.MODE_FIXED);
                setColor();
                ivArticle.setImageResource(R.drawable.ic_articleblue);
                tvArticle.setTextColor(Color.parseColor("#0075b2"));

                break;
            case R.id.button_track:
                tvTitle.setText("Track");
                ivAdd.setVisibility(View.INVISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                spUser.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                setupViewPagerTrack(viewPager);
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                tabLayout.setVisibility(View.VISIBLE);
                setColor();
                tvTrack.setTextColor(Color.parseColor("#0075b2"));
                ivTrack.setImageResource(R.drawable.ic_trackb);

                break;

            case R.id.button_profile:
                setColor();
                ivAdd.setVisibility(View.VISIBLE);
                tvTitle.setText("Profile");
                viewPager.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                //  llList.setVisibility(View.GONE);
                spUser.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                setupViewPagerProfile(viewPager);
                tabLayout.setupWithViewPager(viewPager);
                tvProfile.setTextColor(Color.parseColor("#0075b2"));
                ivProfile.setImageResource(R.drawable.ic_profileblue);
                break;

            case R.id.ivAdd:
                Intent intent1=new Intent(TrackActivity.this, EditProfileActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;




        }
    }

    public void foodfitness() {
        frameLayout.setVisibility(View.VISIBLE);
        spUser.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);

    }
    public void bloodcount() {
        spUser.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);

    }
    public void onMenu(View view) {
  /*      if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            drawer.openDrawer(GravityCompat.START);
            */
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<ExpandedMenuModel>();
        listDataChild = new HashMap<ExpandedMenuModel, List<String>>();
        ExpandedMenuModel item1 = new ExpandedMenuModel();
        item1.setIconName("About us");
        item1.setIconImg(R.drawable.profileb);
        listDataHeader.add(item1);

        ExpandedMenuModel item2 = new ExpandedMenuModel();
        item2.setIconName("Contact Us");
        item2.setIconImg(R.drawable.contactus);
        listDataHeader.add(item2);
        ExpandedMenuModel item3 = new ExpandedMenuModel();
        item3.setIconName("Feedback");
        item3.setIconImg(R.drawable.feedback);
        listDataHeader.add(item3);


        ExpandedMenuModel item5 = new ExpandedMenuModel();
        item5.setIconName("Rate App");
        item5.setIconImg(R.drawable.rateapp);
        listDataHeader.add(item5);

        ExpandedMenuModel item6 = new ExpandedMenuModel();
        item6.setIconName("Refer A Friend");
        item6.setIconImg(R.drawable.refer);
        listDataHeader.add(item6);

        ExpandedMenuModel item7 = new ExpandedMenuModel();
        item7.setIconName("Signout");
        item7.setIconImg(R.drawable.signout);
        listDataHeader.add(item7);


        List<String> heading6 = new ArrayList<String>();
        heading6.add("My Family Posts");
        heading6.add("My Public Posts");
        heading6.add("My Friend's Posts");

        List<String> heading = new ArrayList<String>();
        listDataChild.put(listDataHeader.get(0), heading);// Header, Child data
        listDataChild.put(listDataHeader.get(1), heading);
        listDataChild.put(listDataHeader.get(2), heading);
        listDataChild.put(listDataHeader.get(3), heading);
        listDataChild.put(listDataHeader.get(4), heading);
        listDataChild.put(listDataHeader.get(5), heading);


    }

    public void onClickListner() {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void AddBloodSugarRecords() {
        addBloodSugarDialog = new AddBloodSugarDialog(this, new AddBloodSugarDialog.AddBloodSugarDialogClickListener() {


            @Override
            public void onSubmit() {
                addBloodSugarDialog.dismiss();
            }

            @Override
            public void onCancel() {
                addBloodSugarDialog.dismiss();
            }
        });

        addBloodSugarDialog.show();


    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            if (manager.getFragments() != null) {
                manager.getFragments().clear();
            }
        }

        @Override
        public Fragment getItem(int position) {
            add = position + "";
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
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}

