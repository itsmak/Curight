package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.innovellent.curight.R;
import com.innovellent.curight.model.BloodCount;


import java.util.ArrayList;


public class BloodCountListAdapter extends RecyclerView.Adapter<BloodCountListAdapter.MyViewHolder> {

    private ArrayList<BloodCount> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;

    class MyViewHolder extends RecyclerView.ViewHolder {


       TextView tvAntiCPP,tvCRP,tvESR,tvHaemoglobin,tvHbA1c,tvINR,tvPlatelet,tvProlactin,tvRBC,tvRF,tvWBC;

        MyViewHolder(View view) {
            super(view);
            tvAntiCPP=(TextView) view.findViewById(R.id.tvAntiCPP);
            tvCRP=(TextView)view.findViewById(R.id.tvCRP);
            tvESR=(TextView)view.findViewById(R.id.tvESR);
            tvHaemoglobin=(TextView)view.findViewById(R.id.tvHaemoglobin);
            tvHbA1c=(TextView)view.findViewById(R.id.tvHbA1c);
            tvINR=(TextView)view.findViewById(R.id.tvINR);
            tvPlatelet=(TextView)view.findViewById(R.id.tvPlatelet);
            tvProlactin=(TextView)view.findViewById(R.id.tvProlactin);
            tvRBC=(TextView)view.findViewById(R.id.tvRBC);
            tvRF=(TextView)view.findViewById(R.id.tvRF);
            tvWBC=(TextView)view.findViewById(R.id.tvWBC);

        }
    }

    public BloodCountListAdapter(Context context,ArrayList<BloodCount> arrayList) {
        mContext = context;
        this.arrayList = arrayList;

    }

    @Override
    public BloodCountListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_blood_count, parent, false);
        return new BloodCountListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BloodCountListAdapter.MyViewHolder holder, final int position) {
        holder.tvAntiCPP.setText(arrayList.get(position).getAntiCPP());
        holder.tvCRP.setText(arrayList.get(position).getCRP());
        holder.tvESR.setText(arrayList.get(position).getESR());
        holder.tvHaemoglobin.setText(arrayList.get(position).getHaemoglobin());
        holder.tvHbA1c.setText(arrayList.get(position).getHbA1c());
        holder.tvINR.setText(arrayList.get(position).getINR());
        holder.tvPlatelet.setText(arrayList.get(position).getPlatelet());
        holder.tvProlactin.setText(arrayList.get(position).getProlactin());
        holder.tvRBC.setText(arrayList.get(position).getRBC());
        holder.tvRF.setText(arrayList.get(position).getRF());
        holder.tvWBC.setText(arrayList.get(position).getWBC());



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
