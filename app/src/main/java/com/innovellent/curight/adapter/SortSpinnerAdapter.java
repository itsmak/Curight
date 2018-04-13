package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.SortBy_Item;

import java.util.ArrayList;

/**
 * Created by Mak on 4/13/2018.
 */

public class SortSpinnerAdapter extends RecyclerView.Adapter<SortSpinnerAdapter.MyViewHolder>{


    ArrayList<SortBy_Item> sortitem = new ArrayList<>();
    int position;
    OnSpinnerClickListener listener;
    private Context mContext;

    public SortSpinnerAdapter(Context mContext,ArrayList<SortBy_Item> sortitem, int position,SortSpinnerAdapter.OnSpinnerClickListener listener) {

        this.listener = listener;
        this.sortitem = sortitem;
        this.mContext = mContext;
        this.position = position;
    }

    @Override
    public SortSpinnerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sortby_adapter, parent, false);
        itemView.setClickable(true);
        return new SortSpinnerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SortSpinnerAdapter.MyViewHolder holder, final int position) {

        holder.textView_sortby.setText(sortitem.get(position).getSortBy());
        holder.rl_sortitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onspinneritemselect(sortitem.get(position),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sortitem.size();
    }
    public interface OnSpinnerClickListener {
        void onspinneritemselect(SortBy_Item item_f, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_sortby;
        RelativeLayout rl_sortitem;
        MyViewHolder(View view) {
            super(view);
            textView_sortby = (TextView) view.findViewById(R.id.textView_sortby);
            rl_sortitem = (RelativeLayout) view.findViewById(R.id.rl_sortitem);
        }
    }
}
