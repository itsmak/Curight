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
import java.util.List;

/**
 * Created by SUNIL on 12/21/2017.
 */

public class BloodCountListAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int DATE = 0;
    private final int RECORD = 1;
    private Context context;
    private ArrayList<BloodCount> arrayList = new ArrayList<>();

    public BloodCountListAdapter1(Context context,ArrayList<BloodCount> arrayList) {
        context = context;
        this.arrayList = arrayList;

    }

   /* @Override
    public int getItemViewType(int position) {
        return arrayList.get(position) instanceof String ? DATE : RECORD;
    }*/
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == 0) {
            return new BloodCountListAdapter1.StringViewHolder(inflater.inflate(R.layout.text_row, parent, false));
        }
        return new BloodCountViewHolder(inflater.inflate(R.layout.list_row_blood_count, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final BloodCount item = arrayList.get(position);

        /*if (holder instanceof BloodCountListAdapter1.StringViewHolder)
            ((BloodCountListAdapter1.StringViewHolder) holder).text.setText(((String) item));
        else{

        }*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private class BloodCountViewHolder extends RecyclerView.ViewHolder{

        TextView tvAntiCPP,tvCRP,tvESR,tvHaemoglobin,tvHbA1c,tvINR,tvPlatelet,tvProlactin,tvRBC,tvRF,tvWBC,txt_bloodcountdate;


        public BloodCountViewHolder(View view) {
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
        }
    }

    private class StringViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        StringViewHolder(View view) {
            super(view);
            text = (TextView) view;
        }
    }
}
