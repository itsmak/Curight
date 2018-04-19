package com.innovellent.curight.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.activities.ResizableCustomTextView;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.DeleteParameterpatientreport;
import com.innovellent.curight.model.PatientReportsData;
import com.innovellent.curight.model.PatientReportsPojo;
import com.innovellent.curight.model.Report_FEED;
import com.innovellent.curight.model.Test_List;
import com.innovellent.curight.model.WHR_LIST;
import com.innovellent.curight.model.WHR_LIST_DATE;
import com.innovellent.curight.utility.Config;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SUNIL on 12/22/2017.
 */

public class YourReportsAdapter extends RecyclerView.Adapter<YourReportsAdapter.MyViewHolder>  {

    private static final int MAX_LINES =1;
    private final OnItemClickListener listener;
    private final int position;
    Context mContext;
    ArrayList<Report_FEED> reportlist = new ArrayList<Report_FEED>();
    ArrayList<PatientReportsData> patientReportsDatas =new ArrayList<PatientReportsData>();
    ArrayList<PatientReportsData> model;
    PatientReportsData patientReportsData;
    String doctorno;
    ProgressDialog progressDialog;

    public YourReportsAdapter(Context context, ArrayList<Report_FEED> reportlist,int position, OnItemClickListener listener) {
        mContext = context;
        this.reportlist = reportlist;
        this.listener = listener;
        this.position = position;
    }



    public void filterlist(ArrayList<Report_FEED> filteredlist) {
        reportlist=filteredlist;
        notifyDataSetChanged();
    }

    @Override
    public YourReportsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.yourreports_item, parent, false);
        return new YourReportsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final YourReportsAdapter.MyViewHolder holder, final int position) {

      //  reportlist = reportlist.get(position);
        // add PhoneStateListener
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);

        holder.txt_day.setText(reportlist.get(position).getVisitday());
        holder.txt_month.setText(reportlist.get(position).getVisitmonth());
        if(holder.txt_month.getText().toString().equals("Jaunary")){
            holder.txt_month.setText("JAN");
        }else if(holder.txt_month.getText().toString().equals("Febuary")){
            holder.txt_month.setText("FEB");
        }else if(holder.txt_month.getText().toString().equals("March")){
            holder.txt_month.setText("MAR");
        }else if(holder.txt_month.getText().toString().equals("April")){
            holder.txt_month.setText("APR");
        }else if(holder.txt_month.getText().toString().equals("May")){
            holder.txt_month.setText("MAY");
        }else if(holder.txt_month.getText().toString().equals("June")){
            holder.txt_month.setText("JUN");
        }else if(holder.txt_month.getText().toString().equals("July")){
            holder.txt_month.setText("JUL");
        }else if(holder.txt_month.getText().toString().equals("August")){
            holder.txt_month.setText("AUG");
        }else if(holder.txt_month.getText().toString().equals("September")){
            holder.txt_month.setText("SEP");
        }else if(holder.txt_month.getText().toString().equals("October")){
            holder.txt_month.setText("OCT");
        }else if(holder.txt_month.getText().toString().equals("November")){
            holder.txt_month.setText("NOV");
        } else if(holder.txt_month.getText().toString().equals("December")){
            holder.txt_month.setText("DEC");
        }
        holder.txt_diagonsticname.setText(reportlist.get(position).getDiagnosticcentrename());
        holder.txt_reason.setText(reportlist.get(position).getReason());
        holder.txt_doctorname.setText(reportlist.get(position).getDoctorname());
        doctorno = reportlist.get(position).getDoctornumber();
       // holder.txt_doctornumber.setText(patientReportsData.getDoctornumber());
        holder.txt_comments.setText(reportlist.get(position).getComments());
        //holder.txt_visitdate.setText(patientReportsData.getVisitdate());


//       ResizableCustomTextView.doResizeTextView(holder.txt_comments, MAX_LINES, "View More", true);

        holder.img_reportfiledownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFileClick(reportlist.get(position),position);
            }
        });
        holder.img_calldoctor_fromreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(reportlist.get(position),position);

            }
        });
        holder.img_reportfileshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Curight");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I am Curight!! You can view my report from this app :)");
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        holder.img_delete_parentreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);

                ab.setMessage("Are you sure, delete this patient report?");
                ab.setCancelable(false);
                ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     //   patientReportsData = patientReportsDatas.get(position);

                        showProgressDialog("Deleting item");
                        deletepatientreport(reportlist.get(position).getPatientreportid());
                        removeAt(position);
                    }
                });

                ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                ab.create();
                ab.show();


            }
        });


    }

    private void removeAt(int position) {
     //   patientReportsDatas.remove(position);
        reportlist.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, reportlist.size());
    }

    private void showProgressDialog(String title) {
        progressDialog = ProgressDialog.show(mContext, title, "please wait", true, false);
        progressDialog.show();
    }

    @Override
    public int getItemCount() {
        return reportlist.size();
    }

    private void deletepatientreport(int patientreportid){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        try{
            DeleteParameterpatientreport deleteParameterpatientreport =new DeleteParameterpatientreport(patientreportid);
            final Call<ResponseBody> call = apiInterface.deletepatientreportrecord("abc", deleteParameterpatientreport);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                         progressDialog.dismiss();
                        try{
                            String res_delete = response.body().string();

                            JSONObject jsonObject = new JSONObject(res_delete);
                            String results = jsonObject.getString("Results");

                            if(results.equals("Success")){
                                Toast.makeText(mContext, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                showProgressDialog("Loading");
                                progressDialog.dismiss();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }




    public interface OnItemClickListener {

        void onItemClick(Report_FEED item,int position);
        void onFileClick(Report_FEED item,int position);

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_day,txt_month,txt_diagonsticname,txt_reason,txt_doctorname,txt_doctornumber,txt_comments,txt_visitdate;
        ImageView img_calldoctor_fromreport,img_delete_parentreport,img_reportfiledownload,img_reportfileshare;

        public MyViewHolder(View itemView) {
            super(itemView);
            img_reportfiledownload = (ImageView)itemView.findViewById(R.id.img_reportfiledownload);
            txt_day = (TextView)itemView.findViewById(R.id.txt_day);
            txt_month = (TextView)itemView.findViewById(R.id.txt_month);
            txt_diagonsticname = (TextView)itemView.findViewById(R.id.txt_diagonsticname);
            txt_reason = (TextView)itemView.findViewById(R.id.txt_reason);
            txt_doctorname = (TextView)itemView.findViewById(R.id.txt_doctorname);
            //txt_doctornumber = (TextView)itemView.findViewById(R.id.txt_doctornumber);
            txt_comments = (TextView)itemView.findViewById(R.id.txt_comments);
            //txt_visitdate = (TextView)itemView.findViewById(R.id.txt_visitdate);
            img_calldoctor_fromreport = (ImageView)itemView.findViewById(R.id.img_calldoctor_fromreport);
            img_delete_parentreport =(ImageView)itemView.findViewById(R.id.img_delete_parentreport);

            img_reportfileshare =(ImageView)itemView.findViewById(R.id.img_reportfileshare);
        }
    }

    private class PhoneCallListener extends PhoneStateListener {

        String LOG_TAG = "LOGGING 123";
        private boolean isPhoneCalling = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = mContext.getPackageManager()
                            .getLaunchIntentForPackage(
                                    mContext.getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }


}
