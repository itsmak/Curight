package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.CategoryResult;
import com.innovellent.curight.model.Category_Feed;
import com.innovellent.curight.model.FoodItemDetails;

import java.util.ArrayList;

/**
 * Created by Mak on 5/17/2018.
 */

public class FooditemDialogAdapter extends RecyclerView.Adapter<FooditemDialogAdapter.MyViewHolder>{

    ArrayList<FoodItemDetails> sortitem = new ArrayList<>();
    int position;
    FooditemDialogAdapterClickListener listener;
    private Context mContext;

    public FooditemDialogAdapter(Context mContext, ArrayList<FoodItemDetails> sortitem, int position, FooditemDialogAdapterClickListener listener) {

        this.listener = listener;
        this.sortitem = sortitem;
        this.mContext = mContext;
        this.position = position;
    }

    @Override
    public FooditemDialogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sortby_adapter, parent, false);
        itemView.setClickable(true);
        return new FooditemDialogAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FooditemDialogAdapter.MyViewHolder holder, final int position) {

        holder.textView_sortby.setText(sortitem.get(position).getFoodName());

        holder.rl_sortitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onfoodclick(sortitem.get(position),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sortitem.size();
    }
    public interface FooditemDialogAdapterClickListener {
        void onfoodclick(FoodItemDetails item_f, int position);

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_sortby;

        RelativeLayout rl_sortitem;
        MyViewHolder(View view) {
            super(view);
            textView_sortby = (TextView) view.findViewById(R.id.textView_sortby);
            rl_sortitem = (RelativeLayout) view.findViewById(R.id.rl_sortitem);
            //  iv_toolbar_close = (ImageView) view.findViewById(R.id.iv_toolbar_close);
        }
    }
}

