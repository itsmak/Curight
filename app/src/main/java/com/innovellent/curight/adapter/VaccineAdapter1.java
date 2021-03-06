package com.innovellent.curight.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.innovellent.curight.R;
import com.innovellent.curight.model.Vaccine;
import com.innovellent.curight.model.VaccineReminderYearDialog;

import java.util.ArrayList;

/**
 * Created by sagar on 9/13/2017.
 */

public class VaccineAdapter1 extends RecyclerView.Adapter<VaccineAdapter1.MyViewHolder> {

    private ArrayList<Vaccine> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;
    VaccineReminderYearDialog vaccineReminderYearDialog;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvVaccine;
        TextView tvDate;
        ImageView ivTaken;
         LinearLayout llVaccince;

        MyViewHolder(View view) {
            super(view);
            tvVaccine = (TextView) view.findViewById(R.id.tvVaccine);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            llVaccince=(LinearLayout)view.findViewById(R.id.llVaccince);
             ivTaken=(ImageView)view.findViewById(R.id.ivTaken);
        }
    }

    public VaccineAdapter1(Context context,ArrayList<Vaccine> arrayList) {
        mContext = context;
        this.arrayList = arrayList;

    }

    @Override
    public VaccineAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_vaccine, parent, false);
        return new VaccineAdapter1.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VaccineAdapter1.MyViewHolder holder, final int position) {

        holder.tvVaccine.setText(arrayList.get(position).getVaccinename());
        holder.tvDate.setText(arrayList.get(position).getDate());
        if(position%2==0){
            holder.llVaccince.setBackgroundColor(Color.parseColor("#D9DADD"));

        }else{
        }

        holder.llVaccince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    addVaccine(arrayList.get(position).getDate(),arrayList.get(position).getDate(),arrayList.get(position).getDate(),
                     //   arrayList.get(position).getDate(),arrayList.get(position).getComments(),1,1,1,
                      //  arrayList.get(position).getDate());
            }
        });
        holder.ivTaken.setImageResource(R.mipmap.ic_right);


    }
//    private void addVaccine(String a,String b,String c,String d,String e,int f,int g,int h,String i) {
//        vaccineReminderYearDialog = new VaccineReminderYearDialog(mContext,a,b,c,d,f,g,h,i, new VaccineReminderYearDialog.VaccineReminderYearDialogClickListener(){
//
//
//            @Override
//            public void onSubmit() {
//                vaccineReminderYearDialog.dismiss();
//            }
//        });
//
//        vaccineReminderYearDialog.show();
//
//
//    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

