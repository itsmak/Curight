package com.innovellent.curight.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.innovellent.curight.BuildConfig;
import com.innovellent.curight.R;
import com.innovellent.curight.adapter.AutocompleteAdapter;
import com.innovellent.curight.adapter.Category_SpinnerAdapter;
import com.innovellent.curight.adapter.FoodConsumptionAdapter;
import com.innovellent.curight.adapter.FoodItemSpinnerAdapter;
import com.innovellent.curight.adapter.FoodUnitSpinnerAdapter;
import com.innovellent.curight.adapter.FoodunitAdapter;
import com.innovellent.curight.adapter.SwimmingAdapter;
import com.innovellent.curight.api.ApiClient;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.Calorie;
import com.innovellent.curight.model.CalorieRes;
import com.innovellent.curight.model.Category_Feed;
import com.innovellent.curight.model.Category_List;
import com.innovellent.curight.model.FOOD_ITEM;
import com.innovellent.curight.model.FoodConsumptionModel;
import com.innovellent.curight.model.FoodItem;
import com.innovellent.curight.model.FoodItem_Feed;
import com.innovellent.curight.model.FoodUnit;
import com.innovellent.curight.model.FoodUnit_Feed;
import com.innovellent.curight.model.Food_Units;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.model.ServerResponseCalorie;
import com.innovellent.curight.model.ServerResponseConsumption;
import com.innovellent.curight.model.ServerResponseFoodCategory;
import com.innovellent.curight.model.ServerResponseFoodItem;
import com.innovellent.curight.model.ServerResponseTest;
import com.innovellent.curight.model.Swimming;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.Constants;
import com.innovellent.curight.utility.DividerItemDecoration;
import com.innovellent.curight.utility.SharedPrefService;
import com.innovellent.curight.utility.Util;
import com.github.mikephil.charting.data.Entry;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.innovellent.curight.utility.Constants.ACCESS_TOKEN;
import static com.innovellent.curight.utility.Constants.BREAKFAST;
import static com.innovellent.curight.utility.Constants.DATE;
import static com.innovellent.curight.utility.Constants.DINNER;
import static com.innovellent.curight.utility.Constants.FOOD_ID;
import static com.innovellent.curight.utility.Constants.LUNCH;
import static com.innovellent.curight.utility.Constants.MEAL_TYPE_NAME;
import static com.innovellent.curight.utility.Constants.SERVING_QTY;
import static com.innovellent.curight.utility.Constants.SERVING_UNIT;
import static com.innovellent.curight.utility.Constants.SNACKS;
import static com.innovellent.curight.utility.Constants.TIME;
import static com.innovellent.curight.utility.Constants.TITLE;
import static com.innovellent.curight.utility.Constants.USER_ID;

public class AddFoodConsumptionActivity extends Activity {

    private static final String TAG = "CuRight";
    Category_SpinnerAdapter category_spinneradapter;
    ArrayList<Category_List> categorylist = new ArrayList<Category_List>();
    EditText et_date,et_fooditem,et_unit;
    RecyclerView rv_add_food,rv_add_unit;
    //AutoCompleteTextView foodautocomplete;
    AutocompleteAdapter madapter;
    FoodunitAdapter unit_adapter;
    ArrayAdapter<Category_List> adapter;
    ArrayList<FoodUnit_Feed> foodUnits;
    ArrayList<Category_Feed> result;
    int position;
    PieChart pieChart_food;
    ArrayList<Entry> yvalues;
    ArrayList<String> xVals;
    Retrofit retrofit;
    ArrayList<FoodConsumptionModel> food_arraylist = new ArrayList<FoodConsumptionModel>();
    ArrayList<FOOD_ITEM> food_details = new ArrayList<FOOD_ITEM>();
    String finaldate;
    private Toolbar toolbar;
    private TextView tvTitle,tv_protein,tv_carbs,tv_fat,tv_fiber,tvTotalCal;
    private ImageView ivback1_consumption;
    private JSONArray jsonArray;
    private RecyclerView recyclerView,food_recycler;
    private Spinner itemSpinner, unitSpinner,sp_category;
    private Button btnSubmit, btnAdd;
    private int mYear, mMonth, mDay;
    private EditText etQuantity;
    private ProgressDialog progressDialog;
    private SharedPrefService sharedPrefService;
    private FoodItemSpinnerAdapter foodItemSpinnerAdapter;
    private SwimmingAdapter swimmingAdapter;
    private FoodConsumptionAdapter consumptionAdapter;
    private long userId;
    private String accessToken, title, time, quantity;
    private ArrayList<Swimming> foodConsumptions = new ArrayList<>();
    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Toast.makeText(AddFoodConsumptionActivity.this,
                            "Clicked item from auto completion list "
                                    + adapterView.getItemAtPosition(i)
                            , Toast.LENGTH_SHORT).show();
                }
            };

    public AddFoodConsumptionActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
     //   setContentView(R.layout.activity_add_food);

        initReferences();
        setupToolbar();
        initClickListeners();

        pieChart_food.setUsePercentValues(true);
        sharedPrefService = SharedPrefService.getInstance();
        userId = sharedPrefService.getLong(USER_ID);
        accessToken = sharedPrefService.getString(ACCESS_TOKEN);
        String month,day;
        final Calendar c = Calendar.getInstance();
        int monthnumbr = c.get(Calendar.MONTH)+1;
        int daynumber = c.get(Calendar.DATE);

        if (monthnumbr >= 1 && monthnumbr <= 9) {
            month = "0" + monthnumbr;
        } else {
            month = monthnumbr + "";
        }
        if (daynumber >= 1 && daynumber <= 9) {
            day = "0" + daynumber;
        } else {
            day = daynumber + "";
        }
        finaldate = c.get(Calendar.YEAR) +"-"+month+"-" +day;
        et_date.setText(finaldate);
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate(et_date);
            }
        });
        getallfoodSpinnerdata();
