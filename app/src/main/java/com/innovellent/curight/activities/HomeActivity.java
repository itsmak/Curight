package com.innovellent.curight.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.innovellent.curight.LoginActivity;
import com.innovellent.curight.R;
import com.innovellent.curight.adapter.CustomDrawerAdapter;
import com.innovellent.curight.adapter.CustomSpinnerAdapter2;
import com.innovellent.curight.adapter.DiagnosticTestAdapter;
import com.innovellent.curight.adapter.PROFILE_SPINNER_ADAPTER;
import com.innovellent.curight.api.ApiInterface;
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
import com.innovellent.curight.fragment.NavigationDrawerFragment;
import com.innovellent.curight.fragment.ProfileFragment;
import com.innovellent.curight.fragment.FemaleCycleFragment;
import com.innovellent.curight.fragment.VaccineFragment;
import com.innovellent.curight.fragment.WHRFragment;
import com.innovellent.curight.fragment.WishListFragment;
import com.innovellent.curight.model.AddBMIRecordsDialog;
import com.innovellent.curight.model.AddBPRecordsDialog;
import com.innovellent.curight.model.AddBloodSugarDialog;
import com.innovellent.curight.model.DrawerItem;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.ReminderDialog;
import com.innovellent.curight.utility.BottomNavigationViewHelper;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.innovellent.curight.fragment.NavigationDrawerFragment.dataList;


public class HomeActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, View.OnClickListener {

    private static final String KEY_SELECTED_PAGE = "KEY_SELECTED_PAGE";
    private static final String TAG = "CuRight";
    private static final String IS_LOGIN = "Islogin";
    public static String USER_ID;
    public static int uid;
    public boolean activityStartup = true;
    public CustomDrawerAdapter mDrawerAdapter;
    DrawerLayout drawer;
    BottomNavigationView bottomNavigationView;
    RecyclerView recycler_view;
    DiagnosticTestAdapter mAdapter;
    Toolbar toolbar;
    ViewPager viewpager;
    HomeActivity.ViewPagerAdapter adapter;
    Spinner spUser;
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
    String[] spinner1 = {"John", "Jobby", "Suresh", "Mahesh"};
    ArrayList<String> arrayList = new ArrayList<String>();
    FrameLayout frameLayout;
    ImageView ivBack, ivBack1, ivAdd,ivAddprofile;
    Fragment currentFragment;
    TabLayout tabLayout;
    NumberPicker numberpicker;
    ImageView searchView;
    PROFILE_SPINNER_ADAPTER customSpinnerAdapter3;
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();
    SharedPreferences sharedPreferences;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private int i = 0;
    private ViewPager viewPager;
    private String add = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG,"Homeactivity: oncreate");
        sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);

        init();
//        getSpinnerData();
        //onclick();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            drawer.closeDrawer(GravityCompat.START);
        }

        ivAdd = (ImageView) findViewById(R.id.ivAdd);
//        searchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(getApplicationContext(),"Select a location",Toast.LENGTH_SHORT).show();
//
//            }
//        });
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this,"Cart under development!",Toast.LENGTH_SHORT).show();
            }
        });


        ivBack = (ImageView) findViewById(R.id.ivback);
        ivAddprofile = (ImageView) findViewById(R.id.ivAddprofile);
        ivBack1 = (ImageView) findViewById(R.id.ivback1);

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"Homeactivity: onresume");
        String destination =Prefs.getString("destination","");
        Log.d(TAG, "spvaluerestore" + destination);
