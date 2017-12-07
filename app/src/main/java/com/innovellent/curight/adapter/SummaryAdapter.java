package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.innovellent.curight.R;

import java.util.ArrayList;

/**
 * Created by sagar on 9/6/2017.
 */

public class SummaryAdapter  extends RecyclerView.Adapter<SummaryAdapter.MyViewHolder> {

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> amountList = new ArrayList<>();
    private ArrayList<String> mTestsList = new ArrayList<>();
    private Context mContext;
    private String state;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvHealthDetails;
        TextView tvRupee;
        ImageView deleteBtn;

        MyViewHolder(View view) {
            super(view);
            tvHealthDetails = (TextView) view.findViewById(R.id.tvHealthDetails);
            tvRupee = (TextView) view.findViewById(R.id.tvRupee);
            deleteBtn = (ImageView) view.findViewById(R.id.ivClose);

        }
    }

    public SummaryAdapter(Context context,ArrayList<String> arrayList,ArrayList<String> amountList) {
        mContext = context;
        this.arrayList = arrayList;
        this.amountList = amountList;

    }


    @Override
    public SummaryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_summary_details, parent, false);
        return new SummaryAdapter.MyViewHolder(itemView);
    }

    public void swap(ArrayList<String> list,ArrayList<String> amountList){
        if (arrayList != null) {
            arrayList.clear();
            arrayList.addAll(list);
        }
        else {
            arrayList = list;
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final SummaryAdapter.MyViewHolder holder, final int position) {

        Log.e("onBindView", "Size :: "+arrayList.size() + " position :: "+position+ "  JJJ : " +holder.getAdapterPosition());

        holder.tvHealthDetails.setText(arrayList.get(position));
        holder.tvRupee.setText(amountList.get(position));

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("TAG","Rc view count :: "+arrayList.size());
        return arrayList.size();
    }


    private void removeAt(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
    }
}


