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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.innovellent.curight.R;
import com.innovellent.curight.adapter.DoctorAppointmentAdapter;
import com.innovellent.curight.adapter.MedicineListAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.fragment.Medicine_list;
import com.innovellent.curight.model.Medicine;
import com.innovellent.curight.model.Medicine_List_Feed;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.ServerResponseDoctorAppointment;
import com.innovellent.curight.model.ServerResponsemedicine;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.Constants;
import com.innovellent.curight.utility.DividerItemDecoration;
import com.innovellent.curight.utility.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MedicineReminderListActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recycler_view;
    MedicineListAdapter mAdapter;
    Button btnSubmit;
    ImageView ivAdd,ivBack;
    EditText etMedicineName,etSearch,etsearch_med;
    Toolbar toolbar;
    private static final String TAG = "CuRight";
    ArrayList<Medicine_list> arrayList=new ArrayList<Medicine_list>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder_list);
        init();
        iniClick();
        //getData();
        getAllMedicine();
        etsearch_med.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
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

                           filteredList.add(arrayList.get(i).getMedicinename());
                        }
                    }


//                    mAdapter=new MedicineListAdapter(MedicineReminderListActivity.this,filteredList);
//                    recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//                    recycler_view.setAdapter(mAdapter);
                }
            });

        }catch (Exception e){

        }
    }

    private void filter(String text){

        ArrayList<Medicine_list> filteredlist = new ArrayList<>();
        for(Medicine_list item : arrayList){
            if(item.getMedicinename().toLowerCase().contains(text.toLowerCase()))
            {
                filteredlist.add(item);
            }
        }
        mAdapter.filterlist(filteredlist);
    }
    public void init(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        etsearch_med = (EditText) findViewById(R.id.etsearch_med);
        etSearch=(EditText)findViewById(R.id.etSearch);
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        etMedicineName=(EditText)findViewById(R.id.etMedicineName);
        ivAdd=(ImageView)findViewById(R.id.ivAdd);
      //  ivBack=(ImageView)findViewById(R.id.ivBack);

    }
    public void iniClick(){

 //       arrayList.add(1,"New York,NY");
//        arrayList.add("Los Angeles,CA");
//        arrayList.add("Chicago,IL");
//        arrayList.add("Houston,TX");
//        arrayList.add("NEW Delhi,DL");
//        arrayList.add("Mumbai,MH");
//        arrayList.add("Bangalore,KA");
        ivAdd.setOnClickListener(this);
        //ivBack.setOnClickListener(this);
    }

//    public void getData(){
//
//        mAdapter=new MedicineListAdapter(MedicineReminderListActivity.this,arrayList);
//        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//        recycler_view.setAdapter(mAdapter);
//
//    }
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
                    finish();
                }
                break;

        }
    }
    private void getAllMedicine(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ServerResponsemedicine> call = apiInterface.getAllMedicine();


        call.enqueue(new Callback<ServerResponsemedicine>() {
            @Override
            public void onResponse(Call<ServerResponsemedicine> call, Response<ServerResponsemedicine> response) {
                if (response.body() != null) {

                    Log.d(TAG, "getmedicine: " + response);
                    ArrayList<Medicine_List_Feed> result = response.body().getResults();
                    Log.e(TAG, "getmedicine:  listsize: " + result.size());
                    for (int i = 0; i < result.size(); i++){
                        arrayList.add(new Medicine_list(result.get(i).getMedicineid(),result.get(i).getMedicinename()));

                    }
                }else {
                    Toast.makeText(MedicineReminderListActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
                mAdapter=new MedicineListAdapter(MedicineReminderListActivity.this,arrayList);
                recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                recycler_view.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<ServerResponsemedicine> call, Throwable t) {
                t.getMessage();
                String message = t.getMessage();
                Log.e("TAG","error :: "+message);

            }
        });
    }

}
