package com.innovellent.curight.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.innovellent.curight.R;
import com.innovellent.curight.fragment.AddRemainder_FRAGMENT_DAILOG;
import com.innovellent.curight.model.Vaccine;
import com.innovellent.curight.model.VaccineReminderYearDialog;

import java.util.ArrayList;

/**
 * Created by sagar on 9/13/2017.
 */

public class VaccineAdapter  extends RecyclerView.Adapter<VaccineAdapter.MyViewHolder> {

    private ArrayList<Vaccine> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;
    VaccineReminderYearDialog vaccineReminderYearDialog;
    AddRemainder_FRAGMENT_DAILOG vaccineadddailog;
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvVaccine;
        TextView tvDate,duration_txtvw;
        ImageView ivTaken,add_medcn_imgvw;
        LinearLayout   llVaccince,duration_layout;
        MyViewHolder(View view) {
            super(view);
            duration_layout = (LinearLayout) view.findViewById(R.id.duration_layout);
            tvVaccine = (TextView) view.findViewById(R.id.tvVaccine);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            duration_txtvw = (TextView) view.findViewById(R.id.duration_txtvw);
            llVaccince=(LinearLayout)view.findViewById(R.id.llVaccince);
            ivTaken=(ImageView)view.findViewById(R.id.ivTaken);
            add_medcn_imgvw =(ImageView)view.findViewById(R.id.add_medcn_imgvw);

        }
    }

    public VaccineAdapter(Context context,ArrayList<Vaccine> arrayList) {
        mContext = context;
        this.arrayList = arrayList;

    }

    @Override
    public VaccineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_vaccine, parent, false);
        return new VaccineAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VaccineAdapter.MyViewHolder holder, final int position) {

        if(arrayList.get(position).isVisiblity())
        {
            holder.duration_layout.setVisibility(View.VISIBLE);
            holder.llVaccince.setVisibility(View.GONE);
            Log.d("adapter age", String.valueOf(arrayList.get(position).getDuration_age()));
            holder.duration_txtvw.setText(arrayList.get(position).getDuration_age());
        }else{
            holder.duration_layout.setVisibility(View.GONE);
            holder.llVaccince.setVisibility(View.VISIBLE);
            if(position%2==0){
                holder.llVaccince.setBackgroundColor(Color.parseColor("#D9DADD"));

            }else{
            }

            holder.tvVaccine.setText(arrayList.get(position).getVaccinename());
            holder.tvDate.setText(arrayList.get(position).getDuedate());
            Log.d("duedate", String.valueOf(arrayList.get(position).getDate()));
          if(arrayList.get(position).getDate().equals("")){
            holder.ivTaken.setImageResource(R.drawable.multiply);
            }else {
                holder.ivTaken.setImageResource(R.mipmap.ic_right);
            }
        }

        Log.d("adapter visiblity", String.valueOf(arrayList.get(position).isVisiblity()));
        Log.d("adapter doctor", String.valueOf(arrayList.get(position).getDoctorname()));
        Log.d("adapter details", String.valueOf(arrayList.get(position).getComments()));
        if(position%2==0){
          holder.llVaccince.setBackgroundColor(Color.parseColor("#D9DADD"));

        }else{
            holder.llVaccince.setBackgroundColor(Color.parseColor("#ffffff"));
        }
      holder.add_medcn_imgvw.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              addVaccine();
          }
      });
      holder.llVaccince.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               modifyVaccine(String.valueOf(arrayList.get(position).getVaccinename()),
                       String.valueOf(arrayList.get(position).getAgeinonth()),
                       String.valueOf(arrayList.get(position).getDuedate()),
                       String.valueOf(arrayList.get(position).getDoctorname()),
                       String.valueOf(arrayList.get(position).getComments()));
           }
       });
      //  holder.ivTaken.setImageResource(R.mipmap.ic_right);
    }

    private void addVaccine() {
        vaccineadddailog = new AddRemainder_FRAGMENT_DAILOG(mContext, new AddRemainder_FRAGMENT_DAILOG.AddRemainder_FRAGMENT_DAILOGClickListener(){


            @Override
            public void onSubmit() {
                vaccineadddailog.dismiss();
            }
        });

        vaccineadddailog.show();
    }
    private void modifyVaccine(String vaccine_name,String ageinmonth,String DueDate,String doctorname,String details) {
        vaccineReminderYearDialog = new VaccineReminderYearDialog(mContext, vaccine_name,ageinmonth,DueDate,doctorname,details,new VaccineReminderYearDialog.VaccineReminderYearDialogClickListener(){

            @Override
            public void onSubmit() {
                vaccineReminderYearDialog.dismiss();
            }
        });

        vaccineReminderYearDialog.show();


    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
