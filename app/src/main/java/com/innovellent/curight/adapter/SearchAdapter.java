package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.Search;

import java.util.ArrayList;

/**
 * Created by SUNIL on 12/5/2017.
 */

public class SearchAdapter extends  RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    Context mContext;
    String doctorname,categoryname,id,DOCTORNAME,categoryid;
    String tvcategory,name;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<Search> searchListArrayList;
    public SearchAdapter(Context context, ArrayList<String> arrayList, ArrayList<Search> testObjs, String tvcategory) {
        mContext = context;
        this.arrayList = arrayList;
        this.searchListArrayList = testObjs;
        this.tvcategory = tvcategory;
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search_row, parent, false);
        itemView.setClickable(true);
        //itemView.setOnClickListener(this);
        return new SearchAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
         doctorname = searchListArrayList.get(position).getName();
         categoryname = searchListArrayList.get(position).getCategory();
         id = searchListArrayList.get(position).getId();

         if (categoryname.equals("DR"))
         {
             DOCTORNAME = "Doctor";
         }
         if(categoryname.equals("DC")){
             DOCTORNAME = "DiagnosticCentre";
         }
        if(categoryname.equals("TE")){
            DOCTORNAME = "Test";
        }

        holder.tv_doctorname.setText(doctorname);
        holder.tv_categoryname.setText(DOCTORNAME);




    }


    public String getcatgory(int pos){
        categoryname = searchListArrayList.get(pos).getCategory();
        return categoryname;
    }
    public String getsearchedid(int pos){
        categoryid = searchListArrayList.get(pos).getId();
        return categoryid;
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_doctorname,tv_categoryname;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_doctorname = (TextView) itemView.findViewById(R.id.tv_searchdoctorname);
            tv_categoryname = (TextView)itemView.findViewById(R.id.tv_categoryname);
        }



    }
}
