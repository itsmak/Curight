package com.innovellent.curight.adapter;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
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
    Context mContext;
    ArrayList<Report_FEED> reportlist = new ArrayList<Report_FEED>();
    ArrayList<PatientReportsData> patientReportsDatas =new ArrayList<PatientReportsData>();
    ArrayList<PatientReportsData> model;
    PatientReportsData patientReportsData;
    String doctorno;
    ProgressDialog progressDialog;

//    public YourReportsAdapter(Context context, ArrayList<PatientReportsData> patientReportsDatas) {
//        mContext = context;
//        this.patientReportsDatas = patientReportsDatas;
//        this.model = new ArrayList<PatientReportsData>();
//        this.model.addAll(patientReportsDatas);
//
//    }

    public YourReportsAdapter(Context context, ArrayList<Report_FEED> reportlist) {
        mContext = context;
        this.reportlist = reportlist;
    }

    @Override
    public YourReportsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.yourreports_item, parent, false);
        return new YourReportsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final YourReportsAdapter.MyViewHolder holder, final int position) {

      //  reportlist = reportlist.get(position);

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


        holder.img_calldoctor_fromreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + doctorno));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mContext.startActivity(intent);
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

    // Filter Class
//    public void filter(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        reportlist.clear();
//        if (charText.length() == 0) {
//            patientReportsDatas.addAll(model);
//        } else {
//            for (PatientReportsData wp : model) {
//                if (wp.getReason().toLowerCase(Locale.getDefault()).contains(charText)|| wp.getDiagnsticcentrename().toLowerCase(Locale.getDefault()).contains(charText)|| wp.getDoctorname().toLowerCase(Locale.getDefault()).contains(charText)) {
//                   patientReportsDatas.add(wp);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }

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

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_day,txt_month,txt_diagonsticname,txt_reason,txt_doctorname,txt_doctornumber,txt_comments,txt_visitdate;
        ImageView img_calldoctor_fromreport,img_delete_parentreport;

        public MyViewHolder(View itemView) {
            super(itemView);

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
        }
    }

}
