package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.Medicine;

import java.util.ArrayList;

public class MedicineAdpater extends RecyclerView.Adapter<MedicineAdpater.MyViewHolder> {

    private ArrayList<Medicine> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;
    private int rowlayout;
    private LinearLayout llAmount;


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTestName,tvHour,tvDistance;
        LinearLayout llLayout;

        MyViewHolder(View view) {
            super(view);
            tvTestName = (TextView) view.findViewById(R.id.tvTestName);
            tvHour = (TextView) view.findViewById(R.id.tvHour);
            tvDistance=(TextView) view.findViewById(R.id.tvDistance);
            llLayout=(LinearLayout)view.findViewById(R.id.llLayout);
        }
    }

    public MedicineAdpater(Context context,int  rowlayout,ArrayList<Medicine> arrayList) {
        mContext = context;
        this.arrayList = arrayList;
        this.rowlayout=rowlayout;

    }

    @Override
    public MedicineAdpater.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowlayout, parent, false);
        return new MedicineAdpater.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MedicineAdpater.MyViewHolder holder, final int position) {

        holder.tvTestName.setText(arrayList.get(position).getMedicinename());
        holder.tvHour.setText(arrayList.get(position).getMedicinemeasure());

        if((position%2)==0){

        }else{
            holder.llLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.graylight));
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

