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
import com.innovellent.curight.model.SummaryDetails;

import java.util.ArrayList;

/**
 * Created by sagar on 9/6/2017.
 */

public class SummaryAdapter  extends RecyclerView.Adapter<SummaryAdapter.MyViewHolder> {

    Boolean flag;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> amountList = new ArrayList<>();
    private ArrayList<SummaryDetails> summaryList = new ArrayList<SummaryDetails>();
    private ArrayList<String> mTestsList = new ArrayList<>();
    private Context mContext;
    private String state;

    public SummaryAdapter(Context context,ArrayList<String> arrayList,ArrayList<String> amountList) {
        mContext = context;
        this.arrayList = arrayList;
        this.amountList = amountList;

    }

    public SummaryAdapter(Context context,ArrayList<SummaryDetails> summaryList,Boolean flag) {
        mContext = context;
        this.summaryList = summaryList;
        this.flag=flag;
     }

    @Override
    public SummaryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_summary_details, parent, false);
        return new SummaryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SummaryAdapter.MyViewHolder holder, final int position) {

        Log.e("onBindView", "Size :: "+arrayList.size() + " position :: "+position+ "  JJJ : " +holder.getAdapterPosition());
        if(flag)
        {
            if(summaryList.get(position).getHomepickup().equalsIgnoreCase("Y")){
                holder.ivHome.setImageResource(R.mipmap.ic_homegreen);
                holder.ivBasic.setImageResource(R.mipmap.ic_basic_gray);
            }else {
                holder.ivHome.setImageResource(R.mipmap.ic_home);
                holder.ivBasic.setImageResource(R.mipmap.ic_basicgreen);
            }


        }else {
            holder.ivHome.setImageResource(R.mipmap.ic_home);
            holder.ivBasic.setImageResource(R.mipmap.ic_basicgreen);
        }
        holder.tvHealthDetails.setText(summaryList.get(position).getName());
        holder.tvRupee.setText(summaryList.get(position).getAmount());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(position);
            }
        });
    }

   /* public void swap(ArrayList<String> list,ArrayList<String> amountList){
        if (arrayList != null) {
            arrayList.clear();
            arrayList.addAll(list);
        }
        else {
            arrayList = list;
        }
        notifyDataSetChanged();
    }*/

    @Override
    public int getItemCount() {
        Log.e("TAG","Rc view count :: "+arrayList.size());
        return summaryList.size();
    }

    private void removeAt(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvHealthDetails;
        TextView tvRupee;
        ImageView deleteBtn,ivHome,ivBasic;

        MyViewHolder(View view) {
            super(view);
            tvHealthDetails = (TextView) view.findViewById(R.id.tvHealthDetails);
            tvRupee = (TextView) view.findViewById(R.id.tvRupee);
            deleteBtn = (ImageView) view.findViewById(R.id.ivClose);
            ivHome = (ImageView) view.findViewById(R.id.ivHome);
            ivBasic = (ImageView) view.findViewById(R.id.ivBasic);
        }
    }
}


