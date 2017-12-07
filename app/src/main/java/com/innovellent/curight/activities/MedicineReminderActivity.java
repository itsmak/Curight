package com.innovellent.curight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.innovellent.curight.R;
import com.innovellent.curight.adapter.MedicineAdpater;

import java.util.ArrayList;



public class MedicineReminderActivity  extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recycler_view;
    MedicineAdpater mAdapter;
    Button btnSubmit;
    ImageView ivBack;
    Toolbar toolbar;
    ArrayList<String> arrayList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder);
        init();
        iniClick();
        getData();
    }
    public void init(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
      //  ivBack=(ImageView)findViewById(R.id.ivBack);
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);

    }
    public void iniClick(){
        btnSubmit.setOnClickListener(this);
        arrayList.add("New York,NY");
        arrayList.add("Los Angeles,CA");
        arrayList.add("Chicago,IL");
        arrayList.add("Houston,TX");
        arrayList.add("NEW Delhi,DL");
        arrayList.add("Mumbai,MH");
        arrayList.add("Bangalore,KA");
    }

    public void getData(){

     //   mAdapter=new MedicineAdpater(MedicineReminderActivity.this,arrayList);
      //  recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
       // recycler_view.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnSubmit:
                Intent i=new Intent(MedicineReminderActivity.this,AmountPaidActivity.class);
                startActivity(i);
                break;


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
