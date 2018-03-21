package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.innovellent.curight.R;
import com.innovellent.curight.model.BloodCount;


import java.util.ArrayList;


public class BloodCountListAdapter extends RecyclerView.Adapter<BloodCountListAdapter.MyViewHolder> {

     Context mContext;
    BloodCount bloodCount;
    private ArrayList<BloodCount> arrayList = new ArrayList<>();
    private String state;



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


        if(arrayList.size()>0) {

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
            holder.txt_bloodcountdate.setText(arrayList.get(position).getDate());
        }else{
            holder.tvAntiCPP.setText("");
            holder.tvCRP.setText("");
            holder.tvESR.setText("");
            holder.tvHaemoglobin.setText("");
            holder.tvHbA1c.setText("");
            holder.tvINR.setText("");
            holder.tvPlatelet.setText("");
            holder.tvProlactin.setText("");
            holder.tvRBC.setText("");
            holder.tvRF.setText("");
            holder.tvWBC.setText("");
            holder.txt_bloodcountdate.setText("");
        }

        holder.img_deleteitemforbloodcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, arrayList.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvAntiCPP,tvCRP,tvESR,tvHaemoglobin,tvHbA1c,tvINR,tvPlatelet,tvProlactin,tvRBC,tvRF,tvWBC,txt_bloodcountdate;
        ImageView img_deleteitemforbloodcount;
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
            txt_bloodcountdate = (TextView)view.findViewById(R.id.txt_bloodcountdate);
            img_deleteitemforbloodcount = (ImageView)view.findViewById(R.id.img_deleteitemforbloodcount);
        }
    }
}
