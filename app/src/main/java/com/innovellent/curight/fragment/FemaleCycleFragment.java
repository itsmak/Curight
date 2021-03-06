package com.innovellent.curight.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.innovellent.curight.R;
import com.innovellent.curight.adapter.FemaleCycleAdapter;
import com.innovellent.curight.adapter.TRACK_SPINNER_ADAPTER;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddBloodCountDialog;
import com.innovellent.curight.model.AddFCTCountDialog;
import com.innovellent.curight.model.FCT;
import com.innovellent.curight.model.FctPojo;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.ServerResponseFct;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.ContentValues.TAG;
import static com.innovellent.curight.utility.Constants.CURIGHT_TAG;

/**
 * Created by sagar on 10/2/2017.
 */

public class FemaleCycleFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    FemaleCycleAdapter mAdapter;
    TextView tvList;
  //  AddBloodCountDialog addBloodCountDialog;
    ImageView ivAdd;
    EditText tv_normalduration,et_gap,et_reminderdays,etNotes;
    TextView tv_date;
    RadioGroup radio_group;
    RadioButton radio_button_yes,radio_button_no;
    RelativeLayout rlFemaleCycle;
    ScrollView svAddBloodCount;
    FCT fct;
    Button btnSave_fct;
    SimpleDateFormat simpledateformat;
    Calendar calendar;
    ProgressDialog progressDialog;
    AddFCTCountDialog addFCTCountDialog;
    DatePickerDialog datePickerDialog;
    String normalduration,gap,currentperiod,missing,notes,reminder,radiobutton_selected_yes="",Date,fctid;
    RelativeLayout date_layout;
    String USER_ID;
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();
    TRACK_SPINNER_ADAPTER customSpinnerAdapter3;
    Spinner spUser;
    int uid;
    ArrayList<FCT> arrayList=new ArrayList<FCT >();
    public FemaleCycleFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_female_cycle, container, false);

       // getFCTData();
        initReferences(rootView);
        inClick();
        //getData();


        spinnerList.clear();
        getSpinnerData();

        calendar = Calendar.getInstance();


        /*simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
         Date = simpledateformat.format(calendar.getTime());*/

       /* datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv_date.setText(getContext().getString(R.string.date_formatted, year, month + 1, dayOfMonth));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        date_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if(group.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getActivity(), "Please Select any one yes or no", Toast.LENGTH_SHORT).show();
                }else if(radio_button_yes.isChecked()) {

                    radiobutton_selected_yes = "Yes";
                }else if(radio_button_no.isChecked()) {

                    radiobutton_selected_yes = "No";
                }



            }
        });*/
        return rootView;

    }

   /* public void getData(){
        arrayList.add(new BloodCount("4","4","3","2","6","3","8","0","6","9","9"));
        arrayList.add(new BloodCount("8","8","3","2","7","3","8","2","6","8","3"));
        arrayList.add(new BloodCount("9","4","8","2","6","3","3","0","0","9","8"));
        arrayList.add(new BloodCount("1","0","3","2","6","8","8","0","6","9","3"));
        arrayList.add(new BloodCount("2","0","2","2","6","8","8","4","6","5","6"));
        mAdapter=new FemaleCycleAdapter(getActivity(),arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        rlFemaleCycle.setVisibility(View.GONE);


    }*/


    private void showProgressDialog(String title) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public void initReferences(View rootView) {
        tvList=(TextView) rootView.findViewById(R.id.tvList);
        ivAdd=(ImageView)rootView.findViewById(R.id.ivAdd);
        //rlFemaleCycle=(RelativeLayout)rootView.findViewById(R.id.rlFemaleCycle);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);

       // date_layout = (RelativeLayout)rootView.findViewById(R.id.date_layout);
        spUser = (Spinner) rootView.findViewById(R.id.spUser);


    }
    public void inClick(){
        tvList.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
//        btnSave_fct.setOnClickListener(this);
        tvList.setTextColor(Color.parseColor("#FFFFFF"));


    }

    private void AddFCTRecords() {

        addFCTCountDialog = new AddFCTCountDialog(getActivity(), new AddFCTCountDialog.AddFCTDialogClickListener() {
            @Override
            public void onSubmit(String Normalperiodduration, String Gap, String Reminderdays, String CurrentPeriod, String Miss, String Notes,String Date) {
                addFCTCountDialog.dismiss();
                showProgressDialog("Adding");
                addfctrecord(Normalperiodduration,Gap,Reminderdays,CurrentPeriod,Miss,Notes,Date);
            }

            @Override
            public void onCancel() {
                addFCTCountDialog.dismiss();
            }
        });

        addFCTCountDialog.show();

    }



    private void getSpinnerData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        int uid = (int) Prefs.getLong("user_id",0);
        PostBodyProfile postBodyprofile = new PostBodyProfile(uid, "family");
        Call<MyProfile_Response> call = reditapi.getProfile(postBodyprofile);

        call.enqueue(new Callback<MyProfile_Response>() {
            @Override
            public void onResponse(Call<MyProfile_Response> call, Response<MyProfile_Response> response) {


                if (response.body() != null) {


                    Log.e("", "profileResponse: code: " + response.body().getCode());

                    ArrayList<PROFILE_FEED> result = response.body().getResults();

                    Log.e("", "profileResponse: listsize: " + result.size());
                    for (int i = 0; i < result.size(); i++) {

                        String name = result.get(i).getName();
                        String lastName = "";
                        String firstName= "";
                        if(name.split("\\w+").length>1){

                            //lastName = name.substring(name.lastIndexOf(" ")+1);
                            firstName = name.substring(0, name.lastIndexOf(' '));
                        }
                        else{
                            firstName = name;
                        }

                        USER_ID = result.get(i).getUserid();
                        //spinnerList.add(new PROFILE("","","",""));
                        spinnerList.add(new PROFILE(result.get(i).getUserid(),result.get(i).getId(), firstName, result.get(i).getAge(), result.get(i).getRelationship(),result.get(i).getGender()));
                    }
                    getData2();
                    USER_ID = result.get(0).getUserid();
                    Prefs.putLong("user_id",Long.parseLong(USER_ID));
                    Prefs.putString("spinner_gender", result.get(0).getGender());
                    // GetData(result.get(1).getUserid());
                } else {

                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

                }

                //remainder_rclrvw.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<MyProfile_Response> call, Throwable t) {

                Log.e("", "onFailure: Somethings went wrong" + t.getMessage());
                Toast.makeText(getActivity(), "Somethings went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void getData2() {

        customSpinnerAdapter3 = new TRACK_SPINNER_ADAPTER(getActivity(), spinnerList);
        spUser.setAdapter(customSpinnerAdapter3);
        spUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText(PasswordRecoveryQuestionsActivity.this, spinner3[i], Toast.LENGTH_SHORT).show();
                //spAge.setText(spinnerList.get(i).getUser_age());
                USER_ID = spinnerList.get(i).getUser_id();
                String user_gender = spinnerList.get(i).getUser_getGender();
                Prefs.putLong("spinner_id", Long.parseLong(spinnerList.get(i).getUser_id()));
                Prefs.putString("spinner_gender", user_gender);
                uid = (int) Prefs.getLong("spinner_id",0);
                Log.d(TAG, "Myuserid on select" + uid);

                getFCTData(uid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                int uid = (int) Prefs.getLong("user_id",0);
                Log.d("user_forwhr", ""+uid);
                Log.d(TAG, "MyUSER_ID on spinner" + USER_ID);

                getFCTData(uid);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivAdd:
                /*tvList.setTextColor(Color.parseColor("#9DA1A0"));
                rlFemaleCycle.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);*/
                String gender = Prefs.getString("spinner_gender", "");
                Log.d(TAG, "selected gender ::" + gender);
                if (gender.equalsIgnoreCase("Female"))
                {
                    AddFCTRecords();
                }else {
                    Toast.makeText(getActivity(),"Sorry only Females can add this",Toast.LENGTH_SHORT).show();
                }
               //
                break;
            case R.id.tvList:
                tvList.setTextColor(Color.parseColor("#FFFFFF"));
                //rlFemaleCycle.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);

            /*case R.id.btnSave_fct:
               // showProgressDialog("Adding");
                //addfctrecord(tv_normalduration.getText().toString().trim(),et_gap.getText().toString().trim(),et_reminderdays.getText().toString().trim(),tv_date.getText().toString().trim(),radiobutton_selected_yes,etNotes.getText().toString().trim(),Date);
                tvList.setTextColor(Color.parseColor("#FFFFFF"));
                rlFemaleCycle.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);*/
            default:
                break;

        }
    }


    private void addfctrecord(String Normalperiodduration, String Gap,String Reminderdays,String CurrentPeriod,String Miss,String Notes,String Date){
        Log.d("fctaddcalling", "called");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        int uid = (int) Prefs.getLong("spinner_id",0);
        try{


            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", uid);
            paramObject.put("normalperiodduration", Normalperiodduration);
            paramObject.put("gap", Gap);
            paramObject.put("reminderdays", Reminderdays);
            paramObject.put("currentperioddate", CurrentPeriod);
            paramObject.put("miss", Miss);
            paramObject.put("notes",Notes);
            paramObject.put("date", Date);

            Log.d(TAG, "Fctaddingvalues" +uid+Normalperiodduration +Gap+Reminderdays +CurrentPeriod +Miss +Notes +Date);
            Call<ServerResponseFct<String>> call = apiInterface.addfctrecord("abc", paramObject.toString());

            call.enqueue(new Callback<ServerResponseFct<String>>() {
                @Override
                public void onResponse(Call<ServerResponseFct<String>> call, Response<ServerResponseFct<String>> response) {
                    if (getActivity() != null) {
                        progressDialog.dismiss();

                        if(response.isSuccessful()){
                            ServerResponseFct<String>  serverResponseFct = response.body();
                            Log.d("beforeresponse","response_before");
                            Log.d("addfctdialog_response", serverResponseFct.getResults());
                            Log.d("afteresponse", "ressponse_after");
                            if(serverResponseFct.getResults().equals("Success")){
                                Log.d("insideresponse","responsefct");
                                Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_SHORT).show();
                                showProgressDialog("Loading");
                                int uid = (int) Prefs.getLong("spinner_id",0);
                                getFCTData(uid);
                                progressDialog.dismiss();
                            }else {
                                Toast.makeText(getActivity(), "please try again", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<ServerResponseFct<String>> call, Throwable t) {
                    if (getActivity() != null) progressDialog.dismiss();
                    Log.d(CURIGHT_TAG, t.getMessage());
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void getFCTData(int user_id){
        cleardata();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
       // int uid = (int) Prefs.getLong("user_id",0);

        FctPojo fctPojo = new FctPojo(user_id);

        final Call<ResponseBody> call = apiInterface.getfctdata("abc", fctPojo);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){

                    try{
                        String res_data = response.body().string();
                        Log.e("res_dataforfct", res_data);

                        JSONObject jsonObject = new JSONObject(res_data);
                        String code = jsonObject.getString("Code");

                        JSONArray jsonArray = jsonObject.getJSONArray("Results");
                        for(int i=0; i<jsonArray.length(); i++){
                            fct = new FCT();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            normalduration = jsonObject1.getString("normalperiodduration");
                            gap = jsonObject1.getString("gap");
                            reminder = jsonObject1.getString("reminderdays");
                            currentperiod = jsonObject1.getString("currentperioddate");
                            missing = jsonObject1.getString("miss");
                            notes = jsonObject1.getString("notes");



                            fct.setNormalperiodduration(normalduration);
                            fct.setGap(gap);
                            fct.setReminderdays(reminder);
                            fct.setCurrentperioddate(currentperiod);
                            fct.setMiss(missing);
                            fct.setNotes(notes);
                            fct.setFemalecycletrackid(Integer.parseInt(jsonObject1.getString("femalecycletrackid")));

                            arrayList.add(fct);


                        }

                        if(getActivity()!=null) {
                            mAdapter = new FemaleCycleAdapter(getActivity(), arrayList);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            recyclerView.setAdapter(mAdapter);
//                            rlFemaleCycle.setVisibility(View.GONE);
                        }


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    recyclerView.removeAllViews();
                    Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (getActivity() != null) progressDialog.dismiss();
                Log.d(CURIGHT_TAG, t.getMessage());
            }
        });

    }

    private void cleardata(){

        mAdapter = new FemaleCycleAdapter(getActivity(),arrayList);

        arrayList.clear();
        mAdapter.notifyDataSetChanged();
    }
}




