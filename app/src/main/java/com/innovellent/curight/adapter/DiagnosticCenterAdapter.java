package com.innovellent.curight.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.innovellent.curight.R;
import com.innovellent.curight.activities.SummaryDetailsActivity;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.Center;
import com.innovellent.curight.model.DiagnosticCenterDoctorByDC;
import com.innovellent.curight.model.GetTestDetailCenter;
import com.innovellent.curight.model.OverviewCenterByDC;
import com.innovellent.curight.model.PhotosCenterByDC;
import com.innovellent.curight.model.ServerResponseDoctorByDC;
import com.innovellent.curight.model.ServerResponseGetTestDetail;
import com.innovellent.curight.model.ServerResponseOverviewByDC;
import com.innovellent.curight.model.ServerResponsePhotosByDC;
import com.innovellent.curight.model.TestDetail;
import com.innovellent.curight.utility.Config;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sagar on 9/5/2017.
 */

public class DiagnosticCenterAdapter extends RecyclerView.Adapter<DiagnosticCenterAdapter.MyViewHolder> implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private static final String TAG = "CuRight";
    TextView doctorname_doctorbydc,specialization_doctorbydc,tvemail_doctorbydc,tvtime_doctorbydc,tvmobile_doctorbydc,tvaddress_doctorbydc;
    String doctorname,spec,email,normalworkingdays,weekendworkingdays,mobile,address;//strings for getdoctorbydc
    String summary;
    String testcode,testname,testknownas,dept,desc,testinst;
    ServerResponseDoctorByDC serverResponseDoctorByDC;
    ServerResponseGetTestDetail serverResponseGetTestDetail;
    ServerResponseOverviewByDC serverResponseOverviewByDC;
    ServerResponsePhotosByDC serverResponsePhotosByDC;
    TextView input;
    SliderLayout sliderLayout;
    HashMap<String,String> Hash_file_maps ;
    private ArrayList<Center> arrayList = new ArrayList<Center>();
    private Context mContext;

    public DiagnosticCenterAdapter(Context context, ArrayList<Center> arrayList) {
        mContext = context;
        this.arrayList = arrayList;

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public DiagnosticCenterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_diagnostic_center, parent, false);
        return new DiagnosticCenterAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DiagnosticCenterAdapter.MyViewHolder holder, final int position) {

        holder.tvCenterName.setText(arrayList.get(position).getDiagnosticcentrename());
        holder.rbSpec.setText(arrayList.get(position).getSpecializationname());
        holder.startTime.setText(arrayList.get(position).getNormalworkingschedule());
        holder.rbTag.setText(arrayList.get(position).getTagline());
        holder.endTime.setText(arrayList.get(position).getWeekendworkingschedule());
        holder.location.setText(arrayList.get(position).getAddress()+","+arrayList.get(position).getCity());

        int tests_count = 0;
        ArrayList<TestDetail> _test = arrayList.get(holder.getAdapterPosition()).getTestDetail();
        for (int j=0;j<_test.size();j++) {
            if ("Y".equals(_test.get(j).getTestchoosen())) {
                tests_count++;
            }
        }

        holder.testCount.setText(tests_count+" Out of "+arrayList.get(position).getCount()+" tests available");

        holder.btnOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("OverView","center :: "+arrayList.get(position).getDiagnosticcentreid());
            }
        });

        holder.btnPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.btnBookTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext,SummaryDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("dc_id",arrayList.get(position).getDiagnosticcentreid());
                bundle.putString("dc_name",arrayList.get(position).getDiagnosticcentrename());
                bundle.putString("location",arrayList.get(position).getAddress());

                Log.e("TAG","Size is ::  "+arrayList.get(position).getTestDetail().size());


                ArrayList<TestDetail> testObj = arrayList.get(position).getTestDetail();
                String test_amounts = "";
                for (int j=0;j<testObj.size();j++) {
                    if ("Y".equals(testObj.get(j).getTestchoosen())) {
                        test_amounts = test_amounts + testObj.get(j).getAmount() + ",";
                        Log.d(TAG, "test_amounts"+ test_amounts);
                    }
                }
                //Log.e("AMOUNTS","Val :: "+test_amounts);

                String test_amnt_str = test_amounts;
                if (test_amnt_str.endsWith(",")) {
                    test_amnt_str = test_amnt_str.substring(0,test_amnt_str.length()-1);
                }

                //Log.e("AMOUNTS","amnt_str :: "+test_amnt_str);

                String sel_test_names = DiagnosticTestAdapter.sel_test_names;
                if (sel_test_names.endsWith("^")) {
                    sel_test_names = sel_test_names.substring(0,sel_test_names.length()-1);
                }
                String sel_test_ids = DiagnosticTestAdapter.sel_test_ids;
                if (sel_test_ids.endsWith("^")) {
                    sel_test_ids = sel_test_ids.substring(0,sel_test_ids.length()-1);
                }
                bundle.putString("sel_test_ids",sel_test_ids);
                bundle.putString("test_names",sel_test_names);
                bundle.putString("test_amounts",test_amnt_str);
                i.putExtras(bundle);
                mContext.startActivity(i);
            }
        });


        holder.btnDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getdoctorbydc(position);

            }
        });


        holder.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gettestbydc(position);
            }
        });


        holder.btnOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getoverviewbydc(position);
            }
        });

        holder.btnPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getphotosbydc(position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    //getdoctorbydc api
    private void getdoctorbydc(int pos){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        final DiagnosticCenterDoctorByDC centre = new DiagnosticCenterDoctorByDC(arrayList.get(pos).getDiagnosticcentreid(),"0");

        Call<ServerResponseDoctorByDC> call = apiInterface.getDoctorByDC(centre);

        call.enqueue(new Callback<ServerResponseDoctorByDC>() {
            @Override
            public void onResponse(Call<ServerResponseDoctorByDC> call, Response<ServerResponseDoctorByDC> response) {
                serverResponseDoctorByDC =(ServerResponseDoctorByDC) response.body();
                String code = serverResponseDoctorByDC.getCode();

                if ("200".equals(code)) {
                    for (int i = 0; i < serverResponseDoctorByDC.getResults().size(); i++) {
                         doctorname = serverResponseDoctorByDC.getResults().get(i).getDoctorname();
                         spec = serverResponseDoctorByDC.getResults().get(i).getSpecialization();
                         email = serverResponseDoctorByDC.getResults().get(i).getEmail();
                         normalworkingdays = serverResponseDoctorByDC.getResults().get(i).getNormalworkingschedule();
                         weekendworkingdays = serverResponseDoctorByDC.getResults().get(i).getWeekendworkingschedule();
                         mobile = serverResponseDoctorByDC.getResults().get(i).getMobile();
                         address = serverResponseDoctorByDC.getResults().get(i).getAddresscentre();
                    }

                    Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.dialog_doctorbydc);

                    doctorname_doctorbydc = (TextView)dialog.findViewById(R.id.doctorname_doctorbydc);
                    specialization_doctorbydc = (TextView)dialog.findViewById(R.id.specialization_doctorbydc);
                    tvemail_doctorbydc = (TextView)dialog.findViewById(R.id.tvemail_doctorbydc);
                    tvtime_doctorbydc = (TextView)dialog.findViewById(R.id.tvtime_doctorbydc);
                    tvmobile_doctorbydc = (TextView)dialog.findViewById(R.id.tvmobile_doctorbydc);
                    tvaddress_doctorbydc = (TextView)dialog.findViewById(R.id.tvaddress_doctorbydc);


                    doctorname_doctorbydc.setText(doctorname);
                    specialization_doctorbydc.setText(spec);
                    tvemail_doctorbydc.setText("Email:"+" "+email);
                    tvtime_doctorbydc.setText("Time:"+" "+normalworkingdays+" "+weekendworkingdays);
                    tvmobile_doctorbydc.setText("Mobile:"+" "+mobile);
                    tvaddress_doctorbydc.setText("Address:"+" "+address);

                    //dialog.create();
                    dialog.show();


                }
            }

            @Override
            public void onFailure(Call<ServerResponseDoctorByDC> call, Throwable t) {

            }
        });
    }

    //gettestbydc api
    private void gettestbydc(int pos){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        final GetTestDetailCenter centre = new GetTestDetailCenter(arrayList.get(pos).getDiagnosticcentreid());

        Call<ServerResponseGetTestDetail> call = apiInterface.getTestByDc(centre);

        call.enqueue(new Callback<ServerResponseGetTestDetail>() {
            @Override
            public void onResponse(Call<ServerResponseGetTestDetail> call, Response<ServerResponseGetTestDetail> response) {
                serverResponseGetTestDetail =(ServerResponseGetTestDetail) response.body();
                String code = serverResponseGetTestDetail.getCode();

                ScrollView sv = new ScrollView(mContext);
                ImageView divider = new ImageView(mContext);

                sv.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                LinearLayout layout1 = new LinearLayout(mContext);
                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layout.setMargins(1, 1, 1, 1);
                divider.setLayoutParams(layout);
                divider.setBackgroundColor(Color.WHITE);
                layout1.setOrientation(LinearLayout.VERTICAL);

                sv.addView(layout1);


                if("200".equals(code)){
                    final Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.dialog_gettestbydc);
                    LinearLayout ll = (LinearLayout)dialog.findViewById(R.id.ll_gettestbydc);
                    ImageView img_clsoedialog = (ImageView)dialog.findViewById(R.id.img_closedialog);

                    for (int i = 0; i < serverResponseGetTestDetail.getResults().size(); i++) {
                        testcode = serverResponseGetTestDetail.getResults().get(i).getTestcode();
                        testname = serverResponseGetTestDetail.getResults().get(i).getTestname();
                        Log.d("TestName==", testname);
                        testknownas = serverResponseGetTestDetail.getResults().get(i).getTestknownas();
                        dept = serverResponseGetTestDetail.getResults().get(i).getDepartment();
                        desc = serverResponseGetTestDetail.getResults().get(i).getDescription();
                        testinst = serverResponseGetTestDetail.getResults().get(i).getTestinstruction();

                       input = new TextView(mContext);
                        input.setPadding(5, 5, 5, 5);
                        input.setText(testname);
                        input.setTypeface(input.getTypeface(), Typeface.BOLD);


                        layout1.addView(input);
                    }

                    ll.addView(sv);
                    dialog.show();


                    img_clsoedialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<ServerResponseGetTestDetail> call, Throwable t) {

            }
        });
    }

    //overview api
    private void getoverviewbydc(int pos){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        final OverviewCenterByDC centre = new OverviewCenterByDC(arrayList.get(pos).getDiagnosticcentreid());

        Call<ServerResponseOverviewByDC> call = apiInterface.getOverviewByDc(centre);

        call.enqueue(new Callback<ServerResponseOverviewByDC>() {
            @Override
            public void onResponse(Call<ServerResponseOverviewByDC> call, Response<ServerResponseOverviewByDC> response) {
                serverResponseOverviewByDC =(ServerResponseOverviewByDC) response.body();
                String code = serverResponseOverviewByDC.getCode();

                if("200".equals(code)){
                for(int i=0; i<serverResponseOverviewByDC.getResults().size(); i++){
                     summary = serverResponseOverviewByDC.getResults().get(i).getSummary();
                    Log.d("Summary==", summary);
                }
                    final Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.dialog_overviewbydc);

                    TextView tv_summary = (TextView)dialog.findViewById(R.id.tv_summaryoverviewbydc);
                    ImageView img = (ImageView)dialog.findViewById(R.id.img_closedialog_overview);

                    tv_summary.setText(summary);


                    dialog.show();



                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ServerResponseOverviewByDC> call, Throwable t) {

            }
        });
    }

    //getphotosbydc api
    private void getphotosbydc(int pos){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        final PhotosCenterByDC centre = new PhotosCenterByDC(arrayList.get(pos).getDiagnosticcentreid());

        Call<ServerResponsePhotosByDC> call = apiInterface.getPhotosByDC(centre);

        call.enqueue(new Callback<ServerResponsePhotosByDC>() {
            @Override
            public void onResponse(Call<ServerResponsePhotosByDC> call, Response<ServerResponsePhotosByDC> response) {
                serverResponsePhotosByDC =(ServerResponsePhotosByDC) response.body();
                String code = serverResponsePhotosByDC.getCode();



                    final Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.activity_photosbydc);


                    Hash_file_maps = new HashMap<String, String>();

                    sliderLayout = (SliderLayout)dialog.findViewById(R.id.slider);


                if("200".equals(code)){
                    for(int i=0; i<serverResponsePhotosByDC.getResults().size(); i++){


                        String photos = serverResponsePhotosByDC.getResults().get(i).getPhotoname();
                        Log.d("Photo Urls", photos);


                                Hash_file_maps.put("Android CupCake", photos);

                            for(String name : Hash_file_maps.keySet()){

                                TextSliderView textSliderView = new TextSliderView(mContext);
                                textSliderView
                                        //.description(name)
                                        .image(Hash_file_maps.get(name))
                                        .setScaleType(BaseSliderView.ScaleType.Fit);
                                //.setOnSliderClickListener((BaseSliderView.OnSliderClickListener) mContext);
                        /*textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra",name);*/
                                sliderLayout.addSlider(textSliderView);
                            }


                    }
                    sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                    sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    sliderLayout.setCustomAnimation(new DescriptionAnimation());
                    sliderLayout.setDuration(3000);
                   // sliderLayout.addOnPageChangeListener((ViewPagerEx.OnPageChangeListener) mContext);



                    dialog.show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponsePhotosByDC> call, Throwable t) {

            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCenterName;
        Button btnBookTest;
        RadioButton rbSpec;
        TextView startTime;
        RadioButton rbTag;
        TextView endTime;
        TextView location;
        TextView testCount;
        Button btnOverview,btnDoctors,btnTest;
        Button btnPhotos;

        MyViewHolder(View view) {
            super(view);
            tvCenterName = (TextView) view.findViewById(R.id.tvCenterName);
            btnBookTest=(Button)view.findViewById(R.id.btnBookTest);
            rbSpec = (RadioButton) view.findViewById(R.id.rbSpec);
            startTime = (TextView) view.findViewById(R.id.tvDateTime);
            rbTag = (RadioButton) view.findViewById(R.id.rbFullTime);
            endTime = (TextView) view.findViewById(R.id.tvFullTimeDateTime);
            location = (TextView) view.findViewById(R.id.tvLocation);
            testCount = (TextView) view.findViewById(R.id.tvTestCount);
            btnOverview = (Button) view.findViewById(R.id.btnOverview);
            btnPhotos = (Button) view.findViewById(R.id.btnPhotos);
            btnDoctors = (Button)view.findViewById(R.id.btnDoctors);
            btnTest = (Button)view.findViewById(R.id.btnTest);

        }
    }



}

