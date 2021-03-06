package com.innovellent.curight.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.LoginActivity;
import com.innovellent.curight.R;
import com.innovellent.curight.adapter.CustomDrawerAdapter;
import com.innovellent.curight.adapter.DiagnosticTestAdapter;
import com.innovellent.curight.adapter.TRACK_SPINNER_ADAPTER;
import com.innovellent.curight.fragment.ArticleFragment;
import com.innovellent.curight.fragment.BMIFragment;
import com.innovellent.curight.fragment.BPFragment;
import com.innovellent.curight.fragment.BloodCountListFragment;
import com.innovellent.curight.fragment.BloodSugarFragment;
import com.innovellent.curight.fragment.CholesterolFragment;
import com.innovellent.curight.fragment.ExerciseFragment;
import com.innovellent.curight.fragment.FoodFragment;
import com.innovellent.curight.fragment.ForyouFragment;
import com.innovellent.curight.fragment.HomeFragment;
import com.innovellent.curight.fragment.ListBloodSugarFragment;
import com.innovellent.curight.fragment.MedicineReminderFragment;
import com.innovellent.curight.fragment.NavigationDrawerFragment;
import com.innovellent.curight.fragment.ProfileFragment;
import com.innovellent.curight.fragment.FemaleCycleFragment;
import com.innovellent.curight.fragment.TrackDataFragment;
import com.innovellent.curight.fragment.VaccineFragment;
import com.innovellent.curight.fragment.WHRFragment;
import com.innovellent.curight.fragment.WishListFragment;
import com.innovellent.curight.model.AddBMIRecordsDialog;
import com.innovellent.curight.model.AddBPRecordsDialog;
import com.innovellent.curight.model.AddBloodSugarDialog;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.ReminderDialog;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, View.OnClickListener {

    private static final String KEY_SELECTED_PAGE = "KEY_SELECTED_PAGE";
    private static final String TAG = "CuRight";
    private static final String IS_LOGIN = "Islogin";
    public static String USER_ID;
    public static int uid;
    public boolean activityStartup = true;
    public CustomDrawerAdapter mDrawerAdapter;
    RelativeLayout rl_home,rl_remainder,rl_article,rl_track,rl_profile;
    DrawerLayout drawer;


//    BottomNavigationView bottomNavigationView;
    RecyclerView recycler_view;
    DiagnosticTestAdapter mAdapter;
    Toolbar toolbar;
    ViewPager viewpager;
    HomeActivity.ViewPagerAdapter adapter;
    //Spinner spUser;
    TextView tvTrends, tvSave;
    TextView tvHome, tvTitle, tvReminder, tvArticle, tvTrack, tvProfile;
    ImageView ivHome, ivreminder, ivArticle, ivTrack, ivProfile;
    Button btnSubmit;
    AddBPRecordsDialog addRecordsDialog;
    ReminderDialog reminderDialog;
    AddBMIRecordsDialog addBMIRecordsDialog;
    AddBloodSugarDialog addBloodSugarDialog;
    FrameLayout frameontainer;
    LinearLayout button_panel, llList, button_track, button_profile, button_article, button_reminder, button_home, button_report, button_bmi, button_bloodsugar, button_excercise;
    RelativeLayout rl_location;
    String[] spinner1 = {"John", "Jobby", "Suresh", "Mahesh"};
    ArrayList<String> arrayList = new ArrayList<String>();
    FrameLayout frameLayout;
    ImageView ivBack, ivBack1, ivAdd,ivAddprofile;
    TextView tv_locality;
    Fragment currentFragment;
    TabLayout tabLayout;
    NumberPicker numberpicker;
 //   ImageView searchView;
    TRACK_SPINNER_ADAPTER customSpinnerAdapter3;
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();
    SharedPreferences sharedPreferences;
    String source="";

    boolean doubleBackToExitPressedOnce = false;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private int i = 0;
    private ViewPager viewPager;
    private String add = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_nw);
        Log.d(TAG,"Homeactivity: oncreate");
     //   Prefs.putString("source","");
        sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        init();
        if(b!=null)
        {
            source = b.getString("source");
         if(source!=null)
         {
             if(source.equalsIgnoreCase("consumption"))
             {
                 Prefs.putString("source","consumption");
             }else {
                 Prefs.putString("source","exersize");
             }
         }

        }else{
            Prefs.putString("source","");
        }

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            drawer.closeDrawer(GravityCompat.START);
        }

        ivAdd = (ImageView) findViewById(R.id.ivAdd);

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this,"Cart under development!",Toast.LENGTH_SHORT).show();
            }
        });




        ivAddprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(getApplicationContext(),Add_Profile_Activity.class);
                startActivity(add);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNavigationDrawerFragment.isDrawerOpen()) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                    /*ivBack.setVisibility(View.VISIBLE);
                    ivBack1.setVisibility(View.GONE);*/
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"Homeactivity: onstart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"Homeactivity: onrestart");
        Prefs.putString("destination","");
        setupViewPagerreset();
       // setupViewPagerHome(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ivBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.putString("source","");
                Prefs.putString("source1","");
                android.support.v4.app.Fragment fragment= getSupportFragmentManager().findFragmentById(R.id.rlMainFragment);
                if(fragment instanceof TrackDataFragment)
                {
                    tvTitle.setVisibility(View.GONE);
                    ivAddprofile.setVisibility(View.GONE);
                    ivAdd.setVisibility(View.VISIBLE);
                    ivBack.setVisibility(View.VISIBLE);
                    ivBack1.setVisibility(View.GONE);
                    viewPager.setVisibility(View.VISIBLE);
                    frameLayout.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    setupViewPagerHome(viewPager);
                    tabLayout.setupWithViewPager(viewPager);
                }
            }
        });
        Log.d(TAG,"Homeactivity: onresume");
        String destination =Prefs.getString("destination","");
        Log.d(TAG, "spvaluerestore" + destination);
        if(destination.equals("Remainder")){

            ivAddprofile.setVisibility(View.GONE);
            tvTitle.setText("Reminder");
            ivAdd.setVisibility(View.INVISIBLE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.VISIBLE);
           // spUser.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerMedicineReminder(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);

        }else if(destination.equals("Profile"))
        {
            ivAddprofile.setVisibility(View.VISIBLE);
            ivAdd.setVisibility(View.INVISIBLE);
            tvTitle.setText("Profile");
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
           // spUser.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerProfile(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(destination.equals("Track"))
        {
            ivAddprofile.setVisibility(View.GONE);
            tvTitle.setText("Track");
            ivAdd.setVisibility(View.INVISIBLE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            //spUser.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
            setupViewPagerTrack(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            tabLayout.setVisibility(View.VISIBLE);
        }
        else if(destination.equals("Home")){

            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            //tvTitle.setText("Home");
            tvTitle.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            //spUser.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(destination.equals("trackdata")){

            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
//            Prefs.putString("source","trackfood");
            //tvTitle.setText("Home");
            tvTitle.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            //spUser.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        } else{
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
//tvTitle.setText("Home");
            tvTitle.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
//spUser.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
        Log.d(TAG,"shared-destination"+destination);
        new_onclick();
    }

    private void new_onclick() {
     //   android.support.v4.app.Fragment fragment= getSupportFragmentManager().findFragmentById(R.id.rlMainFragment);

        rl_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.putString("source","");
                Prefs.putString("source1","");
                tvTitle.setVisibility(View.GONE);
                ivAdd.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                ivBack1.setVisibility(View.GONE);
                ivBack1.setVisibility(View.GONE);
                rl_location.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                setupViewPagerHome(viewPager);
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        rl_remainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long uid = Prefs.getLong("user_id",0);
                if (uid==0) {
                    Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                    Prefs.putString("destination", "Remainder");
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // setupViewPagerMedicineReminder(viewPager);
                    startActivity(i);
                }else {
                    ivAddprofile.setVisibility(View.GONE);
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setText("Reminder");
                    ivAdd.setVisibility(View.INVISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                    rl_location.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.VISIBLE);
                    frameLayout.setVisibility(View.GONE);
                    setupViewPagerMedicineReminder(viewPager);
                    tabLayout.setupWithViewPager(viewPager);
                    tabLayout.setTabMode(TabLayout.MODE_FIXED);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        rl_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ivAddprofile.setVisibility(View.GONE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Article");
                ivAdd.setVisibility(View.INVISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                rl_location.setVisibility(View.GONE);
                tabLayout.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                setupViewPagerArticle(viewPager);
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setTabMode(TabLayout.MODE_FIXED);
            }
        });
        rl_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long uid = Prefs.getLong("user_id",0);
                if (uid==0) {
                    Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                    Prefs.putString("destination", "Track");
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // setupViewPagerMedicineReminder(viewPager);
                    startActivity(i);
                }else {
                    ivAddprofile.setVisibility(View.GONE);
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setText("Track");
                    ivAdd.setVisibility(View.INVISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                    frameLayout.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.VISIBLE);
                    rl_location.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    setupViewPagerTrack(viewPager);
                    tabLayout.setupWithViewPager(viewPager);
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                    tabLayout.setVisibility(View.VISIBLE);
                }
            }
        });
         rl_profile.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Long uid = Prefs.getLong("user_id",0);
                 if (uid==0) {
                     Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                     Prefs.putString("destination", "Profile");
                     i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     // setupViewPagerMedicineReminder(viewPager);
                     startActivity(i);
                 }else {
                     ivAddprofile.setVisibility(View.VISIBLE);
                     ivAdd.setVisibility(View.INVISIBLE);
                     tvTitle.setVisibility(View.VISIBLE);
                     tvTitle.setText("Profile");
                     rl_location.setVisibility(View.GONE);
                     viewPager.setVisibility(View.VISIBLE);
                     frameLayout.setVisibility(View.GONE);
                     tabLayout.setVisibility(View.GONE);
                     adapter.notifyDataSetChanged();
                     setupViewPagerProfile(viewPager);
                     tabLayout.setupWithViewPager(viewPager);
                 }
             }
         });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"Homeactivity: onPause");
        Prefs.putString("source","");
        Prefs.putString("source1","");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"Homeactivity: onStop");
        //setupViewPagerHome(viewPager);
       // Prefs.putString("destination","");
    }

   /* public void getData2() {

        customSpinnerAdapter3 = new TRACK_SPINNER_ADAPTER(HomeActivity.this, spinnerList);
        spUser.setAdapter(customSpinnerAdapter3);
        spUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText(PasswordRecoveryQuestionsActivity.this, spinner3[i], Toast.LENGTH_SHORT).show();
                //spAge.setText(spinnerList.get(i).getUser_age());
                USER_ID = spinnerList.get(i).getUser_id();
                Prefs.putLong("spinner_id", Long.parseLong(spinnerList.get(i).getUser_id()));
                uid = (int) Prefs.getLong("spinner_id",0);
                Log.e("Userid", spinnerList.get(i).getUser_id());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getSpinnerData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        int uid = (int) Prefs.getLong("user_id",0);
        PostBodyProfile postBodyprofile = new PostBodyProfile(uid, "family");
        Call<MyProfile_Response> call = reditapi.getProfile(postBodyprofile);

        call.enqueue(new Callback<MyProfile_Response>() {
            @Override
            public void onResponse(Call<MyProfile_Response> call, Response<MyProfile_Response> response) {


                if (response.body() != null) {


                    Log.e("", "profileResponse: code: " + response.body().getCode());

                    ArrayList<PROFILE_FEED> result = response.body().getResults();

                    Log.e("", "profileResponse: listsize: " + result.size());
                    for (int i = 0; i < result.size(); i++) {

                         USER_ID = result.get(i).getUserid();
                        //spinnerList.add(new PROFILE("","","",""));
                        spinnerList.add(new PROFILE(result.get(i).getUserid(),result.get(i).getId(), result.get(i).getName(), result.get(i).getAge(), result.get(i).getRelationship()));
                    }
                    getData2();
                   // GetData(result.get(1).getUserid());
                } else {

                    Toast.makeText(HomeActivity.this, response.message(), Toast.LENGTH_SHORT).show();

                }

                //remainder_rclrvw.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<MyProfile_Response> call, Throwable t) {

                Log.e("", "onFailure: Somethings went wrong" + t.getMessage());
                Toast.makeText(HomeActivity.this, "Somethings went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Homeactivity: onDestroy");
    }

    private void fragmentChange(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String userid = sharedPreferences.getString("userid", "N");
        Log.d("name", name);
        android.support.v4.app.FragmentManager mSettingsFragment = getSupportFragmentManager();
        FragmentTransaction tSettingsFragment = mSettingsFragment.beginTransaction();
        switch (name) {
            case "market":
                button_panel.setVisibility(View.VISIBLE);
                ivAdd.setVisibility(View.VISIBLE);
                SharedPreferences sharedPreferences3 = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor3 = sharedPreferences3.edit();

                break;
            case "myoffering":

        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, currentFragment).commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        SharedPreferences sharedPreferences3 = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = sharedPreferences3.edit();
        editor3.putString("menu", "cube");
        editor3.apply();
        Fragment fragment = null;
        switch (position) {

            case 0:
                //Toast.makeText(getApplicationContext(),"About us Clicked",Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Prefs.putLong("user_id",0);
                Prefs.putString("location", "");
                init();
                Toast.makeText(getApplicationContext(),"You are Sussessfully Logged out",Toast.LENGTH_SHORT).show();
               /* mNavigationDrawerFragment.addItemsToDataList();
                mDrawerAdapter = new CustomDrawerAdapter(HomeActivity.this, R.layout.custom_drawer_item, dataList);
                mDrawerAdapter.notifyDataSetChanged();*/
                //new HomeActivity();
                /*mNavigationDrawerFragment.dataList.clear();
                mNavigationDrawerFragment.dataList.add(new DrawerItem("About Us"));
                mNavigationDrawerFragment.dataList.add(new DrawerItem("Contact Us"));
                mNavigationDrawerFragment.dataList.add(new DrawerItem("Feedback"));
                mNavigationDrawerFragment.dataList.add(new DrawerItem("Rate App"));
                mNavigationDrawerFragment.dataList.add(new DrawerItem("Refer A Friend"));
                mNavigationDrawerFragment.dataList.add(new DrawerItem("Sign in"));
                mNavigationDrawerFragment.dataList.notifyAll();*/
            case 7:

                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

        }
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tv_locality = (TextView)findViewById(R.id.tv_locality);
        rl_location = (RelativeLayout) findViewById(R.id.rl_location);
        ivBack = (ImageView) findViewById(R.id.ivback);
        ivAddprofile = (ImageView) findViewById(R.id.ivAddprofile);
        ivBack1 = (ImageView) findViewById(R.id.ivback1);
