package com.innovellent.curight.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.innovellent.curight.R;
import com.innovellent.curight.model.PaymentDetails;

import java.util.ArrayList;

/**
 * Created by sagar on 9/8/2017.
 */

public class PaymentDetailsAdapter extends RecyclerView.Adapter<PaymentDetailsAdapter.MyViewHolder> {

    private ArrayList<PaymentDetails> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTestName,tvAmount;
        LinearLayout llAmount;

        MyViewHolder(View view) {
            super(view);
            tvTestName = (TextView) view.findViewById(R.id.tvTestName);
            tvAmount = (TextView) view.findViewById(R.id.tvAmount);
            llAmount=(LinearLayout) view.findViewById(R.id.llAmount);
        }
    }

    public PaymentDetailsAdapter(Context context,ArrayList<PaymentDetails> arrayList) {
        mContext = context;
        this.arrayList = arrayList;

    }

    @Override
    public PaymentDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_payment_details, parent, false);
        return new PaymentDetailsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PaymentDetailsAdapter.MyViewHolder holder, final int position) {

        holder.tvTestName.setText(arrayList.get(position).getTestname());
        holder.tvAmount.setText(arrayList.get(position).getAmount());
        if((position%2)==0){
         holder.llAmount.setBackgroundColor(Color.parseColor("#EAEDED"));
        }else{

        }



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

