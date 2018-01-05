package com.innovellent.curight.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.innovellent.curight.R;
import com.innovellent.curight.adapter.DiagnosticTestAdapter;
import com.innovellent.curight.adapter.LunchAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.Food;
import com.innovellent.curight.model.Goal;
import com.innovellent.curight.model.Lunch;
import com.innovellent.curight.model.ServerResponseFood;
import com.innovellent.curight.model.ServerResponseGoal;
import com.innovellent.curight.model.UserIdGoal;
import com.innovellent.curight.utility.Config;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FoodFitnessActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recycler_view;
    DiagnosticTestAdapter mAdapter;
    Button btnSubmit;
    ArrayList<String> arrayList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_fitness);
        init();

    }
    public void init(){
       btnSubmit= (Button)findViewById(R.id.btnSubmit);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnSubmit:
                Intent i=new Intent(FoodFitnessActivity.this,AmountPaidActivity.class);
                startActivity(i);
                break;


        }
    }


}