//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPagerHome(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        //spUser = (Spinner) findViewById(R.id.spUser);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setVisibility(View.GONE);
        rl_home = (RelativeLayout) findViewById(R.id.rl_home);
        rl_remainder = (RelativeLayout) findViewById(R.id.rl_remainder);
        rl_article = (RelativeLayout) findViewById(R.id.rl_article);
        rl_track = (RelativeLayout) findViewById(R.id.rl_track);
        rl_profile = (RelativeLayout) findViewById(R.id.rl_profile);
        rl_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loc_int = new Intent(HomeActivity.this,SearchLocations.class);
                startActivity(loc_int);

//                try {
//                    Intent intent =
//                            new PlaceAutocomplete
//                                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
//                                    .build(HomeActivity.this);
//                    startActivityForResult(intent, 1);
//                } catch (GooglePlayServicesRepairableException e) {
//                    // TODO: Handle the error.
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    // TODO: Handle the error.
//                }
            }
        });
        //searchView.setIconifiedByDefault(true);
        //searchView.setIconified(true);
        //searchView.onActionViewExpanded();
        //searchView.setQuery("Select City",false);
        /*searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (activityStartup) {
                        searchView.clearFocus();
                        activityStartup = false;
                    }
                }
            }
        });*/
        tvSave=(TextView)findViewById(R.id.tvSave);
        frameLayout = (FrameLayout) findViewById(R.id.rlMainFragment);
        tabLayout.setTabTextColors(
                ContextCompat.getColor(this, R.color.graylight),
                ContextCompat.getColor(this, R.color.colorPrimary)
        );
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ivAdd = (ImageView) findViewById(R.id.ivAdd);

    }



    // Get Login State
    public boolean isLoggedIn(){
        Log.d("SPVALUE", ""+sharedPreferences.getBoolean(IS_LOGIN, false));
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    private void setupViewPagerArticle(ViewPager viewPager) {
        adapter = new HomeActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ArticleFragment(), "CATEGORIES");
        adapter.addFragment(new ForyouFragment(), "ARTICLES");
        adapter.addFragment(new WishListFragment(), "WISHLIST");
        viewPager.setAdapter(adapter);
    }

    private void setupViewPagerHome(ViewPager viewPager) {

        try {
            adapter = new HomeActivity.ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new HomeFragment(), "");
            viewPager.setAdapter(adapter);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupViewPagerProfile(ViewPager viewPager) {

        try {
            adapter = new HomeActivity.ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new ProfileFragment(), "");
            viewPager.setAdapter(adapter);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private void setupViewPagerListBP(ViewPager viewPager) {
        adapter = new HomeActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ListBloodSugarFragment(), "");
        viewPager.setAdapter(adapter);
    }

    public void setupViewPagerReminder() {
        viewPager.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
        //spUser.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        adapter = new HomeActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MedicineReminderFragment(), "");
        viewPager.setAdapter(adapter);
    }

    public void setupViewPagerreset() {

        adapter = new HomeActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.notifyDataSetChanged();
//        if(viewPager!=null)
//        {
//            viewpager.invalidate();
//        }

    }

    private void setupViewPagerTrack(ViewPager viewPager) {

        adapter = new HomeActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BPFragment(), "BP");
        adapter.addFragment(new CholesterolFragment(), "Cholestrol");
        adapter.addFragment(new BMIFragment(), "BMI");
        adapter.addFragment(new WHRFragment(), "WHR");
        adapter.addFragment(new BloodSugarFragment(), "Blood Sugar");
        adapter.addFragment(new BloodCountListFragment(), "Blood Count");
        adapter.addFragment(new FemaleCycleFragment(), "Female Cycle");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ivAdd:
                Intent intent1 = new Intent(HomeActivity.this, EditProfileActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.select_loc:
                Toast.makeText(getApplicationContext(),"Wait for implementation",Toast.LENGTH_SHORT);

        }
    }

    public void foodfitness() {
        frameLayout.setVisibility(View.VISIBLE);
       // spUser.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);

    }

    public void bloodcount() {
        //spUser.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    private void setupViewPagerMedicineReminder(ViewPager viewPager) {
        adapter = new HomeActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MedicineReminderFragment(), "Medicine");
        adapter.addFragment(new VaccineFragment(), "Vaccine");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Prefs.putString("source","");
        Prefs.putString("source1","");
        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.rlMainFragment);
        if(fragment instanceof TrackDataFragment)
        {
            tvTitle.setVisibility(View.GONE);
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
            ivBack1.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(fragment instanceof MedicineReminderFragment){
            tvTitle.setVisibility(View.GONE);
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
            ivBack1.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(fragment instanceof VaccineFragment){
            tvTitle.setVisibility(View.GONE);
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
            ivBack1.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(fragment instanceof BPFragment){
            tvTitle.setVisibility(View.GONE);
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
            ivBack1.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(fragment instanceof CholesterolFragment){
            tvTitle.setVisibility(View.GONE);
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
            ivBack1.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(fragment instanceof BMIFragment){
            tvTitle.setVisibility(View.GONE);
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
            ivBack1.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(fragment instanceof WHRFragment){
            tvTitle.setVisibility(View.GONE);
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
            ivBack1.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(fragment instanceof BloodSugarFragment){
            tvTitle.setVisibility(View.GONE);
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
            ivBack1.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(fragment instanceof BloodCountListFragment){
            tvTitle.setVisibility(View.GONE);
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
            ivBack1.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(fragment instanceof FemaleCycleFragment){
            tvTitle.setVisibility(View.GONE);
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
            ivBack1.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(fragment instanceof ProfileFragment){
            tvTitle.setVisibility(View.GONE);
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
            ivBack1.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(fragment instanceof ArticleFragment){
            tvTitle.setVisibility(View.GONE);
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
            ivBack1.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(fragment instanceof ForyouFragment){
            tvTitle.setVisibility(View.GONE);
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
            ivBack1.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(fragment instanceof WishListFragment){
            tvTitle.setVisibility(View.GONE);
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
            ivBack1.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(fragment instanceof FoodFragment){
            android.support.v4.app.Fragment fragment1 = new TrackDataFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rlMainFragment, fragment1);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else if(fragment instanceof ExerciseFragment){
            android.support.v4.app.Fragment fragment1 = new TrackDataFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rlMainFragment, fragment1);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if(fragment instanceof HomeFragment)
        {
            if (doubleBackToExitPressedOnce) {
               /* super.onBackPressed();
                return;*/
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 5000);
          } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<android.support.v4.app.Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
            super(manager);
            if (manager.getFragments() != null) {
                manager.getFragments().clear();
            }
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            add = position + "";
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


