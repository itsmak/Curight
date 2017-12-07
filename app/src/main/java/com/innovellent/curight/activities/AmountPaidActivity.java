package com.innovellent.curight.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.DiagnosticTestAdapter;
import com.innovellent.curight.utility.Util;


import java.util.ArrayList;


public class AmountPaidActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recycler_view;
    DiagnosticTestAdapter mAdapter;
    Button btnSubmit;
    ImageView ivBack;
    ArrayList<String> arrayList=new ArrayList<String>();
    Toolbar toolbar;
    String user_id;

    String mobile;
    Long uid;
    String email;
    String name;
    TextView tvpatientName,tvMobile,tvPaidAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_paid);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            user_id = bundle.getString("user_id");
            uid = bundle.getLong("uid");
            email = bundle.getString("email");
            name = bundle.getString("name");
            mobile = bundle.getString("mobile");
        }
        init();
        iniClick();
    }

    public void init(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        tvpatientName =(TextView)findViewById(R.id.tvPatientName);
        tvMobile =(TextView)findViewById(R.id.tvMobileNo);
        tvPaidAmount =(TextView)findViewById(R.id.tvPaidAmount);
        tvpatientName.setText("Patient name: "+name);
        tvMobile.setText("Mobile No: "+mobile);
        SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        long total_amount = sharedPreferences.getLong("total_amount",0L);
        tvPaidAmount.setText("Paid Amount: "+total_amount);
       // ivBack=(ImageView)findViewById(R.id.ivBack);
    }
    public void iniClick(){
        btnSubmit.setOnClickListener(this);
     //   ivBack.setOnClickListener(this);
        arrayList.add("New York,NY");
        arrayList.add("Los Angeles,CA");
        arrayList.add("Chicago,IL");
        arrayList.add("Houston,TX");
        arrayList.add("NEW Delhi,DL");
        arrayList.add("Mumbai,MH");
        arrayList.add("Bangalore,KA");
    }

    public void getData(){

       // mAdapter=new DiagnosticTestAdapter(AmountPaidActivity.this,arrayList);
      //  recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
       // recycler_view.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {


            case R.id.btnSubmit:
                Intent i1=new Intent(AmountPaidActivity.this,HomeActivity.class);
                i1.putExtra("flag","home");
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i1);
                finish();
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
