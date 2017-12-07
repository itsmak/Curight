package com.innovellent.curight.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.innovellent.curight.MainActivity;
import com.innovellent.curight.R;
import com.innovellent.curight.fragment.BloodSugarFragment;
import com.innovellent.curight.fragment.ExerciseFragment;
import com.innovellent.curight.fragment.FoodFragment;
import com.innovellent.curight.fragment.ListBloodSugarFragment;
import com.innovellent.curight.fragment.ProfileFragment;
import com.innovellent.curight.fragment.TrackFragment;


/**
 * Created by sagar on 8/30/2017.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    Fragment currentFragment;
    TabLayout tabLayout;
    Spinner spUser;
    TextView title,tvProfile,tvBP,tvBMI,tvExcercise,tvFood,tvbloodsugar;
    ImageView ivAdd,ivHome,ivProfile,ivbp,ivBMI,ivExercise,ivFood,ivbloodsugar;
    LinearLayout button_profile,button_bmi,button_report,button_excercise,button_food,button_bloodsugar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        iniClick();

        currentFragment = getSupportFragmentManager().findFragmentById(R.id.rlMainFragment);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Trends"));
        tabLayout.addTab(tabLayout.newTab().setText("List"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        fragmentChange("profile");
        title=(TextView)findViewById(R.id.title);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position =tab.getPosition();
                if(position==0){
                    fragmentChange("BP");
                }else if(position==1){
                    fragmentChange("list");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // setBackgroundColor();
    }
    public void init(){
        ivHome=(ImageView)findViewById(R.id.ivHome);
        ivAdd=(ImageView)findViewById(R.id.ivAdd);
        button_profile=(LinearLayout)findViewById(R.id.button_profile);
        //  button_bp=(LinearLayout)findViewById(R.id.button_bp);
        button_bmi=(LinearLayout)findViewById(R.id.button_bmi);
        button_excercise=(LinearLayout)findViewById(R.id.button_excercise);
        button_food=(LinearLayout)findViewById(R.id.button_food);
        ivProfile=(ImageView)findViewById(R.id.ivProfile);
        tvProfile=(TextView)findViewById(R.id.tvProfile);
        ivbp=(ImageView)findViewById(R.id.ivbp);
        tvBP=(TextView)findViewById(R.id.tvBP);
        ivBMI=(ImageView)findViewById(R.id.ivBMI);
        tvBMI=(TextView)findViewById(R.id.tvBMI);
        ivExercise=(ImageView)findViewById(R.id.ivExercise);
        tvExcercise=(TextView)findViewById(R.id.tvExcercise);
        ivFood=(ImageView)findViewById(R.id.ivFood);
        tvFood=(TextView)findViewById(R.id.tvFood);
        ivbloodsugar=(ImageView)findViewById(R.id.ivbloodsugar);
        button_bloodsugar=(LinearLayout)findViewById(R.id.button_bloodsugar);
        tvbloodsugar=(TextView)findViewById(R.id.tvBP);
        button_report=(LinearLayout)findViewById(R.id.button_report);

    }
    public void iniClick(){
        button_profile.setOnClickListener(this);
        button_bmi.setOnClickListener(this);
        button_excercise.setOnClickListener(this);
        button_food.setOnClickListener(this);
        ivHome.setOnClickListener(this);
        button_report.setOnClickListener(this);
        button_bloodsugar.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
    }


    public void fragmentChange(String name) {

        FragmentManager mSettingsFragment = getSupportFragmentManager();
        FragmentTransaction tSettingsFragment = mSettingsFragment.beginTransaction();

        switch (name) {

            case "profile":

                tabLayout.setVisibility(View.GONE);
                currentFragment = new ProfileFragment();
                break;
            case "list":
                currentFragment = new ListBloodSugarFragment();
                break;
            case "BMI":
                tabLayout.setVisibility(View.GONE);
                // currentFragment = new BPFragment();
                break;
            case "track":
                tabLayout.setVisibility(View.GONE);
                currentFragment = new TrackFragment();
                break;
            case "BP":

                tabLayout.setVisibility(View.VISIBLE);

                break;
            case "BloodSugar":

                tabLayout.setVisibility(View.GONE);
                currentFragment = new BloodSugarFragment();
                break;
            case "exercise":
                tabLayout.setVisibility(View.GONE);
                currentFragment = new ExerciseFragment();
                break;
            case "food":
                tabLayout.setVisibility(View.GONE);
                currentFragment = new FoodFragment();
                break;
            case "ivEdit":
                tabLayout.setVisibility(View.VISIBLE);
                currentFragment = new FoodFragment();
                break;
            case "ivAdd":
                tabLayout.setVisibility(View.GONE);
                currentFragment = new ProfileFragment();
                break;



        }

        tSettingsFragment.replace(R.id.rlMainFragment, currentFragment, "MainFragment");
        tSettingsFragment.commit();
    }

    public  void setBackgroundColor(){
        ivProfile.setImageResource(R.drawable.profileg);
        tvProfile.setTextColor(Color.parseColor("#00bcd4"));
        ivbp.setImageResource(R.drawable.bpg);
        tvBP.setTextColor(Color.parseColor("#00bcd4"));
        ivBMI.setImageResource(R.drawable.bmig);
        tvBMI.setTextColor(Color.parseColor("#00bcd4"));
        ivbloodsugar.setImageResource(R.drawable.bloodsugarg);
        tvbloodsugar.setTextColor(Color.parseColor("#00bcd4"));
        ivExercise.setImageResource(R.drawable.exeg);
        tvExcercise.setTextColor(Color.parseColor("#00bcd4"));
        ivFood.setImageResource(R.drawable.foodg);
        tvFood.setTextColor(Color.parseColor("#00bcd4"));

    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.button_profile:
                title.setText("Profile");
                //  setBackgroundColor();
                tvBP.setTextColor(Color.parseColor("#3C3D3D"));
                ivbp.setImageResource(R.drawable.bpg);
                ivProfile.setImageResource(R.drawable.profileb);
                tvProfile.setTextColor(Color.parseColor("#0075b2"));
                tvExcercise.setTextColor(Color.parseColor("#3C3D3D"));
                ivExercise.setImageResource(R.drawable.exeg);
                fragmentChange("profile");
                break;
            case R.id.ivHome:
                Intent i=new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(i);
                break;
            case R.id.button_bmi:
                // setBackgroundColor();
                tvBP.setTextColor(Color.parseColor("#3C3D3D"));
                ivbp.setImageResource(R.drawable.bpg);
                tvProfile.setTextColor(Color.parseColor("#3C3D3D"));
                ivProfile.setImageResource(R.drawable.profileg);
                ivBMI.setImageResource(R.drawable.bmib);
                tvBMI.setTextColor(Color.parseColor("#0075b2"));
                tvExcercise.setTextColor(Color.parseColor("#3C3D3D"));
                ivExercise.setImageResource(R.drawable.exeg);
                title.setText("BMI");
                fragmentChange("BMI");
                break;

            case R.id.button_excercise:
                // setBackgroundColor();
                tvExcercise.setTextColor(Color.parseColor("#0075b2"));
                ivExercise.setImageResource(R.drawable.exe);
                tvBP.setTextColor(Color.parseColor("#3C3D3D"));
                ivbp.setImageResource(R.drawable.bpg);
                tvProfile.setTextColor(Color.parseColor("#3C3D3D"));
                ivProfile.setImageResource(R.drawable.profileg);
                ivBMI.setImageResource(R.drawable.bmig);
                tvBMI.setTextColor(Color.parseColor("#3C3D3D"));
                ivbloodsugar.setImageResource(R.drawable.bloodsugarg);
                tvbloodsugar.setTextColor(Color.parseColor("#3C3D3D"));
                title.setText("Exercise");
                fragmentChange("exercise");
                break;

            case R.id.button_food:
                // setBackgroundColor();
                title.setText("BreakFast");
                fragmentChange("food");
                break;
            case R.id.button_report:
                title.setText("Track Reports");
                fragmentChange("track");
                break;
            case R.id.button_bloodsugar:
                // setBackgroundColor();
                tvExcercise.setTextColor(Color.parseColor("#3C3D3D"));
                ivExercise.setImageResource(R.drawable.exeg);
                tvBP.setTextColor(Color.parseColor("#3C3D3D"));
                ivbp.setImageResource(R.drawable.bpg);
                tvProfile.setTextColor(Color.parseColor("#3C3D3D"));
                ivProfile.setImageResource(R.drawable.profileg);
                ivBMI.setImageResource(R.drawable.bmig);
                tvBMI.setTextColor(Color.parseColor("#3C3D3D"));
                ivbloodsugar.setImageResource(R.drawable.bloodsugarb);
                tvbloodsugar.setTextColor(Color.parseColor("#0075b2"));
                title.setText("Profile");
                fragmentChange("BloodSugar");
                break;

        }
    }

}