//        etQuantity.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(editable.length()>0)
//                {
//                    if(et_unit.getText().toString().equalsIgnoreCase(""))
//                    {
//                        Toast.makeText(AddFoodConsumptionActivity.this,"Unit Cannot be Blank",Toast.LENGTH_SHORT).show();
//                    }else {
//                        getcaloriesbyid_apicall();
//                    }
//                }
//
//            }
//        });
       // getFoodItems();
//        getfooditemspinner();
        et_fooditem.setClickable(true);
        et_fooditem.clearFocus();

        et_fooditem.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0)
                {
                    rv_add_food.setVisibility(View.VISIBLE);
                    filter(editable.toString());
                }else if(editable.length()==0)
                {
                    rv_add_food.setVisibility(View.GONE);
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {
            }
        });

        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0)
                {
                    if(et_fooditem.getText().toString().trim().equals(""))
                    {
                        et_fooditem.requestFocus();
                        et_fooditem.setError("Required");
                    }else if(et_unit.getText().toString().trim().equals(""))
                    {
                        et_unit.requestFocus();
                        et_unit.setError("Required");
                    }else {
                        int foodid = Prefs.getInt("FOODID",0);
                        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.hideSoftInputFromWindow(etQuantity.getWindowToken(), 0);
                        getCalories(foodid, editable.toString());

                    }
                }
            }
        });


    }

    private void filter(String text){

        ArrayList<Category_Feed> filteredlist = new ArrayList<>();
        for(Category_Feed item : result){
            if(item.getFoodName().toLowerCase().contains(text.toLowerCase()))
            {
                filteredlist.add(item);
            }
        }
        madapter.filterlist(filteredlist);
    }

    private void getfooditemspinner(Long catid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Log.d(TAG,"getfooditem id"+catid);
        Call<ServerResponseFoodItem> call = apiInterface.getfooditem(catid);
        call.enqueue(new Callback<ServerResponseFoodItem>() {
            @Override
            public void onResponse(Call<ServerResponseFoodItem> call, Response<ServerResponseFoodItem> response) {
                Log.d(TAG, "getfooditem body:" + response.body());
                if (response.body() != null) {
                    Log.d(TAG, "getfooditem code: " + response.body().getCode());

                    ArrayList<FoodItem_Feed> foodItems = response.body().getResults();
                    if (foodItems.size() > 0) {
                        foodItemSpinnerAdapter = new FoodItemSpinnerAdapter(AddFoodConsumptionActivity.this, foodItems);
                        itemSpinner.setAdapter(foodItemSpinnerAdapter);
                        itemSpinner.setSelection(0);
                        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                resetFoodUnitSpinnerAdapter();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                        resetFoodUnitSpinnerAdapter();
                    }

                }
            }

            @Override
            public void onFailure(Call<ServerResponseFoodItem> call, Throwable t) {

            }
        });
    }

    private void getallfoodSpinnerdata() {
        categorylist.clear();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ServerResponseFoodCategory> call = apiInterface.getcategory();
        call.enqueue(new Callback<ServerResponseFoodCategory>() {
            @Override
            public void onResponse(Call<ServerResponseFoodCategory> call, Response<ServerResponseFoodCategory> response) {
                if (response.body() != null) {
                    Log.d(TAG, "gettestcentre " + response.body().getCode());
                    if(response.body().getCode()==200){
                        result = response.body().getResults();
                        Log.d(TAG,"food name size: "+result.size());
                        for (int i = 0; i < result.size(); i++){
//                            foodUnits = result.get(i).getFoodUnits();
//                            Log.d(TAG,"food unit size: "+foodUnits.size());
//                            for(int j = 0; j<foodUnits.size();j++)
//                            {
//                                Log.d(TAG,"food name : "+result.get(i).getFoodName());
//                                Log.d(TAG,"food id : "+foodUnits.get(j).getFoodid());
//                              //  categorylist.add(new Category_List(result.get(i).getFoodName(),foodUnits.get(j).getFoodid(),foodUnits.get(j).getUnit(),foodUnits.get(j).getCarbs(),foodUnits.get(j).getProtein(),foodUnits.get(j).getFat(),foodUnits.get(j).getFiber(),foodUnits.get(j).getCalories()));
//                            }
                             // categorylist.add(new Category_List(result.get(i).getFoodcategoryid(),result.get(i).getCategoryname()));
                        }
                        madapter = new AutocompleteAdapter(AddFoodConsumptionActivity.this, result, position, new AutocompleteAdapter.OnFoodClickListener() {
                            @Override
                            public void onfoodnameselected(Category_Feed item_m, int position) {

                                et_fooditem.setText(item_m.getFoodName());
                                Prefs.putString("FOODNAME",item_m.getFoodName());
                                ArrayList<FoodUnit_Feed> foodUnits = item_m.getFoodUnits();
                                if(foodUnits.size()==0)
                                {
                                    Toast.makeText(AddFoodConsumptionActivity.this,"No unit available for this Food. Select Another",Toast.LENGTH_SHORT);
                                }else if(foodUnits.size()==1)
                                {
                                    et_unit.setText(foodUnits.get(0).getUnit());
                                    Prefs.putInt("FOODID",foodUnits.get(0).getFoodid());
                                    rv_add_unit.setVisibility(View.GONE);
                                    yvalues = new ArrayList<Entry>();
                                    yvalues.add(new Entry(foodUnits.get(0).getCarbs(), 0));
                                    yvalues.add(new Entry(foodUnits.get(0).getProtein(), 1));
                                    yvalues.add(new Entry(foodUnits.get(0).getFat(), 2));
                                    yvalues.add(new Entry(foodUnits.get(0).getFiber(), 3));
                                  //  yvalues.add(new Entry(foodUnits.get(0).getCalories(), 4));

                                    PieDataSet dataSet = new PieDataSet(yvalues, "Calorie Results");
                                    xVals = new ArrayList<String>();

                                    xVals.add("Carbs");
                                    xVals.add("Protien");
                                    xVals.add("Fat");
                                    xVals.add("Fiber");
                                   // xVals.add("Calorie");

                                    PieData data = new PieData(xVals, dataSet);
                                    // In percentage Term
                                    data.setValueFormatter(new PercentFormatter());
                                    // Default value
                                    //data.setValueFormatter(new DefaultValueFormatter(0));
                                    dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                                    dataSet.setValueTextSize(10f);
                                    dataSet.setValueTextColor(Color.DKGRAY);
                                    pieChart_food.setDrawHoleEnabled(false);
                                    pieChart_food.setRotationAngle(0);
                                    pieChart_food.setRotationEnabled(true);
                                    pieChart_food.setCenterTextSize(8f);
                                    pieChart_food.setData(data);

                                    Prefs.putString("FOODUNIT",foodUnits.get(0).getUnit());
                                    Prefs.putInt("FOODID",foodUnits.get(0).getFoodid());
                                    Log.d(TAG,"consumption calory::"+foodUnits.get(0).getCalories());
                                    Prefs.putInt("FOODCALORY",foodUnits.get(0).getCalories());
                                    tv_protein.setText("Protien : "+String.valueOf(foodUnits.get(0).getProtein()));
                                    tv_carbs.setText("Carbs : "+String.valueOf(foodUnits.get(0).getCarbs()));
                                    tv_fat.setText("Fat : "+String.valueOf(foodUnits.get(0).getFat()));
                                    tv_fiber.setText("Fiber : "+String.valueOf(foodUnits.get(0).getFiber()));
                                    tvTotalCal.setText("Total Calories : "+String.valueOf(foodUnits.get(0).getCalories()));
                                }else {

                                    final Dialog dialog = new Dialog(AddFoodConsumptionActivity.this);
                                    dialog.setCancelable(true);
                                    dialog.setContentView(R.layout.dialog_add_foodunit);
                                    food_recycler = (RecyclerView) dialog.findViewById(R.id.recycler_view_foodunit);
                                    dialog.setCancelable(false);
                                    unit_adapter = new FoodunitAdapter(AddFoodConsumptionActivity.this, foodUnits, position, new FoodunitAdapter.OnFoodunitClickListener() {
                                        @Override
                                        public void onfoodunitselect(FoodUnit_Feed item_f, int position) {

                                            et_unit.setText(item_f.getUnit());
                                            Prefs.putString("FOODUNIT",item_f.getUnit());
                                            rv_add_unit.setVisibility(View.GONE);
                                            yvalues = new ArrayList<Entry>();
                                            yvalues.add(new Entry(item_f.getCarbs(), 0));
                                            yvalues.add(new Entry(item_f.getProtein(), 1));
                                            yvalues.add(new Entry(item_f.getFat(), 2));
                                            yvalues.add(new Entry(item_f.getFiber(), 3));
// yvalues.add(new Entry(item_f.getCalories(), 4));
                                            PieDataSet dataSet = new PieDataSet(yvalues, "Calorie Results");
                                            xVals = new ArrayList<String>();
                                            xVals.add("Carbs");
                                            xVals.add("Protien");
                                            xVals.add("Fat");
                                            xVals.add("Fiber");
// xVals.add("Calorie");
                                            PieData data = new PieData(xVals, dataSet);
// In percentage Term
                                            data.setValueFormatter(new PercentFormatter());
// Default value
//data.setValueFormatter(new DefaultValueFormatter(0));
                                            dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                                            dataSet.setValueTextSize(10f);
                                            dataSet.setValueTextColor(Color.DKGRAY);
                                            pieChart_food.setDrawHoleEnabled(false);
                                            pieChart_food.setRotationAngle(0);
                                            pieChart_food.setRotationEnabled(true);
                                            pieChart_food.setCenterTextSize(8f);
                                            pieChart_food.setData(data);
                                            Prefs.putInt("FOODID",item_f.getFoodid());
                                            Prefs.putInt("FOODCALORY",item_f.getCalories());
                                            tv_protein.setText("Protien : "+String.valueOf(item_f.getProtein()));
                                            tv_carbs.setText("Carbs : "+String.valueOf(item_f.getCarbs()));
                                            tv_fat.setText("Fat : "+String.valueOf(item_f.getFat()));
                                            tv_fiber.setText("Fiber : "+String.valueOf(item_f.getFiber()));
                                            tvTotalCal.setText("Total Calories : "+String.valueOf(item_f.getCalories()));

                                            dialog.dismiss();
                                        }
                                    });

                                    food_recycler.setItemAnimator(new DefaultItemAnimator());
                                    food_recycler.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(AddFoodConsumptionActivity.this, LinearLayoutManager.VERTICAL));
                                    food_recycler.setLayoutManager(new LinearLayoutManager(AddFoodConsumptionActivity.this, LinearLayoutManager.VERTICAL, false));
                                    food_recycler.setAdapter(unit_adapter);
                                    Window dialogWindow = dialog.getWindow();
                                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                                    dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

                                    dialogWindow.setAttributes(lp);
                                    dialog.show();
//                                    rv_add_unit.setVisibility(View.VISIBLE);
//                                    unit_adapter = new FoodunitAdapter(AddFoodConsumptionActivity.this, foodUnits, position, new FoodunitAdapter.OnFoodunitClickListener() {
//                                        @Override
//                                        public void onfoodunitselect(FoodUnit_Feed item_f, int position) {
//
//                                            et_unit.setText(item_f.getUnit());
//                                            Prefs.putString("FOODUNIT",item_f.getUnit());
//                                            rv_add_unit.setVisibility(View.GONE);
//                                            yvalues = new ArrayList<Entry>();
//                                            yvalues.add(new Entry(item_f.getCarbs(), 0));
//                                            yvalues.add(new Entry(item_f.getProtein(), 1));
//                                            yvalues.add(new Entry(item_f.getFat(), 2));
//                                            yvalues.add(new Entry(item_f.getFiber(), 3));
//                                           // yvalues.add(new Entry(item_f.getCalories(), 4));
//
//                                            PieDataSet dataSet = new PieDataSet(yvalues, "Calorie Results");
//                                            xVals = new ArrayList<String>();
//
//                                            xVals.add("Carbs");
//                                            xVals.add("Protien");
//                                            xVals.add("Fat");
//                                            xVals.add("Fiber");
//                                           // xVals.add("Calorie");
//
//                                            PieData data = new PieData(xVals, dataSet);
//                                            // In percentage Term
//                                            data.setValueFormatter(new PercentFormatter());
//                                            // Default value
//                                            //data.setValueFormatter(new DefaultValueFormatter(0));
//                                            dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
//                                            dataSet.setValueTextSize(10f);
//                                            dataSet.setValueTextColor(Color.DKGRAY);
//                                            pieChart_food.setDrawHoleEnabled(false);
//                                            pieChart_food.setRotationAngle(0);
//                                            pieChart_food.setRotationEnabled(true);
//                                            pieChart_food.setCenterTextSize(8f);
//                                            pieChart_food.setData(data);
//                                            Prefs.putInt("FOODID",item_f.getFoodid());
//                                            Prefs.putInt("FOODCALORY",item_f.getCalories());
//                                            tv_protein.setText("Protien : "+String.valueOf(item_f.getProtein()));
//                                            tv_carbs.setText("Carbs : "+String.valueOf(item_f.getCarbs()));
//                                            tv_fat.setText("Fat : "+String.valueOf(item_f.getFat()));
//                                            tv_fiber.setText("Fiber : "+String.valueOf(item_f.getFiber()));
//                                            tvTotalCal.setText("Total Calories : "+String.valueOf(item_f.getCalories()));
//                                        }
//                                    });
//                                    rv_add_unit.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
//                                    rv_add_unit.setAdapter(unit_adapter);
                                }

                                rv_add_food.setVisibility(View.GONE);

                            }
                        });
                        rv_add_food.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        rv_add_food.setAdapter(madapter);
//                        madapter = new AutocompleteAdapter(AddFoodConsumptionActivity.this, categorylist);
//                        foodautocomplete.setAdapter(adapter);

//                        final AutoCompleteTextView text = (AutoCompleteTextView)
//                                findViewById(R.id.autocomplete_food);
//                        final List<String> list = new ArrayList<String>();
//                        Log.d(TAG,"foodname : size="+categorylist.size());
//                        for(int i=0;i<categorylist.size();i++)
//                        {
//                            Log.d(TAG,"foodname : item="+categorylist.get(i).getFoodName());
//                            list.add(categorylist.get(i).getFoodName());
//                        }
//
//                        Collections.sort(list);
//
//                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
//                                (getApplicationContext(), android.R.layout.simple_spinner_item, list);
//
//                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        text.setThreshold(1);
//                        text.setAdapter(dataAdapter);
//
//                        text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                            @Override
//                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                                // TODO Auto-generated method stub
//                                Toast.makeText(getBaseContext(), categorylist.get(arg2).getFoodName(),
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        });



                    }else {

                    }
                }else {

                }
            }
            public void onFailure(Call<ServerResponseFoodCategory> call, Throwable t) {
                t.getMessage();
                String message = t.getMessage();
                Log.e("TAG","error :: "+message);
                if (!isFinishing()) {
                    if (Constants.SERVER_DOWN.equals(message)) {
                        Util.showAlertDialog(AddFoodConsumptionActivity.this, "Server is Down! Please try  again later!", "ERROR");
                        return;
                    } else {
                        Util.showAlertDialog(AddFoodConsumptionActivity.this, message, "ERROR");
                        return;
                    }
                }


            }
        });
    }

    private void setspinneradpter() {
        category_spinneradapter = new Category_SpinnerAdapter(getApplicationContext(),categorylist);
        sp_category.setAdapter(category_spinneradapter);
        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            //    getfooditemspinner(categorylist.get(i).getFoodcategoryid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
             //   getfooditemspinner(categorylist.get(0).getFoodcategoryid());
            }
        });
    }

    private void initReferences() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.title);
        ivback1_consumption = (ImageView) findViewById(R.id.ivback1_consumption);
        tvTotalCal = (TextView) findViewById(R.id.tvTotalCal);
        et_unit = (EditText) findViewById(R.id.et_unit);
        et_date = (EditText) findViewById(R.id.et_date);
        et_fooditem = (EditText) findViewById(R.id.et_fooditem);
        rv_add_food = (RecyclerView) findViewById(R.id.rv_add_food);
        rv_add_unit = (RecyclerView) findViewById(R.id.rv_add_unit);
        pieChart_food = (PieChart) findViewById(R.id.piechart_food);
        tv_protein = (TextView) findViewById(R.id.tv_protein);
        tv_carbs = (TextView) findViewById(R.id.tv_carbs);
        tv_fat = (TextView) findViewById(R.id.tv_fat);
        tv_fiber = (TextView) findViewById(R.id.tv_fiber);
