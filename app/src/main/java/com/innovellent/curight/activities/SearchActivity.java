package com.innovellent.curight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.SearchAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.fragment.HomeFragment;
import com.innovellent.curight.model.Search;
import com.innovellent.curight.model.SearchingCenter;
import com.innovellent.curight.model.ServerResponseTest;
import com.innovellent.curight.model.ServerSearchPage;
import com.innovellent.curight.model.Test;
import com.innovellent.curight.model.TestingCenter;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.DividerItemDecoration;
import com.innovellent.curight.utility.RecyclerItemClickListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SUNIL on 12/5/2017.
 */

public class SearchActivity extends AppCompatActivity {

    RecyclerView recycler_view_search;
    ServerSearchPage diagCenterByTest;
    Search search;
    SearchingCenter searchingCenter;
    ArrayList<Search> searchingCenterObjcts= new ArrayList<Search>();
    ArrayList<String> searchArrayList = new ArrayList<String>();
    String category;
    Long sel_test_ids;
    ArrayList<Test> testArrayList = new ArrayList<Test>();
    ServerResponseTest serverResponseTest;
    Test test;
    //private EditText searchBar;
    private String test_ids="";
    SearchAdapter searchAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recycler_view_search = (RecyclerView)findViewById(R.id.recycler_view_search);


        Search();

        //getTestById();



        recycler_view_search.addOnItemTouchListener(new RecyclerItemClickListener(SearchActivity.this, recycler_view_search, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(SearchActivity.this, "item clicked", Toast.LENGTH_SHORT).show();

                if(searchAdapter.getcatgory(position).equals("DR")) {
                    Intent intent = new Intent(SearchActivity.this, DoctorAppointmentActivity.class);
                    startActivity(intent);
                    finish();
                }else if(searchAdapter.getcatgory(position).equals("DC")){
                    Intent intent = new Intent(SearchActivity.this, DiagnosticCentersActivity.class);
                    startActivity(intent);
                    finish();
                }else if(searchAdapter.getcatgory(position).equals("TE")){
                    Intent intent = new Intent(SearchActivity.this, DiagnosticTestListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

    }


    //search functionality
    private void Search(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        final SearchingCenter centre = new SearchingCenter(HomeFragment.editMobileNo.getText().toString().trim());

        Call<ServerSearchPage> call = apiInterface.getSearch(centre);

        call.enqueue(new Callback<ServerSearchPage>() {
            @Override
            public void onResponse(Call<ServerSearchPage> call, Response<ServerSearchPage> response) {
                diagCenterByTest =(ServerSearchPage) response.body();
                String code = diagCenterByTest.getCode();
                if ("200".equals(code)) {

                    for (int j = 0;j < diagCenterByTest.getSearchResults().size(); j++) {
                        search = diagCenterByTest.getSearchResults().get(j);
                        Log.d("searchdata===", ""+search.getCategory());
                        searchingCenterObjcts.add(search);
                        try{
                            category = search.getCategory();
                            Log.d("categorydata===", category);
                            String name = search.getName();
                            Log.d("NAME==", name);
                            searchArrayList.add(name);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    if (searchArrayList.size()!=0) {
                        searchAdapter = new SearchAdapter(SearchActivity.this, searchArrayList, searchingCenterObjcts,category);
                        recycler_view_search.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler_view_search.getContext());
                        recycler_view_search.addItemDecoration(dividerItemDecoration);
                        recycler_view_search.setAdapter(searchAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerSearchPage> call, Throwable t) {
                Log.e("ERROR==", t.getMessage());
            }
        });
    }


    //getting test data by ID
    private void getTestById(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

       // sel_test_ids = DiagnosticTestListActivity.testid;

        final TestingCenter centre = new TestingCenter("1");


        Call<ServerResponseTest> call = apiInterface.getTestById("abc", centre);

        call.enqueue(new Callback<ServerResponseTest>() {
            @Override
            public void onResponse(Call<ServerResponseTest> call, Response<ServerResponseTest> response) {
                serverResponseTest =(ServerResponseTest) response.body();
                String code = serverResponseTest.getCode();
                if ("200".equals(code)) {
                    for (int i = 0; i < serverResponseTest.getResults().size(); i++) {
                        Test jsonObject = serverResponseTest.getResults().get(i);
                        String testname = jsonObject.getTestname();
                        Log.d("TestName==", testname);
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponseTest> call, Throwable t) {

            }
        });
    }
}
