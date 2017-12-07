package com.innovellent.curight;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.innovellent.curight.activities.TrackActivity;
import com.innovellent.curight.fragment.AccountFragment;
import com.innovellent.curight.fragment.ArticleFragment;
import com.innovellent.curight.fragment.MedicineReminderFragment;
import com.innovellent.curight.fragment.TestFragment;


public class MainActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener{

    AHBottomNavigation bottomNavigation;
    TextView title;
    FrameLayout frameontainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title=(TextView)findViewById(R.id.title);
        bottomNavigation= (AHBottomNavigation) findViewById(R.id.myBottomNavigation_ID);
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();
    }

    private void createNavItems()
    {
        //CREATE ITEMS
        AHBottomNavigationItem home=new AHBottomNavigationItem("Home",R.drawable.ic_home);
        AHBottomNavigationItem reminder=new AHBottomNavigationItem("Reminder",R.drawable.ic_reminder);
        AHBottomNavigationItem article=new AHBottomNavigationItem("Article",R.drawable.ic_article);
        AHBottomNavigationItem track=new AHBottomNavigationItem("Track",R.drawable.ic_track);
        AHBottomNavigationItem account=new AHBottomNavigationItem("Account",R.drawable.ic_account);

        //ADD THEM to bar
        bottomNavigation.addItem(home);
        bottomNavigation.addItem(reminder);
        bottomNavigation.addItem(article);
        bottomNavigation.addItem(track);
        bottomNavigation.addItem(account);

        //set properties
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setCurrentItem(0);

    }

    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        //show fragment
        if (position==0)
        {    title.setText("Home");
            TestFragment homeFragment=new TestFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id,homeFragment).commit();
        }else  if (position==1)
        {      title.setText("Reminder");
            MedicineReminderFragment reminderFragment=new MedicineReminderFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id,reminderFragment).commit();
        }else  if (position==2)

        {  title.setText("Article");
            ArticleFragment articleFragment=new ArticleFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id,articleFragment).commit();
        }
        else  if (position==3)
        {
            Intent i=new Intent(MainActivity.this,TrackActivity.class);
            startActivity(i);
          //  title.setText("Track");
           // TrackFragment trackFragment=new TrackFragment();
            //getSupportFragmentManager().beginTransaction().replace(R.id.content_id,trackFragment).commit();
        }
        else  if (position==4)
        {     title.setText("Account");
            AccountFragment accountFragment=new AccountFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id,accountFragment).commit();
        }
    }
}
















