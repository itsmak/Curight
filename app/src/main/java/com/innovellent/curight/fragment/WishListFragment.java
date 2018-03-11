package com.innovellent.curight.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.MyOfferingAdapter;
import com.innovellent.curight.adapter.TrackAdapter;
import com.innovellent.curight.adapter.WishList_Adapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.Article_FEED;
import com.innovellent.curight.model.Post_Body_Article;
import com.innovellent.curight.model.ServerResponseOffer;
import com.innovellent.curight.model.WishList_Model;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sagar on 10/17/2017.
 */

public class WishListFragment extends Fragment {
    private static final String TAG = ".Curight";
    RecyclerView recycler_view;
    TextView tv_locationtxt,tv_locationsymbl,tvTitle;
    WishList_Adapter wish_adapter;
    ArrayList<Article_FEED> arrayList=new ArrayList<Article_FEED>();
    ArrayList<WishList_Model> wish_arrayList = new ArrayList<WishList_Model>();
    MyOfferingAdapter myOfferAdapter;
    TrackAdapter mAdapter;
    Context context;
    private ProgressDialog progressDialog;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_wishlist,container,false);
        recycler_view = (RecyclerView)  rootView.findViewById(R.id.recycler_view);
        tv_locationtxt = (TextView) getActivity().findViewById(R.id.tv_locationtxt);
        tv_locationsymbl = (TextView) getActivity().findViewById(R.id.tv_locationsymbl);
        tvTitle = (TextView) getActivity().findViewById(R.id.tvTitle);
        tvTitle.setText("Article");
        tv_locationtxt.setVisibility(View.GONE);
        tv_locationsymbl.setVisibility(View.GONE);
        getOffer();
        return rootView;
    }

    private void getOffer() {
        progressDialog = ProgressDialog.show(context, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        clearData();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        int uid = (int) Prefs.getLong("user_id",0);
        Log.d(TAG,"Shared_profile_uid"+uid);
        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        Post_Body_Article post_body_article = new Post_Body_Article(1,"all");
        Call<ServerResponseOffer> call = reditapi.getArticle(post_body_article);
        call.enqueue(new Callback<ServerResponseOffer>() {
            @Override
            public void onResponse(Call<ServerResponseOffer> call, Response<ServerResponseOffer> response) {
                progressDialog.dismiss();
                Log.d(TAG,"Article response :"+response);
                Log.d(TAG,"Article response body :"+response.body());
                ServerResponseOffer  serverResponseOffer = response.body();
                int code = serverResponseOffer.getCode();
                if (code==200) {
                    Log.d(TAG,"Article response cd:"+response.body().getCode());
                    arrayList = serverResponseOffer.getResults();
                    Log.d(TAG,"Article size :"+arrayList.size());
                    for(int i = 0;i<arrayList.size();i++)
                    {
                        if (arrayList.get(i).getArticlewishlistid()>0)
                        {
                            wish_arrayList.add(new WishList_Model(arrayList.get(i).getArticleid(),arrayList.get(i).getTitle(),arrayList.get(i).getDescription(),arrayList.get(i).getImageurl(),arrayList.get(i).getCategory(),arrayList.get(i).getArticlewishlistid(),arrayList.get(i).getModifiedby()));
                        }

                    }

                    wish_adapter = new WishList_Adapter(getActivity(),wish_arrayList,"Y",WishListFragment.this);
                    recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_view.setAdapter(wish_adapter);
                }
            }

            @Override
            public void onFailure(Call<ServerResponseOffer> call, Throwable t) {
                t.getMessage();
                String s = t.getMessage();
                progressDialog.dismiss();
            }
        });
    }

    public void init(View rootview){
        recycler_view=(RecyclerView)rootview.findViewById(R.id.recycler_view);
    }

    public void clearData() {
        wish_adapter = new WishList_Adapter(context, wish_arrayList,"Y",WishListFragment.this);
        wish_arrayList.clear(); //clear list
        wish_adapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.

    }


}
