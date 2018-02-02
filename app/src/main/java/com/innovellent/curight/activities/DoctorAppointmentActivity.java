package com.innovellent.curight.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.DoctorAppointmentAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.DoctorList;
import com.innovellent.curight.model.ServerResponseDoctorAppointment;
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

/**
 * Created by SUNIL on 12/4/2017.
 */

public class DoctorAppointmentActivity extends AppCompatActivity {

    RecyclerView recycler_view_doctorappointment;
    DoctorAppointmentAdapter doctorAppointmentAdapter;
    ImageView ivback1;
    ServerResponseDoctorAppointment serverResponseDoctorAppointment;
    ArrayList<String> doctorArrayList = new ArrayList<String>();

    ArrayList<DoctorList> arrayList_doctorlist = new ArrayList<DoctorList>();

    DoctorList doctorList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorappointment);

        recycler_view_doctorappointment = (RecyclerView)findViewById(R.id.recycler_view_doctorappointment);
        ivback1 = (ImageView) findViewById(R.id.ivback1);

        ivback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getAllDoctors();


       /* recycler_view_doctorappointment.addOnItemTouchListener(new RecyclerItemClickListener(DoctorAppointmentActivity.this, recycler_view_doctorappointment, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(DoctorAppointmentActivity.this, "item clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));*/


    }


    private void getAllDoctors(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ServerResponseDoctorAppointment> call = apiInterface.getAllDoctor();


        call.enqueue(new Callback<ServerResponseDoctorAppointment>() {
            @Override
            public void onResponse(Call<ServerResponseDoctorAppointment> call, Response<ServerResponseDoctorAppointment> response) {
                serverResponseDoctorAppointment =(ServerResponseDoctorAppointment) response.body();
                String code = serverResponseDoctorAppointment.getCode();
                if("200".equals(code)){

                    for (int i=0; i<serverResponseDoctorAppointment.getResults().size(); i++){
                        doctorList = serverResponseDoctorAppointment.getResults().get(i);
                            Log.d("doctoralldata===", ""+doctorList.getDoctorname());
                        arrayList_doctorlist.add(doctorList);
                        try{
                            String doctor_name = doctorList.getDoctorname();
                            Log.d("doctorname", doctor_name);
                            doctorArrayList.add(doctor_name);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if (doctorArrayList.size()!=0) {
                        doctorAppointmentAdapter = new DoctorAppointmentAdapter(DoctorAppointmentActivity.this, doctorArrayList, arrayList_doctorlist);
                        recycler_view_doctorappointment.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler_view_doctorappointment.getContext());
                        recycler_view_doctorappointment.addItemDecoration(dividerItemDecoration);
                        recycler_view_doctorappointment.setAdapter(doctorAppointmentAdapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<ServerResponseDoctorAppointment> call, Throwable t) {
                t.getMessage();
                String message = t.getMessage();
                Log.e("TAG","error :: "+message);
                if (!isFinishing()) {
                    if (Constants.SERVER_DOWN.equals(message)) {
                        Util.showAlertDialog(DoctorAppointmentActivity.this, "Server is Down! Please try  again later!", "ERROR");
                        return;
                    } else {
                        Util.showAlertDialog(DoctorAppointmentActivity.this, message, "ERROR");
                        return;
                    }
                }
            }
        });
    }
}
