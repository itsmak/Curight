package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.innovellent.curight.R;

import java.util.ArrayList;

/**
 * Created by sagar on 9/15/2017.
 */

public class ArticleHealthAdapter extends RecyclerView.Adapter<ArticleHealthAdapter.MyViewHolder> {

    private ArrayList<String> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCity;

        MyViewHolder(View view) {
            super(view);
            tvCity = (TextView) view.findViewById(R.id.tvCity);

        }
    }

    public ArticleHealthAdapter(Context context,ArrayList<String> arrayList) {
        mContext = context;
        this.arrayList = arrayList;

    }

    @Override
    public ArticleHealthAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_diagnostic_test, parent, false);
        return new ArticleHealthAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ArticleHealthAdapter.MyViewHolder holder, final int position) {

        holder.tvCity.setText(arrayList.get(position).toString());



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
