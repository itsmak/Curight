package com.innovellent.curight.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.innovellent.curight.R;
import com.innovellent.curight.adapter.MedicineListAdapter;

import java.util.ArrayList;


public class MedicineReminderListActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recycler_view;
    MedicineListAdapter mAdapter;
    Button btnSubmit;
    ImageView ivAdd,ivBack;
    EditText etMedicineName,etSearch;
    Toolbar toolbar;
    ArrayList<String> arrayList=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder_list);
        init();
        iniClick();
        getData();
        try {
            etSearch.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence query, int start, int before, int count) {
                    query = etSearch.getText().toString().toString().toLowerCase();

                    final ArrayList<String> filteredList = new ArrayList<>();

                    for (int i = 0; i < arrayList.size(); i++) {

                        final String text = arrayList.get(i).toString().toLowerCase();
                            if (text.contains(query)) {

                           filteredList.add(arrayList.get(i));
                        }
                    }


                    mAdapter=new MedicineListAdapter(MedicineReminderListActivity.this,filteredList);
                    recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    recycler_view.setAdapter(mAdapter);
                }
            });

        }catch (Exception e){

        }
    }
    public void init(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        etSearch=(EditText)findViewById(R.id.etSearch);
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        etMedicineName=(EditText)findViewById(R.id.etMedicineName);
        ivAdd=(ImageView)findViewById(R.id.ivAdd);
      //  ivBack=(ImageView)findViewById(R.id.ivBack);

    }
    public void iniClick(){

        arrayList.add("New York,NY");
        arrayList.add("Los Angeles,CA");
        arrayList.add("Chicago,IL");
        arrayList.add("Houston,TX");
        arrayList.add("NEW Delhi,DL");
        arrayList.add("Mumbai,MH");
        arrayList.add("Bangalore,KA");
        ivAdd.setOnClickListener(this);
        //ivBack.setOnClickListener(this);
    }

    public void getData(){

        mAdapter=new MedicineListAdapter(MedicineReminderListActivity.this,arrayList);
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnSubmit:
                Intent i=new Intent(MedicineReminderListActivity.this,DiagnosticCentersActivity.class);
                startActivity(i);
                break;


            case R.id.ivAdd:
                if(TextUtils.isEmpty(etMedicineName.getText().toString())){
                    Intent i1 = new Intent(MedicineReminderListActivity.this, MedicineReminderSetActivity.class);
                    startActivity(i1);
                }else {
                    SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor et = sharedPreferences.edit();
                    et.putString("medicinename",etMedicineName.getText().toString());
                    et.apply();
                    et.commit();
                    Intent i1 = new Intent(MedicineReminderListActivity.this, MedicineReminderSetActivity.class);
                    startActivity(i1);
                }
                break;


        }
    }


}
