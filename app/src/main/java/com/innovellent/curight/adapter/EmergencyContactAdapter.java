package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.innovellent.curight.R;
import com.innovellent.curight.model.BloodPressure;

import java.util.ArrayList;

/**
 * Created by sagar on 9/12/2017.
 */

public class EmergencyContactAdapter extends RecyclerView.Adapter<EmergencyContactAdapter.MyViewHolder> {

    private ArrayList<BloodPressure> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvConsumedNumber;

        MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvConsumedNumber = (TextView) view.findViewById(R.id.tvConsumedNumber);

        }
    }

    public EmergencyContactAdapter(Context context,ArrayList<BloodPressure> arrayList) {
        mContext = context;
        this.arrayList = arrayList;

    }

    @Override
    public EmergencyContactAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_diagnostic_test, parent, false);
        return new EmergencyContactAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EmergencyContactAdapter.MyViewHolder holder, final int position) {

        holder.tvName.setText(arrayList.get(position).getSystolic().toString());
        holder.tvConsumedNumber.setText(arrayList.get(position).getPulse().toString());



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