//        tv_calories = (TextView) findViewById(R.id.tv_calories);
 //       foodautocomplete = (AutoCompleteTextView) findViewById(R.id.autocomplete_food);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        countrySearch = (AutoCompleteTextView) findViewById(R.id.myautocomplete_fooditem);
//        madapter = new AutocompleteAdapter(this,categorylist);
     //   sp_category = (Spinner) findViewById(R.id.sp_fooditem);
     //   itemSpinner = (Spinner) findViewById(R.id.spItem);
        unitSpinner = (Spinner) findViewById(R.id.spUnit);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        etQuantity = (EditText) findViewById(R.id.et_quantity);
   //     foodautocomplete.setOnItemClickListener(onItemClickListener);
    }

    private void setupToolbar() {

        title = getIntent().getStringExtra(TITLE);
        if (title != null) {
            switch (title) {
                case BREAKFAST:
                    time = "10:30AM";
                    break;
                case LUNCH:
                    time = "1:30PM";
                    break;
                case SNACKS:
                    time = "5:30PM";
                    break;
                case DINNER:
                    time = "8:30PM";
                    break;
                default:
                    time = "";
                    break;
            }
        } else title = "Curight";
        tvTitle.setText(title);
    }

    private void initClickListeners() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etQuantity.getText().toString().trim().equals(""))
                {
                    etQuantity.requestFocus();
                    etQuantity.setError("Quantity Required");
                }else if(et_date.getText().toString().trim().equals("")){
                    Toast.makeText(AddFoodConsumptionActivity.this,"Please Enter Date",Toast.LENGTH_SHORT).show();
                }else {
                    String foodname=Prefs.getString("FOODNAME","");
                    //String unit=Prefs.getString("FOODUNIT","");

                  //  addFoodConsumption(foodname, ((Food_Units) unitSpinner.getSelectedItem()));

                 //   addFoodConsumption(foodname);
                    add_foodconsumptiontoarray();
                }
            }
        });
        ivback1_consumption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hm = new Intent(AddFoodConsumptionActivity.this,HomeActivity.class);
                hm.putExtra("source", "consumption");
                startActivity(hm);
            }
        });
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!(quantity = etQuantity.getText().toString().trim()).isEmpty())
//                    //addFoodConsumption(((FoodItem) itemSpinner.getSelectedItem()).getName(), ((FoodUnit) unitSpinner.getSelectedItem()));
//                addFoodConsumption(((FoodItem_Feed) itemSpinner.getSelectedItem()).getFoodName(), ((Food_Units) unitSpinner.getSelectedItem()));
//                else
//                    Toast.makeText(AddFoodConsumptionActivity.this, "Please enter valid quantity", Toast.LENGTH_SHORT).show();
//            }
//        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(food_arraylist.size()==0)
               {
                   Toast.makeText(AddFoodConsumptionActivity.this,"Please add Food before Saving",Toast.LENGTH_SHORT).show();
               }else {
                   send_consumptionlist();
               }


            }
        });
    }
    private void send_consumptionlist() {
        progressDialog = ProgressDialog.show(AddFoodConsumptionActivity.this, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        try {
            //  jsonArray = new JSONArray("[{\"classid\":1,\"completedby\":\"2018-01-20\",\"hashtag\":\"\",\"homeworktext\":\"jjerigo3rg ibuhrejd viuev iuigre\",\"teacherid\":2}]");
            Gson gson = new GsonBuilder().create();
            JsonArray myCustomArray = gson.toJsonTree(food_arraylist).getAsJsonArray();
            String string_array=String.valueOf(myCustomArray);
            Log.d(TAG,"arraylist full:"+string_array);
            jsonArray = new JSONArray(string_array);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClient.addInterceptor(logging);
        }
        OkHttpClient client = httpClient.build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(new Config().SERVER_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        Call<ServerResponseConsumption> serverResponseCall = retrofit.create(ApiInterface.class).saveConsumption(jsonArray.toString());
        serverResponseCall.enqueue(new Callback<ServerResponseConsumption>() {
            @Override
            public void onResponse(Call<ServerResponseConsumption> call, retrofit2.Response<ServerResponseConsumption> response) {
                progressDialog.dismiss();
                Log.d(TAG,"arrayresponse success:"+response);
                if(response.body() !=null)
                {
                    progressDialog.dismiss();
                    if(response.body().getResults().equalsIgnoreCase("S"))
                    {
                        Toast.makeText(AddFoodConsumptionActivity.this,"Food Consumption Successfully saved",Toast.LENGTH_SHORT).show();
                        food_arraylist.clear();
                        food_details.clear();
                        consumptionAdapter.notifyDataSetChanged();
                        Intent hm = new Intent(AddFoodConsumptionActivity.this,HomeActivity.class);
                        hm.putExtra("source", "consumption");
                        startActivity(hm);
                    }else {
                        progressDialog.dismiss();
                        Log.d(TAG,"arrayresponse:"+response.body());
                    }

                }


            }

            @Override
            public void onFailure(Call<ServerResponseConsumption> call, Throwable t) {
                progressDialog.dismiss();
                Log.d(TAG,"arrayresponse failure :"+t.toString());
            }
        });


    }
    private void add_foodconsumptiontoarray() {

        //String s_userid = String.valueOf(Prefs.getLong("user_id",0));
        String selected_date = et_date.getText().toString().trim();
        String food_id = String.valueOf(Prefs.getInt("FOODID",0));
        String meal_type = title;
        String serving_qnty = etQuantity.getText().toString().trim();
        String serving_unit = et_unit.getText().toString().trim();
        String serving_time = time;
      //  Log.d(TAG,"consumption_userid"+s_userid);
        Log.d(TAG,"consumption_date"+selected_date);
        Log.d(TAG,"consumption_foodid"+food_id);
        Log.d(TAG,"consumption_mealtype"+meal_type);
        Log.d(TAG,"consumption_qntity"+serving_qnty);
        Log.d(TAG,"consumption_unit"+serving_unit);
        Log.d(TAG,"consumption_time"+serving_time);
        int s_uid = (int) Prefs.getLong("spinner_id",0);
        int uid;
        if(s_uid==0)
        {
            uid = (int) Prefs.getLong("user_id",0);
        }else {
            uid = s_uid;
        }
        String s_userid = String.valueOf(uid);
        food_arraylist.add(new FoodConsumptionModel(s_userid,selected_date,food_id,meal_type,serving_qnty,serving_unit,serving_time));
        String selected_item = et_fooditem.getText().toString().trim();
        String selected_quantity = etQuantity.getText().toString().trim();
        String selected_unit = et_unit.getText().toString().trim();
        int calorie = Prefs.getInt("FOODCALORY",0);
        Log.d(TAG,"consumption calory::"+calorie);
        Log.d(TAG,"consumption quantity::"+selected_quantity);
        int selected_cal = calorie/(Integer.parseInt(selected_quantity));
        Log.d(TAG,"consumption calryperq::"+selected_cal);
        food_details.add(new FOOD_ITEM(selected_item,selected_quantity,selected_unit,String.valueOf(selected_cal)));
        consumptionAdapter = new FoodConsumptionAdapter(AddFoodConsumptionActivity.this, R.layout.list_row_food, food_details);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddFoodConsumptionActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(consumptionAdapter);
        clear_page_data();
    }

    private void clear_page_data() {
        et_fooditem.setText("");
        et_unit.setText("");
        etQuantity.setText("");
        et_date.setText("");
        yvalues = new ArrayList<Entry>();

        //  yvalues.add(new Entry(foodUnits.get(0).getCalories(), 4));

        PieDataSet dataSet = new PieDataSet(yvalues, "Calorie Results");
        xVals = new ArrayList<String>();


        // xVals.add("Calorie");

        PieData data = new PieData(xVals, dataSet);
        // In percentage Term
        data.setValueFormatter(new PercentFormatter());
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataSet.setValueTextSize(10f);
        dataSet.setValueTextColor(Color.DKGRAY);
        pieChart_food.setDrawHoleEnabled(false);
        pieChart_food.setRotationAngle(0);
        pieChart_food.setRotationEnabled(true);
        pieChart_food.setCenterTextSize(8f);
        pieChart_food.setData(data);

        tv_protein.setText("Protien : ");
        tv_carbs.setText("Carbs : ");
        tv_fat.setText("Fat : ");
        tv_fiber.setText("Fiber : ");
        tvTotalCal.setText("Total Calories : ");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public void getFoodItems() {
//        Log.d(TAG,"getFoodItemcalled");
//        progressDialog = ProgressDialog.show(AddFoodConsumptionActivity.this, "Loading", "please wait", false);
//        progressDialog.show();
//
//        ApiInterface client = ApiClient.getClient();
//        Call<ServerResponse<List<FoodItem>>> call = client.getAllFoodItem(accessToken);
//
//        call.enqueue(new Callback<ServerResponse<List<FoodItem>>>() {
//            @Override
//            public void onResponse(Call<ServerResponse<List<FoodItem>>> call, Response<ServerResponse<List<FoodItem>>> response) {
//                Log.d(TAG,"getFoodItem response"+getBaseContext());
//                if (getBaseContext() != null) {
//                    progressDialog.dismiss();
//                    if (response.isSuccessful()) {
//                        List<FoodItem> foodItems = response.body().getResults();
//                        if (foodItems.size() > 0) {
//                            foodItemSpinnerAdapter = new FoodItemSpinnerAdapter(AddFoodConsumptionActivity.this, foodItems);
//                            itemSpinner.setAdapter(foodItemSpinnerAdapter);
//                            itemSpinner.setSelection(0);
//                            itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                    resetFoodUnitSpinnerAdapter();
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> adapterView) {
//                                }
//                            });
//                            resetFoodUnitSpinnerAdapter();
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse<List<FoodItem>>> call, Throwable t) {
//                if (getBaseContext() != null) progressDialog.dismiss();
//            }
//        });
//
//    }

    private void resetFoodUnitSpinnerAdapter() {
        if (itemSpinner.getSelectedItem() != null) {

           // ArrayList<FoodUnit> foodUnits = ((FoodItem) itemSpinner.getSelectedItem()).getUnits();
            ArrayList<Food_Units> foodUnits = ((FoodItem_Feed) itemSpinner.getSelectedItem()).getResults();
            if (foodUnits.size() > 0) {
                FoodUnitSpinnerAdapter foodUnitSpinnerAdapter = new FoodUnitSpinnerAdapter(AddFoodConsumptionActivity.this, foodUnits);
                unitSpinner.setAdapter(foodUnitSpinnerAdapter);
                unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (!(quantity = etQuantity.getText().toString().trim()).isEmpty())
                            getCalories(id, quantity);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }
    }

    public void selectDate(final EditText setedttxt) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddFoodConsumptionActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        GregorianCalendar GregorianCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth - 1);

                        int dayOfWeek = GregorianCalendar.get(GregorianCalendar.DAY_OF_WEEK);

                        String day = "", monthYear = "";
                        int month = monthOfYear + 1;
                        if (dayOfMonth >= 1 && dayOfMonth <= 9) {
                            day = "0" + dayOfMonth;
                        } else {
                            day = dayOfMonth + "";
                        }
                        if (month >= 1 && month <= 9) {
                            monthYear = "0" + month;
                        } else {
                            monthYear = month + "";
                        }

                        String date = year + "-" + monthYear + "-" + day;
                        setedttxt.setText(year + "-" + monthYear + "-" + day);


                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void getCalories(long food_id, String quantity) {
        progressDialog = ProgressDialog.show(AddFoodConsumptionActivity.this, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        ApiInterface client = ApiClient.getClient();
        Log.d(TAG,"Calories foodid:"+food_id);
        Log.d(TAG,"Calories foodquantity:"+quantity);

        Calorie cal = new Calorie(food_id + "", quantity);

        Call<ServerResponseCalorie> call = client.getCalories(accessToken, cal);

        call.enqueue(new Callback<ServerResponseCalorie>() {
            @Override
            public void onResponse(Call<ServerResponseCalorie> call, Response<ServerResponseCalorie> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Log.e(TAG, "Calories Response ::  " + response.code() + "   message ::  " + response.message());
                    ArrayList<CalorieRes> result = response.body().getResults();
                    yvalues = new ArrayList<Entry>();
                    yvalues.add(new Entry(result.get(0).getCarbs(), 0));
                    yvalues.add(new Entry(result.get(0).getProtein(), 1));
                    yvalues.add(new Entry(result.get(0).getFat(), 2));
                    yvalues.add(new Entry(result.get(0).getFiber(), 3));
                   // yvalues.add(new Entry(result.get(0).getCalories(), 4));

                    PieDataSet dataSet = new PieDataSet(yvalues, "Calorie Results");
                    xVals = new ArrayList<String>();

                    xVals.add("Carbs");
                    xVals.add("Protien");
                    xVals.add("Fat");
                    xVals.add("Fiber");
                  //  xVals.add("Calorie");

                    PieData data = new PieData(xVals, dataSet);
                    // In percentage Term
                    data.setValueFormatter(new PercentFormatter());
                    // Default value
                    //data.setValueFormatter(new DefaultValueFormatter(0));
                    dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                    dataSet.setValueTextSize(10f);
                    dataSet.setValueTextColor(Color.DKGRAY);
                    pieChart_food.setDrawHoleEnabled(false);
                    pieChart_food.setRotationAngle(0);
                    pieChart_food.setRotationEnabled(true);
                    pieChart_food.setCenterTextSize(8f);
                    pieChart_food.setData(data);
                    pieChart_food.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    tv_protein.setText("Protien : "+String.valueOf(result.get(0).getProtein()));
                    tv_carbs.setText("Carbs : "+String.valueOf(result.get(0).getCarbs()));
                    tv_fat.setText("Fat : "+String.valueOf(result.get(0).getFat()));
                    tv_fiber.setText("Fiber : "+String.valueOf(result.get(0).getFiber()));
                    Prefs.putInt("FOODCALORY",result.get(0).getCalories());
                    tvTotalCal.setText("Total Calories : "+String.valueOf(result.get(0).getCalories()));

                }
            }

            @Override
            public void onFailure(Call<ServerResponseCalorie> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


    }
// public void addFoodConsumption(final String foodItem, final Food_Units foodUnit)
//    public void addFoodConsumption(final String foodItem) {
//
//        progressDialog = ProgressDialog.show(AddFoodConsumptionActivity.this, "Adding", "please wait", false);
//        progressDialog.show();
//        final int uid = (int) Prefs.getLong("user_id",0);
//        int foodid = Prefs.getInt("FOODID",0);
//        quantity = etQuantity.getText().toString();
//        ApiInterface client = ApiClient.getClient();
//        final String foodunit = Prefs.getString("FOODUNIT","");
//        try {
//            JSONObject paramObject = new JSONObject();
//            paramObject.put(USER_ID, uid);
//            paramObject.put(DATE, new SimpleDateFormat(getString(R.string.date_format), Locale.ENGLISH).format(Calendar.getInstance().getTime()));
//            paramObject.put(FOOD_ID, foodid);
//            paramObject.put(MEAL_TYPE_NAME, title);
//            paramObject.put(SERVING_QTY, quantity);
//           // paramObject.put(SERVING_UNIT, foodUnit.getUnit());
//            paramObject.put(SERVING_UNIT, foodunit);
//            paramObject.put(TIME, time);
//
//            Call<ServerResponse<String>> call = client.createFood(accessToken, paramObject.toString());
//            call.enqueue(new Callback<ServerResponse<String>>() {
//                @Override
//                public void onResponse(Call<ServerResponse<String>> call, Response<ServerResponse<String>> response) {
//                    if (getBaseContext() != null) {
//                        progressDialog.dismiss();
//                        if (response.isSuccessful()) {
//                            foodConsumptions.add(new Swimming(foodItem, String.valueOf(uid), foodunit));
//                            swimmingAdapter = new SwimmingAdapter(AddFoodConsumptionActivity.this, R.layout.list_row_food, foodConsumptions);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(AddFoodConsumptionActivity.this, LinearLayoutManager.VERTICAL, false));
//                            recyclerView.setAdapter(swimmingAdapter);
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ServerResponse<String>> call, Throwable t) {
//                    if (getBaseContext() != null) progressDialog.dismiss();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            progressDialog.dismiss();
//        }
//    }

    @Override
    public void onBackPressed() {
        Intent hm = new Intent(AddFoodConsumptionActivity.this,HomeActivity.class);
        hm.putExtra("source", "consumption");
        startActivity(hm);

    //    super.onBackPressed();
    }
}
