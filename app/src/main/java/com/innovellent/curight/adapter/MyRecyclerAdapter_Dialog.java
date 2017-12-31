package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.Center;
import com.innovellent.curight.model.DoctorByDC;
import com.innovellent.curight.model.ServerResponseDoctorByDC;

import java.util.ArrayList;

/**
 * Created by SUNIL on 12/31/2017.
 */

public class MyRecyclerAdapter_Dialog extends RecyclerView.Adapter<MyRecyclerAdapter_Dialog.DialogViewHolder> {

    Context mContext;
    ArrayList<DoctorByDC> serverResponseDoctorByDCs = new ArrayList<DoctorByDC>();
    String doctorname,spec,email,normalworkingdays,weekendworkingdays,mobile,address;
    ServerResponseDoctorByDC serverResponseDoctorByDCdatas;
    public MyRecyclerAdapter_Dialog(Context context,ArrayList<DoctorByDC> serverResponseDoctorByDCs) {
        mContext = context;
        this.serverResponseDoctorByDCs=serverResponseDoctorByDCs;


    }
    @Override
    public DialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_doctorbydc, parent, false);
        return new MyRecyclerAdapter_Dialog.DialogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DialogViewHolder holder, int position) {


        holder.doctorname_doctorbydc.setText(serverResponseDoctorByDCs.get(position).getDoctorname());
        holder.specialization_doctorbydc.setText(serverResponseDoctorByDCs.get(position).getSpecialization());
        holder.tvemail_doctorbydc.setText("Email:"+" "+serverResponseDoctorByDCs.get(position).getEmail());
        holder.tvtime_doctorbydc.setText("Time:"+" "+serverResponseDoctorByDCs.get(position).getNormalworkingschedule()+" "+serverResponseDoctorByDCs.get(position).getWeekendworkingschedule());
        holder.tvmobile_doctorbydc.setText("Mobile:"+" "+serverResponseDoctorByDCs.get(position).getMobile());
        holder.tvaddress_doctorbydc.setText("Address:"+" "+serverResponseDoctorByDCs.get(position).getAddresscentre());
    }

    @Override
    public int getItemCount() {
        return serverResponseDoctorByDCs.size();
    }

    public class DialogViewHolder extends RecyclerView.ViewHolder {

        TextView doctorname_doctorbydc,specialization_doctorbydc,tvemail_doctorbydc,tvtime_doctorbydc,tvmobile_doctorbydc,tvaddress_doctorbydc;

        public DialogViewHolder(View itemView) {
            super(itemView);


            doctorname_doctorbydc = (TextView)itemView.findViewById(R.id.doctorname_doctorbydc);
            specialization_doctorbydc = (TextView)itemView.findViewById(R.id.specialization_doctorbydc);
            tvemail_doctorbydc = (TextView)itemView.findViewById(R.id.tvemail_doctorbydc);
            tvtime_doctorbydc = (TextView)itemView.findViewById(R.id.tvtime_doctorbydc);
            tvmobile_doctorbydc = (TextView)itemView.findViewById(R.id.tvmobile_doctorbydc);
            tvaddress_doctorbydc = (TextView)itemView.findViewById(R.id.tvaddress_doctorbydc);
        }
    }
}
