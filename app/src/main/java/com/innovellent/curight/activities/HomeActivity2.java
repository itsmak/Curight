package com.innovellent.curight.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.DiagnosticTestAdapter;
import com.innovellent.curight.fragment.NavigationDrawerFragment;
import com.innovellent.curight.utility.BottomNavigationViewHelper;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by sagar on 10/10/2017.
 */

public class HomeActivity2  extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks,View.OnClickListener {

    private static final String KEY_SELECTED_PAGE = "KEY_SELECTED_PAGE";
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private DrawerLayout drawerLayout;
    private int i=0;
    private static final int PERMISSION_REQUEST_CODE = 200;
    ImageView ivBack,ivAdd;
    private TextView line1,tvHome,tvmymarketplace,tvmywishlist,tvmyoffering;
    Fragment currentFragment;
    LinearLayout llMyoffer,llOffering,llWishlist,button_panel;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        ivBack=(ImageView)findViewById(R.id.ivback);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNavigationDrawerFragment.isDrawerOpen()) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        ivAdd = (ImageView) findViewById(R.id.ivAdd);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity2.this,"Cart click 2!",Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA,READ_PHONE_STATE,WRITE_EXTERNAL_STORAGE,ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);

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
                      break;
                    }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }
    }


    @Override
    public void onBackPressed() {

        if(i==0){
            i++;
        }else{
            super.onBackPressed();
            finish();
        }


    }

}
