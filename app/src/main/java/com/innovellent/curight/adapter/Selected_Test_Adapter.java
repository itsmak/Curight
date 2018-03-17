package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.SelectedTest;
import com.innovellent.curight.model.Test_List;

import java.util.ArrayList;

/**
 * Created by Mak on 3/15/2018.
 */

public class Selected_Test_Adapter extends RecyclerView.Adapter<Selected_Test_Adapter.MyViewHolder>{

    private final OnTestClickListener listener;
    private final int position;
    private ArrayList<SelectedTest> arrayList = new ArrayList<>();
    private Context mContext;

    public Selected_Test_Adapter(Context context, ArrayList<SelectedTest> arrayList, int position, OnTestClickListener listener) {
        mContext = context;
        this.arrayList = arrayList;
        this.position = position;
        this.listener =listener;
    }

    @Override
    public Selected_Test_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_test_adapter, parent, false);
        return new Selected_Test_Adapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final Selected_Test_Adapter.MyViewHolder holder, final int position) {
        holder.id_index_gallery_item_text.setText(arrayList.get(position).getTestname());

        holder.iv_selected_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface OnTestClickListener {
        void closeclicked(Test_List item_m,int position);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_index_gallery_item_text;
        ImageView iv_selected_close;

        MyViewHolder(View view) {
            super(view);
            id_index_gallery_item_text = (TextView) view.findViewById(R.id.id_index_gallery_item_text);
            iv_selected_close = (ImageView) view.findViewById(R.id.iv_selected_close);
        }
    }

}
