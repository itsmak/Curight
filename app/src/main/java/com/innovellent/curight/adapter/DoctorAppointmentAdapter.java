package com.innovellent.curight.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.DoctorList;
import com.innovellent.curight.model.Report_FEED;

import java.util.ArrayList;

/**
 * Created by SUNIL on 12/4/2017.
 */

public class DoctorAppointmentAdapter extends RecyclerView.Adapter<DoctorAppointmentAdapter.ViewHolder> {

    public static String sel_test_names = "";
    public static String sel_test_ids = "";
    private final int position;
    Context mContext;
    String doctornumber, specialization, doctorname;
    OnItemClickListener listener;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<DoctorList> doctorListArrayList;

    public DoctorAppointmentAdapter(Context context, ArrayList<String> arrayList, ArrayList<DoctorList> testObjs,int position,OnItemClickListener listener) {
        mContext = context;
        this.arrayList = arrayList;
        this.doctorListArrayList = testObjs;
        this.position = position;
        this.listener = listener;
    }

    @Override
    public DoctorAppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_doctorappointment, parent, false);
        itemView.setClickable(true);
        //itemView.setOnClickListener(this);
        return new DoctorAppointmentAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DoctorAppointmentAdapter.ViewHolder holder, final int position) {

        doctorname = doctorListArrayList.get(position).getDoctorname();
        specialization = doctorListArrayList.get(position).getSpecialization();
        doctornumber = doctorListArrayList.get(position).getMobile();
        Log.d("DoctorNo==", doctornumber);

        holder.tv_doctorname.setText(doctorname);
        holder.tv_specialization.setText(specialization);


        holder.img_calldoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPhoneClick(doctorListArrayList.get(position),position);
            }
        });
    }

    public void filterlist(ArrayList<DoctorList> filteredlist) {
        doctorListArrayList=filteredlist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return doctorListArrayList.size();
    }
    public interface OnItemClickListener {

        void onPhoneClick(DoctorList item, int position);

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_doctorname,tv_specialization;
        ImageView img_calldoctor;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_doctorname = (TextView) itemView.findViewById(R.id.tv_doctorname);
            tv_specialization = (TextView)itemView.findViewById(R.id.tv_specialization);
            img_calldoctor = (ImageView)itemView.findViewById(R.id.img_calldoctor);
        }
    }
}
