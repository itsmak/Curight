/*
package com.innovellent.curight.fragment;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.LoginActivity;
import com.innovellent.curight.R;
import com.innovellent.curight.activities.HomeActivity;
import com.innovellent.curight.adapter.DiagnosticTestAdapter;
import com.innovellent.curight.adapter.PROFILE_SPINNER_ADAPTER;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddBMIRecordsDialog;
import com.innovellent.curight.model.AddBPRecordsDialog;
import com.innovellent.curight.model.AddBloodSugarDialog;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.ReminderDialog;
import com.innovellent.curight.utility.BottomNavigationViewHelper;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

*/
/**
 * Created by SUNIL on 12/27/2017.
 *//*


public class LandingMainFragment extends Fragment {


    BottomNavigationView bottomNavigationView;
    RecyclerView recycler_view;
    DiagnosticTestAdapter mAdapter;
    Spinner spUser;
    TextView tvTrends, tvSave;
    TextView tvHome, tvTitle, tvReminder, tvArticle, tvTrack, tvProfile;
    Button btnSubmit;
    AddBPRecordsDialog addRecordsDialog;
    ReminderDialog reminderDialog;
    AddBMIRecordsDialog addBMIRecordsDialog;
    AddBloodSugarDialog addBloodSugarDialog;
    FrameLayout frameontainer;
    LinearLayout button_panel, llList, button_track, button_profile, button_article, button_reminder, button_home, button_report, button_bmi, button_bloodsugar, button_excercise;
    ArrayList<String> arrayList = new ArrayList<String>();
    ImageView ivBack, ivBack1, ivAdd,ivAddprofile;
    Fragment currentFragment;
    TabLayout tabLayout;
    private int i = 0;
    private ViewPager viewPager;
    private String add = "N";
    NumberPicker numberpicker;
    SearchView searchView;
    BPFragment bp;
    PROFILE_SPINNER_ADAPTER customSpinnerAdapter3;
    private static final String TAG = "CuRight";
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();
    public static String USER_ID;
    SharedPreferences sharedPreferences;
    FrameLayout frameLayout;
    // index to identify current nav menu item
    public static int navItemIndex = 0;
    private static final String IS_LOGIN = "Islogin";
    private View myFragmentView;

    public static Fragment newInstance(Bundle args) {
        LandingMainFragment fragment = new LandingMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LandingMainFragment() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.content_home, container, false);


        return myFragmentView;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"Homeactivity: onresume");
        String destination = Prefs.getString("destination","");
        if(destination.equalsIgnoreCase("Remainder")){

            ivAddprofile.setVisibility(View.GONE);
            tvTitle.setText("Reminder");
            ivAdd.setVisibility(View.INVISIBLE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.VISIBLE);
            spUser.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerMedicineReminder(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);

        }else if(destination.equalsIgnoreCase("Profile"))
        {
            ivAddprofile.setVisibility(View.VISIBLE);
            ivAdd.setVisibility(View.INVISIBLE);
            tvTitle.setText("Profile");
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            spUser.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerProfile(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }else if(destination.equalsIgnoreCase("Track"))
        {
            ivAddprofile.setVisibility(View.GONE);
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
        }
        else {
            ivAddprofile.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            //tvTitle.setText("Home");
            tvTitle.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            spUser.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            setupViewPagerHome(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
        Log.d(TAG,"shared-destination"+destination);
        onclick();
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"Homeactivity: onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"Homeactivity: onStop");
        Prefs.putString("destination","");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Homeactivity: onDestroy");
    }

    public void getData2() {

        customSpinnerAdapter3 = new PROFILE_SPINNER_ADAPTER(getActivity(), spinnerList);
        spUser.setAdapter(customSpinnerAdapter3);
        spUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText(PasswordRecoveryQuestionsActivity.this, spinner3[i], Toast.LENGTH_SHORT).show();
                //spAge.setText(spinnerList.get(i).getUser_age());
                Log.e("Userid", spinnerList.get(i).getUser_id());
                bp.getBloodPressureRecords(spinnerList.get(i).getUser_id());
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

                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

                }

                //remainder_rclrvw.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<MyProfile_Response> call, Throwable t) {

                Log.e("", "onFailure: Somethings went wrong" + t.getMessage());
                Toast.makeText(getActivity(), "Somethings went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }



    public void init(View view) {

        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPagerHome(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        spUser = (Spinner) view.findViewById(R.id.spUser);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setVisibility(View.GONE);
        searchView = (SearchView) view.findViewById(R.id.select_loc);
        searchView.setVisibility(View.VISIBLE);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint("Select City");
        //searchView.setIconifiedByDefault(true);
        //searchView.setIconified(true);
        //searchView.onActionViewExpanded();
        //searchView.setQuery("Select City",false);
        */
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
        });*//*

        tvSave=(TextView)view.findViewById(R.id.tvSave);
        frameLayout = (FrameLayout) view.findViewById(R.id.rlMainFragment);
        tabLayout.setTabTextColors(
                ContextCompat.getColor(getActivity(), R.color.graylight),
                ContextCompat.getColor(getActivity(), R.color.colorPrimary)
        );
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        ivAdd = (ImageView) view.findViewById(R.id.ivAdd);

    }


    public void onclick() {
        ivAdd.setOnClickListener(getActivity());
        spUser.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        //ivAdd.setVisibility(View.INVISIBLE);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Long uid = Prefs.getLong("user_id",0);
                switch (item.getItemId()) {
                    case R.id.action_home:
                        ivAddprofile.setVisibility(View.GONE);
                        ivAdd.setVisibility(View.VISIBLE);
                        //tvTitle.setText("Home");
                        tvTitle.setVisibility(View.GONE);
                        viewPager.setVisibility(View.VISIBLE);
                        frameLayout.setVisibility(View.GONE);
                        spUser.setVisibility(View.GONE);
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
                            tvTitle.setText("Reminder");
                            ivAdd.setVisibility(View.INVISIBLE);
                            viewPager.setVisibility(View.VISIBLE);
                            frameLayout.setVisibility(View.GONE);
                            tabLayout.setVisibility(View.VISIBLE);
                            spUser.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                            setupViewPagerMedicineReminder(viewPager);
                            tabLayout.setupWithViewPager(viewPager);
                            tabLayout.setTabMode(TabLayout.MODE_FIXED);
                        }

                        return true;
                    case R.id.action_article:
//                        if (!HomeActivity.this.isLoggedIn()) {
//                            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
//                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(i);
//                        }else {
                        ivAddprofile.setVisibility(View.GONE);
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
                            tvTitle.setText("Profile");
                            viewPager.setVisibility(View.VISIBLE);
                            frameLayout.setVisibility(View.GONE);
                            spUser.setVisibility(View.GONE);
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

}
*/
