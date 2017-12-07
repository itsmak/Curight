package com.innovellent.curight.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.innovellent.curight.R;
import com.innovellent.curight.adapter.CustomSpinnerAdapter1;
import com.innovellent.curight.adapter.CustomSpinnerAdapter2;
import com.innovellent.curight.adapter.VaccineAdapter;
import com.innovellent.curight.adapter.VaccineAdapter1;
import com.innovellent.curight.adapter.VaccineAdapter2;
import com.innovellent.curight.model.Vaccine;
import com.innovellent.curight.model.VaccineAddReminderDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by sagar on 9/13/2017.
 */

public class VaccineActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recycler_view,recycler_view1,recycler_view2;
    VaccineAdapter mAdapter;
    VaccineAdapter1 mAdapter1;
    VaccineAdapter2 mAdapter2 ;
    Button btnSubmit;
    Spinner spYear,spAge;
    RelativeLayout rlDate;
    ImageView ivAdd1,ivAdd2,ivAdd3,ivBack;
    VaccineAddReminderDialog vaccineAddReminderDialog;
    ArrayList<Vaccine> arrayList=new ArrayList<Vaccine>();
    ArrayList<Vaccine> arrayList1=new ArrayList<Vaccine>();
    ArrayList<Vaccine> arrayList2=new ArrayList<Vaccine>();
    String[] spinner1 = {"Name", "Jobby","Suresh", "Mahesh", "Pooja"};
    String[] spinner2 = {"Age", "1","2", "3", "4","5","6","7","8"};
    private int mYear, mMonth, mDay;
    TextView tvDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine);
        init();
        iniClick();
        getData();
        getData2();
    }
    public void init(){
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        recycler_view1=(RecyclerView)findViewById(R.id.recycler_view1);
        recycler_view2=(RecyclerView)findViewById(R.id.recycler_view2);
        spYear=(Spinner)findViewById(R.id.spYear);
        spAge=(Spinner)findViewById(R.id.spAge);
        ivAdd1=(ImageView)findViewById(R.id.ivAdd1);
        ivAdd2=(ImageView)findViewById(R.id.ivAdd2);
        ivAdd3=(ImageView)findViewById(R.id.ivAdd3);
        ivBack=(ImageView)findViewById(R.id.ivBack);
        rlDate=(RelativeLayout)findViewById(R.id.date_layout);
        tvDate=(TextView)findViewById(R.id.tv_date);
    }
    public void iniClick(){
        ivBack.setOnClickListener(this);
        arrayList.add(new Vaccine("HPV1 ","12-09-2017"));
        arrayList.add(new Vaccine("HPV11 ","12-10-2017"));
        arrayList1.add(new Vaccine("Tdap/Td ","15-09-2017"));
        arrayList1.add(new Vaccine("HPV11 ","2-10-2017"));
        arrayList1.add(new Vaccine("Tdap/Td ","12-10-2017"));
        arrayList1.add(new Vaccine("HPV1 ","22-10-2017"));
        arrayList2.add(new Vaccine("OPV3 ","15-09-2017"));
        /*arrayList2.add(new Vaccine("DTP Booster ","2-10-2017"));
        arrayList2.add(new Vaccine("Vercella ","19-10-2017"));
        arrayList2.add(new Vaccine("Typphoid Booster ","22-10-2017"));
        arrayList2.add(new Vaccine("HPV1 ","22-10-2017"));
        arrayList2.add(new Vaccine("OPV3 ","15-09-2017"));
        arrayList2.add(new Vaccine("DTP Booster ","2-10-2017"));
        arrayList2.add(new Vaccine("Vercella ","19-10-2017"));*/
        ivAdd1.setOnClickListener(this);
        ivAdd2.setOnClickListener(this);
        ivAdd3.setOnClickListener(this);
        rlDate.setOnClickListener(this);
    }

    public void getData(){

        mAdapter=new VaccineAdapter(VaccineActivity.this,arrayList);
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);

        mAdapter1=new VaccineAdapter1(VaccineActivity.this,arrayList1);
        recycler_view1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view1.setAdapter(mAdapter1);

        mAdapter2=new VaccineAdapter2(VaccineActivity.this,arrayList2);
        recycler_view2.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view2.setAdapter(mAdapter2);

    }
    public void selectDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        GregorianCalendar GregorianCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth - 1);

                        int dayOfWeek = GregorianCalendar.get(GregorianCalendar.DAY_OF_WEEK);

                        String day = "", monthYear = "";
                        int month = monthOfYear + 1;
                        if (dayOfMonth >= 1 && dayOfMonth <= 9) {
                            day = "0" + dayOfMonth;
                        } else {
                            day = dayOfMonth + "";
                        }
                        if (month >= 1 && month <= 9) {
                            monthYear = "0" + month;
                        } else {
                            monthYear = month + "";
                        }

                        String date = day + "-" + monthYear + "-" + year;
                        tvDate.setText(dayOfMonth + "/" + (monthYear) + "/" + year);


                    }
                }, mYear, mMonth, mDay);


        datePickerDialog.show();
    }

    public void getData2() {

        CustomSpinnerAdapter2 customSpinnerAdapter3 = new CustomSpinnerAdapter2(VaccineActivity.this, spinner1);
        spYear.setAdapter(customSpinnerAdapter3);
        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText(PasswordRecoveryQuestionsActivity.this, spinner3[i], Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        CustomSpinnerAdapter1 customSpinnerAdapter1 = new CustomSpinnerAdapter1(VaccineActivity.this, spinner2);
        spAge.setAdapter(customSpinnerAdapter1);
        spAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void addVaccine() {
        vaccineAddReminderDialog = new VaccineAddReminderDialog(this, new VaccineAddReminderDialog.VaccineAddReminderDialogClickListener(){


            @Override
            public void onSubmit() {
                vaccineAddReminderDialog.dismiss();
            }
        });

        vaccineAddReminderDialog.show();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.ivAdd1:
               addVaccine();
                break;
            case R.id.ivAdd2:
                addVaccine();
                break;

            case R.id.ivAdd3:
                addVaccine();
                break;
            case R.id.date_layout:
                selectDate();
                break;
            case R.id.ivBack:
                Intent i=new Intent(VaccineActivity.this,HomeActivity.class);
                i.putExtra("flag","reminder");
                startActivity(i);
                break;



        }
    }


}