//        if(destination.equals("Remainder")){
//
//            ivAddprofile.setVisibility(View.GONE);
//            tvTitle.setText("Reminder");
//            ivAdd.setVisibility(View.INVISIBLE);
//            viewPager.setVisibility(View.VISIBLE);
//            frameLayout.setVisibility(View.GONE);
//            tabLayout.setVisibility(View.VISIBLE);
//            spUser.setVisibility(View.GONE);
//            adapter.notifyDataSetChanged();
//            setupViewPagerMedicineReminder(viewPager);
//            tabLayout.setupWithViewPager(viewPager);
//            tabLayout.setTabMode(TabLayout.MODE_FIXED);
//
//        }else if(destination.equals("Profile"))
//        {
//            ivAddprofile.setVisibility(View.VISIBLE);
//            ivAdd.setVisibility(View.INVISIBLE);
//            tvTitle.setText("Profile");
//            viewPager.setVisibility(View.VISIBLE);
//            frameLayout.setVisibility(View.GONE);
//            spUser.setVisibility(View.GONE);
//            tabLayout.setVisibility(View.GONE);
//            adapter.notifyDataSetChanged();
//            setupViewPagerProfile(viewPager);
//            tabLayout.setupWithViewPager(viewPager);
//        }else if(destination.equals("Track"))
//        {
//            ivAddprofile.setVisibility(View.GONE);
//            tvTitle.setText("Track");
//            ivAdd.setVisibility(View.INVISIBLE);
//            viewPager.setVisibility(View.VISIBLE);
//            frameLayout.setVisibility(View.GONE);
//            spUser.setVisibility(View.VISIBLE);
//            tabLayout.setVisibility(View.VISIBLE);
//            adapter.notifyDataSetChanged();
//            setupViewPagerTrack(viewPager);
//            tabLayout.setupWithViewPager(viewPager);
//            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//            tabLayout.setVisibility(View.VISIBLE);
//        }
//        else if(destination.equals("Home")){
//            ivAddprofile.setVisibility(View.GONE);
//            ivAdd.setVisibility(View.VISIBLE);
//            //tvTitle.setText("Home");
//            tvTitle.setVisibility(View.GONE);
//            viewPager.setVisibility(View.VISIBLE);
//            frameLayout.setVisibility(View.GONE);
//            spUser.setVisibility(View.GONE);
//            tabLayout.setVisibility(View.GONE);
//            adapter.notifyDataSetChanged();
//            setupViewPagerHome(viewPager);
//            tabLayout.setupWithViewPager(viewPager);
//        }
        Log.d(TAG,"shared-destination"+destination);
        onclick();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"Homeactivity: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"Homeactivity: onStop");
       // Prefs.putString("destination","");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Homeactivity: onDestroy");
    }

   /* public void getData2() {

        customSpinnerAdapter3 = new PROFILE_SPINNER_ADAPTER(HomeActivity.this, spinnerList);
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
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPagerHome(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        //spUser = (Spinner) findViewById(R.id.spUser);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("Choose Location");
        searchView = (ImageView) findViewById(R.id.select_loc);
//        searchView.setVisibility(View.VISIBLE);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setQueryHint("Select City");
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent =
                            new PlaceAutocomplete
                                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(HomeActivity.this);
                    startActivityForResult(intent, 1);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent =
                            new PlaceAutocomplete
                                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(HomeActivity.this);
                    startActivityForResult(intent, 1);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());
                tvTitle.setText(place.getName());
                Log.d(TAG,"location latitude::"+place.getLatLng());

//                ((TextView) findViewById(R.id.searched_address))
//                        .setText(place.getName()+",\n"+
//                                place.getAddress() +"\n" + place.getPhoneNumber());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
    public void onclick() {
        ivAdd.setOnClickListener(this);
        //searchView.setOnClickListener(this);
//        spUser.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        //ivAdd.setVisibility(View.INVISIBLE);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Long uid = Prefs.getLong("user_id",0);
                switch (item.getItemId()) {
                    case R.id.action_home:
                        tvTitle.setVisibility(View.VISIBLE);
                        tvTitle.setText("Choose Location");
                        ivAddprofile.setVisibility(View.GONE);
                        ivAdd.setVisibility(View.VISIBLE);
                        ivBack.setVisibility(View.VISIBLE);
                        ivBack1.setVisibility(View.GONE);
                        searchView.setVisibility(View.VISIBLE);
                        //tvTitle.setText("Home");
                        viewPager.setVisibility(View.VISIBLE);
                        frameLayout.setVisibility(View.GONE);
                        //spUser.setVisibility(View.GONE);
                        tabLayout.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                        setupViewPagerHome(viewPager);
                        tabLayout.setupWithViewPager(viewPager);

                        return true;
                    case R.id.action_reminder:

                       Log.d("TAG","Shared userid"+uid);
                        if (uid==0) {
                            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                           Prefs.putString("destination", "Remainder");
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }else {
                            ivAddprofile.setVisibility(View.GONE);
                             searchView.setVisibility(View.GONE);
                            tvTitle.setVisibility(View.VISIBLE);
                            tvTitle.setText("Reminder");
                            ivAdd.setVisibility(View.INVISIBLE);
                            viewPager.setVisibility(View.VISIBLE);
                            frameLayout.setVisibility(View.GONE);
                            tabLayout.setVisibility(View.VISIBLE);
                            //spUser.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                            setupViewPagerMedicineReminder(viewPager);
                            tabLayout.setupWithViewPager(viewPager);
                            tabLayout.setTabMode(TabLayout.MODE_FIXED);
                        }

                        return true;
                    case R.id.action_article:

                            ivAddprofile.setVisibility(View.GONE);
                            searchView.setVisibility(View.GONE);
                            tvTitle.setVisibility(View.VISIBLE);
                            tvTitle.setText("Article");
                            ivAdd.setVisibility(View.INVISIBLE);
                            viewPager.setVisibility(View.VISIBLE);
                            frameLayout.setVisibility(View.GONE);
                            tabLayout.setVisibility(View.VISIBLE);
                            //spUser.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                            setupViewPagerArticle(viewPager);
                            tabLayout.setupWithViewPager(viewPager);
                            tabLayout.setTabMode(TabLayout.MODE_FIXED);
                       // }

                        return true;
                    case R.id.action_track:

                        if (uid==0) {
                            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                            Prefs.putString("destination", "Track");
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }else {
                            ivAddprofile.setVisibility(View.GONE);
                            tvTitle.setVisibility(View.VISIBLE);
                            tvTitle.setText("Track");
                           searchView.setVisibility(View.GONE);
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

                        return true;

                    case R.id.action_profile:
                       // Long uid = Prefs.getLong("user_id",0);
                        if (uid==0) {
                            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                            Prefs.putString("destination", "Profile");
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }else {
                            ivAddprofile.setVisibility(View.VISIBLE);
                            ivAdd.setVisibility(View.INVISIBLE);
                            tvTitle.setVisibility(View.VISIBLE);
                            tvTitle.setText("Profile");
                           searchView.setVisibility(View.GONE);
                            viewPager.setVisibility(View.VISIBLE);
                            frameLayout.setVisibility(View.GONE);
                           // spUser.setVisibility(View.GONE);
                            tabLayout.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                            setupViewPagerProfile(viewPager);
                            tabLayout.setupWithViewPager(viewPager);

                        }
                        return true;
                }
                return false;
            }
        });

    }


    // Get Login State
    public boolean isLoggedIn(){
        Log.d("SPVALUE", ""+sharedPreferences.getBoolean(IS_LOGIN, false));
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

   /* public void getData() {

        CustomSpinnerAdapter2 customSpinnerAdapter3 = new CustomSpinnerAdapter2(HomeActivity.this, spinner1);
        spUser.setAdapter(customSpinnerAdapter3);
        spUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }*/

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
        viewpager.invalidate();
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

    /*private void AddBloodSugarRecords() {
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
*/
    @Override
    public void onBackPressed() {

        if (i == 0) {
            i++;
        } else {
            super.onBackPressed();
            finish();
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


