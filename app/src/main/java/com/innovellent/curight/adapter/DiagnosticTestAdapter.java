package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import  android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.model.Test;
import com.innovellent.curight.model.Test_List;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class DiagnosticTestAdapter extends RecyclerView.Adapter<DiagnosticTestAdapter.MyViewHolder> {

    public static String sel_test_names = "";
    public static String sel_test_ids = "";
    String test_id_text="";
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<Test_List> t_arrayList = new ArrayList<>();
    //private ArrayList<String> testIdList = new ArrayList<>();
    private Context mContext;
    private ArrayList<Test> testObjs;
    //private onRecyclerViewItemClickListener mItemClickListener;

    public DiagnosticTestAdapter(Context context,ArrayList<Test_List> arrayList) {
        mContext = context;
        this.t_arrayList = arrayList;

    }

   public DiagnosticTestAdapter(Context context,ArrayList<Test_List> arrayList,ArrayList<Test> testObjs) {
       mContext = context;
       this.t_arrayList = arrayList;
       this.testObjs = testObjs;
    }

    @Override
    public DiagnosticTestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_diagnostic_test, parent, false);
        itemView.setClickable(true);
        //itemView.setOnClickListener(this);
        return new DiagnosticTestAdapter.MyViewHolder(itemView);
    }

//    private void addItem(String test_name,Test test) {
//        this.arrayList.add(test_name);
//        this.testObjs.add(test);
//        notifyDataSetChanged();
//    }

    @Override
    public void onBindViewHolder(final DiagnosticTestAdapter.MyViewHolder holder, final int position) {

        holder.tvtestname.setText(t_arrayList.get(position).getTestname());
        holder.tvdescription.setText(t_arrayList.get(position).getDescription());

        Log.d(TAG, "gettest:  desc "+ holder.tvdescription.getText().toString());
        Log.d(TAG, "gettest:  name "+ holder.tvtestname.getText().toString());
        /*String testChoosenFlag = testObjs.get(position).getTestcode();
        if ("Y".equals(testChoosenFlag)) {
            holder.testCheckBox.setChecked(true);
        }*/
        holder.testCheckBox.setClickable(false);
        holder.rl_diagnostictest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.testCheckBox.isChecked()) {
                    holder.testCheckBox.setChecked(false);
                    test_id_text=Prefs.getString("test_id","");
                    if(test_id_text.length()>0)
                    {
                    String subtext="";
                    for(int i=0;i<test_id_text.length();i++)
                    {
                        if(String.valueOf(test_id_text.charAt(i)).equals(String.valueOf(t_arrayList.get(position).getTestid())))
                        {

                        }else {
                            subtext = subtext + String.valueOf(test_id_text.charAt(i));
                        }
                    }
                    test_id_text = subtext;
                    Prefs.putString("test_id",subtext);
                    }
                } else {
                    holder.testCheckBox.setChecked(true);
                    //Toast.makeText(mContext.getApplicationContext(),"checked id="+t_arrayList.get(position).getTestid(),Toast.LENGTH_SHORT).show();
                    test_id_text=Prefs.getString("test_id","");
                    if(test_id_text.length()>0)
                    {

                        test_id_text=test_id_text+t_arrayList.get(position).getTestid();
                        Prefs.putString("test_id",test_id_text);
                    }else {

                        test_id_text=test_id_text+t_arrayList.get(position).getTestid();
                        Prefs.putString("test_id",test_id_text);
                    }

                }

            }
        });

        holder.tvtestname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.testCheckBox.isChecked()) {
                    holder.testCheckBox.setChecked(false);
                    test_id_text=Prefs.getString("test_id","");
                    if(test_id_text.length()>0)
                    {
                        String subtext="";
                        for(int i=0;i<test_id_text.length();i++)
                        {
                            if(String.valueOf(test_id_text.charAt(i)).equals(String.valueOf(t_arrayList.get(position).getTestid())))
                            {

                            }else {
                                subtext = subtext + String.valueOf(test_id_text.charAt(i));
                            }
                        }
                        test_id_text = subtext;
                        Prefs.putString("test_id",subtext);
                    }
                } else {
                    holder.testCheckBox.setChecked(true);
                   // Toast.makeText(mContext.getApplicationContext(),"checked id="+t_arrayList.get(position).getTestid(),Toast.LENGTH_SHORT).show();
                    test_id_text=Prefs.getString("test_id","");
                    test_id_text=test_id_text+t_arrayList.get(position).getTestid();
                    Prefs.putString("test_id",test_id_text);
                }
            }
        });
//        holder.testCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (holder.testCheckBox.isChecked()) {
//                    holder.testCheckBox.setChecked(false);
//                    test_id_text=Prefs.getString("test_id","");
//                    if(test_id_text.length()>0)
//                    {
//                        String subtext="";
//                        for(int i=0;i<test_id_text.length();i++)
//                        {
//                            if(String.valueOf(test_id_text.charAt(i)).equals(String.valueOf(t_arrayList.get(position).getTestid())))
//                            {
//
//                            }else {
//                                subtext = subtext + String.valueOf(test_id_text.charAt(i));
//                            }
//                        }
//                        test_id_text = subtext;
//                        Prefs.putString("test_id",subtext);
//                    }
//                } else {
//                    holder.testCheckBox.setChecked(true);
//                    Toast.makeText(mContext.getApplicationContext(),"checked id="+t_arrayList.get(position).getTestid(),Toast.LENGTH_SHORT).show();
//                    test_id_text=Prefs.getString("test_id","");
//                    test_id_text=test_id_text+t_arrayList.get(position).getTestid();
//                    Prefs.putString("test_id",test_id_text);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return t_arrayList.size();
    }

    public void filterlist(ArrayList<Test_List> filteredlist) {
        t_arrayList=filteredlist;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvtestname,tvdescription;
        CheckBox testCheckBox;
        //CardView cardView;
        RelativeLayout rl_diagnostictest;
        MyViewHolder(View view) {
            super(view);
            tvtestname = (TextView) view.findViewById(R.id.tvtestname);
            tvdescription = (TextView) view.findViewById(R.id.tvdescription);
            testCheckBox = (CheckBox) view.findViewById(R.id.cbDiagnosticTest);
            rl_diagnostictest = (RelativeLayout) view.findViewById(R.id.rl_addanothertest);
      //      cardView = (CardView) view.findViewById(R.id.card_view2);
            //cardView.setOnClickListener(this);
            testCheckBox.setClickable(true);
            //testCheckBox.setOnClickListener(this);

        }

//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.card_view2:
//                    if (testCheckBox.isChecked()) {
//                        testCheckBox.setChecked(false);
//                    } else {
//                        testCheckBox.setChecked(true);
//                        //*onBindViewHolder();
//                        sel_test_ids = sel_test_ids +testObjs.get();
//                    }
//                    break;
//
//                case R.id.tvCity:
//                    if (testCheckBox.isChecked()) {
//                        testCheckBox.setChecked(false);
//                    } else {
//                        testCheckBox.setChecked(true);
//                        //sel_test_ids = sel_test_ids +testObjs.get(position).getTestid()+",";
//                    }
//                    break;
//            }
//
//      }
    }
}
