package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.TEST_DETAILS;
import com.innovellent.curight.model.TestDetail;
import com.innovellent.curight.model.Test_List;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

/**
 * Created by Mak on 12/30/2017.
 */

public class Add_Diagnostictest_Adapter extends RecyclerView.Adapter<Add_Diagnostictest_Adapter.MyViewHolder>{

  //  RelativeLayout rl_addanthrtest;
    String test_id_text="",test_name_text="",test_amount_text="";
    private ArrayList<TEST_DETAILS> t_arrayList = new ArrayList<>();
    private Context mContext;
    public Add_Diagnostictest_Adapter(Context context, ArrayList<TEST_DETAILS> arrayList) {
        mContext = context;
        this.t_arrayList = arrayList;

    }

    @Override
    public Add_Diagnostictest_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_diagnostic_test, parent, false);
        itemView.setClickable(true);
        //itemView.setOnClickListener(this);
        return new Add_Diagnostictest_Adapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final Add_Diagnostictest_Adapter.MyViewHolder holder, final int position) {

        holder.tvtestname.setText(t_arrayList.get(position).getTestName());
        if(t_arrayList.get(position).getTestchoosen().equalsIgnoreCase("Y"))
        {

            holder.testCheckBox.setChecked(true);
            t_arrayList.get(position).setChecked(true);

        }else {
            holder.testCheckBox.setChecked(false);
        }
        holder.testCheckBox.setClickable(false);
        holder.rl_addanothertest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.testCheckBox.isChecked())
                {
                    t_arrayList.get(position).setChecked(false);
                    holder.testCheckBox.setChecked(false);
                }else {
                    t_arrayList.get(position).setChecked(true);
                    holder.testCheckBox.setChecked(true);
                }

            }
        });
        //holder.tvdescription.setText(t_arrayList.get(position).getDescription());
    }
    @Override
    public int getItemCount() {
        return t_arrayList.size();
    }

    public void filterlist(ArrayList<TEST_DETAILS> filteredlist) {
        t_arrayList=filteredlist;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvtestname, tvdescription;
        CheckBox testCheckBox;
        //CardView cardView;
        RelativeLayout rl_addanothertest;

        MyViewHolder(View view) {
            super(view);
            tvtestname = (TextView) view.findViewById(R.id.tvtestname);
            tvdescription = (TextView) view.findViewById(R.id.tvdescription);
            testCheckBox = (CheckBox) view.findViewById(R.id.cbDiagnosticTest);
            rl_addanothertest = (RelativeLayout) view.findViewById(R.id.rl_addanothertest);
            //      cardView = (CardView) view.findViewById(R.id.card_view2);
            //cardView.setOnClickListener(this);
            testCheckBox.setClickable(false);

            //testCheckBox.setOnClickListener(this);

        }
    }
}
